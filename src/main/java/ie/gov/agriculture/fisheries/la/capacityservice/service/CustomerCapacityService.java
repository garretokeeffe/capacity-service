package ie.gov.agriculture.fisheries.la.capacityservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ie.gov.agriculture.fisheries.la.capacityservice.dto.AllCapacityDTO;
import ie.gov.agriculture.fisheries.la.capacityservice.dto.CapacityDTO;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.Capacity;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.CapacityDetail;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.IfisWrapper;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.PenaltyPoints;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.VesselSummary;
import ie.gov.agriculture.fisheries.la.capacityservice.exception.CustomerCapacityException;
import ie.gov.agriculture.fisheries.la.capacityservice.exception.ResourceNotFoundException;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CCSRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CapacityPenaltyPointsRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CapacityRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CustomerCapacityDetailRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CustomerCapacityRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.VesselSummaryRepository;

/** https://confluence.agriculture.gov.ie/confluence/display/FISHERIES/Get+Capacity **/

@Component
@Service
public class CustomerCapacityService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerCapacityService.class);
	private static final String CUSTOMER_CAP_ERROR_MESSAGE = "No capacity detail found for capacity account: {}";
	private static final String PEN_POINTS_ERROR_MESSAGE = "No penality points found for capacity account: {}";
	private static final String VESSEL_SUM_ERROR_MESSAGE = "Vessel summary information not found for capacity account: {}";
	private static final String CAP_ACCOUNT_ERROR_MESSAGE = "Error processing customer capacity detail for capacity account: ";

	@Autowired
	CustomerCapacityRepository customerCapacityRepository;
	
	@Autowired
	CapacityRepository capacityRepository;
	
	@Autowired
	CustomerCapacityDetailRepository customerCapacityDetailRepository;
	
	@Autowired
	VesselSummaryRepository vesselSummaryRepository;
	
	@Autowired
	CapacityPenaltyPointsRepository capacityPenaltyPointsRepository;
	
	@Autowired
	CCSRepository cCSRepository;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Value("${database.role:151}")
	private Integer databaseRole;
	
	/***
	 * public AllCapacityDTO getAllCapacity (String customerId, boolean convertCcsToIfisID) throws ResourceNotFoundException;
	 * @param customerId
	 * @param convertCcsToIfisID
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public AllCapacityDTO getAllCapacity (String customerId, boolean convertCcsToIfisID) throws Exception {
		LOGGER.debug("CustomerCapacityService.getCapacity:{}", customerId);
		
		AllCapacityDTO allCapacityDTO = null;
		
		String ifisCustomerId = (convertCcsToIfisID ? this.getIfisIdByCcsId(customerId) : customerId);
		
		allCapacityDTO = new AllCapacityDTO();
		List<CapacityDTO> capacityDTO = new ArrayList<>();
		
		List<Capacity> capacity = capacityRepository.findCapacityByOwnerId(ifisCustomerId); // Retrieve high level capacity information ...
		
		if (CollectionUtils.isEmpty(capacity)) {
			LOGGER.info("No capacity information found for customer: {}", customerId);
			throw new ResourceNotFoundException("No capacity information found for customer:" + customerId);
		}
		
		// Retrieve capacity detail items ...
		// ... Note - always calling the ASync method, Sync methods retained (but commented out) as fall back if required ...
		capacity.forEach(item -> this.getCapacityDetailItems_ASync(item));
		
		// Convert capacity items to DTO ...
		capacity.forEach(item -> capacityDTO.add(this.convertCapacityToDTO(item)));
		
		allCapacityDTO.setOwnerId(ifisCustomerId);
		
		// Set Off-Register capacity ...
		allCapacityDTO.setOffRegister (
			capacityDTO.stream().filter(s -> s.isOffRegister()).collect(Collectors.toList())
		);
		
		// Set On-Register capacity ...
		allCapacityDTO.setOnRegister (
			capacityDTO.stream().filter(s -> !s.isOffRegister()).collect(Collectors.toList())
		);
		
		return allCapacityDTO;
	}
	
	/***
	 * private void getCapacityDetailItems_ASync(Capacity capacity) throws ResourceNotFoundException;
	 * @param capacity
	 * @throws ResourceNotFoundException
	 * Async function to run call in parallel
	 */
	private void getCapacityDetailItems_ASync(Capacity capacity) throws CustomerCapacityException {
		LOGGER.debug("CustomerCapacityService.getCapacityDetailItems_ASync: {}", capacity.getCapAccountId());
		
		final long startTime = System.currentTimeMillis();
		int capAccountId = capacity.getCapAccountId();
		
		//wait for all the queries to complete
		List<Object> queriedObjects = new ArrayList<>();
		
		CompletableFuture<List<CapacityDetail>> capacityDetail = null;
		try {
			capacityDetail = this.getCapacityDetailByCapAccountId(capacity);
			queriedObjects.add(capacityDetail);
		}
		catch (InterruptedException e) {
			LOGGER.debug(CUSTOMER_CAP_ERROR_MESSAGE, capAccountId);
			Thread.currentThread().interrupt();
			throw new CustomerCapacityException(CAP_ACCOUNT_ERROR_MESSAGE + capAccountId + " [original exception: " + e.getMessage() + "]");
		}
		catch (ExecutionException | ResourceNotFoundException e) {
			LOGGER.debug(CUSTOMER_CAP_ERROR_MESSAGE, capAccountId);
			throw new CustomerCapacityException(CAP_ACCOUNT_ERROR_MESSAGE + capAccountId + " [original exception: " + e.getMessage() + "]");
		}
		
		// For OnRegister, get rolled-up penalty points with latest expiry date if applicable ...
		CompletableFuture<List<PenaltyPoints>> points = null;
		try {
			if (capacity.isOnRegister()) {
				points = this.getRolledUpPenaltyPointsWithLatestExpiryDate(capAccountId);
				queriedObjects.add(points);
			}
		}
		catch (ResourceNotFoundException e) {
			LOGGER.debug(PEN_POINTS_ERROR_MESSAGE, capAccountId);
			throw new CustomerCapacityException(CAP_ACCOUNT_ERROR_MESSAGE + capAccountId + " [original exception: " + e.getMessage() + "]");
		}
		
		// Get vessel summary ...
		CompletableFuture<VesselSummary> vesselSummary = null;
		try {
			vesselSummary = this.getVesselSummary(capacity.getVesselId());
			queriedObjects.add(vesselSummary);
		}
		catch (ResourceNotFoundException e) {
			LOGGER.debug(VESSEL_SUM_ERROR_MESSAGE, capAccountId);
			throw new CustomerCapacityException(CAP_ACCOUNT_ERROR_MESSAGE + capAccountId + " [original exception: " + e.getMessage() + "]");
		}
		
		CompletableFuture[] futures = queriedObjects.toArray(new CompletableFuture[queriedObjects.size()]);
		CompletableFuture.allOf(futures); //wait for all to complete
		
		// Queries have completed, add the components to the capacity object ...
		try {
			capacity.setCapDetail(capacityDetail==null ? null : capacityDetail.get());
			capacity.setPenaltyPoints(points==null ? null : points.get());
			capacity.setVesselSummary(vesselSummary==null ? null : vesselSummary.get());
		} catch (InterruptedException e) {
			LOGGER.error("Error handling Capacity Detail Items (ASync), cap account: {}", capAccountId, e);
			Thread.currentThread().interrupt();
			throw new CustomerCapacityException(CAP_ACCOUNT_ERROR_MESSAGE + capAccountId + " [original exception: " + e.getMessage() + "]");
		}
		catch (ExecutionException e) {
			LOGGER.error("Error handling Capacity Detail Items (ASync), cap account: {}", capAccountId, e);
			throw new CustomerCapacityException(CAP_ACCOUNT_ERROR_MESSAGE + capAccountId + " [original exception: " + e.getMessage() + "]");
		}
		
		LOGGER.info("CustomerCapacityService.getCapacityDetailItems_ASync({}) EElapsed time(ms): {}", capAccountId, (System.currentTimeMillis() - startTime));
    }

	/***
	 * public CompletableFuture<List<CapacityDetail>> getCapacityDetailByCapAccountId(int capAccountId) throws ResourceNotFoundException;
	 * @param capAccountId
	 * @return CompletableFuture<List<CapacityDetail>>
	 * @throws ResourceNotFoundException
	 */
	private CompletableFuture<List<CapacityDetail>> getCapacityDetailByCapAccountId(Capacity capacity) throws ResourceNotFoundException, InterruptedException, ExecutionException {
		int capAccountId = capacity.getCapAccountId();
		LOGGER.debug("CustomerCapacityService.getCapacityDetailByCapAccountId({})", capAccountId);
		
		final long startTime = System.currentTimeMillis();
		
		CompletableFuture<List<CapacityDetail>> capDetail = customerCapacityDetailRepository.findCapacityDetailByCapAccountId(capAccountId);
		
		if (capDetail==null || capDetail.isCompletedExceptionally()) {
			LOGGER.info(CUSTOMER_CAP_ERROR_MESSAGE, capAccountId);
			throw new ResourceNotFoundException("No capacity detail found for capacity account: " + capAccountId);
		}
		else {
			// For OffRegister, include penalty points info for each CapacityDetail block item if applicable ...
			if (!capacity.isOnRegister()) {
				try {
					List<CapacityDetail> capDetail_Out = capDetail.get().stream().map(detail -> detail.setPenaltyPointsReturnDetail (
						this.getRolledUpPenaltyPointsWithLatestExpiryDate_Sync(capAccountId)
					)).collect(Collectors.toList());
					
					capDetail.complete(capDetail_Out);
				} catch (InterruptedException | ExecutionException e) {
					LOGGER.error("Exception reading capacity detail points information for capacity account: " + capAccountId, e);
					throw e;
				}
			}
		}
		
		LOGGER.info("CustomerCapacityService.getCapacityDetailByCapAccountId({}) EElapsed time(ms):{}", capAccountId, (System.currentTimeMillis() - startTime));
		return capDetail;
	}
	
	/**
	 * public CompletableFuture<List<PenaltyPoints>> getRolledUpPenalityPointsWithLatestExpiryDate(int capAccountId) throws ResourceNotFoundException;
	 * @param capAccountId
	 * @return CompletableFuture<List<PenaltyPoints>>
	 * @throws ResourceNotFoundException
	 */
	private CompletableFuture<List<PenaltyPoints>> getRolledUpPenaltyPointsWithLatestExpiryDate(int capAccountId) throws ResourceNotFoundException {
		LOGGER.debug("CustomerCapacityService.getRolledUpPenaltyPointsWithLatestExpiryDate({})", capAccountId);
		
		final long startTime = System.currentTimeMillis();
		
		CompletableFuture<List<PenaltyPoints>> points = capacityPenaltyPointsRepository.findCustomerCapacityPenaltyPointsByCapAccountId(capAccountId);
		if (points==null || points.isCompletedExceptionally()) {
			LOGGER.info(PEN_POINTS_ERROR_MESSAGE, capAccountId);
			throw new ResourceNotFoundException("No penality points found for capacity account: " + capAccountId);
		}
		
		LOGGER.info("CustomerCapacityService.getRolledUpPenaltyPointsWithLatestExpiryDate({}) EElapsed time(ms):{}", capAccountId, (System.currentTimeMillis() - startTime));
		return points;
	}
	
	/**
	 * public CompletableFuture<List<PenaltyPoints>> getRolledUpPenalityPointsWithLatestExpiryDate(int capAccountId) throws ResourceNotFoundException;
	 * @param capAccountId
	 * @return CompletableFuture<List<PenaltyPoints>>
	 * @throws ResourceNotFoundException
	 */
	private PenaltyPoints getRolledUpPenaltyPointsWithLatestExpiryDate_Sync(int capAccountId) {
		LOGGER.debug("CustomerCapacityService.getRolledUpPenaltyPointsWithLatestExpiryDate_Sync({})", capAccountId);
		
		final long startTime = System.currentTimeMillis();
		
		PenaltyPoints points = capacityPenaltyPointsRepository.findCustomerCapacityPenaltyPointsByCapAccountId_Sync(capAccountId);
		if (points==null) {
			LOGGER.info(PEN_POINTS_ERROR_MESSAGE, capAccountId);
		}
		
		LOGGER.info("CustomerCapacityService.getRolledUpPenaltyPointsWithLatestExpiryDate_Sync({}) EElapsed time(ms):{}", capAccountId, (System.currentTimeMillis() - startTime));
		return points;
	}
	
	/**
	 * public CompletableFuture<VesselSummary> getVesselSummary(int capAccountId) throws ResourceNotFoundException;
	 * @param capAccountId
	 * @return CompletableFuture<VesselSummary>
	 * @throws ResourceNotFoundException
	 */
	private CompletableFuture<VesselSummary> getVesselSummary(int vesselId) throws ResourceNotFoundException {
		LOGGER.debug("CustomerCapacityService.getVesselSummary({})", vesselId);
		
		final long startTime = System.currentTimeMillis();
		
		CompletableFuture<VesselSummary> vesselSummary = vesselSummaryRepository.findVesselSummaryByVesselId(vesselId);
		if (vesselSummary==null || vesselSummary.isCompletedExceptionally()) {
			LOGGER.info(VESSEL_SUM_ERROR_MESSAGE, vesselId);
			throw new ResourceNotFoundException("Vessel summary information not found for vessel id: " + vesselId);
		}
		
		LOGGER.info("CustomerCapacityService.getVesselSummary({}) EElapsed time(ms):{}", vesselId, (System.currentTimeMillis() - startTime));
		return vesselSummary;
	}
	
	/***
	 * private CapacityDTO convertCapacityToDTO(Capacity capacity);
	 * @param capacity
	 * @return CapacityDTO
	 */
	private CapacityDTO convertCapacityToDTO(Capacity capacity) {
		return modelMapper.map(capacity, CapacityDTO.class);
    }
	
	/**
	 * public List<VesselIdentification> findVesselIdentifiers(String id)
	 * @param id
	 * @return List<VesselIdentification> 
	 * @throws ResourceNotFoundException 
	 */
	private String getIfisIdByCcsId(String ccsId) throws ResourceNotFoundException {
		LOGGER.debug("VesselService.getIfisIdbyCcsId({}) - role:{}", ccsId, databaseRole);
		
		IfisWrapper ifisDetail = cCSRepository.findIfisIdByCcsId(ccsId, databaseRole);
		
		if (ifisDetail==null || ifisDetail.getIfisId() == null || ifisDetail.getIfisId().isEmpty()) {
			LOGGER.info("Unable to find IFIS-Id for ccsId {}", ccsId);
			throw new ResourceNotFoundException("No IFIS-ID found for ccsId:" + ccsId);
		}
		
		LOGGER.info("IFIS-ID:{} found for ccsID:{}", ifisDetail.getIfisId());
		
		return ifisDetail.getIfisId();
	}
}
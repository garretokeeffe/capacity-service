package ie.gov.agriculture.fisheries.la.capacityservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ie.gov.agriculture.fisheries.la.capacityservice.dto.AllCapacityDTO;
import ie.gov.agriculture.fisheries.la.capacityservice.dto.CapacityDTO;
import ie.gov.agriculture.fisheries.la.capacityservice.dto.CustomerCapacityDTO;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.Capacity;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.CapacityDetail;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.CustomerCapacity;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.IfisWrapper;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.PenaltyPoints;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.VesselSummary;
import ie.gov.agriculture.fisheries.la.capacityservice.exception.ResourceNotFoundException;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CCSRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CapacityPenaltyPointsRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CapacityRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CustomerCapacityDetailRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CustomerCapacityRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.VesselSummaryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import java.util.concurrent.ExecutionException;

/** https://confluence.agriculture.gov.ie/confluence/display/FISHERIES/Get+Capacity **/

@Component
@Service
public class CustomerCapacityService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerCapacityService.class);

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
	
	/***
	 * public AllCapacityDTO getAllCapacity (String customerId, boolean convertCcsToIfisID) throws ResourceNotFoundException;
	 * @param customerId
	 * @param convertCcsToIfisID
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public AllCapacityDTO getAllCapacity (String customerId, boolean convertCcsToIfisID) throws ResourceNotFoundException {
		LOGGER.debug("CustomerCapacityService.getCapacity(" + customerId + ")");
		
		AllCapacityDTO allCapacityDTO = null;
		
		String ifisCustomerId = (convertCcsToIfisID ? this.getIfisIdbyCcsId(customerId) : customerId);
		
		allCapacityDTO = new AllCapacityDTO();
		List<CapacityDTO> capacityDTO = new ArrayList<CapacityDTO>();
		
		// Retrieve high level capacity information ...
		List<Capacity> capacity = capacityRepository.findCapacityByOwnerId(ifisCustomerId);
		
		if (CollectionUtils.isEmpty(capacity)) {
			LOGGER.info("No capacity information found for customer:{}", customerId);
			throw new ResourceNotFoundException("No capacity information found for customer:" + customerId);
		}
		
		// Retrieve capacity detail items ...
		capacity.forEach(item -> {
			try {
				this.getCapacityDetailItems_ASync(item);
			} catch (ResourceNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
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
	private void getCapacityDetailItems_ASync(Capacity capacity) throws ResourceNotFoundException {
		LOGGER.debug("CustomerCapacityService.getCapacityDetailItems_ASync:" + capacity.getCapAccountId());
		
		final long startTime = System.currentTimeMillis();
		int capAccountId = capacity.getCapAccountId();
		
		//wait for all the queries to complete
		List<Object> queriedObjects = new ArrayList<>();
		
		CompletableFuture<List<CapacityDetail>> capacityDetail = null;
		try {
			capacityDetail = this.getCapacityDetailByCapAccountId(capAccountId);
			queriedObjects.add(capacityDetail);
		}
		catch (ResourceNotFoundException e) {
			LOGGER.debug("No capacity detail found for capacity account:{}", capAccountId);
		}
		
		// Get penalty points ...
		CompletableFuture<List<PenaltyPoints>> points = null;
		try {
			points = this.getCustomerCapacityPenaltyPointsByCapAccountId(capAccountId);
			queriedObjects.add(points);
		}
		catch (ResourceNotFoundException e) {
			LOGGER.debug("No penality points found for capacity account:{}", capAccountId);
		}
		
		// Get vessel summary ...
		CompletableFuture<VesselSummary> vesselSummary = null;
		try {
			vesselSummary = this.getVesselSummary(capacity.getVesselId());
			queriedObjects.add(vesselSummary);
		}
		catch (ResourceNotFoundException e) {
			LOGGER.debug("Vessel summary information not found for capacity account:{}", capAccountId);
		}
		
		CompletableFuture[] futures = queriedObjects.toArray(new CompletableFuture[queriedObjects.size()]);
		CompletableFuture.allOf(futures); //wait for all to complete
		
		//Queries have completed, add the components to the VesselDTO
		try {
			capacity.setCapDetail(capacityDetail.get());
			capacity.setPenaltyPoints(points.get());
			capacity.setVesselSummary(vesselSummary.get());
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error("Error handling Capacity Detail Items (ASync), cap account :{}", capAccountId, e);
			throw new ResourceNotFoundException(e.getMessage());
		}
		
		LOGGER.info("CustomerCapacityService.getCapacityDetailItems_ASync({}) EElapsed time(ms):{}", capAccountId, (System.currentTimeMillis() - startTime));
    }
	
	/***
	 * private void getCapacityDetailItems_Sync(Capacity capacity) throws ResourceNotFoundException;
	 * @param capacity
	 * @throws ResourceNotFoundException
	 * Standard Sync function for fall-back if Async version cannot be used ...
	 */
	private void getCapacityDetailItems_Sync(Capacity capacity) throws ResourceNotFoundException {
		LOGGER.debug("CustomerCapacityService.getCapacityDetailItems_Sync:" + capacity.getCapAccountId());
		
		final long startTime = System.currentTimeMillis();
		int capAccountId = capacity.getCapAccountId();

		// Get capacity detail ...
		CompletableFuture<List<CapacityDetail>> capacityDetail = null;
		try {
			capacityDetail = this.getCapacityDetailByCapAccountId(capAccountId);
			capacity.setCapDetail(capacityDetail.get());
		}
		catch (ResourceNotFoundException | InterruptedException | ExecutionException e) {
			LOGGER.debug("No capacity detail found for capacity account:{}", capAccountId);
		}
		
		// Get penalty points ...
		CompletableFuture<List<PenaltyPoints>> points = null;
		try {
			points = this.getCustomerCapacityPenaltyPointsByCapAccountId(capAccountId);
			capacity.setPenaltyPoints(points.get());
		}
		catch (ResourceNotFoundException | InterruptedException | ExecutionException e) {
			LOGGER.debug("No penality points found for capacity account:{}", capAccountId);
		}
		
		// Get vessel summary ...
		CompletableFuture<VesselSummary> vesselSummary = null;
		try {
			vesselSummary = this.getVesselSummary(capacity.getVesselId());
			capacity.setVesselSummary(vesselSummary.get());
		}
		catch (ResourceNotFoundException | InterruptedException | ExecutionException e) {
			LOGGER.debug("Vessel summary information not found for capacity account:{}", capAccountId);
		}
		
		LOGGER.info("CustomerCapacityService.getCapacityDetailItems_Sync({}) EElapsed time(ms):{}", capAccountId, (System.currentTimeMillis() - startTime));
    }
	
	/***
	 * private void getCapacityDetailItems_Original(Capacity capacity)
	 * @param capacity
	 * Original function calling later objects ..
	 */
	private void getCapacityDetailItems_Original(Capacity capacity) {
		// Get capacity detail ...
		LOGGER.debug("CustomerCapacityService.getCapacityDetailItems:" + capacity.getCapAccountId());
//		capacity.setCapDetail(customerCapacityDetailRepository.findCapacityDetailByCapAccountId(capacity.getCapAccountId()));
		
		// Get penalty points ...
//		capacity.setPenaltyPoints(capacityPenaltyPointsRepository.findCustomerCapacityPenaltyPointsByCapAccountId(capacity.getCapAccountId()));
		
		// Get vessel ...
//		capacity.setVesselSummary(vesselSummaryRepository.findVesselSummaryByVesselId(capacity.getVesselId()));
    }

	/***
	 * public CompletableFuture<List<CapacityDetail>> getCapacityDetailByCapAccountId(int capAccountId) throws ResourceNotFoundException;
	 * @param capAccountId
	 * @return CompletableFuture<List<CapacityDetail>>
	 * @throws ResourceNotFoundException
	 */
	public CompletableFuture<List<CapacityDetail>> getCapacityDetailByCapAccountId(int capAccountId) throws ResourceNotFoundException {
		LOGGER.debug("CustomerCapacityService.getCapacityDetailByCapAccountId({})", capAccountId);
		
		final long startTime = System.currentTimeMillis();
		
		CompletableFuture<List<CapacityDetail>> capDetail = customerCapacityDetailRepository.findCapacityDetailByCapAccountId(capAccountId);
		if (capDetail==null || capDetail.isCompletedExceptionally()) {
			LOGGER.info("No capacity detail found for capacity account{}", capAccountId);
			throw new ResourceNotFoundException("No capacity detail found for capacity account: " + capAccountId);
		}
		
		LOGGER.info("CustomerCapacityService.getCapacityDetailByCapAccountId({}) EElapsed time(ms):{}", capAccountId, (System.currentTimeMillis() - startTime));
		return capDetail;
	}
	
	/**
	 * public CompletableFuture<List<PenaltyPoints>> getCustomerCapacityPenaltyPointsByCapAccountId(int capAccountId) throws ResourceNotFoundException;
	 * @param capAccountId
	 * @return CompletableFuture<List<PenaltyPoints>>
	 * @throws ResourceNotFoundException
	 */
	public CompletableFuture<List<PenaltyPoints>> getCustomerCapacityPenaltyPointsByCapAccountId(int capAccountId) throws ResourceNotFoundException {
		LOGGER.debug("CustomerCapacityService.getCustomerCapacityPenaltyPointsByCapAccountId({})", capAccountId);
		
		final long startTime = System.currentTimeMillis();
		
		CompletableFuture<List<PenaltyPoints>> points = capacityPenaltyPointsRepository.findCustomerCapacityPenaltyPointsByCapAccountId(capAccountId);
		if (points==null || points.isCompletedExceptionally()) {
			LOGGER.info("No penality points found for capacity account{}", capAccountId);
			throw new ResourceNotFoundException("No penality points found for capacity account: " + capAccountId);
		}
		
		LOGGER.info("CustomerCapacityService.getCustomerCapacityPenaltyPointsByCapAccountId({}) EElapsed time(ms):{}", capAccountId, (System.currentTimeMillis() - startTime));
		return points;
	}
	
	/**
	 * public CompletableFuture<VesselSummary> getVesselSummary(int capAccountId) throws ResourceNotFoundException;
	 * @param capAccountId
	 * @return CompletableFuture<VesselSummary>
	 * @throws ResourceNotFoundException
	 */
	public CompletableFuture<VesselSummary> getVesselSummary(int capAccountId) throws ResourceNotFoundException {
		LOGGER.debug("CustomerCapacityService.getVesselSummary({})", capAccountId);
		
		final long startTime = System.currentTimeMillis();
		
		CompletableFuture<VesselSummary> vesselSummary = vesselSummaryRepository.findVesselSummaryByVesselId(capAccountId);
		if (vesselSummary==null || vesselSummary.isCompletedExceptionally()) {
			LOGGER.info("Vessel summary information not found for capacity account{}", capAccountId);
			throw new ResourceNotFoundException("Vessel summary information not found for capacity account: " + capAccountId);
		}
		
		LOGGER.info("CustomerCapacityService.getVesselSummary({}) EElapsed time(ms):{}", capAccountId, (System.currentTimeMillis() - startTime));
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
	
	/***
	 * public List<CustomerCapacityDTO> getCustomerCapacityInformation (String customerId);
	 * @param customerId
	 * @return List<CustomerCapacityDTO>
	 */
	public List<CustomerCapacityDTO> getCustomerCapacityInformation (String customerId) {
		LOGGER.debug("CustomerCapacityService.getCustomerCapacityInformation(" + customerId + ")");
		
		List<CustomerCapacityDTO> listCustomerCapacityDTO = new ArrayList<CustomerCapacityDTO>();
		
		List<CustomerCapacity> listCustomerCapacity = customerCapacityRepository.findCapacityInformationByOwnerId(customerId);
		
		listCustomerCapacity.forEach(item -> listCustomerCapacityDTO.add(this.convertCustomerCapacityToDTO(item)));
		
		return listCustomerCapacityDTO;
	}
	
	/***
	 * private CustomerCapacityDTO convertCustomerCapacityToDTO(CustomerCapacity customerCapacity)
	 * @param customerCapacity
	 * @return CustomerCapacityDTO
	 */
	private CustomerCapacityDTO convertCustomerCapacityToDTO(CustomerCapacity customerCapacity) {
		return modelMapper.map(customerCapacity, CustomerCapacityDTO.class);
    }
	
	/**
	 * public List<VesselIdentification> findVesselIdentifiers(String id)
	 * @param id
	 * @return List<VesselIdentification> 
	 * @throws ResourceNotFoundException 
	 */
	public String getIfisIdbyCcsId(String ccsId) throws ResourceNotFoundException {
		LOGGER.debug("VesselService.getIfisIdbyCcsId({})", ccsId);
		
		List<IfisWrapper> results = cCSRepository.findIfisIdbyCcsId(ccsId);
		if (results==null || CollectionUtils.isEmpty(results)) {
			LOGGER.info("Unable to find IFIS-Id for ccsId {}", ccsId);
			throw new ResourceNotFoundException("No IFIS-ID found for ccsId:" + ccsId);
		}
		
		String ifisId = results.get(0).getIfisId();
		ifisId = (ifisId==null ? "" : ifisId);

		// Double check we have a valid value returned ...
		if (ifisId.trim().equalsIgnoreCase("")) {
			LOGGER.info("Unable to find IFIS-Id for ccsId {}", ccsId);
			throw new ResourceNotFoundException("No IFIS-ID found for ccsId:" + ccsId);
		}
		else {
			if (results.size() > 1) { //should not happen
				LOGGER.warn("For ccsId:{}, Found {} IFIS-IDss:{}. Will use first:{}",ccsId, results.size(), results, results.get(0));
			}
			else {
				LOGGER.info("IFIS-ID:{} found for ccsID:{}", ifisId);
			}
		}
		
		return ifisId;
	}
}
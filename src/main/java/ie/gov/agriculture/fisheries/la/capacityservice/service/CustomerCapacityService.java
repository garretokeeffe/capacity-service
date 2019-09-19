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
import ie.gov.agriculture.fisheries.la.capacityservice.entity.AllCapacity;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.Capacity;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.CustomerCapacity;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.IfisWrapper;
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
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;

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
	
	public AllCapacityDTO getAllCapacity (String customerId, boolean convertCcsToIfisID) throws ResourceNotFoundException {
		LOGGER.debug("CustomerCapacityService.getCapacity(" + customerId + ")");
		
		AllCapacityDTO allCapacityDTO = null;
		
		try {
			String ifisCustomerId = (convertCcsToIfisID ? this.getIfisIdbyCcsId(customerId) : customerId);
			
			allCapacityDTO = new AllCapacityDTO();
			List<CapacityDTO> capacityDTO = new ArrayList<CapacityDTO>();
			
			List<Capacity> capacity = capacityRepository.findCapacityByOwnerId(ifisCustomerId);
			
			if (CollectionUtils.isEmpty(capacity)) {
				LOGGER.info("No caapcitry information found for customer:{}", customerId);
				throw new ResourceNotFoundException("No caapcitry information found for customer:" + customerId);
			}
			
			// Get capacity detail items ...
			capacity.forEach(item -> this.getCapacityDetailItems(item));
			
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
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return allCapacityDTO;
	}
	
	private void getCapacityDetailItems(Capacity capacity) {
		// Get capacity detail ...
		LOGGER.debug("XXX - CustomerCapacityService.getCapacityDetailItems:" + capacity.getCapAccountId());
		capacity.setCapDetail(customerCapacityDetailRepository.findCapacityDetailByCapAccountId(capacity.getCapAccountId()));
		
		// Get penalty points ...
		capacity.setPenaltyPoints(capacityPenaltyPointsRepository.findCustomerCapacityPenaltyPointsByCapAccountId(capacity.getCapAccountId()));
		
		// Get vessel ...
		capacity.setVesselSummary(vesselSummaryRepository.findVesselSummaryByVesselId(capacity.getVesselId()));
    }
	
	private CapacityDTO convertCapacityToDTO(Capacity capacity) {
		return modelMapper.map(capacity, CapacityDTO.class);
    }
	
	public List<CustomerCapacityDTO> getCustomerCapacityInformation (String customerId) {
		LOGGER.debug("CustomerCapacityService.getCustomerCapacityInformation(" + customerId + ")");
		
		List<CustomerCapacityDTO> listCustomerCapacityDTO = new ArrayList<CustomerCapacityDTO>();
		
		List<CustomerCapacity> listCustomerCapacity = customerCapacityRepository.findCapacityInformationByOwnerId(customerId);
		
		listCustomerCapacity.forEach(item -> listCustomerCapacityDTO.add(this.convertCustomerCapacityToDTO(item)));
		
		return listCustomerCapacityDTO;
	}
	
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
		if (CollectionUtils.isEmpty(results)) {
			LOGGER.info("Unable to find IFIS-Id for ccsId {}", ccsId);
			throw new ResourceNotFoundException("No IFIS-ID found for ccsId:" + ccsId);
		}
		
		String ifisId = results.get(0).getIfisId();
		if (results.size() > 1) { //should not happen
			LOGGER.warn("For ccsId:{}, Found {} IFIS-IDss:{}. Will use first:{}",ccsId, results.size(), results, results.get(0));
		}
		else {
			LOGGER.info("IFIS-ID:{} found for ccsID:{}", ifisId);
		}
		return ifisId;
	}
}
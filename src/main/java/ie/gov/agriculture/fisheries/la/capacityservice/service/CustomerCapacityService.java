package ie.gov.agriculture.fisheries.la.capacityservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ie.gov.agriculture.fisheries.la.capacityservice.dto.AllCapacityDTO;
import ie.gov.agriculture.fisheries.la.capacityservice.dto.CapacityDTO;
import ie.gov.agriculture.fisheries.la.capacityservice.dto.CustomerCapacityDTO;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.AllCapacity;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.Capacity;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.CustomerCapacity;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.VesselSummary;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CapacityPenaltyPointsRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CapacityRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CustomerCapacityDetailRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CustomerCapacityRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.VesselSummaryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

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
    private ModelMapper modelMapper;
	
	public AllCapacityDTO getAllCapacity (String customerId) {
		LOGGER.debug("CustomerCapacityService.getCapacity(" + customerId + ")");
		
		AllCapacityDTO allCapacityDTO = new AllCapacityDTO();
		List<CapacityDTO> capacityDTO = new ArrayList<CapacityDTO>();
		
		List<Capacity> capacity = capacityRepository.findCapacityByOwnerId(customerId);
		
		// Get capacity detail items ...
		capacity.forEach(item -> this.getCapacityDetailItems(item));
		
		// Convert capacity items to DTO ...
		capacity.forEach(item -> capacityDTO.add(this.convertCapacityToDTO(item)));
		
		allCapacityDTO.setOwnerId(customerId);
		
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
	
	private void getCapacityDetailItems(Capacity capacity) {
		// Get capacity detail ...
		System.out.println("XXX - CustomerCapacityService.getCapacityDetailItems:" + capacity.getCapAccountId());
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
}

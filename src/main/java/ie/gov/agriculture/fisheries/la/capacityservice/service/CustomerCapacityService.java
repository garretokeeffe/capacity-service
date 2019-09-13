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
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CapacityRepository;
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
	VesselSummaryRepository vesselSummaryRepository;
	
	@Autowired
    private ModelMapper modelMapper;
	
	public AllCapacityDTO getAllCapacity (String customerId) {
		LOGGER.debug("CustomerCapacityService.getCapacity(" + customerId + ")");
		
		AllCapacityDTO allCapacityDTO = new AllCapacityDTO();
		List<CapacityDTO> capacityDTO = new ArrayList<CapacityDTO>();
		
		List<Capacity> capacity = capacityRepository.findCapacityByOwnerId(customerId);
		
		// Get vessel for capacity ...
		capacity.forEach(item -> this.getVesselForCapacity(item));
		
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
	
	public List<CustomerCapacityDTO> getCustomerCapacityInformation (String customerId) {
		LOGGER.debug("CustomerCapacityService.getCustomerCapacityInformation(" + customerId + ")");
		
		List<CustomerCapacityDTO> listCustomerCapacityDTO = new ArrayList<CustomerCapacityDTO>();
		
		List<CustomerCapacity> listCustomerCapacity = customerCapacityRepository.findCapacityInformationByOwnerId(customerId);
		
		listCustomerCapacity.forEach(item -> listCustomerCapacityDTO.add(this.convertCustomerCapacityToDTO(item)));
		
		return listCustomerCapacityDTO;
	}
	
	private CapacityDTO convertCapacityToDTO(Capacity capacity) {
		return modelMapper.map(capacity, CapacityDTO.class);
    }
	
	private void getVesselForCapacity(Capacity capacity) {
		//VesselSummary vesselSummary = vesselSummaryRepository.findVesselSummaryByVesselId(capacity.getVesselId());
		capacity.setVesselSummary(vesselSummaryRepository.findVesselSummaryByVesselId(capacity.getVesselId()));
    }
	
	private CustomerCapacityDTO convertCustomerCapacityToDTO(CustomerCapacity customerCapacity) {
		return modelMapper.map(customerCapacity, CustomerCapacityDTO.class);
    }
}

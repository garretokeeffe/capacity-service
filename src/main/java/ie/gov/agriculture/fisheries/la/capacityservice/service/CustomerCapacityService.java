package ie.gov.agriculture.fisheries.la.capacityservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ie.gov.agriculture.fisheries.la.capacityservice.dto.CustomerCapacityDTO;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.CustomerCapacity;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CustomerCapacityRepository;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@Service
public class CustomerCapacityService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerCapacityService.class);

	@Autowired
	CustomerCapacityRepository customerCapacityRepository;
	
	@Autowired
    private ModelMapper modelMapper;
	
	public List<CustomerCapacityDTO> getCustomerCapacityInformation (String customerId) {
		LOGGER.debug("CustomerCapacityService.getCustomerCapacityInformation(" + customerId + ")");
		
		List<CustomerCapacityDTO> listCustomerCapacityDTO = new ArrayList<CustomerCapacityDTO>();
		
		List<CustomerCapacity> listCustomerCapacity = customerCapacityRepository.getCustomerCapacityInformation(customerId);
		
		listCustomerCapacity.forEach(item -> listCustomerCapacityDTO.add(this.convertCustomerCapacityToDTO(item)));
		
		return listCustomerCapacityDTO;
	}
	
	private CustomerCapacityDTO convertCustomerCapacityToDTO(CustomerCapacity customerCapacity) {
		return modelMapper.map(customerCapacity, CustomerCapacityDTO.class);
    }
}

package ie.gov.agriculture.fisheries.la.capacityservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ie.gov.agriculture.fisheries.la.capacityservice.dto.CustomerCapacityDTO;
import ie.gov.agriculture.fisheries.la.capacityservice.service.CustomerCapacityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("sfos/customer/capacity")
@Api(value = "customerCapacityServices")
public class CustomerCapacityController {
	@Autowired
	CustomerCapacityService customerCapacityService;

	@ApiOperation(value = "Retrieve capacity information for an individual customer [e.g. http://localhost:8080/sfos/customer/capacity/23630]")
	@GetMapping(path = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CustomerCapacityDTO>> getVesselDetailsyId(@PathVariable(required = true) String customerId) throws Exception {
		List<CustomerCapacityDTO> customerCapacityDTO = customerCapacityService.getCustomerCapacityInformation(customerId);
		
		return new ResponseEntity<>(customerCapacityDTO, HttpStatus.OK);
	}
}

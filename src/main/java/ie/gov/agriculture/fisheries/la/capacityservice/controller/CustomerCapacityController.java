package ie.gov.agriculture.fisheries.la.capacityservice.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ie.gov.agriculture.fisheries.la.capacityservice.dto.AllCapacityDTO;
import ie.gov.agriculture.fisheries.la.capacityservice.dto.CustomerCapacityDTO;
import ie.gov.agriculture.fisheries.la.capacityservice.service.CustomerCapacityService;
import ie.gov.agriculture.fisheries.la.capacityservice.user.UserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.MDC;

@RestController
@RequestMapping("sfos/capacity")
@Api(value = "customerCapacityServices")
public class CustomerCapacityController {
	@Autowired
	CustomerCapacityService customerCapacityService;
	
	@ApiOperation(value = "Retrieve All Capacity information for an individual customer using CCS Customer ID [e.g. http://localhost:8080/sfos/capacity/ccs/FBY10086C]")
	@GetMapping(path = "/ccs/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllCapacityDTO> getCustomerCapacityByCcsID(HttpServletRequest request, @PathVariable(required = true) String customerId) throws Exception {
		this.pushUserInfoToThread(request);
		
		AllCapacityDTO allCapacityDTO = customerCapacityService.getAllCapacity(customerId, true);
		
		return new ResponseEntity<AllCapacityDTO>(allCapacityDTO, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Retrieve All Capacity information for an individual customer using IFIS Customer ID [e.g. http://localhost:8080/sfos/capacity/ifis/2957]")
	@GetMapping(path = "/ifis/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllCapacityDTO> getCustomerCapacityByIfisID(HttpServletRequest request, @PathVariable(required = true) String customerId) throws Exception {
		this.pushUserInfoToThread(request);
		
		AllCapacityDTO allCapacityDTO = customerCapacityService.getAllCapacity(customerId, false);
		
		return new ResponseEntity<AllCapacityDTO>(allCapacityDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "Retrieve raw capacity information for an individual IFIS customer id [e.g. http://localhost:8080/sfos/capacity/ifisraw/23630]")
	@GetMapping(path = "/ifisraw/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CustomerCapacityDTO>> getCustomerCapacityInformation(HttpServletRequest request, @PathVariable(required = true) String customerId) throws Exception {
		this.pushUserInfoToThread(request);
		
		List<CustomerCapacityDTO> customerCapacityDTO = customerCapacityService.getCustomerCapacityInformation(customerId);
		
		return new ResponseEntity<>(customerCapacityDTO, HttpStatus.OK);
	}
	
	private void pushUserInfoToThread (HttpServletRequest request) {
		UserPrincipal up = UserPrincipal.getUserPrincipal(request);
		MDC.put("name", up.getName());
		MDC.put("userName", up.getUsername());
		MDC.put("email", up.getEmail());
	}
}

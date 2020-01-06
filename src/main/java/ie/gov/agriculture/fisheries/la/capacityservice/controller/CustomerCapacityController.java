package ie.gov.agriculture.fisheries.la.capacityservice.controller;

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
import ie.gov.agriculture.fisheries.la.capacityservice.service.CustomerCapacityService;
import ie.gov.agriculture.fisheries.la.capacityservice.user.UserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * public class CustomerCapacityController
 * @author stephen.mccosker
 * RestController exposing CustomerCapacity end points. 
 */

@RestController
@RequestMapping("sfos/capacity/users")
@Api(value = "customerCapacityServices")
public class CustomerCapacityController {
	@Autowired
	CustomerCapacityService customerCapacityService;
	
	@ApiOperation(
		value = "Retrieve All Capacity information for an individual customer using CCS Customer ID [e.g. http://localhost:8080/sfos/capacity/users/ccs/FBY10090G or FBY10086C]",
		response = AllCapacityDTO.class,
		produces = MediaType.APPLICATION_JSON_VALUE,
		notes = "Retrieve All Capacity information for an individual customer using CCS Customer ID [e.g. http://localhost:8080/sfos/capacity/users/ccs/FBY10125D or FBY10086C]"
	)
	@ApiResponses(value = {@ApiResponse(code = 415, message = "Content type not supported.")})
	@GetMapping(path = "/ccs/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllCapacityDTO> getCustomerCapacityByCcsID(HttpServletRequest request, @PathVariable(required = true) String customerId) throws Exception {
		UserPrincipal.getUserPrincipal(request);
		
		AllCapacityDTO allCapacityDTO = customerCapacityService.getAllCapacity(customerId, true);
		
		return new ResponseEntity<>(allCapacityDTO, HttpStatus.OK);
	}
	
	@ApiOperation(
		value = "Retrieve All Capacity information for an individual customer using IFIS Customer ID [e.g. http://localhost:8080/sfos/capacity/users/ifis/66959069 or 2957]",
		response = AllCapacityDTO.class,
		produces = MediaType.APPLICATION_JSON_VALUE,
		notes = "Retrieve All Capacity information for an individual customer using IFIS Customer ID [e.g. http://localhost:8080/sfos/capacity/users/ifis/66959069 or 2957]"
	)
	@ApiResponses(value = {@ApiResponse(code = 415, message = "Content type not supported.")})
	@GetMapping(path = "/ifis/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllCapacityDTO> getCustomerCapacityByIfisID(HttpServletRequest request, @PathVariable(required = true) String customerId) throws Exception {
		UserPrincipal.getUserPrincipal(request);
		
		AllCapacityDTO allCapacityDTO = customerCapacityService.getAllCapacity(customerId, false);
		
		return new ResponseEntity<>(allCapacityDTO, HttpStatus.OK);
	}
}

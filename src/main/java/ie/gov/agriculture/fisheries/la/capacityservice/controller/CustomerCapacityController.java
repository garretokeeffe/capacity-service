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
@RequestMapping("sfos")
@Api(value = "customerCapacityServices")
public class CustomerCapacityController {
	@Autowired
	CustomerCapacityService customerCapacityService;
	
	@ApiOperation(value = "Retrieve complete All Capacity information for an individual customer using ccs customer id [e.g. http://localhost:8080/sfos/capacity/ccs/FBY10086C]")
	@GetMapping(path = "/capacity/ccs/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllCapacityDTO> getCustomerCapacityByCcsID(HttpServletRequest request, @PathVariable(required = true) String customerId) throws Exception {
		this.pushUserInfoToThread(request);
		
		AllCapacityDTO allCapacityDTO = customerCapacityService.getAllCapacity(customerId, true);
		
		return new ResponseEntity<AllCapacityDTO>(allCapacityDTO, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Retrieve complete All Capacity information for an individual customer using ifis customer id [e.g. http://localhost:8080/sfos/capacity/ifis/2957]")
	@GetMapping(path = "/capacity/ifis/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllCapacityDTO> getCustomerCapacityByIfisID(HttpServletRequest request, @PathVariable(required = true) String customerId) throws Exception {
		this.pushUserInfoToThread(request);
		
		AllCapacityDTO allCapacityDTO = customerCapacityService.getAllCapacity(customerId, false);
		
		return new ResponseEntity<AllCapacityDTO>(allCapacityDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "Retrieve basic capacity information for an individual customer [e.g. http://localhost:8080/sfos/customer/capacity/23630]")
	@GetMapping(path = "/customer/basic_capacity/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
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
		
//		System.out.println("XXX - CustomerCapacityController.pushUserInfoToThread:" + request);
//		System.out.println("XXX - CustomerCapacityController.pushUserInfoToThread:" + up.getName());
//		System.out.println("XXX - CustomerCapacityController.pushUserInfoToThread:" + up.getUsername());
//		System.out.println("XXX - CustomerCapacityController.pushUserInfoToThread:" + up.getEmail());
	}
}

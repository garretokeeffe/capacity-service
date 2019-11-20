package ie.gov.agriculture.fisheries.la.capacityservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ie.gov.agriculture.fisheries.la.capacityservice.dto.AllCapacityDTO;
import ie.gov.agriculture.fisheries.la.capacityservice.dto.CustomerCapacityDTO;
import ie.gov.agriculture.fisheries.la.capacityservice.service.CustomerCapacityService;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class CapacityControllerTest {
	private static final String CUSTOMER_ID = "1000";
	
    @InjectMocks
    CustomerCapacityController ceustomerCapacityController;
     
    @Mock
    CustomerCapacityService customerCapacityService;

    @Test
    public void testGetCustomerCapacityByCcsID() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        
        AllCapacityDTO allCapacityDTO = new AllCapacityDTO();
         
        when(customerCapacityService.getAllCapacity(anyString(), eq(true))).thenReturn(allCapacityDTO);

        ResponseEntity<AllCapacityDTO> responseEntity = ceustomerCapacityController.getCustomerCapacityByCcsID(request, CUSTOMER_ID);
        
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
        Mockito.verify(customerCapacityService, Mockito.times(1)).getAllCapacity(anyString(), eq(true));
    }
    
    @Test
    public void testGetCustomerCapacityByIfisID() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        
        AllCapacityDTO allCapacityDTO = new AllCapacityDTO();
         
        when(customerCapacityService.getAllCapacity(anyString(), eq(false))).thenReturn(allCapacityDTO);

        ResponseEntity<AllCapacityDTO> responseEntity = ceustomerCapacityController.getCustomerCapacityByIfisID(request, CUSTOMER_ID);
        
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
        Mockito.verify(customerCapacityService, Mockito.times(1)).getAllCapacity(anyString(), eq(false));
    }
    
    @Test
    public void testGetCustomerCapacityInformation() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        
        List<CustomerCapacityDTO> listCustomerCapacityDTO = new ArrayList<>();
         
        when(customerCapacityService.getCustomerCapacityInformation(anyString())).thenReturn(listCustomerCapacityDTO);

        ResponseEntity<List<CustomerCapacityDTO>> responseEntity = ceustomerCapacityController.getCustomerCapacityInformation(request, CUSTOMER_ID);
        
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
        Mockito.verify(customerCapacityService, Mockito.times(1)).getCustomerCapacityInformation(anyString());
    }
}

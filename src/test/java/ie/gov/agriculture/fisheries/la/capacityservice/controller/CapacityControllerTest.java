package ie.gov.agriculture.fisheries.la.capacityservice.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ie.gov.agriculture.fisheries.la.capacityservice.CapaityServiceApplication;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CCSRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CapacityPenaltyPointsRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CapacityRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CustomerCapacityDetailRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CustomerCapacityRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.VesselSummaryRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.service.CustomerCapacityService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CapaityServiceApplication.class)
public class CapacityControllerTest {
	private String testClass = "CapacityControllerTest";
	private String testMthd = "";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CapacityControllerTest.class);
	
	private boolean doLogging = true;
	
	private MockMvc mockMvc;
	
	@InjectMocks
	private CustomerCapacityController controller;
	
	@Mock
	CustomerCapacityService customerCapacityService;
	
	@Mock
	CustomerCapacityRepository customerCapacityRepository;
	
	@Mock
	CapacityRepository capacityRepository;
	
	@Mock
	CustomerCapacityDetailRepository customerCapacityDetailRepository;
	
	@Mock
	VesselSummaryRepository vesselSummaryRepository;
	
	@Mock
	CapacityPenaltyPointsRepository capacityPenaltyPointsRepository;
	
	@Mock
	CCSRepository ccsRepository;
	
	@Mock
    ModelMapper modelMapper;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void getCustomerCapacity() {
		testMthd = testClass + ".getCustomerCapacity().";
		boolean success = true;
		final String ccsFishBuyerId = "FBY10086C";
		final String ifisCustomerId = "4052";
		
		this.doLog("T E S T - " + testMthd);

		try {
			// ####################### Get by CCS ID ####################### ...
			MvcResult mvcResult = mockMvc.perform(get("/sfos/capacity/ccs/" + ccsFishBuyerId).accept(MediaType.APPLICATION_JSON)).andReturn();
			assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
			assertEquals(content()!=null, true);
			
			// Negative test - Invalid URL ...
			mvcResult = mockMvc.perform(get("/sfos/capacity/ccs-INVALID/" + ccsFishBuyerId).accept(MediaType.APPLICATION_JSON)).andReturn();
			assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.NOT_FOUND.value());
			
			// ####################### Get by IFIS ID ####################### ...
			mvcResult = mockMvc.perform(get("/sfos/capacity/ifis/" + ifisCustomerId).accept(MediaType.APPLICATION_JSON)).andReturn();
			assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
			assertEquals(content()!=null, true);
			
			// Negative test - Invalid URL ...
			mvcResult = mockMvc.perform(get("/sfos/capacity/ifis-INVALID/" + ifisCustomerId).accept(MediaType.APPLICATION_JSON)).andReturn();
			assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.NOT_FOUND.value());
			
			success = true;
		} catch (Exception e) {
			this.doLog("T E S T - " + testMthd + " - error:" + e.getMessage());
			e.printStackTrace();
			success = false;
		}

		assertTrue("T E S T - " + testMthd + " assert true.", success);
		
		this.doLog("T E S T - " + testMthd + " complete: success = " + success);
	}
	
	private void doLog (final String in) {
		if (doLogging) {
			LOGGER.info(in);
		}
	}
}

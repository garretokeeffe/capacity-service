package ie.gov.agriculture.fisheries.la.capacityservice.service;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ie.gov.agriculture.fisheries.la.capacityservice.CapaityServiceApplication;
import ie.gov.agriculture.fisheries.la.capacityservice.dto.AllCapacityDTO;
import ie.gov.agriculture.fisheries.la.capacityservice.exception.ResourceNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CapaityServiceApplication.class)
public class CapacityServiceTest {
	private boolean doLogging = true;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CapacityServiceTest.class);
	
	private String testClass = "CapacityServiceTest";
	private String testMthd = "";
	
	@Autowired
	CustomerCapacityService customerCapacityService;
	
	@Test
	public void getAllCapacityByCcsID_Async() {
		testMthd = testClass + ".getAllCapacityByCcsID_Async().";
		boolean success = true;
		final String customerId = "FBY10086C";
		
		this.doLog("T E S T - " + testMthd);

		try {
			AllCapacityDTO allCapacityDTO = customerCapacityService.getAllCapacity(customerId, true, true);
			
			assertTrue(testMthd + " allCapacityDTO!=null assert true.", allCapacityDTO!=null);
			
			assertTrue(testMthd + " '2957'.equalsIgnoreCase(allCapacityDTO.getOwnerId()) assert true.", "2957".equalsIgnoreCase(allCapacityDTO.getOwnerId()));
			
			assertTrue(testMthd + " allCapacityDTO.getOnRegister()!=null assert true.", allCapacityDTO.getOnRegister()!=null);
			assertTrue(testMthd + " allCapacityDTO.getOffRegister()!=null assert true.", allCapacityDTO.getOffRegister()!=null);
			
			success = true;
		} catch (Exception e) {
			this.doLog("T E S T - " + testMthd + " - error:" + e.getMessage());
			e.printStackTrace();
			success = false;
		}

		assertTrue("T E S T - " + testMthd + " assert true.", success);
		
		this.doLog("T E S T - " + testMthd + " complete: success = " + success);
	}
	
	@Test
	public void getAllCapacityByCcsID_Sync() {
		testMthd = testClass + ".getAllCapacityByCcsID_Sync().";
		boolean success = true;
		final String customerId = "FBY10086C";
		
		this.doLog("T E S T - " + testMthd);

		try {
			AllCapacityDTO allCapacityDTO = customerCapacityService.getAllCapacity(customerId, true, false);
			
			assertTrue(testMthd + " allCapacityDTO!=null assert true.", allCapacityDTO!=null);
			
			assertTrue(testMthd + " '2957'.equalsIgnoreCase(allCapacityDTO.getOwnerId()) assert true.", "2957".equalsIgnoreCase(allCapacityDTO.getOwnerId()));
			
			assertTrue(testMthd + " allCapacityDTO.getOnRegister()!=null assert true.", allCapacityDTO.getOnRegister()!=null);
			assertTrue(testMthd + " allCapacityDTO.getOffRegister()!=null assert true.", allCapacityDTO.getOffRegister()!=null);
			
			success = true;
		} catch (Exception e) {
			this.doLog("T E S T - " + testMthd + " - error:" + e.getMessage());
			e.printStackTrace();
			success = false;
		}

		assertTrue("T E S T - " + testMthd + " assert true.", success);
		
		this.doLog("T E S T - " + testMthd + " complete: success = " + success);
	}
	
	@Test
	public void getAllCapacityByIfisID_Async() {
		testMthd = testClass + ".getAllCapacityByIfisID_Async().";
		boolean success = true;
		final String customerId = "2957";
		
		this.doLog("T E S T - " + testMthd);

		try {
			AllCapacityDTO allCapacityDTO = customerCapacityService.getAllCapacity(customerId, false, true);
			
			assertTrue(testMthd + " allCapacityDTO!=null assert true.", allCapacityDTO!=null);
			
			assertTrue(testMthd + " '2957'.equalsIgnoreCase(allCapacityDTO.getOwnerId()) assert true.", "2957".equalsIgnoreCase(allCapacityDTO.getOwnerId()));
			
			assertTrue(testMthd + " allCapacityDTO.getOnRegister()!=null assert true.", allCapacityDTO.getOnRegister()!=null);
			assertTrue(testMthd + " allCapacityDTO.getOffRegister()!=null assert true.", allCapacityDTO.getOffRegister()!=null);
			
			success = true;
		} catch (Exception e) {
			this.doLog("T E S T - " + testMthd + " - error:" + e.getMessage());
			e.printStackTrace();
			success = false;
		}

		assertTrue("T E S T - " + testMthd + " assert true.", success);
		
		this.doLog("T E S T - " + testMthd + " complete: success = " + success);
	}
	
	@Test
	public void getAllCapacityByIfisID_Sync() {
		testMthd = testClass + ".getAllCapacityByIfisID_Sync().";
		boolean success = true;
		final String customerId = "2957";
		
		this.doLog("T E S T - " + testMthd);

		try {
			AllCapacityDTO allCapacityDTO = customerCapacityService.getAllCapacity(customerId, false, false);
			
			assertTrue(testMthd + " allCapacityDTO!=null assert true.", allCapacityDTO!=null);
			
			assertTrue(testMthd + " '2957'.equalsIgnoreCase(allCapacityDTO.getOwnerId()) assert true.", "2957".equalsIgnoreCase(allCapacityDTO.getOwnerId()));
			
			assertTrue(testMthd + " allCapacityDTO.getOnRegister()!=null assert true.", allCapacityDTO.getOnRegister()!=null);
			assertTrue(testMthd + " allCapacityDTO.getOffRegister()!=null assert true.", allCapacityDTO.getOffRegister()!=null);
			
			success = true;
		} catch (Exception e) {
			this.doLog("T E S T - " + testMthd + " - error:" + e.getMessage());
			e.printStackTrace();
			success = false;
		}

		assertTrue("T E S T - " + testMthd + " assert true.", success);
		
		this.doLog("T E S T - " + testMthd + " complete: success = " + success);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void getAllCapacityByIfisID_Async_ValidCustomerWithNoCapacity() throws ResourceNotFoundException {
		testMthd = testClass + ".getAllCapacityByIfisID_Async_ValidCustomerWithNoCapacity().";
		final String customerId = "140824327";
		
		this.doLog("T E S T - " + testMthd);

		try {
			customerCapacityService.getAllCapacity(customerId, false, true);
		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			try {
				throw e;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void getAllCapacityByCcsID_Async_NegativeTest_ExceptionSatisified() throws ResourceNotFoundException {
		testMthd = testClass + ".getAllCapacityByCcsID_Async_NegativeTest_ExceptionSatisified().";
		final String customerId = "INVALID";
		
		this.doLog("T E S T - " + testMthd);

		try {
			customerCapacityService.getAllCapacity(customerId, true, true);
		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			try {
				throw e;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test
	public void getIfisIdByCcsId() {
		testMthd = testClass + ".getIfisIdByCcsId().";
		boolean success = true;
		final String customerId = "FBY10063J";
		
		this.doLog("T E S T - " + testMthd);

		try {
			String ifisId = customerCapacityService.getIfisIdByCcsId(customerId);
			
			assertTrue(testMthd + " ifisId!=null assert true.", ifisId!=null);
			
			assertTrue(testMthd + " '150266852'.equalsIgnoreCase(ifisId) assert true.", "150266852".equalsIgnoreCase(ifisId));
			
			success = true;
		} catch (Exception e) {
			this.doLog("T E S T - " + testMthd + " - error:" + e.getMessage());
			e.printStackTrace();
			success = false;
		}

		assertTrue("T E S T - " + testMthd + " assert true.", success);
		
		this.doLog("T E S T - " + testMthd + " complete: success = " + success);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void getIfisIdByCcsId_NegativeTest_ExceptionSatisified() throws ResourceNotFoundException {
		testMthd = testClass + ".getIfisIdByCcsId_NegativeTest_ExceptionSatisified().";
		final String customerId = "INVALID";
		
		this.doLog("T E S T - " + testMthd);

		try {
			customerCapacityService.getIfisIdByCcsId(customerId);
		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void doLog (final String in) {
		if (doLogging) {
			LOGGER.info(in);
		}
	}
}
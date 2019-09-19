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
	public void getAllCapacity() {
		testMthd = testClass + ".getAllCapacity().";
		boolean success = true;
		final String customerId = "4052";
		
		this.doLog("T E S T - " + testMthd);

		try {
			AllCapacityDTO allCapacityDTO = customerCapacityService.getAllCapacity(customerId);
			
			assertTrue(testMthd + " allCapacityDTO!=null assert true.", allCapacityDTO!=null);
			
			assertTrue(testMthd + " '4052'.equalsIgnoreCase(allCapacityDTO.getOwnerId()) assert true.", "4052".equalsIgnoreCase(allCapacityDTO.getOwnerId()));
			
			assertTrue(testMthd + " allCapacityDTO.getOnRegister().size()>0 assert true.", allCapacityDTO.getOnRegister().size()>0);
			assertTrue(testMthd + " allCapacityDTO.getOffRegister().size()>0 assert true.", allCapacityDTO.getOffRegister().size()>0);
			
			assertTrue(testMthd + " allCapacityDTO.getOnRegister().size()==1 assert true.", allCapacityDTO.getOnRegister().size()==1);
			assertTrue(testMthd + " allCapacityDTO.getOffRegister().size()==2 assert true.", allCapacityDTO.getOffRegister().size()==1);
			
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
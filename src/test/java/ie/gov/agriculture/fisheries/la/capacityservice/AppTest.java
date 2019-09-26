package ie.gov.agriculture.fisheries.la.capacityservice;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ie.gov.agriculture.fisheries.la.capacityservice.repository.CapacityRepositoryTest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CapaityServiceApplication.class)
public class AppTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(CapacityRepositoryTest.class);
	
	@Test
	public void testCapaityServiceApplication() {
		boolean success = false;

		try {
			CapaityServiceApplication.main(new String[] {});
			
			success = true;
		} catch (Exception e) {}

		assertTrue("T E S T - AppTest.testCapaityServiceApplication assert true.", success);
		
		LOGGER.info("T E S T - AppTest.testCapaityServiceApplication complete: success = " + success);
	}
}

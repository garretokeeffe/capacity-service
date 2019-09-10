package ie.gov.agriculture.fisheries.la.capacityservice;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles(profiles = "tomcat")
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {
	@Test
	public void testLAWebApplication() {
		boolean success = false;

		try {
			CapaityServiceApplication.main(new String[] {});
			
			success = true;
		} catch (Exception e) {}

		assertTrue("CapaityServiceApplication assert true.", success);
	}
}

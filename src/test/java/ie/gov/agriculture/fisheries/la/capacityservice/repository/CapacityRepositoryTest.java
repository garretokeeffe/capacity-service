package ie.gov.agriculture.fisheries.la.capacityservice.repository;

import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ie.gov.agriculture.fisheries.la.capacityservice.CapaityServiceApplication;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.Capacity;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.CapacityDetail;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.PenaltyPoints;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.VesselSummary;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CapaityServiceApplication.class)
public class CapacityRepositoryTest {
	private boolean doLogging = true;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CapacityRepositoryTest.class);
	
	private String testClass = "CapacityRepositoryTest";
	private String testMthd = "";
	
	@Autowired
	CapacityRepository capacityRepository;
	
	@Autowired
	CustomerCapacityDetailRepository customerCapacityDetailRepository;
	
	@Autowired
	VesselSummaryRepository vesselSummaryRepository;
	
	@Autowired
	CapacityPenaltyPointsRepository capacityPenaltyPointsRepository;
	
	@Test
	public void findCapacityByOwnerId() {
		testMthd = testClass + ".findCapacityByOwnerId().";
		boolean success = true;
		final String customerId = "4052";
		
		this.doLog("T E S T - " + testMthd);

		try {
			List<Capacity> capacity = capacityRepository.findCapacityByOwnerId(customerId);
			
			assertTrue(testMthd + " capacity!=null assert true.", capacity!=null);
			
			assertTrue(testMthd + " capacity.size()>0 assert true.", capacity.size()>0);
			
			assertTrue(testMthd + " capacity.size()==2 assert true.", capacity.size()==2);
			
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
	public void findCapacityDetailByCapAccountId() {
		testMthd = testClass + ".findCapacityDetailByCapAccountId().";
		boolean success = true;
		final int capAccountId = 297458102;
		
		this.doLog("T E S T - " + testMthd);

		try {
			List<CapacityDetail> capacityDetail = customerCapacityDetailRepository.findCapacityDetailByCapAccountId(capAccountId);
			
			assertTrue(testMthd + " capacityDetail!=null assert true.", capacityDetail!=null);
			
			assertTrue(testMthd + " capacityDetail.size()>0 assert true.", capacityDetail.size()>0);
			
			assertTrue(testMthd + " capacity.size()==4 assert true.", capacityDetail.size()==4);
			
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
	public void findVesselSummaryByVesselId() {
		testMthd = testClass + ".findVesselSummaryByVesselId().";
		boolean success = true;
		final int vesselId = 4329;
		
		this.doLog("T E S T - " + testMthd);

		try {
			VesselSummary vesselSummary = vesselSummaryRepository.findVesselSummaryByVesselId(vesselId);
			
			assertTrue(testMthd + " vesselSummary!=null assert true.", vesselSummary!=null);
			
			assertTrue(testMthd + " 'BRIDGET CARMEL'.equalsIgnoreCase(vesselSummary.getName()) assert true.", "BRIDGET CARMEL".equalsIgnoreCase(vesselSummary.getName()));
			
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
	public void findCustomerCapacityPenaltyPointsByCapAccountId() {
		testMthd = testClass + ".findCustomerCapacityPenaltyPointsByCapAccountId().";
		boolean success = true;
		final int capAccountId = 297458102;
		
		this.doLog("T E S T - " + testMthd);

		try {
			List<PenaltyPoints> points = capacityPenaltyPointsRepository.findCustomerCapacityPenaltyPointsByCapAccountId(capAccountId);
			
			assertTrue(testMthd + " points!=null assert true.", points!=null);
			
			assertTrue(testMthd + " points.size()>0 assert true.", points.size()>0);
			
			assertTrue(testMthd + " points.size()==4 assert true.", points.size()==4);
			
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
package ie.gov.agriculture.fisheries.la.capacityservice.repository;

import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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
import ie.gov.agriculture.fisheries.la.capacityservice.entity.IfisWrapper;
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
	
	@Autowired
	CCSRepository cCSRepository;
	
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
			
			/* Capacity records may change over time and impact reliability of below tests */
			//assertTrue(testMthd + " capacity.size()==2 assert true.", capacity.size()==1);
			
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
			CompletableFuture<List<CapacityDetail>> capacityDetail = customerCapacityDetailRepository.findCapacityDetailByCapAccountId(capAccountId);
			
			assertTrue(testMthd + " capacityDetail!=null assert true.", capacityDetail!=null);
			
			assertTrue(testMthd + " capacityDetail.get()!=null assert true.", capacityDetail.get()!=null);
			
			assertTrue(testMthd + " capacityDetail.size()>0 assert true.", capacityDetail.get().size()>0);
			
			/* Capacity records may change over time and impact reliability of below tests */
			//assertTrue(testMthd + " capacity.size()==4 assert true.", capacityDetail.get().size()==4);
			
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
			CompletableFuture<VesselSummary> vesselSummary = vesselSummaryRepository.findVesselSummaryByVesselId(vesselId);
			
			assertTrue(testMthd + " vesselSummary!=null assert true.", vesselSummary.get()!=null);
			
			assertTrue(testMthd + " 'BRIDGET CARMEL'.equalsIgnoreCase(vesselSummary.getName()) assert true.", "BRIDGET CARMEL".equalsIgnoreCase(vesselSummary.get().getName()));
			
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
			CompletableFuture<List<PenaltyPoints>> points = capacityPenaltyPointsRepository.findCustomerCapacityPenaltyPointsByCapAccountId(capAccountId);
			
			assertTrue(testMthd + " points!=null assert true.", points!=null);
			
			assertTrue(testMthd + " points!=null assert true.", points.get()!=null);
			
			assertTrue(testMthd + " points.size()>0 assert true.", points.get().size()>0);
			
			/* Capacity records may change over time and impact reliability of below tests */
			//assertTrue(testMthd + " points.size()==4 assert true.", points.get().size()==4);
			
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
	public void findIfisIdByCcsId() {
		testMthd = testClass + ".findIfisIdByCcsId().";
		boolean success = true;
		final String ccsId = "FBY10063J";
		
		this.doLog("T E S T - " + testMthd);

		try {
			List<IfisWrapper> results = cCSRepository.findIfisIdByCcsId(ccsId);
			
			assertTrue(testMthd + " results!=null assert true.", results!=null);
			assertTrue(testMthd + " results.get(0)!=null assert true.", results.get(0)!=null);
			
			String ifisId = results.get(0).getIfisId();
			
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
	
	private void doLog (final String in) {
		if (doLogging) {
			LOGGER.info(in);
		}
	}
}
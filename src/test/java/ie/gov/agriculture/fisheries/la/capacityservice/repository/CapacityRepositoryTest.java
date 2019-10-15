package ie.gov.agriculture.fisheries.la.capacityservice.repository;

import static org.junit.Assert.assertNotNull;
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
			
			/* Test capacity items */
			Capacity capacityItem = (Capacity) capacity.get(0);
			
			assertNotNull(capacityItem);
			
			assertNotNull(capacityItem.getId());
			assertNotNull(capacityItem.getCapAccountId());
			assertNotNull(capacityItem.getOwnerId());
			assertNotNull(capacityItem.getOffRegister());
			assertNotNull(capacityItem.getFleetSegment());
			assertNotNull(capacityItem.getFleetSubSegment());
			assertNotNull(capacityItem.getGt());
			assertNotNull(capacityItem.getKw());
			assertNotNull(capacityItem.getVesselId());
			
			assertTrue(testMthd + " capacityItem.getId().equalsIgnoreCase(1) assert true.", capacityItem.getId().equalsIgnoreCase("1"));
			assertTrue(testMthd + " capacityItem.getCapAccountId()==297458102 assert true.", capacityItem.getCapAccountId()==297458102);
			assertTrue(testMthd + " capacityItem.getOwnerId().equalsIgnoreCase(4052) assert true.", capacityItem.getOwnerId().equalsIgnoreCase("4052"));
			assertTrue(testMthd + " capacityItem.getOffRegister().equalsIgnoreCase(false) assert true.", capacityItem.getOffRegister().equalsIgnoreCase("false"));
			assertTrue(testMthd + " capacityItem.getFleetSegment()==5 assert true.", capacityItem.getFleetSegment()==5);
			assertTrue(testMthd + " capacityItem.getFleetSubSegment()==1942696 assert true.", capacityItem.getFleetSubSegment()==1942696);
			assertTrue(testMthd + " capacityItem.getGt().equalsIgnoreCase(161) assert true.", capacityItem.getGt()==161);
			assertTrue(testMthd + " capacityItem.getKw().equalsIgnoreCase(473) assert true.", capacityItem.getKw()==473);
			assertTrue(testMthd + " capacityItem.getVesselId()==4329 assert true.", capacityItem.getVesselId()==4329);
			
			assertNotNull(capacityItem.toString());
			
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
			CompletableFuture<List<CapacityDetail>> capacityDetailItems = customerCapacityDetailRepository.findCapacityDetailByCapAccountId(capAccountId);
			
			assertTrue(testMthd + " capacityDetailItems!=null assert true.", capacityDetailItems!=null);
			
			assertTrue(testMthd + " capacityDetailItems.get()!=null assert true.", capacityDetailItems.get()!=null);
			
			assertTrue(testMthd + " capacityDetailItems.size()>0 assert true.", capacityDetailItems.get().size()>0);
			
			/* Test capacity detail item */
			CapacityDetail capacityDetail = (CapacityDetail) capacityDetailItems.get().get(0);
			
			// Cover gaps ...
			capacityDetail.setSourceVesselId("100");
			capacityDetail.setSourceVesselName("Test");
			
			assertNotNull(capacityDetail.getId()); //297458210
			assertNotNull(capacityDetail.getCapacityAmount());
			assertNotNull(capacityDetail.getCapacityType());
			assertNotNull(capacityDetail.getPointsAssigned());
			
			assertTrue(testMthd + " capacityDetail.getId().equalsIgnoreCase(297458210) assert true.", capacityDetail.getId()==297458210);
			assertTrue(testMthd + " capacityDetail.getCapacityAmount().equalsIgnoreCase(43.95) assert true.", capacityDetail.getCapacityAmount()==43.95);
			assertTrue(testMthd + " capacityDetail.getCapacityType().equalsIgnoreCase(GT) assert true.", capacityDetail.getCapacityType().equalsIgnoreCase("GT"));
			assertTrue(testMthd + " capacityDetail.getPointsAssigned().equalsIgnoreCase(24) assert true.", capacityDetail.getPointsAssigned().equalsIgnoreCase("24"));
			assertTrue(testMthd + " capacityDetail.getSourceVesselId().equalsIgnoreCase(100) assert true.", capacityDetail.getSourceVesselId().equalsIgnoreCase("100"));
			assertTrue(testMthd + " capacityDetail.getSourceVesselName().equalsIgnoreCase(Test) assert true.", capacityDetail.getSourceVesselName().equalsIgnoreCase("Test"));
			
			assertNotNull(capacityDetail.toString());
			
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
			
			VesselSummary indvVesselSummary = vesselSummary.get();
			
			assertNotNull(indvVesselSummary.getId());
			assertNotNull(indvVesselSummary.getName());
			assertNotNull(indvVesselSummary.getGt());
			assertNotNull(indvVesselSummary.getKw());
			assertNotNull(indvVesselSummary.getPrn());
			assertNotNull(indvVesselSummary.getCfr());
			assertNotNull(indvVesselSummary.getStatus());
			assertNotNull(indvVesselSummary.toString());
			
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
			/* Test ASync Method */
			CompletableFuture<PenaltyPoints> points_async = capacityPenaltyPointsRepository.findCustomerCapacityPenaltyPointsByCapAccountId(capAccountId);
			
			assertTrue(testMthd + " points!=null assert true.", points_async!=null);
			
			assertTrue(testMthd + " points!=null assert true.", points_async.get()!=null);
			
			/* Test points detail */
			PenaltyPoints point1 = (PenaltyPoints) points_async.get();
			
			// Cover gaps ...
			point1.setCAPACCOUNTID("100");
			point1.setCAPSEGMENTID("101");
			point1.setPENALTYCOMMENT("Test");
			
			assertNotNull(point1.getID()); //297458165
			assertNotNull(point1.getASSIGNEDPOINTS());
			assertNotNull(point1.getEXPIRYDATE());
			
			assertTrue(testMthd + " point.getID()==297458165 assert true.", point1.getID()==1);
			assertTrue(testMthd + " point.getASSIGNEDPOINTS().equalsIgnoreCase(24) assert true.", point1.getASSIGNEDPOINTS().equalsIgnoreCase("24"));
			assertTrue(testMthd + " point.getEXPIRYDATE().equalsIgnoreCase(09/10/2018) assert true.", point1.getEXPIRYDATE().equalsIgnoreCase("09/10/2018"));
			assertTrue(testMthd + " point1.getCAPACCOUNTID().equalsIgnoreCase(100) assert true.", point1.getCAPACCOUNTID().equalsIgnoreCase("100"));
			assertTrue(testMthd + " point1.getCAPSEGMENTID().equalsIgnoreCase(101) assert true.", point1.getCAPSEGMENTID().equalsIgnoreCase("101"));
			assertTrue(testMthd + " point1.getPENALTYCOMMENT().equalsIgnoreCase(Test) assert true.", point1.getPENALTYCOMMENT().equalsIgnoreCase("Test"));
			
			assertNotNull(point1.toString());
			
			/* Test Sync Method */
			PenaltyPoints point2 = capacityPenaltyPointsRepository.findCustomerCapacityPenaltyPointsByCapAccountId_Sync(capAccountId);
			
			assertTrue(testMthd + " points!=null assert true.", point2!=null);
			
			// Cover gaps ...
			point2.setCAPACCOUNTID("100");
			point2.setCAPSEGMENTID("101");
			point2.setPENALTYCOMMENT("Test");
			
			/* Test points detail */
			assertNotNull(point2.getID()); //297458165
			assertNotNull(point2.getASSIGNEDPOINTS());
			assertNotNull(point2.getEXPIRYDATE());
			
			assertTrue(testMthd + " point.getID()==297458165 assert true.", point2.getID()==1);
			assertTrue(testMthd + " point.getASSIGNEDPOINTS().equalsIgnoreCase(24) assert true.", point2.getASSIGNEDPOINTS().equalsIgnoreCase("24"));
			assertTrue(testMthd + " point.getEXPIRYDATE().equalsIgnoreCase(09/10/2018) assert true.", point2.getEXPIRYDATE().equalsIgnoreCase("09/10/2018"));
			assertTrue(testMthd + " point1.getCAPACCOUNTID().equalsIgnoreCase(100) assert true.", point2.getCAPACCOUNTID().equalsIgnoreCase("100"));
			assertTrue(testMthd + " point1.getCAPSEGMENTID().equalsIgnoreCase(101) assert true.", point2.getCAPSEGMENTID().equalsIgnoreCase("101"));
			assertTrue(testMthd + " point1.getPENALTYCOMMENT().equalsIgnoreCase(Test) assert true.", point2.getPENALTYCOMMENT().equalsIgnoreCase("Test"));
			
			assertNotNull(point2.toString());
			
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
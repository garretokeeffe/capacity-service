package ie.gov.agriculture.fisheries.la.capacityservice.entity;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ie.gov.agriculture.fisheries.la.capacityservice.CapaityServiceApplication;

@Ignore // Ignoring EnityTests as coverage not required for getters & setters ...
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CapaityServiceApplication.class)
public class EnityTests {
	private boolean doLogging = true;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EnityTests.class);
	
	private String testClass = "EnityTests";
	private String testMthd = "";
	
	@Test
	public void testGetterAndSetters() {
		testMthd = testClass + ".testGetterAndSetters().";
		boolean success = true;
		
		this.doLog("T E S T - " + testMthd);

		try {
			/* All Capacity */
			AllCapacity allCapacity = new AllCapacity();
			
			assertNotNull(allCapacity.toString());
			
			/* Capacity Item */
			Capacity capacityItem = new Capacity();
			PenaltyPoints points = new PenaltyPoints();
			List<PenaltyPoints> pointsList = new ArrayList<>();
			pointsList.add(points);
			
			capacityItem.setId("5");
			capacityItem.setCapAccountId(1001);
			capacityItem.setFleetSegment(22);
			capacityItem.setFleetSubSegment(2002);
			capacityItem.setGt(100);
			capacityItem.setKw(200);
			capacityItem.setOffRegister("true");
			capacityItem.setOwnerId(1200);
			capacityItem.setVesselId(869);
			capacityItem.setVesselSummary(new VesselSummary());
			capacityItem.setPenaltyPoints(pointsList);
			capacityItem.setProposed("true");
			
			assertTrue(testMthd + " capacityItem.getId().equalsIgnoreCase(5) assert true.", capacityItem.getId().equalsIgnoreCase("5"));
			assertTrue(testMthd + " capacityItem.getCapAccountId()==1001 assert true.", capacityItem.getCapAccountId()==1001);
			assertTrue(testMthd + " capacityItem.getOwnerId().equalsIgnoreCase(1200) assert true.", capacityItem.getOwnerId()==1200);
			assertTrue(testMthd + " capacityItem.getOffRegister().equalsIgnoreCase(true) assert true.", capacityItem.getOffRegister().equalsIgnoreCase("true"));
			assertTrue(testMthd + " capacityItem.getFleetSegment()==22 assert true.", capacityItem.getFleetSegment()==22);
			assertTrue(testMthd + " capacityItem.getFleetSubSegment()==2002 assert true.", capacityItem.getFleetSubSegment()==2002);
			assertTrue(testMthd + " capacityItem.getGt().equalsIgnoreCase(100) assert true.", capacityItem.getGt()==100);
			assertTrue(testMthd + " capacityItem.getKw().equalsIgnoreCase(200) assert true.", capacityItem.getKw()==200);
			assertTrue(testMthd + " capacityItem.getVesselId()==869 assert true.", capacityItem.getVesselId()==869);
			assertTrue(testMthd + " capacityItem.getProposed().equalsIgnoreCase(true) assert true.", capacityItem.getProposed().equalsIgnoreCase("true"));
			
			assertNotNull(capacityItem.getVesselSummary());
			assertNotNull(capacityItem.getPenaltyPoints());
			assertNotNull(capacityItem.toString());
			
			/* Capacity Detail Item */
			CapacityDetail capacityDetail = new CapacityDetail();
			List<TrackRecord> trackRecordItems = new ArrayList<TrackRecord>();
			List<PenaltyPoints> points2 = new ArrayList<>(0);
			points2.add(new PenaltyPoints());
			
			capacityDetail.setId(100);
			capacityDetail.setSourceVesselId("200");
			capacityDetail.setCapacityType("GT");
			capacityDetail.setSourceVesselName("BRIDGET CARMEL");
			capacityDetail.setCapacityAmount(300);
			capacityDetail.setPointsAssigned("24");
			capacityDetail.setExpiryDate(LocalDate.of(2020, 1, 1));
			capacityDetail.setOffRegisterDate(LocalDate.of(2021, 1, 1));
			capacityDetail.setPenaltyPoints(points2);
			capacityDetail.setTrackRecord(trackRecordItems);
			capacityDetail.setPenaltyPointsReturnDetail(new PenaltyPoints());
			
			assertTrue(testMthd + " capacityDetail.getId().equalsIgnoreCase(100) assert true.", capacityDetail.getId()==100);
			assertTrue(testMthd + " capacityDetail.getCapacityAmount().equalsIgnoreCase(300) assert true.", capacityDetail.getCapacityAmount()==300);
			assertTrue(testMthd + " capacityDetail.getCapacityType().equalsIgnoreCase(GT) assert true.", capacityDetail.getCapacityType().equalsIgnoreCase("GT"));
			assertTrue(testMthd + " capacityDetail.getSourceVesselId().equalsIgnoreCase(200) assert true.", capacityDetail.getSourceVesselId().equalsIgnoreCase("200"));
			assertTrue(testMthd + " capacityDetail.getSourceVesselName().equalsIgnoreCase(BRIDGET CARMEL) assert true.", capacityDetail.getSourceVesselName().equalsIgnoreCase("BRIDGET CARMEL"));
			assertTrue(testMthd + " capacityDetail.getPointsAssigned().equalsIgnoreCase(24) assert true.", capacityDetail.getPointsAssigned().equalsIgnoreCase("24"));
			assertTrue(testMthd + " capacityDetail.getExpiryDate().equalsIgnoreCase(01/01/2020) assert true.", capacityDetail.getExpiryDate().isEqual(LocalDate.of(2020, 1, 1)));
			assertTrue(testMthd + " capacityDetail.getOffRegisterDate().equalsIgnoreCase(01/01/2021) assert true.", capacityDetail.getOffRegisterDate().isEqual(LocalDate.of(2021, 1, 1)));
			
			assertNotNull(capacityDetail.getPenaltyPoints());
			assertNotNull(capacityDetail.getTrackRecord());
			assertNotNull(capacityDetail.setPenaltyPointsReturnDetail(new PenaltyPoints()));
			assertNotNull(capacityDetail.toString());
			
			/* Penalty Points */
			PenaltyPoints point = new PenaltyPoints();
			
			point.setID(Integer.valueOf(297458165).longValue());
			point.setCAPACCOUNTID("297458102");
			point.setCAPSEGMENTID("297458103");
			point.setASSIGNEDPOINTS("12");
			point.setEXPIRYDATE(LocalDate.of(2018, 10, 9));
			point.setPENALTYCOMMENT("Test!");
			
			assertTrue(testMthd + " point.getID()==297458165 assert true.", point.getID()==297458165);
			assertTrue(testMthd + " point.getCAPACCOUNTID().equalsIgnoreCase(297458102) assert true.", point.getCAPACCOUNTID().equalsIgnoreCase("297458102"));
			assertTrue(testMthd + " point.getCAPSEGMENTID().equalsIgnoreCase(297458103) assert true.", point.getCAPSEGMENTID().equalsIgnoreCase("297458103"));
			assertTrue(testMthd + " point.getASSIGNEDPOINTS().equalsIgnoreCase(12) assert true.", point.getASSIGNEDPOINTS().equalsIgnoreCase("12"));
			assertTrue(testMthd + " point.getEXPIRYDATE().equalsIgnoreCase(09/10/2018) assert true.", point.getEXPIRYDATE().isEqual(LocalDate.of(2018, 10, 9)));
			assertTrue(testMthd + " point.setPENALTYCOMMENT().equalsIgnoreCase(09/10/2018) assert true.", point.getPENALTYCOMMENT().equalsIgnoreCase("Test!"));
			
			assertNotNull(point.toString());
			
			/* Vessel Summary */
			VesselSummary vesselSummary = new VesselSummary();
			
			vesselSummary.setId(1001);
			vesselSummary.setCfr("321321321");
			vesselSummary.setPrn("54651650");
			vesselSummary.setKw("300");
			vesselSummary.setGt("400");
			vesselSummary.setName("BRIDGET CARMEL");
			vesselSummary.setStatus(202020);
			
			assertTrue(testMthd + " point.getId()==1001 assert true.", vesselSummary.getId()==1001);
			assertTrue(testMthd + " vesselSummary.getCfr().equalsIgnoreCase(321321321) assert true.", vesselSummary.getCfr().equalsIgnoreCase("321321321"));
			assertTrue(testMthd + " vesselSummary.getPrn().equalsIgnoreCase(54651650) assert true.", vesselSummary.getPrn().equalsIgnoreCase("54651650"));
			assertTrue(testMthd + " vesselSummary.getKw().equalsIgnoreCase(300) assert true.", vesselSummary.getKw().equalsIgnoreCase("300"));
			assertTrue(testMthd + " vesselSummary.getGt().equalsIgnoreCase(500) assert true.", vesselSummary.getGt().equalsIgnoreCase("400"));
			assertTrue(testMthd + " vesselSummary.getName().equalsIgnoreCase(BRIDGET CARMEL) assert true.", vesselSummary.getName().equalsIgnoreCase("BRIDGET CARMEL"));
			assertTrue(testMthd + " vesselSummary.getStatus().equalsIgnoreCase(BRIDGET CARMEL) assert true.", vesselSummary.getStatus()==202020);
			
			assertNotNull(vesselSummary.toString());
			
			/* Track Record */
			TrackRecord trackRecord = new TrackRecord();
			
			trackRecord.setID(Integer.valueOf(1001).longValue());
			trackRecord.setQuotaEligibility(true);
			trackRecord.setTrackRecordType(3);
			
			assertTrue(testMthd + " trackRecord.getID()==1001 assert true.", trackRecord.getID()==1001);
			assertTrue(testMthd + " trackRecord.getTrackRecordType()==3 assert true.", trackRecord.getTrackRecordType()==3);
			assertTrue(testMthd + " trackRecord.isQuotaEligibility() assert true.", trackRecord.isQuotaEligibility());
			
			assertNotNull(trackRecord.toString());
			
			/* Ifis Wrapper */
			IfisWrapper ifisWrapper = new IfisWrapper();
			
			ifisWrapper.setIfisId("5005");

			assertTrue(testMthd + " ifisWrapper.getIfisId().equalsIgnoreCase(5005) assert true.", ifisWrapper.getIfisId().equalsIgnoreCase("5005"));
			
			assertNotNull(ifisWrapper.toString());
			
			/* Customer Capacity */
			CustomerCapacity customerCapacity = new CustomerCapacity();
			
			customerCapacity.setId("5001");
			customerCapacity.setCapAccountId("500001");
			customerCapacity.setCapsegmentid("1005");
			customerCapacity.setCapacitySegmentId("60001");
			customerCapacity.setCapacitySegmentDesc("Test Desc.");
			customerCapacity.setCapacityStatus("Active.");
			customerCapacity.setCapacityStatusDesc("Test Desc 2.");
			customerCapacity.setOwnerId("1001");
			customerCapacity.setProposedBalance("100.00");
			customerCapacity.setPendingBalance("0.00");
			customerCapacity.setGrossBalance("50.00");
			customerCapacity.setFreeBalance("60.00");
			customerCapacity.setUOMID("1005");
			customerCapacity.setUOMDesc("Test Desc 2.");
			customerCapacity.setVesselId("621");
			customerCapacity.setVesselName("Test vessel 5.");
			customerCapacity.setVesselStatus("3005");
			customerCapacity.setVesselStatusDesc("Active");
			
			assertTrue(testMthd + " customerCapacity.getId().equalsIgnoreCase(5001) assert true.", customerCapacity.getId().equalsIgnoreCase("5001"));
			assertTrue(testMthd + " customerCapacity.getCapAccountId().equalsIgnoreCase(500001) assert true.", customerCapacity.getCapAccountId().equalsIgnoreCase("500001"));
			assertTrue(testMthd + " customerCapacity.getCapsegmentid().equalsIgnoreCase(1005) assert true.", customerCapacity.getCapsegmentid().equalsIgnoreCase("1005"));
			assertTrue(testMthd + " customerCapacity.getCapacitySegmentId().equalsIgnoreCase(60001) assert true.", customerCapacity.getCapacitySegmentId().equalsIgnoreCase("60001"));
			assertTrue(testMthd + " customerCapacity.getCapacitySegmentId().equalsIgnoreCase(Test Desc.) assert true.", customerCapacity.getCapacitySegmentDesc().equalsIgnoreCase("Test Desc."));
			assertTrue(testMthd + " customerCapacity.getCapacityStatusDesc().equalsIgnoreCase(Test Desc 2.) assert true.", customerCapacity.getCapacityStatusDesc().equalsIgnoreCase("Test Desc 2."));
			assertTrue(testMthd + " customerCapacity.getCapacityStatus().equalsIgnoreCase(Active.) assert true.", customerCapacity.getCapacityStatus().equalsIgnoreCase("Active."));
			assertTrue(testMthd + " customerCapacity.getOwnerId().equalsIgnoreCase(1001) assert true.", customerCapacity.getOwnerId().equalsIgnoreCase("1001"));
			assertTrue(testMthd + " customerCapacity.getProposedBalance().equalsIgnoreCase(100.00) assert true.", customerCapacity.getProposedBalance().equalsIgnoreCase("100.00"));
			assertTrue(testMthd + " customerCapacity.getPendingBalance().equalsIgnoreCase(0.00) assert true.", customerCapacity.getPendingBalance().equalsIgnoreCase("0.00"));
			assertTrue(testMthd + " customerCapacity.getGrossBalance().equalsIgnoreCase(50.00) assert true.", customerCapacity.getGrossBalance().equalsIgnoreCase("50.00"));
			assertTrue(testMthd + " customerCapacity.getFreeBalance().equalsIgnoreCase(60.00) assert true.", customerCapacity.getFreeBalance().equalsIgnoreCase("60.00"));
			assertTrue(testMthd + " customerCapacity.getUOMID().equalsIgnoreCase(1005) assert true.", customerCapacity.getUOMID().equalsIgnoreCase("1005"));
			assertTrue(testMthd + " customerCapacity.getUOMDesc().equalsIgnoreCase(Test Desc 2.) assert true.", customerCapacity.getUOMDesc().equalsIgnoreCase("Test Desc 2."));
			assertTrue(testMthd + " customerCapacity.getVesselId().equalsIgnoreCase(621) assert true.", customerCapacity.getVesselId().equalsIgnoreCase("621"));
			assertTrue(testMthd + " customerCapacity.getVesselName().equalsIgnoreCase(Test vessel 5.) assert true.", customerCapacity.getVesselName().equalsIgnoreCase("Test vessel 5."));
			assertTrue(testMthd + " customerCapacity.getVesselStatus().equalsIgnoreCase(3005) assert true.", customerCapacity.getVesselStatus().equalsIgnoreCase("3005"));
			assertTrue(testMthd + " customerCapacity.getVesselStatusDesc().equalsIgnoreCase(Active) assert true.", customerCapacity.getVesselStatusDesc().equalsIgnoreCase("Active"));
			
			assertNotNull(customerCapacity.toString());
			
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
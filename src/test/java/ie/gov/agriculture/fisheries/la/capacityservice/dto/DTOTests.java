package ie.gov.agriculture.fisheries.la.capacityservice.dto;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ie.gov.agriculture.fisheries.la.capacityservice.CapaityServiceApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CapaityServiceApplication.class)
public class DTOTests {
	private boolean doLogging = true;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DTOTests.class);
	
	private String testClass = "DTOTests";
	private String testMthd = "";
	
	@Test
	public void testGetterAndSetters() {
		testMthd = testClass + ".testGetterAndSetters().";
		boolean success = true;
		
		this.doLog("T E S T - " + testMthd);

		try {
			/* All Capacity */
			AllCapacityDTO allCapacity = new AllCapacityDTO();
			
			assertNotNull(allCapacity.toString());
			
			/* Capacity Item */
			CapacityDTO capacityItemDto = new CapacityDTO();
			PenaltyPointsDTO pointsDto = new PenaltyPointsDTO();
			
			capacityItemDto.setCapAccountId("1001");
			capacityItemDto.setFleetSegment(22);
			capacityItemDto.setFleetSubSegment(2002);
			capacityItemDto.setGt(100);
			capacityItemDto.setKw(200);
			capacityItemDto.setOffRegister(true);
			capacityItemDto.setOwnerId("1200");
			capacityItemDto.setVesselId("869");
			capacityItemDto.setVesselSummary(new VesselSummaryDTO());
			capacityItemDto.setPenaltyPoints(pointsDto);
			capacityItemDto.setProposed("true");
			
			assertTrue(testMthd + " capacityItemDto.getCapAccountId().equalsIgnoreCase(1001) assert true.", capacityItemDto.getCapAccountId().equalsIgnoreCase("1001"));
			assertTrue(testMthd + " capacityItemDto.getOwnerId().equalsIgnoreCase(1200) assert true.", capacityItemDto.getOwnerId().equalsIgnoreCase("1200"));
			assertTrue(testMthd + " capacityItemDto.getOffRegister().equalsIgnoreCase(true) assert true.", capacityItemDto.isOffRegister());
			assertTrue(testMthd + " capacityItemDto.getFleetSegment().equalsIgnoreCase(22) assert true.", capacityItemDto.getFleetSegment()==22);
			assertTrue(testMthd + " capacityItemDto.getFleetSubSegment().equalsIgnoreCase(2002) assert true.", capacityItemDto.getFleetSubSegment()==2002);
			assertTrue(testMthd + " capacityItemDto.getGt().equalsIgnoreCase(100) assert true.", capacityItemDto.getGt()==100);
			assertTrue(testMthd + " capacityItemDto.getKw().equalsIgnoreCase(200) assert true.", capacityItemDto.getKw()==200);
			assertTrue(testMthd + " capacityItemDto.getVesselId().equalsIgnoreCase(869) assert true.", capacityItemDto.getVesselId().equalsIgnoreCase("869"));
			assertTrue(testMthd + " capacityItemDto.getProposed().equalsIgnoreCase(true) assert true.", capacityItemDto.getProposed().equalsIgnoreCase("true"));
			
			assertNotNull(capacityItemDto.getVesselSummary());
			assertNotNull(capacityItemDto.getPenaltyPoints());
			assertNotNull(capacityItemDto.toString());
			
			/* Capacity Detail Item */
			CapacityDetailDTO capacityDetailDto = new CapacityDetailDTO();
			List<TrackRecordDTO> trackRecordItemsDto = new ArrayList<TrackRecordDTO>();
			
			capacityDetailDto.setId(100);
			capacityDetailDto.setSourceVesselId("200");
			capacityDetailDto.setCapacityType("GT");
			capacityDetailDto.setSourceVesselName("BRIDGET CARMEL");
			capacityDetailDto.setCapacityAmount(300);
			capacityDetailDto.setPointsAssigned("24");
			capacityDetailDto.setExpiryDate("01/01/2020");
			capacityDetailDto.setOffRegisterDate("01/01/2021");
			capacityDetailDto.setTrackRecord(trackRecordItemsDto);
			
			assertTrue(testMthd + " capacityDetailDto.getId().equalsIgnoreCase(100) assert true.", capacityDetailDto.getId()==100);
			assertTrue(testMthd + " capacityDetailDto.getCapacityAmount().equalsIgnoreCase(300) assert true.", capacityDetailDto.getCapacityAmount()==300);
			assertTrue(testMthd + " capacityDetailDto.getCapacityType().equalsIgnoreCase(GT) assert true.", capacityDetailDto.getCapacityType().equalsIgnoreCase("GT"));
			assertTrue(testMthd + " capacityDetailDto.getSourceVesselId().equalsIgnoreCase(200) assert true.", capacityDetailDto.getSourceVesselId().equalsIgnoreCase("200"));
			assertTrue(testMthd + " capacityDetailDto.getSourceVesselName().equalsIgnoreCase(BRIDGET CARMEL) assert true.", capacityDetailDto.getSourceVesselName().equalsIgnoreCase("BRIDGET CARMEL"));
			assertTrue(testMthd + " capacityDetailDto.getPointsAssigned().equalsIgnoreCase(24) assert true.", capacityDetailDto.getPointsAssigned().equalsIgnoreCase("24"));
			assertTrue(testMthd + " capacityDetailDto.getExpiryDate().equalsIgnoreCase(01/01/2020) assert true.", capacityDetailDto.getExpiryDate().equalsIgnoreCase("01/01/2020"));
			assertTrue(testMthd + " capacityDetailDto.getOffRegisterDate().equalsIgnoreCase(01/01/2021) assert true.", capacityDetailDto.getOffRegisterDate().equalsIgnoreCase("01/01/2021"));
			
			assertNotNull(capacityDetailDto.getTrackRecord());
			assertNotNull(capacityDetailDto.toString());
			
			/* Penalty Points */
			PenaltyPointsDTO pointDto = new PenaltyPointsDTO();
			
			pointDto.setID(Integer.valueOf(297458165).longValue());
			pointDto.setCAPACCOUNTID("297458102");
			pointDto.setCAPSEGMENTID("297458103");
			pointDto.setASSIGNEDPOINTS("12");
			pointDto.setEXPIRYDATE("09/10/2018");
			pointDto.setPENALTYCOMMENT("Test!");
			
			assertTrue(testMthd + " pointDto.getID()==297458165 assert true.", pointDto.getID()==297458165);
			assertTrue(testMthd + " pointDto.getCAPACCOUNTID().equalsIgnoreCase(297458102) assert true.", pointDto.getCAPACCOUNTID().equalsIgnoreCase("297458102"));
			assertTrue(testMthd + " pointDto.getCAPSEGMENTID().equalsIgnoreCase(297458103) assert true.", pointDto.getCAPSEGMENTID().equalsIgnoreCase("297458103"));
			assertTrue(testMthd + " pointDto.getASSIGNEDPOINTS().equalsIgnoreCase(12) assert true.", pointDto.getASSIGNEDPOINTS().equalsIgnoreCase("12"));
			assertTrue(testMthd + " pointDto.getEXPIRYDATE().equalsIgnoreCase(09/10/2018) assert true.", pointDto.getEXPIRYDATE().equalsIgnoreCase("09/10/2018"));
			assertTrue(testMthd + " pointDto.setPENALTYCOMMENT().equalsIgnoreCase(09/10/2018) assert true.", pointDto.getPENALTYCOMMENT().equalsIgnoreCase("Test!"));
			
			assertNotNull(pointDto.toString());
			
			/* Vessel Summary */
			VesselSummaryDTO vesselSummaryDto = new VesselSummaryDTO();
			
			vesselSummaryDto.setId(1001);
			vesselSummaryDto.setCfr("321321321");
			vesselSummaryDto.setPrn("54651650");
			vesselSummaryDto.setKw("300");
			vesselSummaryDto.setGt("400");
			vesselSummaryDto.setName("BRIDGET CARMEL");
			vesselSummaryDto.setStatus(20022);
			
			assertTrue(testMthd + " point.getId()==1001 assert true.", vesselSummaryDto.getId()==1001);
			assertTrue(testMthd + " vesselSummaryDto.getCfr().equalsIgnoreCase(321321321) assert true.", vesselSummaryDto.getCfr().equalsIgnoreCase("321321321"));
			assertTrue(testMthd + " vesselSummaryDto.getPrn().equalsIgnoreCase(54651650) assert true.", vesselSummaryDto.getPrn().equalsIgnoreCase("54651650"));
			assertTrue(testMthd + " vesselSummaryDto.getKw().equalsIgnoreCase(300) assert true.", vesselSummaryDto.getKw().equalsIgnoreCase("300"));
			assertTrue(testMthd + " vesselSummaryDto.getGt().equalsIgnoreCase(500) assert true.", vesselSummaryDto.getGt().equalsIgnoreCase("400"));
			assertTrue(testMthd + " vesselSummaryDto.getName().equalsIgnoreCase(BRIDGET CARMEL) assert true.", vesselSummaryDto.getName().equalsIgnoreCase("BRIDGET CARMEL"));
			assertTrue(testMthd + " vesselSummaryDto.getStatus().equalsIgnoreCase(BRIDGET CARMEL) assert true.", vesselSummaryDto.getStatus()==20022);
			
			assertNotNull(vesselSummaryDto.toString());
			
			/* Track Record */
			TrackRecordDTO trackRecordDto = new TrackRecordDTO();
			
			trackRecordDto.setID(Integer.valueOf(1001).longValue());
			trackRecordDto.setQuotaEligibility(true);
			trackRecordDto.setTrackRecordType("NW Herring");
			
			assertTrue(testMthd + " trackRecordDto.getID()==1001 assert true.", trackRecordDto.getID()==1001);
			assertTrue(testMthd + " trackRecordDto.getTrackRecordType().equalsIgnoreCase(NW Herring) assert true.", trackRecordDto.getTrackRecordType().equalsIgnoreCase("NW Herring"));
			assertTrue(testMthd + " trackRecordDto.isQuotaEligibility() assert true.", trackRecordDto.isQuotaEligibility());
			
			assertNotNull(trackRecordDto.toString());
			
			/* Customer Capacity */
			CustomerCapacityDTO customerCapacityDto = new CustomerCapacityDTO();
			
			customerCapacityDto.setCapAccountId("500001");
			customerCapacityDto.setCapacitySegmentId("60001");
			customerCapacityDto.setCapacitySegmentDesc("Test Desc.");
			customerCapacityDto.setCapacityStatus("Active.");
			customerCapacityDto.setOwnerId("1001");
			customerCapacityDto.setGROSSBALANCE("100.00");
			customerCapacityDto.setPROPOSEDBALANCE("0.00");
			customerCapacityDto.setPENDINGBALANCE("50.00");
			customerCapacityDto.setFREEBALANCE("0.00");
			customerCapacityDto.setUOMID("1005");
			customerCapacityDto.setUOMDesc("Test Desc 2.");
			customerCapacityDto.setVesselId("621");
			customerCapacityDto.setVesselName("Test vessel 5.");
			customerCapacityDto.setVesselStatus("3005");
			customerCapacityDto.setVesselStatusDesc("Active");
			
			assertTrue(testMthd + " customerCapacityDto.getCapAccountId().equalsIgnoreCase(500001) assert true.", customerCapacityDto.getCapAccountId().equalsIgnoreCase("500001"));
			assertTrue(testMthd + " customerCapacityDto.getCapacitySegmentId().equalsIgnoreCase(60001) assert true.", customerCapacityDto.getCapacitySegmentId().equalsIgnoreCase("60001"));
			assertTrue(testMthd + " customerCapacityDto.getCapacitySegmentId().equalsIgnoreCase(Test Desc.) assert true.", customerCapacityDto.getCapacitySegmentDesc().equalsIgnoreCase("Test Desc."));
			assertTrue(testMthd + " customerCapacityDto.getCapacityStatus().equalsIgnoreCase(Active.) assert true.", customerCapacityDto.getCapacityStatus().equalsIgnoreCase("Active."));
			assertTrue(testMthd + " customerCapacityDto.getOwnerId().equalsIgnoreCase(1001) assert true.", customerCapacityDto.getOwnerId().equalsIgnoreCase("1001"));
			assertTrue(testMthd + " customerCapacityDto.getPROPOSEDBALANCE().equalsIgnoreCase(100.00) assert true.", customerCapacityDto.getPROPOSEDBALANCE().equalsIgnoreCase("0.00"));
			assertTrue(testMthd + " customerCapacityDto.getPENDINGBALANCE().equalsIgnoreCase(50.00) assert true.", customerCapacityDto.getPENDINGBALANCE().equalsIgnoreCase("50.00"));
			assertTrue(testMthd + " customerCapacityDto.getGROSSBALANCE().equalsIgnoreCase(100.00) assert true.", customerCapacityDto.getGROSSBALANCE().equalsIgnoreCase("100.00"));
			assertTrue(testMthd + " customerCapacityDto.getFREEBALANCE().equalsIgnoreCase(50.00) assert true.", customerCapacityDto.getFREEBALANCE().equalsIgnoreCase("0.00"));
			assertTrue(testMthd + " customerCapacityDto.getUOMID().equalsIgnoreCase(1005) assert true.", customerCapacityDto.getUOMID().equalsIgnoreCase("1005"));
			assertTrue(testMthd + " customerCapacityDto.getUOMDesc().equalsIgnoreCase(Test Desc 2.) assert true.", customerCapacityDto.getUOMDesc().equalsIgnoreCase("Test Desc 2."));
			assertTrue(testMthd + " customerCapacityDto.getVesselId().equalsIgnoreCase(621) assert true.", customerCapacityDto.getVesselId().equalsIgnoreCase("621"));
			assertTrue(testMthd + " customerCapacityDto.getVesselName().equalsIgnoreCase(Test vessel 5.) assert true.", customerCapacityDto.getVesselName().equalsIgnoreCase("Test vessel 5."));
			assertTrue(testMthd + " customerCapacityDto.getVesselStatus().equalsIgnoreCase(3005) assert true.", customerCapacityDto.getVesselStatus().equalsIgnoreCase("3005"));
			assertTrue(testMthd + " customerCapacityDto.getVesselStatusDesc().equalsIgnoreCase(Active) assert true.", customerCapacityDto.getVesselStatusDesc().equalsIgnoreCase("Active"));
			
			assertNotNull(customerCapacityDto.toString());
			
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

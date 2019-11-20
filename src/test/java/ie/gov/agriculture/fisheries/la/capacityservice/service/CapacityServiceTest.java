package ie.gov.agriculture.fisheries.la.capacityservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import ie.gov.agriculture.fisheries.la.capacityservice.CapaityServiceApplication;
import ie.gov.agriculture.fisheries.la.capacityservice.dto.AllCapacityDTO;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.Capacity;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.CapacityDetail;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.PenaltyPoints;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.VesselSummary;
import ie.gov.agriculture.fisheries.la.capacityservice.exception.ResourceNotFoundException;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CapacityPenaltyPointsRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CapacityRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.CustomerCapacityDetailRepository;
import ie.gov.agriculture.fisheries.la.capacityservice.repository.VesselSummaryRepository;
import org.junit.platform.runner.JUnitPlatform;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class CapacityServiceTest {
	private static final String CUSTOMER_ID = "2957";
	private static final Integer CAPP_ACCOUNT_ID = 296123425;
	private static final Integer CAPP_ACCOUNT_ID_2 = 296118221;
	private static final Integer CAPP_ACCOUNT_ID_3 = 296139281;
	private static final Integer CAPP_ACCOUNT_ID_4 = 296117114;
	
	private static final Integer VESSEL_ID = 292621439;
	private static final Integer VESSEL_ID_2 = 157377595;
	private static final Integer VESSEL_ID_3 = 13279;
	
	private static final Integer FLEET_SEGMENT_ID = 1;
	private static final Integer FLEET_SUBSEGMENT_ID = 338360921;
	
	@Mock
	CapacityRepository capacityRepository;
	
	@Mock
	CustomerCapacityDetailRepository customerCapacityDetailRepository;
	
	@Mock
	CapacityPenaltyPointsRepository capacityPenaltyPointsRepository;
	
	@Mock
	VesselSummaryRepository vesselSummaryRepository;
	
	@InjectMocks
	private CustomerCapacityService customerCapacityService;
	
	@Spy
	private ModelMapper modelMapper;
	
	@Test
	public void testGetCustomerCapacityInformation() throws ResourceNotFoundException, InterruptedException, ExecutionException {
		// ###############################
		// CapacityRepository
		// ###############################
		List<Capacity> capacityList = new ArrayList<>();

		// Mock high level capacity record ...
		Capacity capacity = new Capacity();
		capacity.setCapAccountId(CAPP_ACCOUNT_ID);
		capacity.setOwnerId(Integer.valueOf(CUSTOMER_ID).intValue());
		capacity.setOffRegister("false");
		capacity.setFleetSegment(FLEET_SEGMENT_ID);
		capacity.setFleetSubSegment(FLEET_SUBSEGMENT_ID);
		capacity.setGt(114.99);
		capacity.setKw(197.21);
		capacity.setVesselId(VESSEL_ID);
		capacity.setProposed(null);
		capacityList.add(capacity);
		
		Capacity capacity2 = new Capacity();
		capacity2.setCapAccountId(CAPP_ACCOUNT_ID_2);
		capacity.setOwnerId(Integer.valueOf(CUSTOMER_ID).intValue());
		capacity2.setOffRegister("false");
		capacity.setFleetSegment(FLEET_SEGMENT_ID);
		capacity2.setFleetSubSegment(FLEET_SUBSEGMENT_ID);
		capacity2.setGt(81.5);
		capacity2.setKw(199.5);
		capacity2.setVesselId(VESSEL_ID_2);
		capacity2.setProposed(null);
		capacityList.add(capacity2);
		
		Capacity capacity3 = new Capacity();
		capacity3.setCapAccountId(CAPP_ACCOUNT_ID_3);
		capacity.setOwnerId(Integer.valueOf(CUSTOMER_ID).intValue());
		capacity3.setOffRegister("false");
		capacity.setFleetSegment(FLEET_SEGMENT_ID);
		capacity3.setFleetSubSegment(FLEET_SUBSEGMENT_ID);
		capacity3.setGt(81.5);
		capacity3.setKw(200);
		capacity3.setVesselId(VESSEL_ID_3);
		capacity3.setProposed(null);
		capacityList.add(capacity3);
		
		Capacity capacity4 = new Capacity();
		capacity4.setCapAccountId(CAPP_ACCOUNT_ID_4);
		capacity.setOwnerId(Integer.valueOf(CUSTOMER_ID).intValue());
		capacity4.setOffRegister("true");
		capacity.setFleetSegment(FLEET_SEGMENT_ID);
		capacity4.setFleetSubSegment(FLEET_SUBSEGMENT_ID);
		capacity4.setGt(0);
		capacity4.setKw(2.5);
		capacity4.setVesselId(VESSEL_ID_3);
		capacity4.setProposed("false");
		capacityList.add(capacity4);
		
		// Mock / define Repository method calls ...
		Mockito.when(capacityRepository.findCapacityByOwnerId(Mockito.eq(CUSTOMER_ID))).thenReturn(capacityList);
		
		// ###############################
		// CustomerCapacityDetailRepository
		// ###############################
		List<CapacityDetail> capDetail = new ArrayList<>();
		
		CapacityDetail capacityDetail = new CapacityDetail();
		capacityDetail.setId(296123606);
		capacityDetail.setCapacityAmount(17.75);
		capacityDetail.setCapacityType("GT");
		capacityDetail.setOffRegisterDate(null);
		capacityDetail.setCapSegmentId(296123426);
		capacityDetail.setPointsAssigned(null);
		capacityDetail.setSourceVesselId(null);
		capacityDetail.setSourceVesselName(null);
		capDetail.add(capacityDetail);
		
		CapacityDetail capacityDetail2 = new CapacityDetail();
		capacityDetail2.setId(296123616);
		capacityDetail2.setCapacityAmount(23.25);
		capacityDetail2.setCapacityType("KW");
		capacityDetail2.setOffRegisterDate(null);
		capacityDetail2.setCapSegmentId(296123426);
		capacityDetail2.setPointsAssigned(null);
		capacityDetail2.setSourceVesselId(null);
		capacityDetail2.setSourceVesselName(null);
		capDetail.add(capacityDetail);
		
		CompletableFuture<List<CapacityDetail>> _capDetail = new CompletableFuture<List<CapacityDetail>>();
		_capDetail.complete(capDetail);
		
		Mockito.when(customerCapacityDetailRepository.findCapacityDetailByCapAccountId(anyInt())).thenReturn(_capDetail);
		
		// ###############################
		// CapacityPenaltyPointsRepository
		// ###############################
		List<PenaltyPoints> points = new ArrayList<>();
		PenaltyPoints penaltyPoint = new PenaltyPoints();
		penaltyPoint.setID(Integer.valueOf(100125).longValue());
		penaltyPoint.setASSIGNEDPOINTS("6");
		penaltyPoint.setEXPIRYDATE("10-Oct-2022");
		points.add(penaltyPoint);
		
		CompletableFuture<List<PenaltyPoints>> penaltyPoints = new CompletableFuture<List<PenaltyPoints>>();
		penaltyPoints.complete(points);
		
		Mockito.when(capacityPenaltyPointsRepository.findCustomerCapacityPenaltyPointsByCapAccountId(anyInt())).thenReturn(penaltyPoints);
		Mockito.when(capacityPenaltyPointsRepository.findCustomerCapacityPenaltyPointsByCapAccountId_Sync(anyInt())).thenReturn(penaltyPoint);
		
		// ###############################
		// VesselSummaryRepository
		// ###############################
		VesselSummary vesselSummary = new VesselSummary();
		vesselSummary.setId(292621439);
		vesselSummary.setName("AUDACIOUS");
		vesselSummary.setStatus(1811007);
		vesselSummary.setCfr("IRL000I14505");
		vesselSummary.setPrn("DA14");
		vesselSummary.setGt("230");
		vesselSummary.setKw("395");
		
		CompletableFuture<VesselSummary> _vesselSummary = new CompletableFuture<VesselSummary>();
		_vesselSummary.complete(vesselSummary);
		
		Mockito.when(vesselSummaryRepository.findVesselSummaryByVesselId(anyInt())).thenReturn(_vesselSummary);
		
		// Run Tests ...
		AllCapacityDTO allCapacityDTO = customerCapacityService.getAllCapacity(CUSTOMER_ID, false);
		System.out.println(allCapacityDTO);
		assertNotNull(allCapacityDTO);
		assertEquals(CUSTOMER_ID, allCapacityDTO.getOwnerId().toString());
		
		// Verify various Repository methods called as expected ...
		Mockito.verify(capacityRepository, Mockito.times(1)).findCapacityByOwnerId(CUSTOMER_ID);
		Mockito.verify(customerCapacityDetailRepository, Mockito.times(4)).findCapacityDetailByCapAccountId(anyInt());
		Mockito.verify(capacityPenaltyPointsRepository, Mockito.times(3)).findCustomerCapacityPenaltyPointsByCapAccountId(anyInt());
		Mockito.verify(vesselSummaryRepository, Mockito.times(4)).findVesselSummaryByVesselId(anyInt());
	}
}


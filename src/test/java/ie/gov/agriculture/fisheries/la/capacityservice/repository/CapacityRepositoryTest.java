package ie.gov.agriculture.fisheries.la.capacityservice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.Capacity;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.CapacityDetail;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.IfisWrapper;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.PenaltyPoints;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.VesselSummary;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class CapacityRepositoryTest {
    @Mock
    CapacityRepository capacityRepository;
	
    @Mock
	CustomerCapacityDetailRepository customerCapacityDetailRepository;
	
    @Mock
	VesselSummaryRepository vesselSummaryRepository;
	
    @Mock
	CapacityPenaltyPointsRepository capacityPenaltyPointsRepository;
	
    @Mock
	CCSRepository ccsRepository;
	
	@Test
	public void findCapacityByOwnerId() {
    	// Prepare mock ...
		List<Capacity> capacity = new ArrayList<>();
    	
		when(capacityRepository.findCapacityByOwnerId(anyString())).thenReturn(capacity);
    	
		// Test repository call ...
		List<Capacity> capacity_out = capacityRepository.findCapacityByOwnerId("1");
        
        assertThat(capacity_out).isNotNull();
		Mockito.verify(capacityRepository, Mockito.times(1)).findCapacityByOwnerId(anyString());
	}
	
	@Test
	public void findCapacityDetailByCapAccountId() throws InterruptedException, ExecutionException {
    	// Prepare mock ...
		CompletableFuture<List<CapacityDetail>> capacityDetail = new CompletableFuture<List<CapacityDetail>>();
    	
		when(customerCapacityDetailRepository.findCapacityDetailByCapAccountId(anyInt())).thenReturn(capacityDetail);
    	
		// Test repository call ...
		CompletableFuture<List<CapacityDetail>> capacityDetail_out = customerCapacityDetailRepository.findCapacityDetailByCapAccountId(1);
        
        assertThat(capacityDetail_out).isNotNull();
		Mockito.verify(customerCapacityDetailRepository, Mockito.times(1)).findCapacityDetailByCapAccountId(anyInt());
	}
	
	@Test
	public void findVesselSummaryByVesselId() throws InterruptedException, ExecutionException {
    	// Prepare mock ...
		CompletableFuture<VesselSummary> vesselSummary = new CompletableFuture<VesselSummary>();
    	
		when(vesselSummaryRepository.findVesselSummaryByVesselId(anyInt())).thenReturn(vesselSummary);
    	
		// Test repository call ...
		CompletableFuture<VesselSummary> vesselSummary_out = vesselSummaryRepository.findVesselSummaryByVesselId(1);
        
        assertThat(vesselSummary_out).isNotNull();
		Mockito.verify(vesselSummaryRepository, Mockito.times(1)).findVesselSummaryByVesselId(anyInt());
	}
	
	@Test
	public void findCustomerCapacityPenaltyPointsByCapAccountId() throws InterruptedException, ExecutionException {
    	// Prepare mock ...
		CompletableFuture<List<PenaltyPoints>> points_async = new CompletableFuture<List<PenaltyPoints>>();
    	
		when(capacityPenaltyPointsRepository.findCustomerCapacityPenaltyPointsByCapAccountId(anyInt())).thenReturn(points_async);
    	
		// Test repository call ...
		CompletableFuture<List<PenaltyPoints>> points_async_out = capacityPenaltyPointsRepository.findCustomerCapacityPenaltyPointsByCapAccountId(1);
        
        assertThat(points_async_out).isNotNull();
		Mockito.verify(capacityPenaltyPointsRepository, Mockito.times(1)).findCustomerCapacityPenaltyPointsByCapAccountId(anyInt());
		
		
    	// Prepare mock ...
		PenaltyPoints points = new PenaltyPoints();
    	
		when(capacityPenaltyPointsRepository.findCustomerCapacityPenaltyPointsByCapAccountId_Sync(anyInt())).thenReturn(points);
    	
		// Test repository call ...
		PenaltyPoints points_out = capacityPenaltyPointsRepository.findCustomerCapacityPenaltyPointsByCapAccountId_Sync(1);
        
        assertThat(points_out).isNotNull();
		Mockito.verify(capacityPenaltyPointsRepository, Mockito.times(1)).findCustomerCapacityPenaltyPointsByCapAccountId_Sync(anyInt());
	}
	
	@Test
	public void findIfisIdByCcsId() {
    	// Prepare mock ...
    	//List<IfisWrapper> ifisWrapper = new ArrayList<>();
	    IfisWrapper ifisWrapper = new IfisWrapper();
    	
		when(ccsRepository.findIfisIdByCcsId(anyString(), anyInt())).thenReturn(ifisWrapper);
    	
		// Test repository call ...
		IfisWrapper ifisWrapper_out = ccsRepository.findIfisIdByCcsId("1000", 151);
        
        assertThat(ifisWrapper_out).isNotNull();
		Mockito.verify(ccsRepository, Mockito.times(1)).findIfisIdByCcsId(anyString(), anyInt());
	}
}
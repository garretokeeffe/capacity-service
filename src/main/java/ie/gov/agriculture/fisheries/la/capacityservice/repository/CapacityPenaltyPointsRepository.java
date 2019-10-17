package ie.gov.agriculture.fisheries.la.capacityservice.repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.PenaltyPoints;

@Component
@Repository
public interface CapacityPenaltyPointsRepository extends CrudRepository<PenaltyPoints, Long> {
	/***
	 * public CompletableFuture<PenaltyPoints> findCustomerCapacityPenaltyPointsByCapAccountId(@Param("capAccountId") Integer capAccountId);
	 * @param capAccountId
	 * @return - PenaltyPoints - Find rolled up penalty points with latest expiry date, async method.
	 */
	@Query (
		value = "" +
			"select distinct 1 ID, sum(ASSIGNEDPOINTS) ASSIGNEDPOINTS, TO_CHAR(MAX(EXPIRYDATE), 'dd/mm/yyyy') EXPIRYDATE, NULL PENALTYCOMMENT, NULL CAPACCOUNTID, NULL CAPSEGMENTID " + 
			"from capaccountsegment a " + 
			"left outer join TDFI_CAPACITYACCOUNTPOINTS b on a.CAPSEGMENTID = b.CAPSEGMENTID " + 
			"left outer join TDFI_PENALTYPOINTS c on b.TDFI_PENALTYPOINTSID = c.TDFI_PENALTYPOINTSID " + 
			"left outer join capacityaccount d on d.CAPACCOUNTID = a.CAPACCOUNTID " + 
			"where a.CAPACCOUNTID = :capAccountId and a.inactiveind = 'N' and b.inactiveind = 'N' " + 
			"group by b.CAPSEGMENTID", nativeQuery = true
	)
	@Async
	public CompletableFuture<List<PenaltyPoints>> findCustomerCapacityPenaltyPointsByCapAccountId(@Param("capAccountId") Integer capAccountId);
	
	/***
	 *public PenaltyPoints findCustomerCapacityPenaltyPointsByCapAccountId_Sync(@Param("capAccountId") Integer capAccountId);
	 * @param capAccountId
	 * @return - PenaltyPoints - Find rolled up penalty points with latest expiry date, sync method.
	 */
	@Query (
		value = "" +
			"select distinct 1 ID, sum(ASSIGNEDPOINTS) ASSIGNEDPOINTS, TO_CHAR(MAX(EXPIRYDATE), 'dd/mm/yyyy') EXPIRYDATE, NULL PENALTYCOMMENT, NULL CAPACCOUNTID, NULL CAPSEGMENTID " + 
			"from capaccountsegment a " + 
			"left outer join TDFI_CAPACITYACCOUNTPOINTS b on a.CAPSEGMENTID = b.CAPSEGMENTID " + 
			"left outer join TDFI_PENALTYPOINTS c on b.TDFI_PENALTYPOINTSID = c.TDFI_PENALTYPOINTSID " + 
			"left outer join capacityaccount d on d.CAPACCOUNTID = a.CAPACCOUNTID " + 
			"where a.CAPACCOUNTID = :capAccountId and a.inactiveind = 'N' and b.inactiveind = 'N' " + 
			"group by b.CAPSEGMENTID", nativeQuery = true
	)
	public PenaltyPoints findCustomerCapacityPenaltyPointsByCapAccountId_Sync(@Param("capAccountId") Integer capAccountId);
}
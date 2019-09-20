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
public interface CapacityPenaltyPointsRepository extends CrudRepository<PenaltyPoints, String> {
	@Query (
		value = "" +
			"select distinct TDFI_CAPACITYACCOUNTPOINTSID ID, ASSIGNEDPOINTS, TO_CHAR(EXPIRYDATE, 'dd/mm/yyyy') EXPIRYDATE, PENALTYCOMMENT, a.CAPACCOUNTID, b.CAPSEGMENTID " + 
			"from capaccountsegment a " + 
			"left outer join TDFI_CAPACITYACCOUNTPOINTS b on a.CAPSEGMENTID = b.CAPSEGMENTID " + 
			"left outer join TDFI_PENALTYPOINTS c on b.TDFI_PENALTYPOINTSID = c.TDFI_PENALTYPOINTSID " + 
			"where a.CAPACCOUNTID = :capAccountId and a.inactiveind = 'N' and b.inactiveind = 'N' order by a.CAPACCOUNTID, b.CAPSEGMENTID", nativeQuery = true
	)
	@Async
	public CompletableFuture<List<PenaltyPoints>> findCustomerCapacityPenaltyPointsByCapAccountId (@Param("capAccountId") Integer capAccountId);
}
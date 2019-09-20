package ie.gov.agriculture.fisheries.la.capacityservice.repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.CapacityDetail;

@Component
@Repository
public interface CustomerCapacityDetailRepository extends CrudRepository<CapacityDetail, String> {
	@Query (
		value = "" +
			"select distinct TRANSDETAILID ID, CAPACITYAMOUNT, CAPACITYTYPE, TO_CHAR(OFFREGDATE, 'dd/mm/yyyy') OFFREGDATE, TO_CHAR(CAPACITYEXPIRYDATE, 'dd/mm/yyyy') CAPACITYEXPIRYDATE, SOURCEVESSELID, SOURCEVESSELNAME, POINTSASSIGNED from ( " + 
				"select c.TRANSDETAILID, c.TRANSACTIONAMOUNT /*case when a.statusid = 6237299 then B.PROPOSEDBALANCE else B.GROSSBALANCE end*/ CAPACITYAMOUNT, " + 
				"pkgifisreference.fngetreferencedesc(b.UOMID) CAPACITYTYPE, " + 
				"b.OFFREGDATE, b.CAPACITYEXPIRYDATE, C.SOURCEVESSELID, d.vesselname SOURCEVESSELNAME, c.POINTSASSIGNED " + 
				"from capacityaccount a " + 
				"inner join capaccountsegment b on b.CAPACCOUNTID = a.CAPACCOUNTID " + 
				"inner join CAPTRANSDETAIL c on b.CAPSEGMENTID = c.CAPSEGMENTID and c.transactiontypeid = 10612 /* credit trans */ " + 
				"inner join vessel d on C.SOURCEVESSELID = d.vesselid " + 
				"where a.inactiveind = 'N' and b.inactiveind = 'N' and c.inactiveind = 'N' " + 
				"and a.capaccountid = :capAccountId " +
			") A where CAPACITYAMOUNT > 0 order by CAPACITYTYPE, CAPACITYAMOUNT desc", nativeQuery = true
	)
	@Async
	public CompletableFuture<List<CapacityDetail>> findCapacityDetailByCapAccountId (@Param("capAccountId") Integer capAccountId);
}
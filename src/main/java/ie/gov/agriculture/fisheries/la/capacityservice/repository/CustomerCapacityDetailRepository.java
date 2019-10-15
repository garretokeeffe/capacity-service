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
public interface CustomerCapacityDetailRepository extends CrudRepository<CapacityDetail, Long> {
	@Query (
		value = "" +
			"select distinct TRANSDETAILID ID, CAPACITYAMOUNT, CAPACITYTYPE, TO_CHAR(OFFREGDATE, 'dd/mm/yyyy') OFFREGDATE, VESSELID, POINTSASSIGNED, CAPSEGMENTID, " +
			"TO_CHAR(CAST(ADD_MONTHS(OFFREGDATE,24) AS DATE), 'dd/mm/yyyy') CAPACITYEXPIRYDATE, " +
			"CASE WHEN offRegister='false' THEN null ELSE SOURCEVESSELID END SOURCEVESSELID, CASE WHEN offRegister='false' THEN null ELSE SOURCEVESSELNAME END SOURCEVESSELNAME from (    " +
			"	select c.TRANSDETAILID, c.TRANSACTIONAMOUNT /*case when a.statusid = 6237299 then B.PROPOSEDBALANCE else B.GROSSBALANCE end*/ CAPACITYAMOUNT, " + 
			"	pkgifisreference.fngetreferencedesc(b.UOMID) CAPACITYTYPE,  " + 
			"	b.OFFREGDATE, A.VESSELID, C.SOURCEVESSELID, d.vesselname SOURCEVESSELNAME, case when c.POINTSASSIGNED = 0 then null else c.POINTSASSIGNED end POINTSASSIGNED, b.CAPSEGMENTID, " + 
			"   case when (a.statusid = 6237297 and e.STATUSID = 1811007) then 'false' else 'true' end offRegister /* Cap Block On/Off Register */" +
			"	from capacityaccount a " + 
			"	inner join capaccountsegment b on b.CAPACCOUNTID = a.CAPACCOUNTID " + 
			"	inner join CAPTRANSDETAIL c on b.CAPSEGMENTID = c.CAPSEGMENTID and c.transactiontypeid = 10612 /* Credit trans */ " + 
			"	inner join vessel d on C.SOURCEVESSELID = d.vesselid " +
			"	inner join vessel e on a.vesselid = e.vesselid " +
			"	where a.inactiveind = 'N' and b.inactiveind = 'N' and c.inactiveind = 'N' " + 
			"	and a.capaccountid = :capAccountId " + 
			") A where CAPACITYAMOUNT > 0 order by VESSELID, CAPACITYTYPE", nativeQuery = true
	)
	@Async
	public CompletableFuture<List<CapacityDetail>> findCapacityDetailByCapAccountId (@Param("capAccountId") Integer capAccountId);
}
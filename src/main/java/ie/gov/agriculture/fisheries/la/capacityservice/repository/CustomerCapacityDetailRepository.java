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
			"select distinct TRANSDETAILID ID, CAPACITYAMOUNT, CAPACITYTYPE, TO_CHAR(OFFREGDATE, 'dd/mm/yyyy') OFFREGDATE, VESSELID, SOURCEVESSELID, SOURCEVESSELNAME, POINTSASSIGNED, " +
			"TO_CHAR(CAST(ADD_MONTHS(OFFREGDATE,24) AS DATE), 'dd/mm/yyyy') CAPACITYEXPIRYDATE, CAP_EXP_WITHIN_3_MONTHS, DAYS_UNTIL_CAP_EXP from (  " +
			"	select c.TRANSDETAILID, c.TRANSACTIONAMOUNT /*case when a.statusid = 6237299 then B.PROPOSEDBALANCE else B.GROSSBALANCE end*/ CAPACITYAMOUNT, " + 
			"	pkgifisreference.fngetreferencedesc(b.UOMID) CAPACITYTYPE,  " + 
			"	b.OFFREGDATE, A.VESSELID, C.SOURCEVESSELID, d.vesselname SOURCEVESSELNAME, c.POINTSASSIGNED, " + 
			"	CASE " + 
			"		WHEN OFFREGDATE IS NULL THEN 'false' " + 
			"		WHEN CAST(ADD_MONTHS(OFFREGDATE,24) AS DATE) BETWEEN CAST(Current_Timestamp AS DATE) AND CAST(ADD_MONTHS(Current_Timestamp,3) AS DATE) THEN 'true'  " + 
			"		ELSE 'false' " + 
			"	END CAP_EXP_WITHIN_3_MONTHS /* Indicator - Capacity Expires Within 3 Months */, " +
			"	CASE " + 
			"		WHEN OFFREGDATE IS NULL THEN NULL " + 
			"		WHEN CAST(ADD_MONTHS(OFFREGDATE,24) AS DATE) BETWEEN CAST(Current_Timestamp AS DATE) AND CAST(ADD_MONTHS(Current_Timestamp,3) AS DATE) THEN ROUND(CAST(ADD_MONTHS(OFFREGDATE,24) AS DATE) - CAST(Current_Timestamp AS DATE)) " + 
			"		ELSE NULL " + 
			"	END DAYS_UNTIL_CAP_EXP /* If Capacity Expires Within 3 Months, Total Days left Until Capacity Expires */ " +  
			"	from capacityaccount a " + 
			"	inner join capaccountsegment b on b.CAPACCOUNTID = a.CAPACCOUNTID " + 
			"	inner join CAPTRANSDETAIL c on b.CAPSEGMENTID = c.CAPSEGMENTID and c.transactiontypeid = 10612 /* Credit trans */ " + 
			"	inner join vessel d on C.SOURCEVESSELID = d.vesselid  " + 
			"	where a.inactiveind = 'N' and b.inactiveind = 'N' and c.inactiveind = 'N' " + 
			"	and a.capaccountid = :capAccountId " + 
			") A where CAPACITYAMOUNT > 0 order by VESSELID, CAPACITYTYPE", nativeQuery = true
	)
	@Async
	public CompletableFuture<List<CapacityDetail>> findCapacityDetailByCapAccountId (@Param("capAccountId") Integer capAccountId);
}
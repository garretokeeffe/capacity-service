package ie.gov.agriculture.fisheries.la.capacityservice.repository;

import java.util.List; 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.Capacity;

@Component
@Repository
public interface CapacityRepository extends CrudRepository<Capacity, String> {
	@Query (
		value = "" +
			"Select rownum ID, A.capaccountid, A.ownerid, A.offRegister, A.fleetSegment, A.fleetSubSegment, DECODE(A.proposed, 0, A.GT, A.GT_Proposed) GT, DECODE(A.proposed, 0, A.KW, A.KW_Proposed) KW, " +  
			"    A.VESSELID, A.vesselname, case when A.offRegister='false' then null else DECODE(A.proposed, 1, 'true', 'false') end proposed FROM ( " +  
			"    SELECT ca.capaccountid, ca.ownerid, cas.segmentid fleetSubSegment, " + 
			"    SUM(DECODE(cas.uomid, '91359', cas.grossbalance, 0)) GT, " + 
			"    SUM(DECODE(cas.uomid, '91290', cas.grossbalance, 0)) KW, " +  
			"    SUM(DECODE(cas.uomid, '91359', cas.proposedbalance, 0)) GT_Proposed, " +  
			"    SUM(DECODE(cas.uomid, '91290', cas.proposedbalance, 0)) KW_Proposed, " + 
			"    ca.VESSELID, " + 
			"    max(case when (ca.statusid = 6237297 and v.STATUSID = 1811007) then 'false' else 'true' end) offRegister, " + 
			"    max(case  " +  
			"        when (cas.segmentid in (1942697, 338360921, 1942700, 338360917)) then '1' " +  
			"        when (cas.segmentid in (1807585)) then '2' " +  
			"        when (cas.segmentid in (1942695, 338360923)) then '3' " +  
			"        when (cas.segmentid in (1942701)) then '4' " +  
			"        when (cas.segmentid in (1942696)) then '5' " +  
			"        else '0' " +  
			"    end) fleetSegment, max(v.vesselname) vesselname, " +  
			"    DECODE(ca.statusid, 6237299, 1, 0) proposed, max(ca.statusid) statusid " +  
			"    FROM CAPACITYACCOUNT ca, CAPACCOUNTSEGMENT cas, vessel v " +  
			"    WHERE ca.ownerid = :ownerId AND ca.capaccountid = cas.capaccountid " +  
			"    and ca.vesselid = v.vesselid AND ca.STATUSID in (6237298, 6237297, 6237299) /*Assigned, Unassigned, Proposed*/  " +  
			"    AND cas.inactiveind = 'N' AND ca.inactiveind = 'N' AND ca.closedind = 'N' AND (v.inactiveind = 'N' and v.statusid is not null /*Only active vessels with a valid status*/)  " +
			"    AND CAST(COALESCE(ADD_MONTHS(OFFREGDATE,24),Current_Timestamp+1) AS DATE) > CAST(Current_Timestamp AS DATE) /* Preclude expired capacity */ " +  
			"    HAVING (SUM(DECODE(cas.uomid, '91359' /*GT*/, cas.grossbalance, 0)) > 0) OR (SUM(DECODE(cas.uomid, '91290' /*KW*/, cas.grossbalance, 0)) > 0) " +  
			"       OR (SUM(DECODE(cas.uomid, '91359' /*GT*/, cas.proposedbalance, 0)) > 0) OR (SUM(DECODE(cas.uomid, '91290' /*KW*/, cas.proposedbalance, 0)) > 0) " +  
			"    GROUP BY ca.capaccountid, ca.ownerid, cas.segmentid, ca.STATUSID, ca.VESSELID " +  
			") A order by A.offregister, A.statusid, A.proposed, A.vesselname", nativeQuery = true
	)
	public List<Capacity> findCapacityByOwnerId (@Param("ownerId") String ownerId);
}
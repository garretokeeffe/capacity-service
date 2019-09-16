package ie.gov.agriculture.fisheries.la.capacityservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.Capacity;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.CustomerCapacity;

@Component
@Repository
public interface CustomerCapacityRepository extends CrudRepository<CustomerCapacity, String> {
	@Query (
		value = "" +
			"select distinct rownum ID, a.capaccountid, a.statusid capacitystatus, pkgifisreference.fngetreferencedesc(a.statusid) capacitystatusdesc, a.ownerid, a.vesselid, c.vesselname, " + 
			"b.capsegmentid, b.segmentid capacitysegmentid, pkgifisreference.fngetreferencedesc(b.segmentid) capacitysegmentdesc, b.UOMID, pkgifisreference.fngetreferencedesc(b.UOMID) UOMDesc, " + 
			"c.STATUSID VesselStatus, pkgifisreference.fngetreferencedesc(c.STATUSID) VesselStatusDesc, b.GROSSBALANCE, b.PENDINGBALANCE, b.PROPOSEDBALANCE, b.FREEBALANCE " + 
			"from capacityaccount a " + 
			"inner join capaccountsegment b on b.CAPACCOUNTID = a.CAPACCOUNTID " + 
			"inner join vessel c on a.vesselid = c.vesselid " + 
			"where ownerid = :ownerId and a.inactiveind = 'N' and b.inactiveind = 'N' " + 
			"order by a.statusid, a.vesselid, b.UOMID", nativeQuery = true
	)
	public List<CustomerCapacity> findCapacityInformationByOwnerId (@Param("ownerId") String ownerId);
	
	@Query (
		value = "" +
			"SELECT A.IDx, A.capaccountid, A.capsegmentid, A.ownerid, A.offregister, A.fleetSegment, A.fleetSubSegment, " + 
			"(SELECT case when a.statusid = 6237299 /* IF PROPOSED */ then B.PROPOSEDBALANCE else B.GROSSBALANCE end FROM capaccountsegment B WHERE B.capaccountid = A.capaccountid AND B.UOMID = 91359 and B.segmentid = A.segmentid) gt, " + 
			"A.kw, A.vesselid, A.vesselname, A.proposed /*, A.segmentid, A.CREATEDATE*/ FROM ( " + 
			"select rownum ID, a.capaccountid, b.capsegmentid, a.ownerid, " + 
			"case when (a.statusid = 6237297 and c.STATUSID = 1811007) then 'false' else 'true' end offRegister, " + 
			"case" + 
			"    when (b.segmentid in (1942697, 338360921, 1942700, 338360917)) then '1' " + 
			"    when (b.segmentid in (1807585)) then '2' " + 
			"    when (b.segmentid in (1942695, 338360923)) then '3' " + 
			"    when (b.segmentid in (1942701)) then '4' " + 
			"    when (b.segmentid in (1942696)) then '5' " + 
			"    else '0' " + 
			"end fleetSegment, " + 
			"case when (b.segmentid is null) then 0 else b.segmentid end fleetSubSegment, a.vesselid, c.vesselname, " + 
			"case when (a.statusid = 6237299) then 'true' else 'false' end proposed, b.segmentid, " + 
			"/*(select GROSSBALANCE from capaccountsegment where CAPACCOUNTID = a.capaccountid and CAPSEGMENTID = b.capsegmentid and UOMID = 91359) gt,*/ " + 
			"(select case when a.statusid = 6237299 /* IF PROPOSED */ then B.PROPOSEDBALANCE else B.GROSSBALANCE end from capaccountsegment where CAPACCOUNTID = a.capaccountid and CAPSEGMENTID = b.capsegmentid and UOMID = 91290) kw, " + 
			"a.CREATEDATE, ROW_NUMBER() OVER (partition by a.capaccountid ORDER BY a.capaccountid) CapAccRank, a.statusid " + 
			"from capacityaccount a " + 
			"inner join capaccountsegment b on b.CAPACCOUNTID = a.CAPACCOUNTID " + 
			"inner join vessel c on a.vesselid = c.vesselid " + 
			"where ownerid = :ownerId and a.inactiveind = 'N' and b.inactiveind = 'N' /*and a.capaccountid = 23906141*/ " + 
			") A WHERE A.CapAccRank = 2 order by A.offregister, A.proposed, A.CREATEDATE", nativeQuery = true
		)
		public List<Capacity> findCapacityByOwnerId (@Param("ownerId") String ownerId);
	
//	select rownum ID, a.capaccountid, a.statusid capacitystatus, pkgifisreference.fngetreferencedesc(a.statusid) capacitystatusdesc, a.ownerid, a.vesselid, c.vesselname,   
//	b.capsegmentid, b.segmentid capacitysegmentid, pkgifisreference.fngetreferencedesc(b.segmentid) capacitysegmentdesc, b.UOMID, pkgifisreference.fngetreferencedesc(b.UOMID) UOMDesc,   
//	c.STATUSID vesselstatus, pkgifisreference.fngetreferencedesc(c.STATUSID) vesselstatusdesc, b.GROSSBALANCE, b.PENDINGBALANCE, b.PROPOSEDBALANCE, b.FREEBALANCE,
//	case when (a.statusid = 6237297 and c.STATUSID = 1811007) then 'false' else 'true' end offRegister,
//	case
//	  when (b.segmentid in (1942697, 338360921, 1942700, 338360917)) then '1'
//	  when (b.segmentid in (1807585)) then '2'
//	  when (b.segmentid in (1942695, 338360923)) then '3'
//	  when (b.segmentid in (1942701)) then '4'
//	  when (b.segmentid in (1942696)) then '5'
//	  else '0'
//	end fleetSegment,
//	case when (b.segmentid is null) then 0 else b.segmentid end fleetSubSegment,
//	b.OFFREGDATE, b.CAPACITYEXPIRYDATE
//	from capacityaccount a   
//	inner join capaccountsegment b on b.CAPACCOUNTID = a.CAPACCOUNTID   
//	inner join vessel c on a.vesselid = c.vesselid   
//	where ownerid = 748 /*748*/ /*163256917*/ /*23630*/ and a.inactiveind = 'N' and b.inactiveind = 'N'
//	and b.segmentid = 338360921
//	order by 4;
}
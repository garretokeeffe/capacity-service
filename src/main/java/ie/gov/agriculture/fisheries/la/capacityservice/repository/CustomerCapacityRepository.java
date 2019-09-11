package ie.gov.agriculture.fisheries.la.capacityservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.CustomerCapacity;

public interface CustomerCapacityRepository extends CrudRepository<CustomerCapacity, String> {
	@Query (
		value = "" +
			"select a.capaccountid, a.statusid capacitystatus, pkgifisreference.fngetreferencedesc(a.statusid) capacitystatusdesc, a.ownerid, a.vesselid, c.vesselname, " + 
			"b.capsegmentid, b.segmentid capacitysegmentid, pkgifisreference.fngetreferencedesc(b.segmentid) capacitysegmentdesc, b.UOMID, pkgifisreference.fngetreferencedesc(b.UOMID) UOMDesc, " + 
			"c.STATUSID VesselStatus, pkgifisreference.fngetreferencedesc(c.STATUSID) VesselStatusDesc, b.GROSSBALANCE, b.PENDINGBALANCE, b.PROPOSEDBALANCE, b.FREEBALANCE " + 
			"from capacityaccount a " + 
			"inner join capaccountsegment b on b.CAPACCOUNTID = a.CAPACCOUNTID " + 
			"inner join vessel c on a.vesselid = c.vesselid " + 
			"where ownerid = :ownerId and a.inactiveind = 'N' and b.inactiveind = 'N' " + 
			"order by a.statusid, a.vesselid, b.UOMID", nativeQuery = true
	)
	public List<CustomerCapacity> getCustomerCapacityInformation (@Param("ownerId") String ownerId);
}
package ie.gov.agriculture.fisheries.la.capacityservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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
//			"select distinct TDFI_CAPACITYACCOUNTPOINTSID ID, ASSIGNEDPOINTS, EXPIRYDATE, PENALTYCOMMENT " + 
//			"from TDFI_CAPACITYACCOUNTPOINTS a " + 
//			"left outer join TDFI_PENALTYPOINTS b on a.TDFI_PENALTYPOINTSID = b.TDFI_PENALTYPOINTSID " + 
//			"where a.CAPSEGMENTID = :capSegmentId " + 
//			"and a.inactiveind = 'N' and b.inactiveind = 'N'", nativeQuery = true
	)
	public List<PenaltyPoints> findCustomerCapacityPenaltyPointsByCapAccountId (@Param("capAccountId") Integer capAccountId);
}
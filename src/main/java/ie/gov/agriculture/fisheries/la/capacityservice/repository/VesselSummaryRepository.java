package ie.gov.agriculture.fisheries.la.capacityservice.repository;

import java.util.concurrent.CompletableFuture;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.VesselSummary;

/*** https://confluence.agriculture.gov.ie/confluence/display/FISHERIES/Get+Capacity ***/

@Component
@Repository
public interface VesselSummaryRepository extends CrudRepository<VesselSummary, Integer> {

	@Query (
		value = "" +
			"SELECT v.vesselid id, v.vesselname \"name\", pkgifisreference.Fngetreferencedesc(v.statusid) status, " + 
			"(select IDENTIFICATIONNO from vesselidentification where IDTYPEID = 91348 and VESSELID = v.vesselid and INACTIVEIND = 'N') cfr, " + 
			"(select IDENTIFICATIONNO from vesselidentification where IDTYPEID = 91347 and VESSELID = v.vesselid and INACTIVEIND = 'N') prn, " + 
			"(select tonnage from vesseldimension where VESSELID = v.vesselid and INACTIVEIND = 'N') gt, " + 
			"(select enginepower from vesselengine where VESSELID = v.vesselid and INACTIVEIND = 'N') kw " + 
			"FROM vessel v WHERE inactiveind = 'N' AND v.vesselid = :vesselId ", nativeQuery = true
	)
	@Async
	public CompletableFuture<VesselSummary> findVesselSummaryByVesselId (@Param("vesselId") Integer vesselId);
}
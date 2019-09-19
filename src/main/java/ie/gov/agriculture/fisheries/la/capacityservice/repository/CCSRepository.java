package ie.gov.agriculture.fisheries.la.capacityservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ie.gov.agriculture.fisheries.la.capacityservice.entity.IfisWrapper;

/**
 * This query is used to convert a CCS-id to an Ifis-id
 * Retrieve the IFIS-id using the ccs-id //SELECT bcus_fisheries_cust_id FROM tdco_business_customers WHERE bcus_bus_id = :ccsId
 * @author garret.okeeffe
 *
 */
public interface CCSRepository extends CrudRepository<IfisWrapper, String> {
	@Query (
		value = "SELECT pkco_ifis.fn_get_fisheries_custid_role(:ccsId, 155) bcus_fisheries_cust_id FROM dual" , nativeQuery = true
	)

	public List<IfisWrapper> findIfisIdbyCcsId (@Param("ccsId") String ccsId);
}
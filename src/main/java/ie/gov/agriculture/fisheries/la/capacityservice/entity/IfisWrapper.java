package ie.gov.agriculture.fisheries.la.capacityservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Simple class used for retrieving the Ifis-id using the ccsId query.
 * @author garret.okeeffe
 *
 */
@Getter @Setter @NoArgsConstructor @ToString
@Entity
public class IfisWrapper {
	@Id
	@Column(name="bcus_fisheries_cust_id")
	private String ifisId;
}

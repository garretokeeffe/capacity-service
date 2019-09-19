package ie.gov.agriculture.fisheries.la.capacityservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor @ToString
public class VesselSummary {
	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "status")
	private String status; // VESSEL STATUS REFERENCE DATA
		 
	@Column(name = "cfr")
	private String cfr;

	@Column(name = "prn")
	private String prn; // port reg number
		 
	@Column(name = "gt")
	private String gt; // Dimensions - tonnage
	
	@Column(name = "kw")
	private String kw; // Propulsion - engine power
}

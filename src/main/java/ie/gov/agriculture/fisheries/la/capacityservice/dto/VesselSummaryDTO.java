package ie.gov.agriculture.fisheries.la.capacityservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class VesselSummaryDTO {
	private Integer id;
	private String name;
	private Integer status; // VESSEL STATUS REFERENCE DATA
	private String cfr;
	private String prn; // port reg number
	private String gt; // Dimensions - tonnage
	private String kw; // Propulsion - engine power
}

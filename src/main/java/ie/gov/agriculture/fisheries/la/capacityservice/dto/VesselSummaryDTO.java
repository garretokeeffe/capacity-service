package ie.gov.agriculture.fisheries.la.capacityservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class VesselSummaryDTO {
	private Integer id;
	
	@JsonInclude(Include.NON_NULL)
	private String name;
	
	@JsonInclude(Include.NON_NULL)
	private Integer status; // VESSEL STATUS REFERENCE DATA
	
	@JsonInclude(Include.NON_NULL)
	private String cfr;
	
	@JsonInclude(Include.NON_NULL)
	private String prn; // port reg number
	
	private double gt; // Dimensions - tonnage
	private double kw; // Propulsion - engine power
}
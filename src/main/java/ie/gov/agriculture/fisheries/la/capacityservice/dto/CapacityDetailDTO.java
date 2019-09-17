package ie.gov.agriculture.fisheries.la.capacityservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class CapacityDetailDTO {
	private String Id;
	private String capacityAmount; // GT or kW amount
	private String capacityType; // 'GT' | 'kW'
	private String offRegisterDate; // dd/mm/yyyy
	private String expiryDate; // dd/mm/yyyy
	private String sourceVesselId;       // future-proof, in case we need to do searches on the source vessel
	private String sourceVesselName;
	
	//private List<?> trackRecord;
	
	//private List<?> penaltyPoints; //No need to display for On-Register in which case penalty points apply to the parent capacity segment
}

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
public class CapacityDetail {
	@Id
	@Column(name = "ID")
	private String Id;
	
	@Column(name = "CAPACITYAMOUNT")
	private String capacityAmount; // GT or kW amount
	
	@Column(name = "CAPACITYTYPE")
	private String capacityType; // 'GT' | 'kW'
		 
	// attributes only applicable to off-register capacity
	@Column(name = "OFFREGDATE")
	private String offRegisterDate; // dd/mm/yyyy
	
	@Column(name = "CAPACITYEXPIRYDATE")
	private String expiryDate; // dd/mm/yyyy
	
	@Column(name = "SOURCEVESSELID")
	private String sourceVesselId;       // future-proof, in case we need to do searches on the source vessel
	
	@Column(name = "SOURCEVESSELNAME")
	private String sourceVesselName;
	
	//private List<?> trackRecord;
	
	//private List<?> penaltyPoints; //No need to display for On-Register in which case penalty points apply to the parent capacity segment
}

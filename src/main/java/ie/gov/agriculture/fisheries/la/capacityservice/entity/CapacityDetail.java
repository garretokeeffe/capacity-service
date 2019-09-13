package ie.gov.agriculture.fisheries.la.capacityservice.entity;

import java.util.List;

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
	
	@Column(name = "apacityamount")
	private String capacityAmount; // GT or kW amount
	
	@Column(name = "capacitytype")
	private String capacityType; // 'GT' | 'kW'
	
	//private List<?> trackRecord;
	
	//private List<?> penaltyPoints; //No need to display for On-Register in which case penalty points apply to the parent capacity segment
		 
	// attributes only applicable to off-register capacity
	@Column(name = "offregisterdate")
	private String offRegisterDate; // dd/mm/yyyy
	
	@Column(name = "expirydate")
	private String expiryDate; // dd/mm/yyyy
	
	@Column(name = "sourcevesselname")
	private String sourceVesselName;
	
	@Column(name = "sourcevesselid")
	private String sourceVesselId;       // future-proof, in case we need to do searches on the source vessel
}

package ie.gov.agriculture.fisheries.la.capacityservice.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor @ToString
public class CapacityDetail {
	@Id
	@Column(name = "ID")
	private Integer Id;
	
	@Column(name = "CAPACITYAMOUNT")
	private double capacityAmount; // GT or kW amount

	@Column(name = "CAPACITYTYPE")
	private String capacityType; // 'GT' | 'kW'
		 
	// attributes only applicable to off-register capacity
	@Column(name = "OFFREGDATE")
	private String offRegisterDate; // dd/mm/yyyy
	
	@Column(name = "CAPACITYEXPIRYDATE")
	private String expiryDate; // dd/mm/yyyy
	
	@Column(name = "SOURCEVESSELID")
	private String sourceVesselId; // future-proof, in case we need to do searches on the source vessel
	
	@Column(name = "SOURCEVESSELNAME")
	private String sourceVesselName;
	
	@Column(name = "POINTSASSIGNED")
	private String pointsAssigned;
	
	@Column(name = "CAPSEGMENTID")
	private Integer capSegmentId;
	
	@JsonInclude()
	@Transient
	private PenaltyPoints penaltyPoints; // Used for On-Register Capacity only (see Capacity entity for Off-Register) ...
	
	@JsonInclude()
	@Transient
	private List<TrackRecord> trackRecord;
	
	public CapacityDetail setPenaltyPointsReturnDetail(PenaltyPoints penaltyPoints) {
		this.penaltyPoints = penaltyPoints;
		
		return this;
	}
}

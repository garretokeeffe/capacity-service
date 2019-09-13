package ie.gov.agriculture.fisheries.la.capacityservice.entity;

import javax.persistence.Transient;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*** https://confluence.agriculture.gov.ie/confluence/display/FISHERIES/Get+Capacity ***/

@Entity
@Getter @Setter @NoArgsConstructor @ToString
public class Capacity {
	@Id
	@Column(name = "ID")
	private String Id;
	
	@Column(name = "capaccountid")
	private Integer capAccountId;
	
	@Column(name = "capsegmentid")
	private Integer capSegmentId;

	@Column(name = "ownerid")
	private String ownerId;
	
	@Column(name = "offregister")
	private String offRegister;
	
	@Column(name = "fleetsegment")
	private Integer fleetSegment; // FLEET SEGMENT REFERENCE DATA
	
	@Column(name = "fleetsubsegment")
	private Integer fleetSubSegment; // FLEET SUBSEGMENT REFERENCE DATA
	
	@Column(name = "gt")
	private String gt; // this may be a subset of the entire capacity of the vessel
	
	@Column(name = "kw")
	private String kw; // this may be a subset of the entire capacity of the vessel

	//private List<?> details; //: Array<CapacityDetail> = [];

	@Column(name = "vesselid")
	private Integer vesselId;
	
	@Column(name = "proposed")
	private boolean proposed; //off-register only attributes

	@JsonInclude()
	@Transient
	private VesselSummary vesselSummary; //: VesselSummary = null;

	//private List<?> penaltyPoints; //: Array<PenaltyPoints> = [];
}

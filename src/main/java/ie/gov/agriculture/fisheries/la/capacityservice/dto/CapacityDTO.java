package ie.gov.agriculture.fisheries.la.capacityservice.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class CapacityDTO {
	private String capAccountId;
//	private String capSegmentId;
	private String ownerId;
	private boolean offRegister;
	private String fleetSegment; // FLEET SEGMENT REFERENCE DATA
	private String fleetSubSegment; // FLEET SUBSEGMENT REFERENCE DATA
	private String gt; // this may be a subset of the entire capacity of the vessel
	private String kw; // this may be a subset of the entire capacity of the vessel
	private String vesselId;
	private String proposed; //off-register only attributes

	private VesselSummaryDTO vesselSummary;
	private List<CapacityDetailDTO> capDetail;
	private List<PenaltyPointsDTO> penaltyPoints;
}

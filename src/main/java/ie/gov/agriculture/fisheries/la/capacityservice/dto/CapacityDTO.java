package ie.gov.agriculture.fisheries.la.capacityservice.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
	private Integer fleetSegment; // FLEET SEGMENT REFERENCE DATA
	private Integer fleetSubSegment; // FLEET SUBSEGMENT REFERENCE DATA
	private double gt; // this may be a subset of the entire capacity of the vessel
	private double kw; // this may be a subset of the entire capacity of the vessel
	private String vesselId;
	
	@JsonInclude(Include.NON_NULL)
	private String proposed; //off-register only attributes

	@JsonProperty("vessel")
	private VesselSummaryDTO vesselSummary;
	
	@JsonProperty("blocks")
	private List<CapacityDetailDTO> capDetail;
	
	@JsonInclude(Include.NON_NULL)
	private PenaltyPointsDTO penaltyPoints;
}

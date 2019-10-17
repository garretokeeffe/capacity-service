package ie.gov.agriculture.fisheries.la.capacityservice.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class CapacityDTO {
	private Integer capAccountId;
	private Integer ownerId;
	private boolean offRegister;
	private Integer fleetSegment; // FLEET SEGMENT REFERENCE DATA
	private Integer fleetSubSegment; // FLEET SUBSEGMENT REFERENCE DATA
	private double gt; // this may be a subset of the entire capacity of the vessel
	private double kw; // this may be a subset of the entire capacity of the vessel
	
    @Getter(onMethod = @__(@JsonIgnore))
    @Setter
	private Integer vesselId;
	
	@JsonInclude(Include.NON_NULL)
	private boolean proposed; //off-register only attributes

	@JsonProperty("vessel")
	private VesselSummaryDTO vesselSummary;
	
	@JsonProperty("blocks")
	private List<CapacityDetailDTO> capDetail;
	
	private List<PenaltyPointsDTO> penaltyPoints;
	
	/* Manual getter for penaltyPoints, if null or empty then return null (rather than empty list) */
	@JsonInclude(Include.NON_NULL)
	public List<PenaltyPointsDTO> getPenaltyPoints () {
		return (
			penaltyPoints==null ? null :
				(penaltyPoints.isEmpty()) ? null : penaltyPoints 
		);
	}
}

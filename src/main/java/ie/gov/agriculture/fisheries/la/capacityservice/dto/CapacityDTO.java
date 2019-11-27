package ie.gov.agriculture.fisheries.la.capacityservice.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@ApiModel(description="The CapacityDTO class defining / storing high-level capacity information.")
public class CapacityDTO {
	@ApiModelProperty(required = true, value = "Integer defining the customer capacity account id.")
	private Integer capAccountId;
	
	@ApiModelProperty(required = true, value = "Integer defining the customer / owner id.")
	private Integer ownerId;
	
	@ApiModelProperty(required = true, value = "Boolean indicating if capacity is 'off-register'.")
	private boolean offRegister;
	
	@ApiModelProperty(required = true, value = "Integer indicating the fleet segment for this capacity.")
	private Integer fleetSegment; // FLEET SEGMENT REFERENCE DATA
	
	@ApiModelProperty(required = true, value = "Integer indicating the fleet sub segment for this capacity.")
	private Integer fleetSubSegment; // FLEET SUBSEGMENT REFERENCE DATA
	
	@ApiModelProperty(required = true, value = "Double indicating capacity GT if applicable.")
	private double gt; // this may be a subset of the entire capacity of the vessel
	
	@ApiModelProperty(required = true, value = "Double indicating capacity kW if applicable.")
	private double kw; // this may be a subset of the entire capacity of the vessel
	
	@ApiModelProperty(required = true, value = "Integer indicating the related vessel id.")
    @Getter(onMethod = @__(@JsonIgnore))
    @Setter
	private Integer vesselId;
	
	@ApiModelProperty(required = true, value = "Boolean indicating if capacity is in 'Proposed' status, applies to 'off-register' capacity only.")
	@JsonInclude(Include.NON_NULL)
	private boolean proposed; //off-register only attributes

	@ApiModelProperty(required = true, value = "VesselSummaryDTO class definig vessel summary information.")
	@JsonProperty("vessel")
	private VesselSummaryDTO vesselSummary;
	
	@ApiModelProperty(required = true, value = "CapacityDetailDTO list defining low-level capacity block / detail information.")
	@JsonProperty("blocks")
	private List<CapacityDetailDTO> capDetail;
	
	@ApiModelProperty(required = true, value = "PenaltyPointsDTO list defining penaltyPoints information if applicable.")
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
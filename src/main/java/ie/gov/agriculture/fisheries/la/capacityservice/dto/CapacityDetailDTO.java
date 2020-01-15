package ie.gov.agriculture.fisheries.la.capacityservice.dto;

import java.util.ArrayList; 
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@ApiModel(description="The CapacityDetailDTO class defining low-level capacity detail / block information.")
public class CapacityDetailDTO {
	@ApiModelProperty(required = true, value = "Integer defining capacity block id.")
	@JsonProperty("capBlockId")
	private Integer Id;
	
	@ApiModelProperty(required = true, value = "Double defining capacity amount.")
	private double capacityAmount; // GT or kW amount
	
	@ApiModelProperty(required = true, value = "String defining capacity type (GT or kW).")
	private String capacityType; // 'GT' | 'kW'
	
	@ApiModelProperty(required = true, value = "String defining capacity offRegisterDate if applicable.")
	@JsonInclude(Include.NON_NULL)
	private String offRegisterDate; // dd/mm/yyyy
	
	@ApiModelProperty(required = true, value = "String defining capacity expiryDate if applicable.")
	@JsonInclude(Include.NON_NULL)
	private String expiryDate; // dd/mm/yyyy
	
	@ApiModelProperty(required = true, value = "Integer defining capacity sourceVesselId if applicable, the original source vessel for the capacity block.")
	@JsonInclude(Include.NON_NULL)
	private Integer sourceVesselId;       // future-proof, in case we need to do searches on the source vessel
	
	@ApiModelProperty(required = true, value = "String defining capacity sourceVesselName if applicable, the original source vessel for the capacity block.")
	@JsonInclude(Include.NON_NULL)
	private String sourceVesselName;
	
	@ApiModelProperty(required = true, value = "Long defining related capSegmentId.")
	private Long capSegmentId;
	
	@ApiModelProperty(required = true, value = "PenaltyPointsDTO list defining penaltyPoints if applicable.")
	private List<PenaltyPointsDTO> penaltyPoints;
	
	@ApiModelProperty(required = true, value = "TrackRecordDTO list defining trackRecord if applicable.")
	@JsonInclude(Include.NON_NULL)
	private List<TrackRecordDTO> trackRecord;
	
	/* Manual getter for penaltyPoints, if null or empty then return null (rather than empty list) */
	@JsonInclude(Include.NON_NULL)
	public List<PenaltyPointsDTO> getPenaltyPoints () {
		return (
			penaltyPoints==null ? null :
				(penaltyPoints.isEmpty()) ? null : penaltyPoints 
		);
	}
	
	/* TODO - Manual getter for trackRecord -- MOCKED FOR NOW, TO BE REPLACED WITH LIVE DATA */
	@JsonInclude(Include.NON_NULL)
	public List<TrackRecordDTO> getTrackRecord () {
		return null;
	}
}
package ie.gov.agriculture.fisheries.la.capacityservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@ApiModel(description="The TrackRecordDTO class defining track record information.")
public class TrackRecordDTO {
	@ApiModelProperty(required = true, value = "Integer defining trackRecordType, ie: TIER_1_MACKERAL, TIER_2_MACKERAL, CS_HERRING, NW_HERRING, GENERAL (WHITEFISH).")
	@JsonProperty("type")
	private Integer trackRecordType;
	
	@ApiModelProperty(required = true, value = "Boolean indicating if quotaEligibility is applicable.")
	private boolean quotaEligibility;
	
	public TrackRecordDTO (int trackRecordType, boolean quotaEligibility) {
		this.trackRecordType = trackRecordType;
		this.quotaEligibility = quotaEligibility;
	}
}

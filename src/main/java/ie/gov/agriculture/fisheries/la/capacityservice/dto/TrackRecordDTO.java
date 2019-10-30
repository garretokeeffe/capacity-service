package ie.gov.agriculture.fisheries.la.capacityservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class TrackRecordDTO {
	@JsonProperty("type")
	private Integer trackRecordType;
	
	private boolean quotaEligibility;
	
	public TrackRecordDTO (int trackRecordType, boolean quotaEligibility) {
		this.trackRecordType = trackRecordType;
		this.quotaEligibility = quotaEligibility;
	}
}

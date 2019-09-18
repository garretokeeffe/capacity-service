package ie.gov.agriculture.fisheries.la.capacityservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class TrackRecordDTO {
	private Long ID;
	private String trackRecordType;
	private boolean quotaEligibility;
}

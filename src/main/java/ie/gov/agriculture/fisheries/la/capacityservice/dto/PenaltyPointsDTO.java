package ie.gov.agriculture.fisheries.la.capacityservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class PenaltyPointsDTO {
	private Long ID;
	private String ASSIGNEDPOINTS;
	private String EXPIRYDATE;
	private Long PENALTYCOMMENT;
	private String CAPACCOUNTID;
	private String CAPSEGMENTID;
}

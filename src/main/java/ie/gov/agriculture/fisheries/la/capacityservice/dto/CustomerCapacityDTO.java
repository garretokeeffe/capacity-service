package ie.gov.agriculture.fisheries.la.capacityservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class CustomerCapacityDTO {
	private String capAccountId;
	private String capacityStatus;
	private String capacityStatusDesc;
	private String ownerId;
	private String vesselId;
	private String vesselName;
	private String capsegmentid;
	private String capacitySegmentId;
	private String capacitySegmentDesc;
	private String VesselStatus;
	private String VesselStatusDesc;
	private String UOMID;
	private String UOMDesc;
	private String GROSSBALANCE;
	private String PENDINGBALANCE;
	private String PROPOSEDBALANCE;
	private String FREEBALANCE;
}

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
public class CapacityDetailDTO {
	@JsonProperty("capBlockId")
	private Integer Id;
	
	private double capacityAmount; // GT or kW amount
	private String capacityType; // 'GT' | 'kW'
	
	@JsonInclude(Include.NON_NULL)
	private String offRegisterDate; // dd/mm/yyyy
	
	@JsonInclude(Include.NON_NULL)
	private String expiryDate; // dd/mm/yyyy
	
	@JsonInclude(Include.NON_NULL)
	private String sourceVesselId;       // future-proof, in case we need to do searches on the source vessel
	
	@JsonInclude(Include.NON_NULL)
	private String sourceVesselName;
	
	private Long capSegmentId;
	
	@JsonInclude(Include.NON_NULL)
	private String pointsAssigned;
	
	@JsonInclude(Include.NON_NULL)
	private PenaltyPointsDTO penaltyPoints;
	
	@JsonInclude(Include.NON_NULL)
	private List<TrackRecordDTO> trackRecord;
}

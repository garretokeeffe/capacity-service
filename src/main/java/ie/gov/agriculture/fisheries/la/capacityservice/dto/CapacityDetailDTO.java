package ie.gov.agriculture.fisheries.la.capacityservice.dto;

import java.util.ArrayList; 
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
	private Integer sourceVesselId;       // future-proof, in case we need to do searches on the source vessel
	
	@JsonInclude(Include.NON_NULL)
	private String sourceVesselName;
	
	private Long capSegmentId;
	
	private List<PenaltyPointsDTO> penaltyPoints;
	
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
	
	/* Manual getter for trackRecord, MOCKED FOR NOW, TO BE REPLACED WITH LIVE DATA */
	@JsonInclude(Include.NON_NULL)
	public List<TrackRecordDTO> getTrackRecord () {
		List<TrackRecordDTO> trackRecordOut = new ArrayList<>(0);
		
		trackRecordOut.add(new TrackRecordDTO (1, true));
		trackRecordOut.add(new TrackRecordDTO (3, false));
		trackRecordOut.add(new TrackRecordDTO (5, true));
		
		return trackRecordOut;
	}
}

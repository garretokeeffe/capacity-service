package ie.gov.agriculture.fisheries.la.capacityservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class PenaltyPointsDTO {
    @Getter(onMethod = @__(@JsonIgnore))
    @Setter
	private Long ID;

	@JsonProperty("numberOfPoints")
	private String ASSIGNEDPOINTS;
	
	private String EXPIRYDATE;
	
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("reasons")
	private String PENALTYCOMMENT;

    @Getter(onMethod = @__(@JsonIgnore))
    @Setter
	private String CAPACCOUNTID;
	
    @Getter(onMethod = @__(@JsonIgnore))
    @Setter
	private String CAPSEGMENTID;
}

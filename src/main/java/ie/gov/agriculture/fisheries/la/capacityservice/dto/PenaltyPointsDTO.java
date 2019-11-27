package ie.gov.agriculture.fisheries.la.capacityservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore; 
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@ApiModel(description="The PenaltyPointsDTO class defining penalty points information.")
public class PenaltyPointsDTO {
	@ApiModelProperty(required = true, value = "Long defining PenaltyPoints id.")
    @Getter(onMethod = @__(@JsonIgnore))
    @Setter
	private Long ID;

	@ApiModelProperty(required = true, value = "Integer defining ASSIGNEDPOINTS (as per the data model).")
	@JsonProperty("numberOfPoints")
	private Integer assignedPoints;
	
	@ApiModelProperty(required = true, value = "String defining penalty points expiryDate.")
	@JsonProperty("expiryDate")
	private String expiryDate;
	
	@ApiModelProperty(required = true, value = "String defining penalty points comment if applicable.")
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("reasons")
	private String penaltyComment;

	@ApiModelProperty(required = true, value = "String defining related customer capacity account id.")
    @Getter(onMethod = @__(@JsonIgnore))
    @Setter
	private String capAccountId;
	
	@ApiModelProperty(required = true, value = "String defining related capacity account segment id.")
    @Getter(onMethod = @__(@JsonIgnore))
    @Setter
	private String capSegmentId;
}
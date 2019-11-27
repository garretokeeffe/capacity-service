package ie.gov.agriculture.fisheries.la.capacityservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@ApiModel(description="The VesselSummaryDTO class defining detailed vessel information.")
public class VesselSummaryDTO {
	@ApiModelProperty(required = true, value = "Integer defining vessel id.")
	private Integer id;
	
	@ApiModelProperty(required = true, value = "String defining vessel name.")
	@JsonInclude(Include.NON_NULL)
	private String name;
	
	@ApiModelProperty(required = true, value = "Integer defining vessel status.")
	@JsonInclude(Include.NON_NULL)
	private Integer status; // VESSEL STATUS REFERENCE DATA
	
	@ApiModelProperty(required = true, value = "String defining vessel cfr.")
	@JsonInclude(Include.NON_NULL)
	private String cfr;
	
	@ApiModelProperty(required = true, value = "String defining vessel prn.")
	@JsonInclude(Include.NON_NULL)
	private String prn; // port reg number
	
	@ApiModelProperty(required = true, value = "Double defining vessel capacity - GT.")
	private double gt; // Dimensions - tonnage
	
	@ApiModelProperty(required = true, value = "Double defining vessel capacity - kW.")
	private double kw; // Propulsion - engine power
}
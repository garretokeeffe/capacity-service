package ie.gov.agriculture.fisheries.la.capacityservice.dto;

import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@ApiModel(description="The AllCapacityDTO class storing high-level customer capacity information.")
public class AllCapacityDTO {
	@ApiModelProperty(required = true, value = "Integer defining owner / customer id.")
	private Integer ownerId;
	
	@ApiModelProperty(required = true, value = "CapacityDTO list defining owner / customer 'On-Register' capacity.")
	private List<CapacityDTO> onRegister;
	
	@ApiModelProperty(required = true, value = "CapacityDTO list defining owner / customer 'Off-Register' capacity.")
	private List<CapacityDTO> offRegister;
}

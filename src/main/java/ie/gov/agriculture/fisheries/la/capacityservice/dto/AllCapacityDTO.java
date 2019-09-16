package ie.gov.agriculture.fisheries.la.capacityservice.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class AllCapacityDTO {
	private String ownerId;
	
	private List<CapacityDTO> onRegister;
	private List<CapacityDTO> offRegister;
	
//	public List<CapacityDTO> getOnRegister() {
//		return onRegister;
//	}
//	public void setOnRegister(List<CapacityDTO> onRegister) {
//		this.onRegister = onRegister;
//	}
//	public List<CapacityDTO> getOffRegister() {
//		return offRegister;
//	}
//	public void setOffRegister(List<CapacityDTO> offRegister) {
//		this.offRegister = offRegister;
//	}
}

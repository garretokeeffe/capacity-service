package ie.gov.agriculture.fisheries.la.capacityservice.entity;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class AllCapacity {
	private Integer ownerId;
	
	private List<Capacity> onRegister;
	private List<Capacity> offRegister;
}

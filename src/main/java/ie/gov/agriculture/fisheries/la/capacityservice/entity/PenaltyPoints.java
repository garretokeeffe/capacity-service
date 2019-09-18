package ie.gov.agriculture.fisheries.la.capacityservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor @ToString
public class PenaltyPoints {	
	@Id
	@Column(name = "ID") //TDFI_CAPACITYACCOUNTPOINTSID
	private Long ID;
	
	@Column(name = "ASSIGNEDPOINTS")
	private String ASSIGNEDPOINTS;
	
	@Column(name = "EXPIRYDATE")
	private String EXPIRYDATE;
	
	@Column(name = "PENALTYCOMMENT")
	private Long PENALTYCOMMENT;

	@Column(name = "CAPACCOUNTID")
	private String CAPACCOUNTID;
	
	@Column(name = "CAPSEGMENTID")
	private String CAPSEGMENTID;
}

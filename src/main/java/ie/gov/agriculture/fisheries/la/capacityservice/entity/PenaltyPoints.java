package ie.gov.agriculture.fisheries.la.capacityservice.entity;

import java.time.LocalDate;

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
	@Column(name = "ID")
	private Long ID;
	
	@Column(name = "ASSIGNEDPOINTS")
	private String ASSIGNEDPOINTS;
	
	@Column(name = "EXPIRYDATE")
	private LocalDate EXPIRYDATE;
	
	@Column(name = "PENALTYCOMMENT")
	private String PENALTYCOMMENT;

	@Column(name = "CAPACCOUNTID")
	private String CAPACCOUNTID;
	
	@Column(name = "CAPSEGMENTID")
	private String CAPSEGMENTID;
}

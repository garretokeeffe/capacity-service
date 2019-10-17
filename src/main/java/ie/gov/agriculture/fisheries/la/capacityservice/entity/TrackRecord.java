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
public class TrackRecord {	
	@Id
	@Column(name = "ID")
	private Long ID;
	
	@Column(name = "trackRecordType")
	private Integer trackRecordType;
	
	@Column(name = "quotaEligibility")
	private boolean quotaEligibility;
}
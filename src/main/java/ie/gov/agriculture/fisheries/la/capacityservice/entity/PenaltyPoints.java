package ie.gov.agriculture.fisheries.la.capacityservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The penalty points entity
 * @author garret.okeeffe
 *
 */
@Entity
@Getter @Setter @NoArgsConstructor @ToString
public class PenaltyPoints {

	@Id
	@Column(name = "CAPACCOUNTID")
	private Long id;
	
	@Column(name = "OWNERID")
	private String ownerId;
	
	@Column(name = "CUSTOMERNAME")
	private String customerName;
	
	@Column(name = "SEGMENTID")
	private Long segmentId;
	
	@Column(name = "CROSSSEGMENT")
	private String crossSegment;
	
	@Column(name = "ASSIGNEDGT")
	private Float assignedGT;
	@Column(name = "PENDINGGT")
	private Float pendingGT;
	
	@Column(name = "ASSIGNEDKW")
	private Float assignedKW;
	@Column(name = "PENDINGKW")
	private Float pendingKW;
	
	@Column(name = "ASSIGNPOINTS")
	private Float pointsAssigned;
	
	@Column(name = "POINTSEXPIREDATE")
	private Date expiryDate;
	
	public transient String reasons;
}

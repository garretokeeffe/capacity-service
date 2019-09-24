package ie.gov.agriculture.fisheries.la.capacityservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Class represent the complete Vessel details
 * @see https://confluence.agriculture.gov.ie/confluence/display/FISHERIES/Get+Vessels
 * @author garret.okeeffe
 *
 */
@Entity
@Getter @Setter @NoArgsConstructor @ToString
public class CustomerCapacity {
	@Id
	@Column(name = "ID")
	private String Id;
	
	@Column(name = "capaccountid")
	private String capAccountId;
	
	@Column(name = "capacitystatus")
	private String capacityStatus;

	@Column(name = "capacitystatusdesc")
	private String capacityStatusDesc;
	
	@Column(name = "ownerid")
	private String ownerId;
	
	@Column(name = "vesselid")
	private String vesselId;
	
	@Column(name = "vesselname")
	private String vesselName;
	
	@Column(name = "capsegmentid")
	private String capsegmentid;
	
	@Column(name = "capacitysegmentid")
	private String capacitySegmentId;
	
	@Column(name = "capacitysegmentdesc")
	private String capacitySegmentDesc;
	
	@Column(name = "UOMID")
	private String UOMID;
	
	@Column(name = "UOMDesc")
	private String UOMDesc;
	
	@Column(name = "vesselstatus")
	private String vesselStatus;

	@Column(name = "vesselstatusdesc")
	private String vesselStatusDesc;
	
	@Column(name = "GROSSBALANCE")
	private String grossBalance;
	
	@Column(name = "PENDINGBALANCE")
	private String pendingBalance;

	@Column(name = "PROPOSEDBALANCE")
	private String proposedBalance;
	
	@Column(name = "FREEBALANCE")
	private String freeBalance;
}

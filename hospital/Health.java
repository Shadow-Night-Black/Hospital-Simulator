package hospital;
import illness.Illness;
/*Stores information about a persons health and any illness that they have
 * 
 */

public class Health {
	private HealthState healthState;
	private int recoveryTime;
	private Illness illness;


	public Health(HealthState state, Illness illness, int recoveryTime) {
		this.healthState = state;
		this.illness = illness;
		this.recoveryTime = recoveryTime;
	}
	
	public HealthState getHealthState() {
		return healthState;
	}

	public Illness getIllness() {
		return illness;
	}

	//Patient is no longer ill, but keep illness in case it is every need
	//just setRecovering
	public boolean treat() {
		setRecovering();
		//Currently cant fail so return true
		return true; 
	}

	//Marks a patient as no longer ill and set the personal recovering time to that of the illness
	public void setRecovering() {
		this.recoveryTime = illness.getRecoveryTime();
		healthState = HealthState.RECOVERING;
	}
	
	//Called everyday for recovery
	public void aDayPasses() {
		//only change recoverytime if they are recovering 
		if (healthState == HealthState.RECOVERING) {
			//decrement the days left to recover
			recoveryTime--;
			if (recoveryTime <= 0) {
				//if there are no more days left to recover, they are no healthy
				healthState = HealthState.HEALTHY;
			}
		}
	}

	//return if a patient is healthy
	public boolean isHealthy() {
		return healthState == HealthState.HEALTHY; 
	}
	
	//return if a patient is sick
	public boolean isSick() {
		return healthState == HealthState.SICK;
	}
	


	//If the patient is recovering, return the time until they are better
	//else return the worst case scenario, the maximum recovery time they can have
	//Unused, Implimented purely for the spec
	public int getRecoveryTime() {
		if (healthState == HealthState.RECOVERING)  
			return recoveryTime;
		else 
			return illness.getMaxRecoveryTime();
	}

	//set the current illness to the specified one
	//Unused, implemented purely for the spec
	public void setIllness(Illness illness) {
		this.illness = illness;

		//check to see if the illness is valid and not the null illness (id 0)
		if (illness != null && illness.getId() != 0)
			//if there is an illness then they are now sick
			healthState = HealthState.SICK;
		else
			//else they are healty
			healthState = HealthState.HEALTHY;
	}
	
	//DONT LIKE THIS ->> TOO MANY EDGE VALUES (sick but no illness, healthy with illness etc)
	//Unused, implemented purely for the spec
	public void setHealthState(HealthState healthState) {
		this.healthState = healthState;
		System.err.println("ERROR: setHealthState Decrepit, Use setIllness or setRecovering to avoid bad edge cases");
	}
}

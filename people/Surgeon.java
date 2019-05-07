package people;

import java.util.HashMap;

import hospital.Health;
import hospital.Hospital;
/* Basic Surgeon that extends Doctors, can operate on Patients
 * 
 */
public class Surgeon extends Doctor {
	//canOperate provides a similar function to the Doctors canTreat, 
	//but any illness stored in there requires the Patient to be in a Operating Theatre
	protected HashMap<Integer, Boolean> canOperate;

	public Surgeon(Gender gender, int age, Health health, Hospital hospital) {
		super(gender, age, health, hospital);
		//set specialism to be surgeon, will override the doctor specialism
		specialism = Specialism.SURGEON;
		canOperate = new HashMap<Integer, Boolean>();
		//only store the illness that require a Operating theatre in canOperate
		canOperate.put(4, true);
	}
	
	public boolean canTreat(int illnessId) {
		//If the doctor can treat it, then return true
		if (super.canTreat(illnessId))
			return true;
		//else check if we can operate on it
		if (canOperate.get(illnessId) != null)
			//and return if we can operate on it
			return canOperate.get(illnessId);
		else 
			return false;
	}

	//Calls treatPatient from Doctor, if that failed, try to operate
	protected boolean treatPatient() {
		if(!super.treatPatient()) {
			int id = assignedPatient.getHealth().getIllness().getId();
			if (canOperate.containsKey(id) && canOperate.get(id)) {
				return operate();
			}
		}
		return false;
	}
	
	//operates on a patient, provided that the illness requires operation and the patient is in an operating theatre 
	private boolean operate() {
		if (hospital.getOperatingTheatre(assignedPatient) != null) {
			System.out.println(this.getName() + " operates on " + assignedPatient.getName() + " to treat " + assignedPatient.getHealth().getIllness().getName());
			return assignedPatient.treat();
		}
		return false;
		
	}
}

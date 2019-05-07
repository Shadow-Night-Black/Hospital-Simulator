package people;
import java.util.HashMap;
import hospital.Health;
import hospital.Hospital;
/* Doctor class, treats patients with illness id 1 -> 3
 * Superclass of all Doctors and Surgeon
 */
public class Doctor extends Person {
	//specialism unused but implemented for spec
	protected Specialism specialism;
	protected Patient assignedPatient;
	//canTreat holds the ids of every illness that the doctor can treat, can then easily modify this if needs be during runtime 
	//for example a doctor learns how to do a simple operation / new Illness
	protected HashMap<Integer, Boolean> canTreat;
	protected Hospital hospital;

	//Sets the specialism (will be overwritten by subclasses), sets canTreat to the defualt ids of 1,2 and 3
	public Doctor(Gender gender, int age, Health health, Hospital hospital) {
		super(gender, age, health);
		specialism = Specialism.DOCTOR;
		this.hospital = hospital;
		canTreat = new HashMap<Integer, Boolean>();
		canTreat.put(1, true);
		canTreat.put(2, true);
		canTreat.put(3, true);
	}
	
	//returns the name doctors name prefaced with their title (Doctor, Surgeon etc)
	public String getName() {
		return this.getClass().getSimpleName() + " " + super.getName();
	}

	public Specialism getSpecialism() {
		return specialism;
	}

	//assigns a patient to the doctor to be treated
	public void assignPatient(Patient p) {
		assignedPatient = p;
	}
	
	//returns if the doctor has a patient or not
	public boolean hasAssignedPatient() {
		return assignedPatient != null;
	}

	//return true if the doctor can treat an illness
	public boolean canTreat(int illnessId) {
		if (canTreat.get(illnessId) != null)
			return canTreat.get(illnessId);
		else 
			return false;
	}
	
	//If the doctor has a patient, and they can treat the illness, then output that they are treating the patient
	//before calling treat on the patient
	//output used for subclasses to test if treat worked
	protected boolean treatPatient() {
		//prevent null pointer errors
		if (assignedPatient != null) {
			int illnessID = assignedPatient.getHealth().getIllness().getId();
			//check we can treat this illness
			if (canTreat.containsKey(illnessID) && canTreat.get(illnessID)) {
				//Output we are treating this patient
				System.out.println(this.getName() + " treats " + assignedPatient.getName() + " of " + assignedPatient.getHealth().getIllness().getName());
				return assignedPatient.treat();
			}
		}
		return false;
	}
			
	//check to see if we have a patient, if so treat the patient, and then remove the patient 
	//so we can be assigned a new patient tommorrow
	//return value never used, only implemented for spec
	public boolean aDayPasses() {
		if (assignedPatient != null) {
			treatPatient();
			assignedPatient = null;
		}
		//only for spec, return value never used.
		return false;
	}

}
	


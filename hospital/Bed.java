package hospital;
import people.Patient;

/* Stores information about the patient in it
 * Used so that the arraylist in hospital is kept ordered
 * and bed numbers are persevered when patients are discharged.
 */

public class Bed {
	protected Patient patient;

	public Patient getPatient() {
		return patient;
	}

	//Patient leaves the hospial, so no longer needs a bed
	public void discharge() {
		patient = null;
	}

	//assigns a patient to this bed
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	//check to see if the bed is empty
	public boolean hasPatient() {
		if (patient == null) 
			return false;
		else
			return true;
	}
	
	//If there is a patient, call aDayPasses
	public void aDayPasses() {
		if (patient != null) {
			patient.aDayPasses();
		}
	}

}


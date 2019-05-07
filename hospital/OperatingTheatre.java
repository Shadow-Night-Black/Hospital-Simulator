package hospital;
import people.Patient;
/*Store infomation about the patient in the operating theatre
 * 
 */
public class OperatingTheatre {
	private Patient patient;

	//return if there is a patient in the theatre
	public boolean isFree() {
		if (patient == null)
			return true;
		else
			return false;
	}

	//set this theatres patient
	public void prep(Patient patient) {
		this.patient = patient;
	}

	//remove the patient from this theatre
	public void discharge() {
		this.patient = null;
	}
	
	//return the patient in this theatres
	public Patient getPatient() {
		return patient;
	}
}

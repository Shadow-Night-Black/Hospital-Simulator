package people;

import java.util.ArrayList;

import hospital.Hospital;
/* Class that runs the hospital and holds the list of Doctors
 * 
 */

public class HospitalAdministrator {
	private Hospital hospital;
	private ArrayList<Doctor> doctors;

	public HospitalAdministrator(Hospital h, ArrayList<Doctor> d) {
		this.hospital = h;
		this.doctors = d;
	}

	//adds a Doctor to the hospital
	//currently unused
	public void addDoctor(Doctor d) {
		doctors.add(d);
	}
	
	//Outputs the current day and how many patients are in the hospital
	//Calls aDayPasses once every 500ms
	public void go() {
		int day = 1;
		while(true) {
			System.out.println("\n\nDay " + day);
			System.out.println("There are " + hospital.size() + " Patients in the hospital");
			aDayPasses();
			day++;
			try {
				Thread.sleep(500);
			}catch(InterruptedException e) {}
		}
	}
	
	
	//Assigns Doctors Patients based on their specialities
	//then calls aDayPasses on the Doctors to treat their Patients
	//then calls discharge() on the hospital to discharge the healthy patients
	//before calling aDayPasses on the hospital to allow patients to recover
	//and then generates a random number of Patients to be admitted to the hospital
	public void aDayPasses() {
		assignDoctors();
		for (Doctor d : doctors) {
			d.aDayPasses();
		}
		
		System.out.println();
		hospital.discharge();
		
		hospital.aDayPasses();
		System.out.println();
		
		//generates a random amount of Patients between 0 and 7 skewed towards the lower end of patients
		for (int i = 0; i <= Math.random() * 7; i++) {
			//Generates the Patient and Outputs their name and illness
			Patient p = PatientGenerator.generatePatient();
			hospital.admitPatient(p);
		}
		System.out.println();

	}

	//Assigns Docotors to the most skilled doctor availible to treat them
	private void assignDoctors() {
		ArrayList<Patient> toTreat;
		//used to keep track of Doctors assigned, if we assign everyone, then stop looping,
		int doctorsAssigned = 0;
		
		toTreat = hospital.getSickPatients();
		for (Patient p: toTreat){
			//if we find a Doctor increment doctorsAssigned
			if (findDoctor(p)) {
				doctorsAssigned++;
			}
			
			//If all doctors are assigned then stop
			if (doctorsAssigned >= doctors.size())
				break;
		}
		//Check next Illness
		//While we still have doctors that can be assigned, or Illness to Check
	}

	//Finds a Doctor for a Illness
	//return true if one is found else returns false
	private boolean findDoctor(Patient p) {
		//loop through every Doctor to find one to treat the Illness
		for (Doctor d: doctors) {
			//Check to see if the doctor already has a patient, and if they dont, if they can treat the Illness
			if (!d.hasAssignedPatient() && d.canTreat(p.getIllnessId())){
				//Check to see if the Illness requires a theatre, if it does, try to assign the patient to one
				if (p.getHealth().getIllness().getRequiresTheatre()) {
					//get a free theatre (returns -1 if there is not a free theatre)
					int theatreNum = hospital.getFreeTheatre();
					if(theatreNum != -1) {
						//The Doctor can treat this patient in this theatre, so assign the patient and prep them for theatre
						hospital.prepForTheatre(theatreNum, p);
						d.assignPatient(p);
						return true;
					}else {
						//Needs a Theatre to be treated, to point assigning without one, 
						//Doctor can treat someone else that doesnt need a theatre
						return false;
					}
				}else {
					//Illness Doesnt need a theatre, and the Doctor can treat them
					//assign the patient and then return
					d.assignPatient(p);
					return true;
				}
				
			}
		}
		//No Doctor found that can treat this patient, return false
		return false;
	}

}

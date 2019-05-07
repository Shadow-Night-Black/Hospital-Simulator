package hospital;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import people.Patient;

/* Class used to store information about beds (and therefore patients) and operating theatres
 * 
 */

public class Hospital {
		private ArrayList<Bed> beds;
		private ArrayList<OperatingTheatre> operatingTheatres;


		//default bed / theatre number is 50 beds and 4 theatres
		public Hospital() {
			this(50, 4);
		}

		//create the beds and operating theatres needed
		public Hospital(int numOfBeds, int numOfOperatingTheatres) {
			beds = new ArrayList<Bed>(numOfBeds);
			//create the beds so people can be stored in them
			for (int i = 0; i < numOfBeds; i++ )
				beds.add(new Bed());
			
			operatingTheatres = new ArrayList<OperatingTheatre>(numOfOperatingTheatres);
			//create the operating theatres to they can take in patients
			for (int i = 0; i< numOfOperatingTheatres; i++)
				operatingTheatres.add(new OperatingTheatre());
		}

		//If the hospital is not full, put the patient in the first available bed and return the bed number (-1 if no bed found) 
		//return never used, but implemented for the spec 
		public int admitPatient(Patient p) {
			//find first empty bed and return that bed number
			for (int i = 0; i < beds.size(); i++ ) {
				Bed bed = beds.get(i);
				if (!bed.hasPatient()) {
					System.out.println("Admitting " + p.getName() + " with " + p.getHealth().getIllness().getName());
					bed.setPatient(p);
					return i;
				}
			}
			//if no bed has been found, return -1
			System.out.println("Can't Admit" + p.getName() + " with " + p.getHealth().getIllness().getName());
			System.out.println("The hospital is full!");
			return -1;
		}

		//return the number of patients in the hopsital
		public int size() {
			int total = 0;
			for (Bed bed : beds) {
				if (bed.hasPatient()) {
					total++;
				}
			}
			return total;
		}
		
		//return the first free theatre, -1 if no theatre found
		public int getFreeTheatre() {
			for (int i = 0; i < operatingTheatres.size(); i++) {
				if (isTheatreFree(i)) {
					return i;
				}
			}
			return -1;
		}

		//check to see if a theatre is free
		public boolean isTheatreFree(int theatreNumber) {
			//make sure the theatre is within the array limit to stop ArrayOutofBound exceptions
			if (theatreNumber < operatingTheatres.size())
				return (operatingTheatres.get(theatreNumber).isFree());
			else
				return false;
		}

		//put a patient in a theatre, will overide an existing patients that are already in the theatre 	
		public void prepForTheatre(int theatreNumber, Patient patient) {
			//make sure the theatre is within the array limit to stop ArrayOutofBound exceptions
			if (theatreNumber < operatingTheatres.size())
				operatingTheatres.get(theatreNumber).prep(patient);
		}

		//removes a patient from a theatre
		public void takeForRecovery(int theatreNumber) {
			//make sure the theatre is within the array limit to stop ArrayOutofBound exceptions
			if (theatreNumber < operatingTheatres.size())
				operatingTheatres.get(theatreNumber).discharge();
		}
		
		//return the operating theatre a patient is in, or null if they are not in on
		public OperatingTheatre getOperatingTheatre(Patient p) {
			for (OperatingTheatre t: operatingTheatres) {
				if (t.getPatient() == p) {
					return t;
				}
			}
			return null;
		}
		
		//returns all the sick patients in the hospital, order highest to lowest in reference to their illness id
		public ArrayList<Patient> getSickPatients() {
			ArrayList<Patient> patients = new ArrayList<Patient>();
			for (Bed b: beds) {
				if (b.hasPatient() && b.getPatient().isSick())
					patients.add(b.getPatient());
			}
			Collections.sort(patients, new Comparator<Patient>() {
				public int compare(Patient p1, Patient p2) {
					if (p1.getIllnessId() == p2.getIllnessId())
						return 0;
					else
						return p2.getIllnessId() < p1.getIllnessId() ? -1 : 1;
				}
			}
			);
			return patients;
			
		}
		
		//loops through all patients and discharges them if they are healthy
		public void discharge() {
			for (Bed b: beds) {
				if (b.getPatient() != null && b.getPatient().isHealthy()) {
					System.out.println(b.getPatient().getName() + " is healthy and being discharged!");
					b.discharge();
				}
			}
		}

		//Loops though all theatres and takes patients for recovery, then calls aDayPasses() on each bed (which calls it on each patient)
		public boolean aDayPasses() {
			for (int i = 0; i < operatingTheatres.size(); i++) {
				takeForRecovery(i);
			}
			for (Bed b: beds) {
				b.aDayPasses();
			}
			//Purely for the spec, return value never uses
			return false;
		}

		//Return the patient in a certain bed id
		//Purely for the spec, never used in simulation,
		public Patient getPatient(int i) {
			return beds.get(i).getPatient();
		}
		
		//discharges a patient in a certain bed id
		//Purely for the spec, never used in simulation
		public void dischargePatient(int bednumber) {
			beds.get(bednumber).discharge();
		}
		

}

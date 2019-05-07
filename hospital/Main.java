package hospital;

import hospital.HealthState;
import illness.Illness;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import people.Doctor;
import people.Gender;
import people.HospitalAdministrator;
import people.LimbSurgeon;
import people.OrganSurgeon;
import people.Patient;
import people.Surgeon;

/*Used to read in a config file and create patients, doctors and hospitals for them,
 * before passing them to the relevant classes and calling go on the HospitalAdministrator
 */

public class Main {

	//opens the Buffered readers for file input then figures out which config reader it should use
	public static void main(String[] args) {
		String firstLine = null;
		BufferedReader config = null;
		try {
			config = new BufferedReader(new FileReader(args[0]));
			firstLine = config.readLine();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Useage: java hospital.Main <config file>");
			System.exit(1);
		} catch (Exception e) {
			System.err.println("Error: Cant read Config File" + args[0]);
			System.exit(1);
		}

		//uses the config specified in the spec
		if (firstLine.split(":")[0].equals("hospital")) {
			readConfig(firstLine, config);
		}else {
			//uses xml config file (not currently implemented, this only used for debugging purposes currently
			System.out.println("line : " + firstLine );
			System.out.println("Split : " + firstLine.split("")[0]);
			readXML();
		}
	}

	//creates patients, doctors and the hospital, takes the first line and a buffered reader that is already open
	public static void readConfig(String line, BufferedReader config) {
		String[] lineParts;
		Hospital hospital = null;
		ArrayList<Patient> patients = new ArrayList<Patient>();
		ArrayList<Doctor> doctors = new ArrayList<Doctor>();
		//loop though every line and switch on the first part to create the correct class
		do {
			try {
				//devide the line bassed on ':' and ','
				lineParts = line.split(":|,");
				//for each case create the relevant class and add it to the relevant list
				switch (lineParts[0]) {

				//Form hospital:beds,theatres
				case "hospital":
					hospital = new Hospital(Integer.parseInt(lineParts[1]), Integer.parseInt(lineParts[2]));
					break;

				//form patient:gender,age,illness,recoverytime
				case "patient":
					Illness illness = new Illness(Integer.parseInt(lineParts[3]));
					Health health = new Health(lineParts[3].equals("0") ? HealthState.RECOVERING : HealthState.SICK, illness, Integer.parseInt(lineParts[4]));
					patients.add(new Patient(Gender.parseGender(lineParts[1]) , Integer.parseInt(lineParts[2]), health));
					break;

				//form patient:gender,age
				case "doctor":
					doctors.add(new Doctor(Gender.parseGender(lineParts[1]), Integer.parseInt(lineParts[2]), new Health(HealthState.HEALTHY, null, 0), hospital));
					break;

				//form surgeon:gender,age
				case "surgeon":
					doctors.add(new Surgeon(Gender.parseGender(lineParts[1]), Integer.parseInt(lineParts[2]), new Health(HealthState.HEALTHY, null, 0), hospital));
					break;

				//form limbSurgeon:gender,age
				case "limbSurgeon":
					doctors.add(new LimbSurgeon(Gender.parseGender(lineParts[1]), Integer.parseInt(lineParts[2]), new Health(HealthState.HEALTHY, null, 0), hospital));
					break;

				//form organSurgeon:gender,age
				case "organSurgeon":
					doctors.add(new OrganSurgeon(Gender.parseGender(lineParts[1]), Integer.parseInt(lineParts[2]), new Health(HealthState.HEALTHY, null, 0), hospital));
					break;

				//was an invalid line, throw exception
				default:
					throw new ConfigFileException(line);
				}

				//while there are still more lines, read in the next line and loop
				if (config.ready())
					line = config.readLine();
				else
					//if no more lines, break out of loop
					break;
				
			//catch all the exceptions from invalid configs
			}catch (ConfigFileException e) {
				System.err.println(e);
			}catch ( ArrayIndexOutOfBoundsException e) {
				System.err.println("Invalid Config File");
				System.err.println("Error on line\n" + line);
			}catch ( IOException e) {
				System.err.println("Error Reading Config File");
				System.err.println("Error after line \n " + line);
			}
		} while (true);
		
		//start the simulation
		startSim(hospital, patients, doctors);
	}
	
	//currently unimplemented, will be used for reading saved data from a hospital
	public static void readXML() {

	}

	//create the hospitalAdministrator, admits the patients and adds te doctors, then calls go on the admin
	public static void startSim(Hospital hospital, ArrayList<Patient> patients, ArrayList<Doctor> doctors) {
		System.out.println("Starting the Hospital \n");
		for (Patient p: patients) {
			hospital.admitPatient(p);
		}
		//create the hospital admin and go
		HospitalAdministrator admin = new HospitalAdministrator(hospital, doctors);
		admin.go();
	}
}

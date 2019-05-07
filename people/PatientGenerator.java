package people;

import hospital.Health;
import hospital.HealthState;
import illness.Illness;

import java.util.Random;
/* Create random Patients for admitting into hospital
 * 
 */
public class PatientGenerator {
	private static Random rng = new Random();
	
	//creates a new Patient
	public static Patient generatePatient() {
		//generate a random gender
		Gender gender = rng.nextBoolean() ? Gender.MALE : Gender.FEMALE;
		//generate a random age from 0 to 99
		int age = rng.nextInt(100);
		//generate a random illness from 1 to 8 and assign to a health
		Health health = new Health(HealthState.SICK, new Illness(Illness.getRandomIllnessID()), -1);
		//return this patient
		return new Patient(gender, age, health);
	}

}

package people;
import illness.Illness;
import hospital.Health;
import hospital.HealthState;
/* Implements a couple of aDayPasses for Recovering
 * and several helper functions for passing infomation to and from its Health class
 */
public class Patient extends Person{
	
	public Patient(Gender gender, int age, Health health) {
		super(gender, age, health);
	}
	
	//Takes a char for the gender, and a int age, assumes they are healthy
	//Implimented for the spec, not used in simulation
	public Patient(char c, int i) {
		this(Gender.parseGender(c), i, new Health(HealthState.HEALTHY, new Illness(0), -1));
	}

	//returns the Illness Id
	public int getIllnessId() {
		return health.getIllness().getId();
	}
	
	//rest of the functions just call the same function in Health
	
	public boolean aDayPasses() {
		health.aDayPasses();
		//only for spec, return value never used.
		return false;
	}

	public boolean treat() {
		return health.treat();
	}
	
	public boolean isHealthy() {
		return health.isHealthy();
	}
	
	public boolean isSick() {
		return health.isSick();
	}
}

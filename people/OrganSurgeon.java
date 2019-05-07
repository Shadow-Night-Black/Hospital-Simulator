package people;

import hospital.Health;
import hospital.Hospital;
/* Class extends Surgeon
 * All code required is in surgeon, just need to update the canOperate to hold the extra Illness
 * and the specialism (which is not used)
 */
public class OrganSurgeon extends Surgeon {

	public OrganSurgeon(Gender gender, int age, Health health, Hospital hospital) {
		super(gender, age, health, hospital);
		specialism = Specialism.ORGAN_SURGEON;
		//add the Illness that OrganSurgeon can operate on to the operable list
		canOperate.put(5, true);
		canOperate.put(6, true);
	}

}

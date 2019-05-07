package people;

import hospital.Health;
import hospital.Hospital;
/* Class extends Surgeon
 * All code required is in surgeon, just need to update the canOperate to hold the extra Illness
 * and the specialism (which is not used)
 */
public class LimbSurgeon extends Surgeon {

	public LimbSurgeon(Gender gender, int age, Health health, Hospital hospital) {
		super(gender, age, health, hospital);
		specialism = Specialism.LIMB_SURGEON;
		canOperate.put(7, true);
		canOperate.put(8, true);
	}

}

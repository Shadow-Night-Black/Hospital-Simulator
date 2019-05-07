package people;
/*Enum Used to Store Gender data
 * also holds the parseGenderFunction for char or Strings to Gender
 */
public enum Gender {
	MALE, FEMALE;
	public static Gender parseGender(String s) {
		s = s.toLowerCase();
		if (s.equals("m"))
			return Gender.MALE;
		if (s.equals("f"))
			return Gender.FEMALE;
		return null;
	}

	public static Gender parseGender(char c) {
		return parseGender(String.valueOf(c));
	}
}

package people;
import hospital.Health;
/* Sub Class of Doctor and Patient
 * stores their gender, age, name and health
 */

public abstract  class Person {
	protected Gender gender;
	protected int age;
	protected Health health;
	private String name;
	private static NameGenerator nameGen = new NameGenerator();

	//if no name is specified generate a name and pass it on (main constructor used in this sim)
	public Person(Gender gender, int age, Health health) {
		this(nameGen.getName(gender), gender, age, health);
	}
	
	public Person(String name, Gender gender, int age, Health health) {
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.health = health;
	}
		

	public String getName() {
		return name;
	}

	//returns the gender in char form
	//used in the sim, Implemented for the spec
	public char getGender() throws Exception {
		switch(gender) {
			case MALE:
				return (char)'M';
			case FEMALE:
				return (char)'F';
			default:
				//should not be possible, Gender can only be two values
				//put in to also stop the may not return error 
				System.err.println("ERROR, INVALID GENDER STORED");
				throw new Exception("Invalid Gender");
		}
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public Health getHealth() {
		return health;
	}
	
	abstract boolean aDayPasses();
}

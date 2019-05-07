package illness;
import java.util.Properties;
/* Stores info about a specific illness
 * Also contains the static reference to Illness Properties which contains the info about every Illness
 */

public class Illness {
	//static to stop every Illness reinitialising it, and so every Illness will get the same data
	static IllnessProperties illnessProperties = new IllnessProperties();
	//contains specific illness properties
	private Properties properties;

	//get the properties for this id from illnessProperites
	public Illness(int id) {
		properties = illnessProperties.getProperties(id);
		if (properties == null) 
			throw new RuntimeException("Invalid Id Numerber " + id);
	}
	
	//get the name of the Illness
	public String getName() {
		return properties.getProperty("name");
	}
	
	//getIntProperty used to stop code duplication for every int parameter stored having to try catch the Integer.parseInt() method
	public int getMaxRecoveryTime() {
		return getIntProperty("maxRecoveryTime");
	}
	
	public int getMinRecoveryTime() {
		return getIntProperty("minRecoveryTime");
	}

	public int getId() {
		return getIntProperty("id");
	}

	//return a value bewteen min and max recovery time
	public int getRecoveryTime() {
		int min = getMinRecoveryTime();
		int max = getMaxRecoveryTime();
		//Math.round returns a long so cast it to int
		return (int) Math.round(min + Math.random() * (max - min));
	}

	//Used to cut code duplication
	//throws an exception if an int property is not an int
	private int getIntProperty(String property) {
		try {
			return Integer.parseInt(properties.getProperty(property));
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	//return if it requires a theatre to be treated
	public boolean getRequiresTheatre() {
		try {
			return Boolean.parseBoolean(properties.getProperty("requiresTheatre"));
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static int getRandomIllnessID() {
		return illnessProperties.getRandomIllnessID();
	}
}

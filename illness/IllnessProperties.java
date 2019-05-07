package illness;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.Random;

/* Class stored as a static instance, only one should exist
 * loads in every file in the Illnesses subfolder, and tries to parse them as xml file
 * containg the properties of Illnesses
 * Also contains Methods for Writing the basic Illnesses (as well as ns one called null with id 0) in the spec
 * back to file to if needed (or other Illnesses)
 */


public  class IllnessProperties {
	//used to store the properties read with the id as key for easy access
	HashMap<Integer, Properties> illnesses;
	ArrayList<Integer> keys;
	Random random;
	
	//reads in the properties files
	public IllnessProperties() {
		illnesses = new HashMap<Integer, Properties>();
		Properties prop = null;
		FileInputStream in;
		
		//loops through every file in the Illnesses sub folder
		for (File f: new File("Illnesses").listFiles()) {
			try {
				//creates a FileInputStream and gives it to the properties to read in
				prop = new Properties();
				in = new FileInputStream(f);
				prop.loadFromXML(in);
				
				//If the Id is already in the HashMap, throw exception, cant have two illness with the same id
				int id = Integer.parseInt(prop.getProperty("id"));
				if (illnesses.get(id) != null) {
					System.err.println("Illness id " + id + " already exists!");
					throw new RuntimeException("Illness Id Clash");
				}else {
					illnesses.put(id, prop);
				}
				in.close();
				
			} catch (FileNotFoundException e) {
				//Should not be possible, as looping though everyfile already found
				System.err.println("Illness not found?!?  " + f.getAbsolutePath());
				e.printStackTrace();
				
			} catch (InvalidPropertiesFormatException e) {
				//called if illness file is not a valid properties file,
				//to get around this use write Properties() found in this class 
				System.err.println("Invalid Illness File at " + f.getAbsolutePath());
				e.printStackTrace();
				
			} catch (IOException e) {
				System.out.println("IOException thrown" + e);
				e.printStackTrace();
			}
		}
		//generate the key list for random illness generation
		keys = new ArrayList<Integer>(illnesses.keySet());
		//remove the null illness so that it cant be randomly generated for patients
		keys.remove(0);
		//create the random instance for random numbers
		random = new Random();
	}
	
	//returns the properties of the Illness id found
	public Properties getProperties(int id) {
		Properties p = illnesses.get(id);
		if (p != null) {
			return p;
		}else {
			//if not found, return the null illness
			return illnesses.get(0);
		}
	}
	
	//returns a random IllnessID 
	public int getRandomIllnessID() {
		//to make sure we dont get the null illness, generate a random number 1 less than the size of the array, and return 
		return keys.get(random.nextInt(keys.size()));
	}
	
	//writes the Illnesses specified in the spec to disk
	//as well as the null Illness
	public static void writeDefualtProperties() {
		writeProperties(0, "null", 0, 0, false);
		writeProperties(1, "Djkstra's Syndrome", 5, 5, false);
		writeProperties(2, "Java Flu", 3, 3, false);
		writeProperties(3, "Deadline Panic Attacks", 1, 1, false);
		writeProperties(4, "Polymorphic Cist", 2, 4, true);
		writeProperties(5, "Semicolon Missing", 5, 8, true);
		writeProperties(6, "Trapped Exception", 6, 8, true);
		writeProperties(7, "Tim Berners Knee", 4, 6, true);
		writeProperties(8, "Coder's Elbow", 2, 3, true);
	}
	
	//used to write an illness to disk
	public static void writeProperties(int id, String name, int min, int max, boolean requireTheatre) {
		//sets the properties to those specified
		Properties prop = new Properties();
		prop.setProperty("id", String.valueOf(id));
		prop.setProperty("name", name);
		prop.setProperty("minRecoveryTime", String.valueOf(min));
		prop.setProperty("maxRecoveryTime", String.valueOf(max));
		prop.setProperty("requiresTheatre", String.valueOf(requireTheatre));
		
		//try to write it to name.xml in Illnesses
		try {
			FileOutputStream out = new FileOutputStream("Illnesses/" + name + ".xml");
			prop.storeToXML(out, null);
			out.close();
		} catch (FileNotFoundException e) {
			System.err.println("Cant find/access file Illnesses/" + name + ".xml");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	public static void main(String[] args) {
		writeProperties(10, "Test", 3, 5, false);
	}
}

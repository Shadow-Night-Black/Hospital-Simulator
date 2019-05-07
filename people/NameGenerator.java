package people;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/* Generate Names for Persons
 * 
 */
public class NameGenerator {

	private ArrayList<String> male;
	private ArrayList<String> female;
	private ArrayList<String> last;
	
	//Reads in the names stores in the config files
	public NameGenerator() {
		//stores the first name of males
		male = readConfig("people/male.cfg");
		//stores the first name of females
		female = readConfig("people/female.cfg");
		//stores surnames
		last = readConfig("people/last.cfg");
	}
	
	//Reads in the file and adds each line as a name
	private ArrayList<String> readConfig(String filename) {
		ArrayList<String> name = new ArrayList<String>();
		try {
			//open the reader
			BufferedReader in = new BufferedReader(new FileReader(filename));
			//while there is still names to be read, read in the names
			while (in.ready()) {
				name.add(in.readLine());
			}
			//prevent memory leaks
			in.close();
		//If the Exceptions are thrown, output the error, and set the name to contain Broken to indicate this
		} catch (FileNotFoundException e) {
			System.err.println("Name config file " + filename + " was not found!");
			name = new ArrayList<String>(); 
			name.add("Broken");
			return name;
		} catch (IOException e) {
			System.err.println("IOException thrown when reading in names");
			name = new ArrayList<String>(); 
			name.add("Broken");
			e.printStackTrace();
			return name;
		}
		return name;
	}
	
	//get a persons name
	public String getName(Gender g) {
		return getFirstName(g) + " " +  getLastName();
	}
	
	//return a random name based on Gender, 
	private String getFirstName(Gender g) {
		if (g == Gender.MALE)
			return male.get((int) (Math.random() * male.size()));
		else
			return female.get((int) (Math.random() * female.size()));
	}
	
	//return a random name
	private String getLastName(){
		return last.get((int) (Math.random() * last.size()));
	}
}

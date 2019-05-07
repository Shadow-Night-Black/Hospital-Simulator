package hospital;
/* Used to show that the s a error in the configuration file being read
 * 
 */

public class ConfigFileException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String configLine;
	
	public ConfigFileException(String configLine) {
		super("Invalid Config Line");
		this.configLine = configLine;
	}
	
	public String getConfigLine() {
		return configLine;
	}
	
	public String toString() {
		return super.toString() + "\n" + configLine;
	}
}

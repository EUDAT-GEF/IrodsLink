package eu.eudat.gef.irodslink;

/**
 * @author edima
 */
public class IrodsTestConfig {
	public static IrodsAccessConfig config = new IrodsAccessConfig() {
		{
			server = "localhost";
			username = "rods";
			password = "";
			path = "/zone/home/rods/unittest";
			resource = "demoResc";
		}
	};
}

package eu.eudat.gef.irodslink.lib;

/**
 *
 * @author edima
 */
public class Sys {
	public static void expect(boolean cond) {
		if (!cond) {
			throw new AssertionError();
		}
	}
}

package eu.eudat.gef.irodslink;

/**
 * @author edima
 */
public class IrodsException extends Exception {
	public IrodsException(String string) {
		super(string);
	}

	public IrodsException(Throwable thrwbl) {
		super(thrwbl);
	}

	public IrodsException(String string, Throwable thrwbl) {
		super(string, thrwbl);
	}
}

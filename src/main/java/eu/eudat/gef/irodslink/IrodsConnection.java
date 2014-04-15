package eu.eudat.gef.irodslink;

import java.net.URI;

/**
 * @author edima
 */
public interface IrodsConnection {
	URI makeUri(IrodsObject name) throws IrodsException;

	IrodsObject getObject(String irodsPath) throws IrodsException;
	
	String getInitialPath() throws IrodsException;
}

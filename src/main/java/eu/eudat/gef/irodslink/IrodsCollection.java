package eu.eudat.gef.irodslink;

import java.util.Collection;

/**
 * @author edima
 */
public interface IrodsCollection extends IrodsObject {
	IrodsCollection getParentCollection() throws IrodsException;

	Collection<IrodsCollection> listCollections() throws IrodsException;

	Collection<IrodsFile> listFiles() throws IrodsException;
	
	void create() throws IrodsException;
}

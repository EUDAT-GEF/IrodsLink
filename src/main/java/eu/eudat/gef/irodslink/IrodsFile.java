package eu.eudat.gef.irodslink;

import java.io.File;

/**
 * @author edima
 */
public interface IrodsFile extends IrodsObject {
	String getChecksum() throws IrodsException;
	
	long getSize() throws IrodsException;

	void moveTo(IrodsFile destination) throws IrodsException;

	void delete() throws IrodsException;

	void downloadToLocalFile(File localFile) throws IrodsException;

	void uploadFromLocalFile(File localFile) throws IrodsException;
}

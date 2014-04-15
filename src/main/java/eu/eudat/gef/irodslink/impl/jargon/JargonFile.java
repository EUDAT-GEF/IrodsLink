package eu.eudat.gef.irodslink.impl.jargon;

import eu.eudat.gef.irodslink.IrodsException;
import eu.eudat.gef.irodslink.IrodsFile;
import java.io.File;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.io.IRODSFile;

/**
 *
 * @author edima
 */
class JargonFile extends JargonObject implements IrodsFile {
	JargonFile(JargonConnection conn, IRODSFile irodsFile) {
		super(conn, irodsFile);
	}

	public String getChecksum() throws IrodsException {
		try {
			return conn.irodsData.computeMD5ChecksumOnDataObject(irodsFile);
		} catch (JargonException ex) {
			throw new IrodsException(ex);
		}
	}

	public long getSize() throws IrodsException {
		return irodsFile.length();
	}

	public void moveTo(IrodsFile destination) throws IrodsException {
		try {
			conn.irodsFileSystem.renameFile(irodsFile, ((JargonFile) destination).irodsFile);
		} catch (Exception ex) {
			throw new IrodsException(ex);
		}
	}

	public void delete() throws IrodsException {
		try {
			conn.irodsFileSystem.fileDeleteNoForce(irodsFile);
		} catch (JargonException ex) {
			throw new IrodsException(ex);
		}
	}

	public void downloadToLocalFile(File localFile) throws IrodsException {
		try {
			conn.irodsTransfers.getOperation(irodsFile, localFile, null, null);
		} catch (JargonException ex) {
			throw new IrodsException(ex);
		}
	}

	public void uploadFromLocalFile(File localFile) throws IrodsException {
		try {
			conn.irodsTransfers.putOperation(localFile, irodsFile, null, null);
		} catch (JargonException ex) {
			throw new IrodsException(ex);
		}
	}
}

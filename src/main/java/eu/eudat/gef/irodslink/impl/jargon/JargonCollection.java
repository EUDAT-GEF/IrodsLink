package eu.eudat.gef.irodslink.impl.jargon;

import eu.eudat.gef.irodslink.IrodsCollection;
import eu.eudat.gef.irodslink.IrodsException;
import eu.eudat.gef.irodslink.IrodsFile;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.io.IRODSFile;

/**
 *
 * @author edima
 */
class JargonCollection extends JargonObject implements IrodsCollection {
	JargonCollection(JargonConnection conn, IRODSFile irodsFile) {
		super(conn, irodsFile);
	}

	public JargonCollection getParentCollection() throws IrodsException {
		IRODSFile p;
		try {
			if (irodsFile.getParent() == null || irodsFile.getParent().isEmpty()) {
				return null;
			}
			p = conn.irodsFileFactory.instanceIRODSFile(irodsFile.getParent());
		} catch (JargonException ex) {
			throw new IrodsException(ex);
		}
		return new JargonCollection(conn, p);
	}

	public Collection<IrodsCollection> listCollections() throws IrodsException {
		List<IrodsCollection> l = new ArrayList<IrodsCollection>();
		for (File f : irodsFile.listFiles()) {
			if (f instanceof IRODSFile && f.isDirectory()) {
				l.add(new JargonCollection(conn, (IRODSFile) f));
			}
		}
		return l;
	}

	public Collection<IrodsFile> listFiles() throws IrodsException {
		List<IrodsFile> l = new ArrayList<IrodsFile>();
		for (File f : irodsFile.listFiles()) {
			if (f instanceof IRODSFile && f.isFile()) {
				l.add(new JargonFile(conn, (IRODSFile) f));
			}
		}
		return l;
	}

	public void create() throws IrodsException {
		try {
			JargonCollection parent = getParentCollection();
			if (parent != null && !parent.exists()) {
				parent.create();
			}
			conn.irodsFileSystem.mkdir(irodsFile, false);
		} catch (JargonException ex) {
			throw new IrodsException(ex);
		}
	}
//	private Collection<String> ils(boolean collFlag, String path) throws Exception {
//		List<String> ret = new ArrayList<String>();
//		String d = getPath(path);
//		List<CollectionAndDataObjectListingEntry> list = collFlag
//				? irodsLister.listCollectionsUnderPath(d, 0) : irodsLister.listDataObjectsUnderPath(d, 0);
//		for (CollectionAndDataObjectListingEntry cdoe : list) {
//			ret.add(cdoe.getFormattedAbsolutePath());
//		}
//		return ret;
//	}
}

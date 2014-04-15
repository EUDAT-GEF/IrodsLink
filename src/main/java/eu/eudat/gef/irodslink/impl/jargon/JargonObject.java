package eu.eudat.gef.irodslink.impl.jargon;

import eu.eudat.gef.irodslink.IrodsCollection;
import eu.eudat.gef.irodslink.IrodsException;
import eu.eudat.gef.irodslink.IrodsFile;
import eu.eudat.gef.irodslink.IrodsObject;
import eu.eudat.gef.irodslink.lib.Sys;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.io.IRODSFile;

/**
 *
 * @author edima
 */
public class JargonObject implements IrodsObject {

    protected final JargonConnection conn;
    final IRODSFile irodsFile;

    JargonObject(JargonConnection conn, IRODSFile irodsFile) {
        this.conn = conn;
        this.irodsFile = irodsFile;
    }

    public String getZone() {
        List<String> l = split(irodsFile.getAbsolutePath());
        Sys.expect(!l.isEmpty());
        return l.get(0);
    }

    public String getFullPath() {
        return irodsFile.getAbsolutePath();
    }

    public String getName() {
        return irodsFile.getName();
    }

    public Date getDate() {
        return new Date(irodsFile.lastModified());
    }

    @Override
    public boolean exists() throws IrodsException {
        try {
            return conn.irodsFileSystem.isFileExists(irodsFile);
        } catch (JargonException ex) {
            throw new IrodsException(ex);
        }
    }

    public boolean isFile() {
        return irodsFile.isFile();
    }

    public boolean isCollection() {
        return irodsFile.isDirectory();
    }

    public IrodsFile asFile() throws IrodsException {
        if (exists()) {
            if (irodsFile.isFile()) {
                return (this instanceof JargonFile) ? (IrodsFile) this : new JargonFile(conn, irodsFile);
            } else {
                throw new IrodsException("irods object already exists and is not a file: " + getFullPath());
            }
        } else {
            return new JargonFile(conn, irodsFile);
        }
    }

    public IrodsCollection asCollection() throws IrodsException {
        if (exists()) {
            if (irodsFile.isDirectory()) {
                return (this instanceof JargonCollection) ? (IrodsCollection) this : new JargonCollection(conn, irodsFile);
            } else {
                throw new IrodsException("irods object already exists and is not a collection: " + getFullPath());
            }
        } else {
            return new JargonCollection(conn, irodsFile);
        }
    }

    private static List<String> split(String path) {
        String[] pathComponents = path.split("/");
        List<String> l = new ArrayList<String>();
        for (String s : pathComponents) {
            if (s != null && !s.isEmpty()) {
                l.add(s);
            }
        }
        return l;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof JargonObject)) {
            return false;
        }
        JargonObject jo = (JargonObject) o;
        return jo.irodsFile.equals(irodsFile);
    }
}

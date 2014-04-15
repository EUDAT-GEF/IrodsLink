package eu.eudat.gef.irodslink;

import java.util.Date;

/**
 * @author edima
 */
public interface IrodsObject {

    String getZone() throws IrodsException;

    String getFullPath() throws IrodsException;

    String getName() throws IrodsException;

    Date getDate() throws IrodsException;

    boolean exists() throws IrodsException;

    IrodsFile asFile() throws IrodsException;

    IrodsCollection asCollection() throws IrodsException;

    boolean isFile();
    boolean isCollection();
}

package eu.eudat.gef.irodslink.impl.jargon;

import eu.eudat.gef.irodslink.IrodsAccessConfig;
import eu.eudat.gef.irodslink.IrodsConnection;
import eu.eudat.gef.irodslink.IrodsException;
import eu.eudat.gef.irodslink.IrodsObject;
import eu.eudat.gef.irodslink.lib.Sys;
import java.net.URI;
import java.net.URISyntaxException;
import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.connection.IRODSProtocolManager;
import org.irods.jargon.core.connection.IRODSServerProperties;
import org.irods.jargon.core.connection.IRODSSession;
import org.irods.jargon.core.connection.IRODSSimpleProtocolManager;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.CollectionAndDataObjectListAndSearchAO;
import org.irods.jargon.core.pub.DataObjectAO;
import org.irods.jargon.core.pub.DataTransferOperations;
import org.irods.jargon.core.pub.EnvironmentalInfoAO;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.IRODSAccessObjectFactoryImpl;
import org.irods.jargon.core.pub.IRODSFileSystemAO;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;

/**
 *
 * @author edima
 */
public class JargonConnection implements IrodsConnection {
	IrodsAccessConfig config;
	IRODSAccount irodsAccount;
	IRODSProtocolManager irodsConnectionManager;
	IRODSSession irodsSession;
	IRODSAccessObjectFactory irodsAccessObjectFactory;
	IRODSFileSystemAO irodsFileSystem;
	IRODSFileFactory irodsFileFactory;
	CollectionAndDataObjectListAndSearchAO irodsLister;
	DataTransferOperations irodsTransfers;
	DataObjectAO irodsData;

	public JargonConnection(IrodsAccessConfig config) throws IrodsException {
		try {
			this.config = config;
			String[] pathComponents = config.path.split("/");
			Sys.expect(pathComponents.length >= 1);
			String zone = pathComponents[pathComponents[0].isEmpty() ? 1 : 0];

			irodsConnectionManager = IRODSSimpleProtocolManager.instance();
			irodsConnectionManager.initialize();
			irodsAccount = IRODSAccount.instance(config.server, config.port,
					config.username, config.password, config.path, zone, config.resource);
			irodsSession = IRODSSession.instance(irodsConnectionManager);
			irodsAccessObjectFactory = new IRODSAccessObjectFactoryImpl(irodsSession);

			EnvironmentalInfoAO environmentalInfoAO =
					irodsAccessObjectFactory.getEnvironmentalInfoAO(irodsAccount);
			IRODSServerProperties props =
					environmentalInfoAO.getIRODSServerPropertiesFromIRODSServer();
			Sys.expect(props.isTheIrodsServerAtLeastAtTheGivenReleaseVersion("rods3.0"));

			irodsLister = irodsAccessObjectFactory.getCollectionAndDataObjectListAndSearchAO(irodsAccount);
			irodsTransfers = irodsAccessObjectFactory.getDataTransferOperations(irodsAccount);
			irodsFileSystem = irodsAccessObjectFactory.getIRODSFileSystemAO(irodsAccount);
			irodsData = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
			irodsFileFactory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
		} catch (JargonException ex) {
			throw new IrodsException(ex);
		}
	}

	public String getInitialPath() throws IrodsException {
		return config.path;
	}

	public URI makeUri(IrodsObject obj) throws IrodsException {
		try {
			String portPart = (config.port == IrodsAccessConfig.DEFAULT_IRODS_PORT) ? "" : (":" + config.port);
			return new URI("irods://" + config.server + portPart + obj.getFullPath());
		} catch (URISyntaxException ex) {
			throw new IrodsException(ex);
		}
	}

	public IrodsObject getObject(String irodsPath) throws IrodsException {
		while (irodsPath.endsWith("/")) {
			irodsPath = irodsPath.substring(0, irodsPath.length() - 1);
		}
		try {
			IRODSFile irodsFile = irodsFileFactory.instanceIRODSFile(irodsPath);
			return new JargonObject(this, irodsFile);
		} catch (Exception ex) {
			throw new IrodsException(ex);
		}
	}
}

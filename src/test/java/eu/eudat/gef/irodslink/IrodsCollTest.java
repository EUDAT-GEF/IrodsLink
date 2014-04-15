package eu.eudat.gef.irodslink;

import eu.eudat.gef.irodslink.impl.jargon.JargonConnection;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 * @author edima
 */
@Ignore // we cannot build with unittests, unless we specify a test server configuration below
public class IrodsCollTest {
	@Test
	public void testSimple() throws Exception {
		IrodsConnection conn = new JargonConnection(IrodsTestConfig.config);
		IrodsObject obj = conn.getObject("/ironZone/home/rods");

		System.out.println("===================================================================");
		System.out.println("mkurl: " + conn.makeUri(obj));
		System.out.println("zone: " + obj.getZone());
		System.out.println("name: " + obj.getName());
		System.out.println("fullpath: " + obj.getFullPath());
		System.out.println("===================================================================");
	}

	@Test
	public void testList() throws Exception {
		IrodsConnection conn = new JargonConnection(IrodsTestConfig.config);
		IrodsCollection coll = conn.getObject("/ironZone/home/rods/").asCollection();

		assertEquals(coll.getParentCollection().getFullPath(), "/ironZone/home");

		IrodsCollection collUT = conn.getObject("/ironZone/home/rods/unittest").asCollection();
		assertTrue(coll.listCollections().contains(collUT));
	}
//	@Test
//	public void testImkdirImvIrm() throws Exception {
//		IrodsConnection conn = new JargonConnectionFactory(IrodsTest.config).makeConnection();
//		IrodsObject obj = conn.getObject("/ironZone/home/rods/unittest/pids.txt");
//		irods.icd("/vzEKUT");
//		irods.imkdir("/vzEKUT/home/fedora/jargontest");
//		assertEquals(irods.ilsColls("/vzEKUT/home/fedora"), Arrays.asList(new String[]{"/vzEKUT/home/fedora/jargontest"}));
//
//		irods.imv("/vzEKUT/home/fedora/jargontest", "/vzEKUT/home/fedora/jargontest2");
//		assertEquals(irods.ilsColls("/vzEKUT/home/fedora"), Arrays.asList(new String[]{"/vzEKUT/home/fedora/jargontest2"}));
//
//		irods.irm("/vzEKUT/home/fedora/jargontest2");
//		assertTrue(irods.ilsColls("/vzEKUT/home/fedora").isEmpty());
//
//		irods.icd("/vzEKUT/home/fedora/");///////////////////////
//		irods.imkdir("jargontest");
//		assertEquals(irods.ilsColls("/vzEKUT/home/fedora"), Arrays.asList(new String[]{"/vzEKUT/home/fedora/jargontest"}));
//
//		irods.imv("jargontest", "jargontest2");
//		assertEquals(irods.ilsColls("/vzEKUT/home/fedora"), Arrays.asList(new String[]{"/vzEKUT/home/fedora/jargontest2"}));
//
//		irods.irm("jargontest2");
//		assertTrue(irods.ilsColls("/vzEKUT/home/fedora").isEmpty());
//	}
}

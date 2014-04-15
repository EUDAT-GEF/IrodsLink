package eu.eudat.gef.irodslink;

import eu.eudat.gef.irodslink.impl.jargon.JargonConnection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author edima
 */
@Ignore // we cannot build with unittests, unless we specify a test server configuration below
public class IrodsFileTest {
	static String TEXT = "Hello World!";

	@Test
	public void testIgetIput() throws Exception {
		IrodsConnection conn = new JargonConnection(IrodsTestConfig.config);
		IrodsFile ifile = conn.getObject("/ironZone/home/rods/unittest/test.txt").asFile();
		if (ifile.exists())
			ifile.delete();
		assertTrue(!ifile.exists());

		File f = new File("/tmp/test.txt");
		Writer w = new FileWriter(f);
		w.append(TEXT);
		w.close();

		IrodsCollection coll = conn.getObject("/ironZone/home/rods/unittest").asCollection();
		if (!coll.exists()) {
			coll.create();
		}

		ifile.uploadFromLocalFile(f);
		assertTrue(ifile.exists());
		assertTrue(ifile.getChecksum().matches("[0-9A-Fa-f]*"));

		f.delete();
		assertTrue(!f.exists());
		ifile.downloadToLocalFile(f);
		assertTrue(f.exists());
		assertEquals(new BufferedReader(new FileReader(f)).readLine(), TEXT);
		f.delete();

		assertTrue(coll.listFiles().contains(ifile));
		ifile.delete();
		assertFalse(coll.listFiles().contains(ifile));
		assertTrue(coll.listFiles().isEmpty());
	}

	@Test
	public void testImkdirImvIrm() throws Exception {
		IrodsConnection conn = new JargonConnection(IrodsTestConfig.config);
		IrodsFile ifile = conn.getObject("/ironZone/home/rods/unittest/test.txt").asFile();

//		irods.imv("/vzEKUT/home/fedora/jargontest", "/vzEKUT/home/fedora/jargontest2");
//		assertEquals(irods.ilsColls("/vzEKUT/home/fedora"), Arrays.asList(new String[]{"/vzEKUT/home/fedora/jargontest2"}));
//
//		irods.irm("/vzEKUT/home/fedora/jargontest2");
//		assertTrue(irods.ilsColls("/vzEKUT/home/fedora").isEmpty());
//
//		irods.imv("jargontest", "jargontest2");
//		assertEquals(irods.ilsColls("/vzEKUT/home/fedora"), Arrays.asList(new String[]{"/vzEKUT/home/fedora/jargontest2"}));
	}
}

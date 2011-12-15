package de.uzk.hki.fedorest;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.UniformInterfaceException;

public class FedoraTests {
	
	private static final String FEDORA_URL = "http://da-nrw-vm2.hki.uni-koeln.de:8080/fedora";
	private static final String FEDORA_USER = "fedoraAdmin";
	private static final String FEDORA_PASSWORD = "Herrlich456FFEE";
	
	private Fedora fedora;

	@Before
	public void setUp() throws Exception {
		fedora = new Fedora(FEDORA_URL,FEDORA_USER,FEDORA_PASSWORD);
	}
	
	@Test
	public void testPurgeObject() {
	
		try {
			fedora.ingest().param("pid","test:1234").execute();
		} catch (IllegalArgumentException e) {
			// do nothing, object probably already exists in fedora
		}
		
		Object result = fedora.purgeObject().param("pid","test:1234").execute();
		System.out.println(result);
		
	}

	@Test
	public void testIngest() {
		
		Object result = fedora.ingest().param("pid","test:1234")
			.param("label","Testobjekt").param("ownerId",FEDORA_USER)
			.execute();
		System.out.println(result);
		
		fedora.purgeObject().param("pid","test:1234").execute();
		
	}
	
	@Test
	public void testAddRelationship() {
		
		try {
			fedora.ingest().param("pid","test:1234").execute();
		} catch (IllegalArgumentException e) {
			// do nothing, object probably already exists in fedora
		}
		
		Object result = fedora.addRelationship().param("pid","test:1234")
				.param("subject","info:fedora/test:1234")
				.param("predicate","info:fedora/inCollection")
				.param("object","info:fedora/test:collection")
				.execute();
		System.out.println(result);

		fedora.purgeObject().param("pid","test:1234").execute();
		
	}
	
	@Test
	public void testPurgeRelationship() {
		
		try {
			fedora.ingest().param("pid","test:1234").execute();
		} catch (IllegalArgumentException e) {
			// do nothing, object probably already exists in fedora
		}
		
		fedora.addRelationship().param("pid","test:1234")
				.param("subject","info:fedora/test:1234")
				.param("predicate","info:fedora/inCollection")
				.param("object","info:fedora/test:collection")
				.execute();
		
		Object result = fedora.purgeRelationship().param("pid","test:1234")
				.param("subject","info:fedora/test:1234")
				.param("predicate","info:fedora/inCollection")
				.param("object","info:fedora/test:collection")
				.execute();
		System.out.println(result);

		fedora.purgeObject().param("pid","test:1234").execute();
		
	}
	
	@Test
	public void testAddDatastream() {
		
		try {
			fedora.ingest().param("pid","test:1234").execute();
		} catch (IllegalArgumentException e) {
			// do nothing, object probably already exists in fedora
		}
		
		Object result = fedora.addDatastream().param("pid","test:1234")
				.param("dsID","test-data")
				.param("mimeType", "text/xml")
				.execute(new File("src/test/resources/test_data.xml"));
		System.out.println(result);

		fedora.purgeObject().param("pid","test:1234").execute();
		
	}
	
	@Test
	public void testModifyObject() {
		
		try {
			fedora.ingest().param("pid","test:1234").execute();
		} catch (IllegalArgumentException e) {
			// do nothing, object probably already exists in fedora
		}
		
		Object result = fedora.modifyObject().param("pid","test:1234")
				.param("label", "Testobjekt verändert")
				.execute();
		System.out.println(result);

		fedora.purgeObject().param("pid","test:1234").execute();
		
	}
	
	@Test
	public void testModifyDatastream() {
		
		try {
			fedora.ingest().param("pid","test:1234").execute();
		} catch (IllegalArgumentException e) {
			// do nothing, object probably already exists in fedora
		}
		
		fedora.addDatastream().param("pid","test:1234")
				.param("dsID","test-data")
				.param("mimeType", "text/xml")
				.execute(new File("src/test/resources/test_data.xml"));
		
		Object result = fedora.modifyDatastream().param("pid","test:1234")
				.param("dsID","test-data")
				.param("mimeType", "text/xml")
				.param("dsLabel","jetzt auch mit label")
				.execute();
		System.out.println(result);

		fedora.purgeObject().param("pid","test:1234").execute();
		
	}
	
	@Test
	public void testPurgeDatastream() {
		
		try {
			fedora.ingest().param("pid","test:1234").execute();
		} catch (IllegalArgumentException e) {
			// do nothing, object probably already exists in fedora
		}
		
		fedora.addDatastream().param("pid","test:1234")
				.param("dsID","test-data")
				.param("mimeType", "text/xml")
				.execute(new File("src/test/resources/test_data.xml"));
		
		Object result = fedora.purgeDatastream().param("pid","test:1234")
				.param("dsID","test-data")
				.execute();
		System.out.println(result);

		fedora.purgeObject().param("pid","test:1234").execute();
		
	}

}

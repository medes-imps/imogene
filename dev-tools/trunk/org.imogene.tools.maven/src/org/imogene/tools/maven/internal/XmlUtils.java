package org.imogene.tools.maven.internal;

import java.io.OutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class XmlUtils {

	public static final String LIBRARY_TAG = "library";

	public static final String ID_ATTR = "id";

	public static final String NAME_ATTR = "name";

	public static final String DESC_ATTR = "desc";

	public static final String JAR_TAG = "jar";

	public static final String FILE_ATTR = "file";

	public static void writeXml(Document dom, OutputStream os)
			throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(dom);
		StreamResult result = new StreamResult(os);
		transformer.transform(source, result);
	}
		
}

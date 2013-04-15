package org.imogene.tools.maven.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.imogene.tools.maven.LibraryInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LibraryUtils {
	
	public static final String LIBS_FOLDER = "libs";
	
	public static final String JARS_FOLDER = "jars";

	
	public static LibraryInfo getLibraryInfo(IFile xmlFile)
			throws CoreException, ParserConfigurationException, SAXException,
			IOException {
		InputStream is = xmlFile.getContents();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(is);

		LibraryInfo info = new LibraryInfo();
		Node root = document.getFirstChild();
		if (root instanceof Element) {

			info.setId(((Element) root).getAttribute("id"));
			info.setName(((Element) root).getAttribute("name"));
			info.setDescription(((Element) root).getAttribute("desc"));
			info.setFileName(xmlFile.getName());
		}
		return info;
	}

	public static List<String> getJarsFile(IFile xmlFile) throws CoreException,
			ParserConfigurationException, SAXException, IOException {
		List<String> jars = new ArrayList<String>();

		InputStream is = xmlFile.getContents();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(is);
		Node root = document.getFirstChild();
		if (root instanceof Element) {
			NodeList jarElts = ((Element) root)
					.getElementsByTagName(XmlUtils.JAR_TAG);
			for (int i = 0; i < jarElts.getLength(); i++) {
				Node n = jarElts.item(i);
				if (n instanceof Element) {
					String fileName = ((Element) n)
							.getAttribute(XmlUtils.FILE_ATTR);
					jars.add(fileName);
				}
			}
		}
		return jars;
	}

	public static void cleaUnusedJar(IFolder jarsFolder, Set<String> jars)
			throws CoreException {
		IResource[] childs = jarsFolder.members();
		for (IResource r : childs) {
			if (r.getType() == IResource.FILE && r.getName().endsWith(".jar")
					&& !jars.contains(r.getName())) {
				r.delete(true, null);
			}
		}
	}
	
	public static Set<String> getAllLibraries(IFolder libFolder) throws CoreException {
		Set<String> jars = new HashSet<String>();		
		IResource[] childs = libFolder.members();
		for(IResource r : childs) {
			if(r.getType()==IResource.FILE && r.getName().endsWith(".xml")){
				try{
					jars.addAll(getJarsFile((IFile)r));
				}catch(Exception e){
					e.printStackTrace();					
				}
			}
		}
		return jars;
	}
}

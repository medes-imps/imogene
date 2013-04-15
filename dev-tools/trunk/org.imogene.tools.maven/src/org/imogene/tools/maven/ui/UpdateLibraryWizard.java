package org.imogene.tools.maven.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.Wizard;
import org.imogene.tools.maven.LibraryInfo;
import org.imogene.tools.maven.internal.LibraryUtils;
import org.imogene.tools.maven.internal.MavenHelper;
import org.imogene.tools.maven.internal.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class UpdateLibraryWizard extends Wizard {

	private UpdateLibraryWizardPage page;

	private IProject project;


	public UpdateLibraryWizard(IProject selection) {
		super();
		project = selection;
		page = new UpdateLibraryWizardPage("Add/Update library", selection);
		addPage(page);
	}

	@Override
	public boolean performFinish() {		
		String pomPath = page.getPomPath();
		File pomFile = new File(pomPath);
		List<File> jars = new MavenHelper(pomFile).getDependenciesJars();

		LibraryInfo libInfo = page.getLibInfo();
		
		File tmpFile;
		try {
			
			IFolder libs = project.getFolder(LibraryUtils.LIBS_FOLDER);
			if(!libs.exists())
				libs.create(true, true, null);
			
			/* library file */
			tmpFile = File.createTempFile("imoglib", ".xml");
			OutputStream os = new FileOutputStream(tmpFile);
			createDom(libInfo, jars, os);
			os.close();
			IFile xmlLib = libs.getFile(libInfo.getFileName());
			InputStream is = new FileInputStream(tmpFile);
			if (xmlLib.exists())
				xmlLib.setContents(is, true, false, null);
			else
				xmlLib.create(is, true, null);
			tmpFile.delete();
			
			/* jars copy */
			IFolder jarsDest = libs.getFolder(LibraryUtils.JARS_FOLDER);
			if(!jarsDest.exists())
				jarsDest.create(true, true, null);
			copyJars(jars, jarsDest);

			/* clean if necessary */
			if(page.cleanUnused()){
				Set<String> used = LibraryUtils.getAllLibraries(libs);
				LibraryUtils.cleaUnusedJar(jarsDest, used);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to update/create the library : "
					+ e.getLocalizedMessage());
		}
		return true;
	}

	private void copyJars(List<File> jars, IFolder dest) throws CoreException, IOException {
		for (File jar : jars) {
			String jarName = jar.getName();
			IFile file = dest.getFile(jarName);
			if (!file.exists()) {
				InputStream is;
				is = new FileInputStream(jar);
				file.create(is, true, null);
				is.close();
			}
		}
	}

	private void createDom(LibraryInfo info, List<File> deps, OutputStream out) throws TransformerException, ParserConfigurationException {
		
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(XmlUtils.LIBRARY_TAG);
			rootElement.setAttribute(XmlUtils.ID_ATTR, info.getId());
			rootElement.setAttribute(XmlUtils.NAME_ATTR, info.getName());
			rootElement.setAttribute(XmlUtils.DESC_ATTR, info.getDescription());
			doc.appendChild(rootElement);

			for (File dep : deps) {
				Element jarElt = doc.createElement(XmlUtils.JAR_TAG);
				jarElt.setAttribute(XmlUtils.FILE_ATTR, dep.getName());
				rootElement.appendChild(jarElt);
			}

			XmlUtils.writeXml(doc, out);		
	}
		
}

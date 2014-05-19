package org.imogene.library.contrib.export;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.imogene.library.Constants;
import org.imogene.library.LibraryPlugin;
import org.imogene.library.Tools;
import org.imogene.studio.contrib.ui.navigator.AdminShadow;
import org.imogene.studio.contrib.ui.navigator.DaoShadow;
import org.imogene.studio.contrib.ui.navigator.InitializerShadow;
import org.imogene.studio.contrib.ui.navigator.NotifierShadow;
import org.imogene.studio.contrib.ui.navigator.ClientShadow;
import org.imogene.studio.contrib.ui.navigator.SynchroShadow;
import org.imogene.studio.contrib.ui.navigator.WebEmbeddedShadow;
import org.imogene.studio.contrib.ui.navigator.WebShadow;

public class ExportManager {

	public static List<ExportedEntry> getClasspath(String id) {
		if (WebShadow.NATURE.equals(id)) {
			return getEntries(Constants.WEB_DESC_PATH);
		} else if (WebEmbeddedShadow.NATURE.equals(id)) {
			return getEntries(Constants.WEBEMB_DESC_PATH);
		} else if (NotifierShadow.NATURE.equals(id)) {
			return getEntries(Constants.NOTIF_DESC_PATH);
		} else if (SynchroShadow.NATURE.equals(id)) {
			return getEntries(Constants.SYNC_DESC_PATH);
		} else if (AdminShadow.NATURE.equals(id)) {
			return getEntries(Constants.ADMIN_DESC_PATH);
		} else if (InitializerShadow.NATURE.equals(id)) {
			return getEntries(Constants.INIT_DESC_PATH);
		} else if (DaoShadow.NATURE.equals(id)) {
			return getEntries(Constants.DAO_DESC_PATH);
		} else if (ClientShadow.NATURE.equals(id)) {
			return getEntries(Constants.SYNC_CLIENT_DESC_PATH);
		}
		// } else if(WebServiceShadow.NATURE.equals(id)){
		// return getEntries(Constants.WS_DESC_PATH);
		// } else if(WebServiceSoapShadow.NATURE.equals(id)){
		// return getEntries(Constants.WS_SOAP_DESC_PATH);
		return new ArrayList<ExportedEntry>();
	}

	private static List<ExportedEntry> getEntries(String path) {
		List<ExportedEntry> entries = new ArrayList<ExportedEntry>();
		URL confFile = LibraryPlugin.getDefault().getBundle().getEntry(path);
		addToEntries(confFile, entries);
		return entries;
	}

	private static void addToEntries(URL xmlDescription, List<ExportedEntry> entries) {
		try {
			for (String name : Tools.getJarNames(xmlDescription.openStream())) {
				URL url = LibraryPlugin.getDefault().getBundle().getEntry(Constants.JAR_PATH + name);
				if (url != null)
					entries.add(new ExportedEntry(name, url));
				else
					throw new RuntimeException("Library named '" + name + "' not found.");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}

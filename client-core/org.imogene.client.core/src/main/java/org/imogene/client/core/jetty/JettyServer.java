package org.imogene.client.core.jetty;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.servlet.Servlet;

import org.apache.log4j.xml.DOMConfigurator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;
import org.imogene.client.core.Activator;
import org.osgi.framework.Bundle;

public class JettyServer {

	private static final String EXTENSION_WEBAPPS = "org.imogene.client.core.jetty.webapps";
	private static final String EXTENSION_SERVLETS = "org.imogene.client.core.jetty.servlets";

	private static final String ATTR_DISABLED = "disabled";
	private static final String ATTR_CONTEXT = "context";
	private static final String ATTR_PATH = "path";
	private static final String ATTR_NAME = "name";
	private static final String ATTR_CLASS = "class";

	private Server server;
	private int port = 0;
	private HandlerCollection handlers;
	private File webAppsDirectory;

	public void start() throws Exception {
		DOMConfigurator.configure(getClass().getResource("log4j.xml"));

		port = Integer.valueOf(System.getProperty("jetty.port", "8080"));

		webAppsDirectory = new File(System.getProperty("jetty.home"), "webapps");
		if (!webAppsDirectory.exists()) {
			webAppsDirectory.mkdirs();
		}

		server = new Server(port);

		handlers = new HandlerCollection();
		handlers.setServer(server);

		getWebApps();
		getServlets();

		server.setHandler(handlers);
		server.start();
	}

	public void stop() throws Exception {
		server.stop();
	}

	private void getServlets() {
		IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_SERVLETS);
		for (int i = 0; i < config.length; i++) {
			try {
				processServletConfig(config[i]);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	private void processServletConfig(IConfigurationElement config) throws IOException, CoreException {
		String name = config.getAttribute(ATTR_NAME);
		String className = config.getAttribute(ATTR_CLASS);
		if (!name.startsWith("/")) {
			name = "/" + name;
		}

		// Retrieve the plug-in declaring the extension point
		Bundle bundle = Platform.getBundle(config.getDeclaringExtension().getNamespaceIdentifier());

		try {
			@SuppressWarnings("unchecked")
			Class<? extends Servlet> clazz = (Class<? extends Servlet>) bundle.loadClass(className);
			ServletContextHandler servletHandler = new ServletContextHandler(server, "/context", true, false);
			servletHandler.setClassLoader(clazz.getClassLoader());
			servletHandler.addServlet(clazz, name);
			handlers.addHandler(servletHandler);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Get all the web applications defined using the extension point.
	 */
	private void getWebApps() {
		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_WEBAPPS);
		for (int i = 0; i < config.length; i++) {
			try {
				processWebappConfig(config[i]);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	private void processWebappConfig(IConfigurationElement config) throws IOException, CoreException {
		String disabled = config.getAttribute(ATTR_DISABLED);
		if (Boolean.parseBoolean(disabled)) {
			return;
		}
		String context = config.getAttribute(ATTR_CONTEXT);
		String relativePath = config.getAttribute(ATTR_PATH);
		if (!context.startsWith("/")) {
			context = "/" + context;
		}

		// Retrieve the plug-in declaring the extension point
		Bundle bundle = Platform.getBundle(config.getDeclaringExtension().getNamespaceIdentifier());
		String path = FileLocator.toFileURL(bundle.getEntry(relativePath)).getPath();

		deployWebApp(path, context);
	}

	private void deployWebApp(String path, String context) throws IOException {
		File dir = new File(webAppsDirectory, context);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		WebAppContext wac = new WebAppContext();
		wac.setContextPath(context);
		wac.setWar(path);
		wac.setServer(server);
		wac.setTempDirectory(dir);
		URL url = getClass().getClassLoader().getResource("/org/eclipse/jetty/webapp/webdefault.xml");
		wac.setDefaultsDescriptor(FileLocator.toFileURL(url).getPath());

		/*
		 * Trick to solve the JSP issue with embedded jetty. As ones said one day : "This problem is now due to the fact
		 * that the Jasper JSP engine cannot pass a sContext loader to the javac process to use for compiling classes.
		 * This is a *DIFFICULT* problem as there is no way for Jetty to extract the classpath and pass that to
		 * JspServlet."
		 */
		WebAppClassLoader wacl = new WebAppClassLoader(getClass().getClassLoader(), wac);
		URL jarsUrl = Activator.getContext().getBundle().getEntry("/lib");
		File jarsFile = new File(FileLocator.toFileURL(jarsUrl).getPath());
		wacl.addJars(Resource.newResource(jarsFile));
		wac.setClassLoader(wacl);

		wac.addServerClass("org.slf4j.");
		wac.addServerClass("org.apache.log4j.");

		handlers.addHandler(wac);
	}

}

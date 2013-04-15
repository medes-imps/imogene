package org.imogene.tools.maven.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.maven.repository.internal.DefaultServiceLocator;
import org.apache.maven.repository.internal.MavenRepositorySystemSession;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.connector.file.FileRepositoryConnectorFactory;
import org.sonatype.aether.connector.wagon.WagonRepositoryConnectorFactory;
import org.sonatype.aether.repository.LocalRepository;
import org.sonatype.aether.repository.MirrorSelector;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.spi.connector.RepositoryConnectorFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@SuppressWarnings("deprecation")
public class MavenUtils {

	private static final String SETTINGS_FILE = "settings.xml";

	private static final String MIRRORS_TAG = "mirrors";

	private static final String MIRROR_TAG = "mirror";

	private static final String ID_TAG = "id";

	private static final String MIRROR_OF_TAG = "mirrorOf";

	private static final String URL_TAG = "url";

	public static RepositorySystem newRepositorySystem() {

		DefaultServiceLocator locator = new DefaultServiceLocator();
		locator.addService(RepositoryConnectorFactory.class,
				FileRepositoryConnectorFactory.class);
		locator.addService(RepositoryConnectorFactory.class,
				WagonRepositoryConnectorFactory.class);

		return locator.getService(RepositorySystem.class);
	}

	public static RepositorySystemSession newRepositorySystemSession(
			RepositorySystem system) {

		MavenRepositorySystemSession session = new MavenRepositorySystemSession();
		// FIXME: use eclipse classpath variable
		LocalRepository localRepo = new LocalRepository(
				org.apache.maven.repository.RepositorySystem.defaultUserLocalRepository);
		// FIXME: read the settings.xml file
//		session.setMirrorSelector(new MirrorSelector() {
//			@Override
//			public RemoteRepository getMirror(RemoteRepository repository) {
//				return new RemoteRepository("nexus", null,
//						"http://kamkam.medes.fr:9081/nexus/content/groups/public");
//			}
//		});
		session.setMirrorSelector(newMirrorSelector());
		session.setLocalRepositoryManager(system
				.newLocalRepositoryManager(localRepo));
		return session;
	}

	public static String getJarPath(Artifact artf, String repositoryPath) {
		if (!repositoryPath.endsWith("/"))
			repositoryPath += "/";
		String artifactPath = createPathAndFilename(artf.getGroupId(),
				artf.getArtifactId(), artf.getVersion(), artf.getExtension(), artf.getClassifier());
		return repositoryPath + artifactPath;
	}

	private static String createPathAndFilename(final String groupId,
			final String artifactId, final String version,
			final String extension, final String classifier) {
		final String filename = getFileName(artifactId, version, extension, classifier);
		return createPath(groupId, artifactId) + "/" + version + "/" + filename;
	}

	private static String createPath(final String groupId,
			final String artifactId) {
		return groupId.replace('.', '/') + "/" + artifactId;
	}

	private static String getFileName(String artifactId, String version,
			String extension, String classifier) {
		StringBuilder builder = new StringBuilder();
		builder.append(artifactId).append("-");
		builder.append(version);		
		if(classifier!=null && !classifier.equals("")){
			builder.append("-");
			builder.append(classifier);			
		}
		builder.append(".");
		builder.append(extension);		
		return builder.toString();
	}

	private static MirrorSelector newMirrorSelector() {
		File settingFile = new File(
				org.apache.maven.repository.RepositorySystem.userMavenConfigurationHome,
				SETTINGS_FILE);
		Map<String, RemoteRepository> mirrors;
		if (settingFile.exists()) {
			mirrors = getMirrors(settingFile);
		}else{
			mirrors = new HashMap<String, RemoteRepository>();
		}
		return new MyMirrorSelector(mirrors);
	}

	private static Map<String, RemoteRepository> getMirrors(File settingsFile) {
		Map<String, RemoteRepository> mirrors = new HashMap<String, RemoteRepository>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream is = new FileInputStream(settingsFile);
			Document document = builder.parse(is);
			NodeList nl = document.getElementsByTagName(MIRRORS_TAG);
			if (nl.getLength() > 0) {
				Element mirrorsTag = (Element) nl.item(0);
				NodeList mirrorTags = mirrorsTag
						.getElementsByTagName(MIRROR_TAG);
				for (int i = 0; i < mirrorTags.getLength(); i++) {
					Element mirror = (Element) mirrorTags.item(i);
					Element idElt = getFirstChildByTag(mirror, ID_TAG);
					Element mirrorForElt = getFirstChildByTag(mirror,
							MIRROR_OF_TAG);
					Element urlElt = getFirstChildByTag(mirror, URL_TAG);
					if (idElt != null && mirrorForElt != null && urlElt != null) {
						RemoteRepository rrepo = new RemoteRepository(
								idElt.getFirstChild().getNodeValue(), null,
								urlElt.getFirstChild().getNodeValue());
						mirrors.put(mirrorForElt.getFirstChild().getNodeValue(), rrepo);
						
						LogUtils.logInfo("Registred mirror: "+mirrorForElt.getFirstChild().getNodeValue());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.logError(e.getLocalizedMessage(), e);
		}
		return mirrors;
	}

	private static Element getFirstChildByTag(Element elt, String tagName) {
		NodeList all = elt.getElementsByTagName(tagName);
		if (all.getLength() > 0)
			return (Element) all.item(0);
		return null;
	}
	
	// INTERNAL CLASSES
	
	/**
	 * MirrorSelector basic implementation, 
	 * hope it matches the Maven expected behavior :-/
	 */
	public static class MyMirrorSelector implements MirrorSelector{
		
		private Map<String, RemoteRepository> mirrors;
		
		public MyMirrorSelector(Map<String, RemoteRepository> mirrors){
			this.mirrors = mirrors;
			LogUtils.logInfo(mirrors.size()+" mirrors registred.");
		}

		@Override
		public RemoteRepository getMirror(RemoteRepository toMirror) {			
		
			if(mirrors.containsKey("*"))
				return mirrors.get("*");
			else if(mirrors.containsKey(toMirror.getId()))
				return mirrors.get(toMirror.getId());			
			else
				return toMirror;
		}
				
	}

}

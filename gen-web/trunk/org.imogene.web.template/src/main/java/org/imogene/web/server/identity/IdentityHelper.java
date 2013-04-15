package org.imogene.web.server.identity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.imogene.encryption.EncryptionManager;
import org.imogene.lib.common.role.ImogRole;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author MEDES-IMPS
 */
public class IdentityHelper {

	/**
	 * Load identities from existing identity files.
	 */
	public static Map<String, Identity> getIdentities(ServletContext context) {

		Map<String, Identity> identities = new HashMap<String, Identity>();

		File identity = new File(context.getRealPath(IdentityConstants.ID_REPOSITORY));
		if (!identity.exists()) {
			identity.mkdir();
		}
		File[] files = identity.listFiles();
		for (File idFile : files) {
			Identity current;
			try {
				current = IdentityHelper.loadFromFile(new FileInputStream(idFile), context);
				identities.put(current.getId(), current);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return identities;
	}

	/**
	 * Load identities from existing identity files.
	 */
	public static String displayOptions(ServletContext context) {

		String result = "";

		File identity = new File(context.getRealPath(IdentityConstants.ID_REPOSITORY));
		if (!identity.exists()) {
			identity.mkdir();
		}
		File[] files = identity.listFiles();
		for (File idFile : files) {
			Identity current;
			try {
				current = IdentityHelper.loadFromFile(new FileInputStream(idFile), context);
				result = result + "<option value='" + current.getId() + "'>" + current.getLogin() + "</option>";
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * Loads an identity from a file.
	 * @param idFile the identity file
	 * @return the identity describe by the file
	 */
	public static Identity loadFromFile(InputStream is, ServletContext context) {

		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		EncryptionManager encrypter = (EncryptionManager) wac.getBean("encryptionManager");

		Properties properties = new Properties();
		Identity ident = null;
		try {
			properties.load(encrypter.getDecryptedInputStream(is));
			ident = new Identity();
			ident.setType(properties.getProperty(IdentityConstants.KEY_TYPE));
			ident.setId(properties.getProperty(IdentityConstants.KEY_ID));
			ident.setName(properties.getProperty(IdentityConstants.KEY_NAME));
			ident.setFirstName(properties.getProperty(IdentityConstants.KEY_FIRSTNAME));
			ident.setLogin(properties.getProperty(IdentityConstants.KEY_LOGIN));
			ident.setPassword(properties.getProperty(IdentityConstants.KEY_PASSWORD));
			ident.setAssignedRoles(getRoles(properties.getProperty(IdentityConstants.KEY_ROLES)));
			is.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ident;
	}

	/**
	 * Check if the loaded file is a valid identity file.
	 * @param idFile the identity file to check
	 * @return true if the file is valid.
	 */
	public static boolean validateIdFile(InputStream is, ServletContext context) {

		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		EncryptionManager encrypter = (EncryptionManager) wac.getBean("encryptionManager");

		Properties properties = new Properties();
		try {
			properties.load(encrypter.getDecryptedInputStream(is));
			if (properties.containsKey(IdentityConstants.KEY_TYPE) && properties.containsKey(IdentityConstants.KEY_ID)
					&& properties.containsKey(IdentityConstants.KEY_LOGIN)
					&& properties.containsKey(IdentityConstants.KEY_PASSWORD)
					&& properties.containsKey(IdentityConstants.KEY_ROLES)) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * Copy files.
	 * @param f1 source file
	 */
	public static void copyfile(InputStream is, ServletContext context) {
		File dFile = new File(context.getRealPath(IdentityConstants.ID_REPOSITORY), UUID.randomUUID() + ".imogid");

		try {

			OutputStream out = new FileOutputStream(dFile);

			byte[] buf = new byte[1024];
			int len;
			while ((len = is.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			is.close();
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param ident
	 * @param context
	 */
	public static void deleteIdentity(String identityId, ServletContext context) {
		
		File identity = new File(context.getRealPath(IdentityConstants.ID_REPOSITORY));
		if (!identity.exists()) {
			identity.mkdir();
		}
		File[] files = identity.listFiles();
		for (File idFile : files) {
			Identity current;
			try {
				current = IdentityHelper.loadFromFile(new FileInputStream(idFile), context);
				if(current.getId().equals(identityId)) {
					idFile.delete();
					return;
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * Gets a set of role ids from a string concatenation of role ids
	 * @param roleList a concatenated list of roles
	 * @return a set of roles as string
	 */
	private static Set<String> getRoles(String roleList) {
		Set<String> result = new HashSet<String>();
		if (roleList != null && roleList.length() > 0) {
			String[] roles = roleList.split(IdentityConstants.ROLE_SEPARATOR);
			for (String role : roles) {
				if (role.length() > 0) {
					if (role.equals(IdentityConstants.ROLE_ERROR))
						return result;
					else
						result.add(role);
				}
			}
		}
		return result;
	}

	/**
	 * Gets a set of roles from a set of role ids
	 * @param roleList a set of roles as strings
	 * @return a set of ImogRole
	 */
	public static Set<ImogRole> getRoles(Set<String> roleList) {
		Set<ImogRole> result = new HashSet<ImogRole>();
		if (roleList != null && roleList.size() > 0) {
			for (String role : roleList) {
				if (role.length() > 0) {
					if (role.equals(IdentityConstants.ROLE_ERROR))
						return result;
					else {
						ImogRole imogRole = new ImogRole();
						imogRole.setId(role);
						imogRole.setName(role);
						imogRole.setVersion(0);
						result.add(imogRole);
					}
				}
			}
		}
		return result;
	}

}

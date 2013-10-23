package org.imogene.web.server.identity;

public class IdentityConstants {
	
	/* directory where the identity files are stored */
	public static String ID_REPOSITORY = "/WEB-INF/identity";
	
	/* keys of the properties that are contained in the identity files */
	public static String KEY_TYPE = "imog.ident.type";
	public static String KEY_ID = "imog.ident.id";	
	public static String KEY_NAME = "imog.ident.name";	
	public static String KEY_FIRSTNAME = "imog.ident.firstname";	
	public static String KEY_LOGIN = "imog.ident.login";
	public static String KEY_PASSWORD = "imog.ident.password";
	public static String KEY_ROLES = "imog.ident.roles";
	
	/* constants for the user role list */
	public static String ROLE_SEPARATOR = ";";
	public static String ROLE_ERROR = "-ERROR-";
	
	/* messages related to the validity of identity files */
	public static int ERROR_INVALID_FILE = 1;	
	public static int WARNING_ALREADY_EXISTS = 2;	
	public static int OK_ADDED = 0;	

}

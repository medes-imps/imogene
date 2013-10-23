package org.imogene.admin.server.security;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

/**
 * Class that enables to manage the user password encoding
 * @author MEDES-IMPS
 */
public class SecurityUtils {

	/**
	 * Encodes a user password
	 * @param password the user password
	 * @param userName the user login
	 * @return the encoded password
	 */
	public static String passwordHashAsBase64(String password, String userName) {
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		encoder.setEncodeHashAsBase64(true);
		return encoder.encodePassword(password, userName);
	}
}
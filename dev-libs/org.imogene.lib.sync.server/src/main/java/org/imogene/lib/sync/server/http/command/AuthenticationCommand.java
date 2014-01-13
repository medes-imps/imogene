package org.imogene.lib.sync.server.http.command;

/**
 * Command used to authenticate the user.
 * 
 * @author Medes-IMPS
 * 
 */
public class AuthenticationCommand {

	private String login;

	private String password;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

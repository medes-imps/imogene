package org.imogene.lib.sync.server.http.command;

public class SearchCommand {

	private String login;
	
	private String password;
	
	private String searchedid;

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

	public String getSearchedid() {
		return searchedid;
	}

	public void setSearchedid(String searchedid) {
		this.searchedid = searchedid;
	}
	
	
}

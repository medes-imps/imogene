package org.imogene.web.server.identity;

import java.util.HashSet;
import java.util.Set;


public class Identity {

	private String type;
	private String id;
	private String firstName;
	private String name;
	private String login;
	private String password;
	private Set<String> assignedRoles = new HashSet<String>();

	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public Set<String> getAssignedRoles() {
		return assignedRoles;
	}

	public void setAssignedRoles(Set<String> assignedRoles) {
		this.assignedRoles = assignedRoles;
	}
	
	
	
	
	
}

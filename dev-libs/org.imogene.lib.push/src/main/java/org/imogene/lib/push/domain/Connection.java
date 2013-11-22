package org.imogene.lib.push.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "push_connection")
@NamedQueries({
		@NamedQuery(name = "findAllConnections", query = "select c from Connection c"),
		@NamedQuery(name = "findConnectionByUserAndTerminal", query = "select c from Connection c where c.login = :login and c.terminal = :terminal"),
		@NamedQuery(name = "findConnectionsByUser", query = "select c from Connection c where c.login = :login"),
		@NamedQuery(name = "findConnectionsByTerminal", query = "select c FROM Connection c WHERE c.terminal = :terminal") })
public class Connection implements Serializable {

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 8997181425581096233L;

	@Id
	private String id;

	private String login;

	private String terminal;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creation;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastConnection;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastPing;

	@Temporal(TemporalType.TIMESTAMP)
	private Date disconnection;

	private long threadId;

	private int status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getLastConnection() {
		return lastConnection;
	}

	public void setLastConnection(Date lastConnection) {
		this.lastConnection = lastConnection;
	}

	public Date getLastPing() {
		return lastPing;
	}

	public void setLastPing(Date lastPing) {
		this.lastPing = lastPing;
	}

	public Date getDisconnection() {
		return disconnection;
	}

	public void setDisconnection(Date disconnection) {
		this.disconnection = disconnection;
	}

	public long getThreadId() {
		return threadId;
	}

	public void setThreadId(long threadId) {
		this.threadId = threadId;
	}

}

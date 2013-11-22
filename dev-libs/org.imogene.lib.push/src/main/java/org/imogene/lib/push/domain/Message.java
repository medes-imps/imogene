package org.imogene.lib.push.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "push_message")
@NamedQuery(name = "findWaitingMessagesByUserAndTerminal", query = "select m from Message m where m.login = :login and m.terminal = :terminal and status = 0")
public class Message implements Serializable {

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = -3036765602962249642L;

	@Id
	private String id;

	private String login;

	private String terminal;

	private String message;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creation;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastTry;
	@Temporal(TemporalType.TIMESTAMP)
	private Date received;

	private int tries;

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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public Date getLastTry() {
		return lastTry;
	}

	public void setLastTry(Date lastTry) {
		this.lastTry = lastTry;
	}

	public int getTries() {
		return tries;
	}

	public void setTries(int tries) {
		this.tries = tries;
	}

	public Date getReceived() {
		return received;
	}

	public void setReceived(Date received) {
		this.received = received;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}

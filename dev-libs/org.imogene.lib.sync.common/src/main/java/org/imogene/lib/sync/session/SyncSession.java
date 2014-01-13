package org.imogene.lib.sync.session;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This class represents a synchronization session.
 * 
 * @author MEDES-IMPS
 */
@Entity
@Table(name = "sync_session")
public class SyncSession implements Serializable {

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = -998653674239470652L;

	@Id
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date initDate;

	private String terminalId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date sendDate;

	private String userId;

	@Column(name = "imogversion")
	private Integer version;

	/**
	 * Get the session id
	 * 
	 * @return the session id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the session id
	 * 
	 * @param id the session id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get the date at when the session was initiated
	 * 
	 * @return the initiation date.
	 */
	public Date getInitDate() {
		return initDate;
	}

	/**
	 * Set the session initiation date
	 * 
	 * @param initDate the initiation date
	 */
	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}

	/**
	 * Get the id of the terminal that initiated this session.
	 * 
	 * @return The terminal id
	 */
	public String getTerminalId() {
		return terminalId;
	}

	/**
	 * Set the id of the terminal associated with this session.
	 * 
	 * @param terminalId
	 */
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	/**
	 * Get the date at when we started to send local modifications.
	 * 
	 * @return The date when we started to send local modification
	 */
	public Date getSendDate() {
		return sendDate;
	}

	/**
	 * Set the date when it started to send local modification.
	 * 
	 * @param sendDate the date
	 */
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	/**
	 * Get the user id of the user that initiated this session.
	 * 
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Set the user id that initiated this session
	 * 
	 * @param userId the user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}

package org.imogene.lib.sync.client.parameter;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.imogene.lib.common.sync.entity.SynchronizableEntity;

/**
 * This interface describe the synchronization parameter object.
 * 
 * @author MEDES-IMPS
 */
@Entity
public class SyncParameter implements Serializable {

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 4674119792958662281L;

	/* constant that specifies a binary synchronization type */
	public static final String SYNC_TYPE_BIN = "bin";

	/* constant that specifies a XML synchronization type */
	public static final String SYNC_TYPE_XML = "xml";

	private static final String ID = "sync-parameter";

	@Id
	private String id = ID;

	private String login;

	private String password;

	private String terminalId;

	private String url;

	private boolean loop;

	private int period;

	private String type;

	private Long offset;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "syncparameter_synchronizables", joinColumns = @JoinColumn(name = "syncparameter_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "synchronizableentity_id", referencedColumnName = "id"))
	private Set<SynchronizableEntity> synchronizables;

	/**
	 * Id of the parameters snapshot.
	 * 
	 * @return the snapshot id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the id of the parameters (does nothing actually)
	 * 
	 * @param id identifier
	 */
	public void setId(String id) {
	}

	/**
	 * Get the user login
	 * 
	 * @return user login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Set the user login
	 * 
	 * @param login the user login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Get the user password
	 * 
	 * @return user password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set the user password
	 * 
	 * @param password user password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get the terminal id
	 * 
	 * @return the terminal id
	 */
	public String getTerminalId() {
		return terminalId;
	}

	/**
	 * Set the terminal id
	 * 
	 * @param terminalId the terminal id
	 */
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	/**
	 * Get the class name of the entities that could be synchronized
	 * 
	 * @return Array of class names
	 */
	public Set<SynchronizableEntity> getSynchronizables() {
		return synchronizables;
	}

	/**
	 * Set the class names of entities that could be synchronized
	 * 
	 * @param synchronizables array of synchronizables entities.
	 */
	public void setSynchronizables(Set<SynchronizableEntity> synchronizables) {
		this.synchronizables = synchronizables;
	}

	/**
	 * Get the synchronization type
	 * 
	 * @return the synchronization type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set the synchronization type
	 * 
	 * @param type the synchronization type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Get the synchronization server URL
	 * 
	 * @return the synchronization server URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Set the synchronization server URL
	 * 
	 * @param the synchronization server URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Indicates whether the synchronization process should be repeated or not
	 * 
	 * @return if the synchronization is looped
	 */
	public boolean isLoop() {
		return loop;
	}

	/**
	 * Set the synchronization process repeatable
	 * 
	 * @param loop if the process is repeatable
	 */
	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	/**
	 * Get the period between two synchronizations
	 * 
	 * @return the period in ms
	 */
	public int getPeriod() {
		return period;
	}

	/**
	 * Set the synchronization period
	 * 
	 * @param period the period in ms
	 */
	public void setPeriod(int period) {
		this.period = period;
	}

	/**
	 * Get the time offset between the device and the NTP server
	 * 
	 * @return the time offset in ms
	 */
	public Long getOffset() {
		return offset;
	}

	/**
	 * Set the time offset between the device and an NTP server
	 * 
	 * @param offset the time offset in ms
	 */
	public void setOffset(Long offset) {
		this.offset = offset;
	}
}

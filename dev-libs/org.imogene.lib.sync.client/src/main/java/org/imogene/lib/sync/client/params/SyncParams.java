package org.imogene.lib.sync.client.params;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sync_parameter")
public class SyncParams implements Serializable {

	public static final String ID = "sync-param";
	public static final long DEFAULT_PERIOD = 15 * 60 * 1000; // 15 min

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = -4211054571698086791L;

	@Id
	private final String id = ID;

	private String terminal;

	private String url;

	private String login;

	private String password;

	private Long period;

	private boolean enabled;

	@Column(name = "timeoffset") // offset is a reserved keyword in h2
	private Long offset;

	public String getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Long getPeriod() {
		return period;
	}

	public void setPeriod(Long period) {
		this.period = period;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Long getOffset() {
		return offset;
	}

	public void setOffset(Long offset) {
		this.offset = offset;
	}

}

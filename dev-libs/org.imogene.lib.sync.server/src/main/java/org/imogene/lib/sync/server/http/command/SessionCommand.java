package org.imogene.lib.sync.server.http.command;

public class SessionCommand {

	private String session;
	
	private int status;
	
	private long len;
	
	private boolean debug=false;

	public String getSession() {
		return session;
	}

	public void setSession(String sessionId) {
		this.session = sessionId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getLen() {
		return len;
	}

	public void setLen(long len) {
		this.len = len;
	}	
	
	public void setDebug(boolean debug){
		this.debug = debug;
	}
	
	public boolean getDebug(){
		return this.debug;
	}
			
}

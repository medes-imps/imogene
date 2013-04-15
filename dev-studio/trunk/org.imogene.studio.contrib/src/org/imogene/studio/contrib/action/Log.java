package org.imogene.studio.contrib.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Log implements org.apache.commons.logging.Log, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 12434254564576L;
	private static List<LogListener> listeners = new ArrayList<LogListener>();
	
	public static void registerToLogFactory() {
		System.setProperty("org.apache.commons.logging.Log", Log.class.getName()); //$NON-NLS-1$
	}

	private String sClassName;
	
	public Log() {
		
	}
	
	public Log(String sName) {
		sClassName = sName;
	}
	
	public String getClassName() {
		return sClassName;
	}
	
	public static synchronized void addLogListener(LogListener l) {
		listeners.add(l);
	}
	
	public static synchronized void removeLogListener(LogListener l) {
		listeners.remove(l);
	}
	
	public static synchronized void announceLogListener(LogEvent e) {
		for (LogListener l : listeners) {
			l.logEvent(e);
		}
	}

	public void debug(Object message) {
		announceLogListener(new LogEvent(String.valueOf(message)));
	}

	public void debug(Object message, Throwable t) {
		announceLogListener(new LogEvent(String.valueOf(message)));
	}

	public void error(Object message) {
		announceLogListener(new LogEvent(String.valueOf(message)));
	}

	public void error(Object message, Throwable t) {
		announceLogListener(new LogEvent(String.valueOf(message)));
	}

	public void fatal(Object message) {
		announceLogListener(new LogEvent(String.valueOf(message)));
	}

	public void fatal(Object message, Throwable t) {
		announceLogListener(new LogEvent(String.valueOf(message)));
	}

	public void info(Object message) {
		announceLogListener(new LogEvent(String.valueOf(message)));
	}

	public void info(Object message, Throwable t) {
		announceLogListener(new LogEvent(String.valueOf(message)));
	}

	public boolean isDebugEnabled() {
		return true;
	}

	public boolean isErrorEnabled() {
		return true;
	}

	public boolean isFatalEnabled() {
		return true;
	}

	public boolean isInfoEnabled() {
		return true;
	}

	public boolean isTraceEnabled() {
		return true;
	}

	public boolean isWarnEnabled() {
		return true;
	}

	public void trace(Object message) {
		announceLogListener(new LogEvent(String.valueOf(message)));
	}

	public void trace(Object message, Throwable t) {
		announceLogListener(new LogEvent(String.valueOf(message)));
	}

	public void warn(Object message) {
		announceLogListener(new LogEvent(String.valueOf(message)));
	}

	public void warn(Object message, Throwable t) {
		announceLogListener(new LogEvent(String.valueOf(message)));
	}
}

abstract class LogListener {
	public abstract void logEvent(LogEvent e);
}

class LogEvent {

	private final String message;

	public LogEvent(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message; 
	}
}

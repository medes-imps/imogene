package org.imogene.lib.push.connections;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.push.dao.ConnectionDao;
import org.imogene.lib.push.dao.MessageDao;
import org.imogene.lib.push.domain.Connection;
import org.imogene.lib.push.domain.Message;
import org.imogene.lib.push.status.ConnectionStatus;
import org.imogene.lib.push.status.MessageStatus;
import org.imogene.lib.sync.security.UserAccessControl;

public class ConnectionController {

	private final Map<String, ConnectionThread> connections = new ConcurrentHashMap<String, ConnectionThread>();

	private UserAccessControl userAccessControl;
	private ConnectionDao connectionDao;
	private MessageDao messageDao;

	public void setConnectionDao(ConnectionDao connectionDao) {
		this.connectionDao = connectionDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public void setUserAccessControl(UserAccessControl userAccessControl) {
		this.userAccessControl = userAccessControl;
	}

	public ConnectionThread getConnection(String connectionId) {
		return connections.get(connectionId);
	}
	
	public void close() {
		for (ConnectionThread conn : connections.values()) {
			conn.abort();
		}
	}

	protected boolean validate(String login, String password) {
		ImogActor actor = userAccessControl.authenticate(login, password);
		if (actor != null) {
			return true;
		}
		return false;
	}

	protected void registerThread(ConnectionThread thread) {
		Connection c = connectionDao.loadConnection(thread.getLogin(), thread.getTerminal());
		if (c == null) {
			c = new Connection();
			c.setId(UUID.randomUUID().toString());
			c.setLogin(thread.getLogin());
			c.setTerminal(thread.getTerminal());
			c.setCreation(new Date());
		}
		c.setThreadId(thread.getId());
		c.setLastConnection(new Date());
		c.setStatus(ConnectionStatus.ENABLED);
		connectionDao.saveOrUpdate(c);

		thread.setConnectionId(c.getId());
		connections.put(c.getId(), thread);

		pushWaitingMessages(thread);
	}

	private void pushWaitingMessages(ConnectionThread thread) {
		List<Message> list = messageDao.loadMessagesWaiting(thread.getLogin(), thread.getTerminal());
		for (Message message : list) {
			try {
				thread.pushAndScheduleTimeout(message.getId(), message.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected void unregisterThread(ConnectionThread thread) {
		connections.remove(thread);
		Connection c = connectionDao.loadConnection(thread.getConnectionId());
		if (c != null) {
			c.setStatus(ConnectionStatus.DISABLED);
			c.setDisconnection(new Date());
			connectionDao.saveOrUpdate(c);
		}
	}

	protected void pingReceived(ConnectionThread thread) {
		Connection c = connectionDao.loadConnection(thread.getConnectionId());
		if (c != null) {
			c.setLastPing(new Date());
			connectionDao.saveOrUpdate(c);
		}
	}

	protected void pongReceived(ConnectionThread thread) {
		pingReceived(thread);
	}

	protected void messagePushed(String id) {
		Message message = messageDao.loadMessage(id);
		if (message != null) {
			message.setStatus(MessageStatus.SENT);
			message.setReceived(new Date());
			messageDao.saveOrUpdate(message);
		}
	}

	protected void messageTimedout(ConnectionThread thread, String id) {
		Message message = messageDao.loadMessage(id);
		if (message != null) {
			message.setTries(message.getTries() + 1);
			message.setLastTry(new Date());
			messageDao.saveOrUpdate(message);
		}
	}

}

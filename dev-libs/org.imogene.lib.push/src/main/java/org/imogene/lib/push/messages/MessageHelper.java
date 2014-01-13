package org.imogene.lib.push.messages;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.imogene.lib.push.connections.ConnectionController;
import org.imogene.lib.push.connections.ConnectionThread;
import org.imogene.lib.push.dao.ConnectionDao;
import org.imogene.lib.push.dao.MessageDao;
import org.imogene.lib.push.domain.Connection;
import org.imogene.lib.push.domain.Message;
import org.imogene.lib.push.status.MessageStatus;

public class MessageHelper {

	private ConnectionController connectionController;
	private ConnectionDao connectionDao;
	private MessageDao messageDao;

	public void setConnectionController(ConnectionController connectionController) {
		this.connectionController = connectionController;
	}

	public void setConnectionDao(ConnectionDao connectionDao) {
		this.connectionDao = connectionDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public void pushMessage(String login, String message) {
		pushMessage(login, null, message);
	}

	public void broadcastMessage(String message) {
		pushMessage(null, null, message);
	}

	public void pushMessage(String login, String terminal, String message) {
		if (login != null && terminal != null) {
			Connection c = connectionDao.loadConnection(login, terminal);
			saveMessage(c, message);
		} else if (login != null) {
			List<Connection> list = connectionDao.loadConnectionsByUser(login);
			saveMessage(list, message);
		} else if (terminal != null) {
			List<Connection> list = connectionDao.loadConnectionsByTerminal(terminal);
			saveMessage(list, message);
		} else {
			List<Connection> list = connectionDao.loadConnections();
			saveMessage(list, message);
		}
	}

	private void saveMessage(List<Connection> connections, String message) {
		for (Connection connection : connections) {
			saveMessage(connection, message);
		}
	}

	private void saveMessage(Connection connection, String message) {
		Message pushMessage = new Message();
		pushMessage.setId(UUID.randomUUID().toString());
		pushMessage.setLogin(connection.getLogin());
		pushMessage.setTerminal(connection.getTerminal());
		pushMessage.setMessage(message);
		pushMessage.setCreation(new Date());
		pushMessage.setStatus(MessageStatus.WAIT);
		pushMessage.setTries(0);
		messageDao.saveOrUpdate(pushMessage);

		ConnectionThread thread = connectionController.getConnection(connection.getId());
		if (thread != null) {
			try {
				thread.pushAndScheduleTimeout(pushMessage.getId(), pushMessage.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

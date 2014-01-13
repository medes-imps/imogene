package org.imogene.lib.push.dao;

import java.util.List;

import org.imogene.lib.push.domain.Message;

public interface MessageDao {

	public Message loadMessage(String id);

	public List<Message> loadMessagesWaiting(String login, String terminal);

	public void saveOrUpdate(Message message);

}

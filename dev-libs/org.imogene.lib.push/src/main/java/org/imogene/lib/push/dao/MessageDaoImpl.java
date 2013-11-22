package org.imogene.lib.push.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.imogene.lib.push.domain.Message;
import org.springframework.transaction.annotation.Transactional;

public class MessageDaoImpl implements MessageDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Message loadMessage(String id) {
		if (id == null) {
			return null;
		}
		return em.find(Message.class, id);
	}

	@Override
	public List<Message> loadMessagesWaiting(String login, String terminal) {
		TypedQuery<Message> query = em.createNamedQuery("findWaitingMessagesByUserAndTerminal", Message.class);
		query.setParameter("login", login);
		query.setParameter("terminal", terminal);
		return query.getResultList();
	}

	@Override
	@Transactional(readOnly = false)
	public void saveOrUpdate(Message message) {
		em.merge(message);
	}

}

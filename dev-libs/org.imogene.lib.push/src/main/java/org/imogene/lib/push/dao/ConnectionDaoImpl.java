package org.imogene.lib.push.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.imogene.lib.push.domain.Connection;
import org.springframework.transaction.annotation.Transactional;

public class ConnectionDaoImpl implements ConnectionDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Connection loadConnection(String id) {
		if (id == null) {
			return null;
		}
		return em.find(Connection.class, id);
	}

	@Override
	public Connection loadConnection(String login, String terminal) {
		TypedQuery<Connection> query = em.createNamedQuery("findConnectionByUserAndTerminal", Connection.class);
		query.setParameter("login", login);
		query.setParameter("terminal", terminal);
		query.setMaxResults(1);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Connection> loadConnectionsByUser(String login) {
		TypedQuery<Connection> query = em.createNamedQuery("findConnectionsByUser", Connection.class);
		query.setParameter("login", login);
		return query.getResultList();
	}

	@Override
	public List<Connection> loadConnectionsByTerminal(String terminal) {
		TypedQuery<Connection> query = em.createNamedQuery("findConnectionsByTerminal", Connection.class);
		query.setParameter("terminal", terminal);
		return query.getResultList();
	}

	@Override
	public List<Connection> loadConnections() {
		return em.createNamedQuery("findAllConnections", Connection.class).getResultList();
	}

	@Override
	@Transactional(readOnly = false)
	public void saveOrUpdate(Connection connection) {
		em.merge(connection);
	}

}

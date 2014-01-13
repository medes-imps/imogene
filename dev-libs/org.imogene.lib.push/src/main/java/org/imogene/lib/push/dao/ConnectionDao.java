package org.imogene.lib.push.dao;

import java.util.List;

import org.imogene.lib.push.domain.Connection;

public interface ConnectionDao {

	public Connection loadConnection(String id);

	public Connection loadConnection(String login, String terminal);

	public List<Connection> loadConnectionsByUser(String login);

	public List<Connection> loadConnectionsByTerminal(String terminal);

	public List<Connection> loadConnections();

	public void saveOrUpdate(Connection connection);

}

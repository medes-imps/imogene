package org.imogene.android.database.sqlite.stmt.query;

import java.util.List;

/**
 * Raw part of the where to just stick in a string in the middle of the WHERE.
 * It is up to the user to do so properly.
 * 
 * @author Medes-IMPS
 */
public class Raw implements Clause {

	private final String statement;
	private final Object[] args;

	public Raw(String statement, Object[] args) {
		this.statement = statement;
		this.args = args;
	}

	@Override
	public void appendSql(String tableName, StringBuilder sb, List<Object> argList) {
		sb.append(statement);
		sb.append(' ');
		for (Object arg : args) {
			argList.add(arg);
		}
	}
}

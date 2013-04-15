package org.imogene.android.database.sqlite.stmt.query;

import java.util.List;

/**
 * Internal marker class for query clauses.
 * 
 * @author Medes-IMPS
 */
public interface Clause {

	/**
	 * Add to the string-builder the appropriate SQL for this clause.
	 * 
	 * @param tableName Name of the table to prepend to any column names or null
	 *            to be ignored.
	 * @param sb The StringBuilder to append the clause to.
	 * @param argList The list of arguments. New arguments will be automatically
	 *            added to this list, it must not be {@code null}.
	 */
	public void appendSql(String tableName, StringBuilder sb, List<Object> argList);
}

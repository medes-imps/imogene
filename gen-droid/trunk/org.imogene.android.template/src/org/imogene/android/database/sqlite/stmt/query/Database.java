package org.imogene.android.database.sqlite.stmt.query;

/**
 * Database Helper class providing methods to ease the construction of SQL
 * statements.
 * 
 * @author Medes-IMPS
 * 
 */
public class Database {

	/**
	 * Append the entity name to the statement escaping the characters.
	 * 
	 * @param sb The StringBuilder to append the entity name.
	 * @param name The name to append to the statement.
	 */
	public static void appendEscapedEntityName(StringBuilder sb, String name) {
		sb.append('`').append(name).append('`');
	}

	/**
	 * Append a limit value to a statement.
	 * 
	 * @param sb The StringBuilder to append the limit statement to.
	 * @param limit The limit value.
	 * @param offset The offset value (ot usued).
	 */
	public static void appendLimitValue(StringBuilder sb, long limit, Long offset) {
		sb.append("LIMIT ").append(limit).append(' ');
	}

	/**
	 * Append an offset value to a statement.
	 * 
	 * @param sb The StringBuilder to append the offset statement to.
	 * @param offset The offset value.
	 */
	public static void appendOffsetValue(StringBuilder sb, long offset) {
		sb.append("OFFSET ").append(offset).append(' ');
	}

	/**
	 * Append a max query statement.
	 * 
	 * @param sb The StringBuilder to add the max statement.
	 * @param column The column name where the max statement should be
	 *            performed.
	 */
	public static void appendMaxQuery(StringBuilder sb, String column) {
		sb.append("MAX(").append(column).append(") ");
	}
}

package org.imogene.android.database.sqlite.stmt.query;

import java.util.List;

/**
 * Internal base class for all comparison operations.
 * 
 * @author Medes-IMPS
 */
abstract class BaseComparison implements Comparison {

	protected final String columnName;
	private final Object value;

	protected BaseComparison(String columnName, Object value, boolean isComparison) {
		this.columnName = columnName;
		this.value = value;
	}

	@Override
	public void appendSql(String tableName, StringBuilder sb, List<Object> argList) {
		if (tableName != null) {
			Database.appendEscapedEntityName(sb, tableName);
			sb.append('.');
		}
		Database.appendEscapedEntityName(sb, columnName);
		sb.append(' ');
		appendOperation(sb);
		// this needs to call appendValue (not appendArgOrValue) because it may
		// be overridden
		appendValue(sb, argList);
	}

	@Override
	public String getColumnName() {
		return columnName;
	}

	@Override
	public void appendValue(StringBuilder sb, List<Object> argList) {
		appendArgOrValue(sb, argList, value);
	}

	/**
	 * Append to the string builder a value object.
	 * 
	 * @param sb The StringBuilder to append the clause to.
	 * @param argList The list of arguments. New arguments values will be added
	 *            to this list.
	 * @param argOrValue Argument to add to the clause.
	 */
	protected void appendArgOrValue(StringBuilder sb, List<Object> argList, Object argOrValue) {
		sb.append('?');
		sb.append(' ');
		argList.add(argOrValue);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(columnName).append(' ');
		appendOperation(sb);
		sb.append(' ');
		sb.append(value);
		return sb.toString();
	}
}

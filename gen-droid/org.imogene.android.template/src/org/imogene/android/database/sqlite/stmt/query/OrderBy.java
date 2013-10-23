package org.imogene.android.database.sqlite.stmt.query;

/**
 * Internal class handling the SQL 'ORDER BY' operation. Used by
 * {@link StatementBuilder#orderBy}.
 * 
 * @author Medes-IMPS
 */
public class OrderBy {

	private final String columnName;
	private final boolean ascending;

	public OrderBy(String columnName, boolean ascending) {
		this.columnName = columnName;
		this.ascending = ascending;
	}

	/**
	 * Return the associated column-name.
	 * 
	 * @return The associated column name.
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * Are we ordering in ascending order. False is descending.
	 * 
	 * @return Whether {@code true} for ascending order, {@code false}
	 *         otherwise.
	 */
	public boolean isAscending() {
		return ascending;
	}
}

package org.imogene.android.database.sqlite.stmt.query;

import org.imogene.android.database.sqlite.stmt.QueryBuilder;

/**
 * Internal class handling the SQL 'ORDER BY' operation. Used by {@link QueryBuilder#orderBy(String, boolean)} and
 * {@link QueryBuilder#orderByRaw(String)}.
 * 
 * @author graywatson
 */
public class OrderBy {

	private final String columnName;
	private final boolean ascending;
	private final String rawSql;
	private final Object[] orderByArgs;

	public OrderBy(String columnName, boolean ascending) {
		this.columnName = columnName;
		this.ascending = ascending;
		this.rawSql = null;
		this.orderByArgs = null;
	}

	public OrderBy(String rawSql, Object[] orderByArgs) {
		this.columnName = null;
		this.ascending = true;
		this.rawSql = rawSql;
		this.orderByArgs = orderByArgs;
	}

	public String getColumnName() {
		return columnName;
	}

	public boolean isAscending() {
		return ascending;
	}

	public String getRawSql() {
		return rawSql;
	}

	public Object[] getOrderByArgs() {
		return orderByArgs;
	}
}

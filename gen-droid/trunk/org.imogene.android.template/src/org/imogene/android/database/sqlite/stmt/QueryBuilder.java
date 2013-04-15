package org.imogene.android.database.sqlite.stmt;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.database.sqlite.stmt.query.Database;
import org.imogene.android.database.sqlite.stmt.query.OrderBy;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Assists in building sql query (SELECT) statements for a particular table in a
 * particular database.
 * 
 * @author Medes-IMPS
 */
public class QueryBuilder extends StatementBuilder {

	private boolean distinct;
	private boolean selectIdColumn = true;
	private List<String> selectColumnList;
	private List<String> selectRawList;
	private List<OrderBy> orderByList;
	private String orderByRaw;
	private Object[] orderByArgs;
	private List<String> groupByList;
	private String groupByRaw;
	private boolean isInnerQuery;
	private boolean isCountOfQuery;
	private boolean isMaxQuery;
	private String maxColumn;
	private String having;
	private Long limit;
	private Long offset;
	private List<JoinInfo> joinList;
	private ImogOpenHelper helper;
	private CursorFactory factory;
	private Uri uri;

	public QueryBuilder(ImogOpenHelper helper, String tableName) {
		super(tableName);
		this.helper = helper;
	}

	/**
	 * This is used by the internal call structure to note when a query builder
	 * is being used as an inner query. This is necessary because by default, we
	 * add in the ID column on every query. When you are returning a data item,
	 * its ID field must be set otherwise you can't do a refresh() or update().
	 * But internal queries must have 1 select column set so we can't add the
	 * ID.
	 */
	void enableInnerQuery() {
		this.isInnerQuery = true;
	}

	/**
	 * Return the number of selected columns in the query.
	 * 
	 * @return Number of selected columns.
	 */
	int getSelectColumnCount() {
		if (selectColumnList == null) {
			return 0;
		} else {
			return selectColumnList.size();
		}
	}

	/**
	 * Return the selected columns in the query or an empty list if none were
	 * specified.
	 * 
	 * @return The list of selected columns.
	 */
	List<String> getSelectColumns() {
		if (selectColumnList == null) {
			return Collections.emptyList();
		} else {
			return selectColumnList;
		}
	}

	/**
	 * Add columns to be returned by the SELECT query. If no columns are
	 * selected then all columns are returned by default. For classes with id
	 * columns, the id column is added to the select list automatically.
	 * 
	 * @param columns The columns to select.
	 * @return This QueryBuilder object to allow for chaining of calls to set
	 *         methods.
	 */
	public QueryBuilder selectColumns(String... columns) {
		if (selectColumnList == null) {
			selectColumnList = new ArrayList<String>();
		}
		for (String column : columns) {
			addSelectColumnToList(column);
		}
		return this;
	}

	/**
	 * Same as {@link #selectColumns(String...)} except the columns are
	 * specified as an iterable -- probably will be a {@link Collection}.
	 * 
	 * @param columns The columns to select.
	 * @return This QueryBuilder object to allow for chaining of calls to set
	 *         methods.
	 */
	public QueryBuilder selectColumns(Iterable<String> columns) {
		if (selectColumnList == null) {
			selectColumnList = new ArrayList<String>();
		}
		for (String column : columns) {
			addSelectColumnToList(column);
		}
		return this;
	}

	/**
	 * Add raw columns or aggregate functions (COUNT, MAX, ...) to the query.
	 * 
	 * @param The raw columns or aggregate functions to add.
	 * @return This QueryBuilder object to allow for chaining of calls to set
	 *         methods.
	 */
	public QueryBuilder selectRaw(String... columns) {
		if (selectRawList == null) {
			selectRawList = new ArrayList<String>();
		}
		for (String column : columns) {
			selectRawList.add(column);
		}
		return this;
	}

	/**
	 * Add "GROUP BY" clause to the SQL query statement. Use of this means that
	 * the resulting objects may not have a valid ID column value so cannot be
	 * deleted or updated.
	 * 
	 * @param The column name to group by.
	 * @return This QueryBuilder object to allow for chaining of calls to set
	 *         methods.
	 */
	public QueryBuilder groupBy(String columnName) {
		if (groupByList == null) {
			groupByList = new ArrayList<String>();
		}
		groupByList.add(columnName);
		selectIdColumn = false;
		return this;
	}

	/**
	 * Add a raw SQL "GROUP BY" clause to the SQL query statement.
	 * 
	 * @param The raw SQL "GROUP BY" clause. This should not include the
	 *            "GROUP BY".
	 * @return This QueryBuilder object to allow for chaining of calls to set
	 *         methods.
	 */
	public QueryBuilder groupByRaw(String rawSql) {
		groupByRaw = rawSql;
		return this;
	}

	/**
	 * Add "ORDER BY" clause to the SQL query statement.
	 * 
	 * @param columnName The columns name to order by.
	 * @param ascending {@code true} for ascending order, {@code false}
	 *            otherwise.
	 * @return This QueryBuilder object to allow for chaining of calls to set
	 *         methods.
	 */
	public QueryBuilder orderBy(String columnName, boolean ascending) {
		if (orderByList == null) {
			orderByList = new ArrayList<OrderBy>();
		}
		orderByList.add(new OrderBy(columnName, ascending));
		return this;
	}

	/**
	 * Add raw SQL "ORDER BY" clause to the SQL query statement.
	 * 
	 * @param rawSql The raw SQL order by clause. This should not include the
	 *            "ORDER BY".
	 * @return This QueryBuilder object to allow for chaining of calls to set
	 *         methods.
	 */
	public QueryBuilder orderByRaw(String rawSql) {
		return orderByRaw(rawSql, (Object[]) null);
	}

	/**
	 * Add raw SQL "ORDER BY" clause to the SQL query statement.
	 * 
	 * @param rawSql The raw SQL order by clause. This should not include the
	 *            "ORDER BY".
	 * @param args Optional arguments that correspond to any ? specified in the
	 *            rawSql. Each of the arguments must have the sql-type set.
	 * @return This QueryBuilder object to allow for chaining of calls to set
	 *         methods.
	 */
	public QueryBuilder orderByRaw(String rawSql, Object... args) {
		orderByRaw = rawSql;
		orderByArgs = args;
		return this;
	}

	/**
	 * Add "DISTINCT" clause to the SQL query statement. Use of this means that
	 * the resulting objects may not have a valid ID column value so cannot be
	 * deleted or updated.
	 * 
	 * @return This QueryBuilder object to allow for chaining of calls to set
	 *         methods.
	 */
	public QueryBuilder distinct() {
		distinct = true;
		selectIdColumn = false;
		return this;
	}

	/**
	 * Limit the output to maxRows maximum number of rows. Set to null for no
	 * limit (the default).
	 * 
	 * @param maxRows The maximum number of rows returned by the query.
	 * @return This QueryBuilder object to allow for chaining of calls to set
	 *         methods.
	 */
	public QueryBuilder limit(Long maxRows) {
		limit = maxRows;
		return this;
	}

	/**
	 * Start the output at this row number. Set to null for no offset (the
	 * default). If you are paging you probably want to specify a
	 * {@link #orderBy(String, boolean)}.
	 * 
	 * @param startRow Row number to start the output.
	 * @return This QueryBuilder object to allow for chaining of calls to set
	 *         methods.
	 */
	public QueryBuilder offset(Long startRow) throws SQLException {
		offset = startRow;
		return this;
	}

	/**
	 * Set whether or not we should only return the count of the results. To get
	 * the count-of directly, use {@link #countOf()}.
	 * 
	 * @param {@code true} for a count-of query, {@code false} otherwise.
	 * 
	 * @return This QueryBuilder object to allow for chaining of calls to set
	 *         methods.
	 */
	public QueryBuilder setCountOf(boolean countOf) {
		this.isCountOfQuery = countOf;
		return this;
	}

	/**
	 * Sets the count-of query flag using {@link #setCountOf(boolean)} to true.
	 * 
	 * @return This QueryBuilder object to allow for chaining of calls to set
	 *         methods.
	 */
	public QueryBuilder countOf() {
		setCountOf(true);
		return this;
	}

	/**
	 * Makes the query return the largest value of the selected column.
	 * 
	 * @param column The column to find the largest value.
	 * @return This QueryBuilder object to allow for chaining of calls to set
	 *         methods.
	 */
	public QueryBuilder setMaxColumn(String column) {
		this.isMaxQuery = true;
		this.maxColumn = column;
		return this;
	}

	/**
	 * Add raw SQL "HAVING" clause to the SQL query statement.
	 * 
	 * @param The raw SQL "HAVING" clause. This should not include the "HAVING"
	 *            string.
	 * @return This QueryBuilder object to allow for chaining of calls to set
	 *         methods.
	 */
	public QueryBuilder having(String having) {
		this.having = having;
		return this;
	}

	/**
	 * Sets the cursor factory to be used for the query. You can use one factory
	 * for all queries on a database but it is normally easier to specify the
	 * factory when doing this query.
	 * 
	 * @param factory The factory to use.
	 * @return This QueryBuilder object to allow for chaining of calls to set
	 *         methods.
	 */
	public QueryBuilder setCursorFactory(CursorFactory factory) {
		this.factory = factory;
		return this;
	}

	/**
	 * Register to watch a content URI for changes. This can be the URI of a
	 * specific data row (for example, "content://my_provider_type/23"), or a a
	 * generic URI for a content type.
	 * 
	 * @param uri The content URI to watch.
	 * @return This QueryBuilder object to allow for chaining of calls to set
	 *         methods.
	 */
	public QueryBuilder setUri(Uri uri) {
		this.uri = uri;
		return this;
	}

	@Override
	public void clear() {
		super.clear();
		distinct = false;
		selectIdColumn = true;
		selectColumnList = null;
		selectRawList = null;
		orderByList = null;
		orderByRaw = null;
		groupByList = null;
		groupByRaw = null;
		isInnerQuery = false;
		isCountOfQuery = false;
		isMaxQuery = false;
		maxColumn = null;
		having = null;
		limit = null;
		offset = null;
		if (joinList != null) {
			// help gc
			joinList.clear();
			joinList = null;
		}
		addTableName = false;
	}

	/**
	 * Query the given statement, returning a {@link Cursor} over the result
	 * set.
	 * 
	 * @return A Cursor object, which is positioned before the first entry.
	 */
	public Cursor query() {
		List<Object> args = new ArrayList<Object>();
		String sql = buildStatementString(args);
		String[] stringArray = args.toArray(new String[args.size()]);
		Cursor cursor = helper.getReadableDatabase().rawQueryWithFactory(factory, sql, stringArray, null);
		if (uri != null) {
			cursor.setNotificationUri(helper.getContext().getContentResolver(), uri);
		}
		return cursor;
	}

	/**
	 * Execute a statement that returns a 1 by 1 table with a numeric value. For
	 * example, SELECT COUNT(*) FROM table, see {@link #countOf()}.
	 * 
	 * @return The result of the query.
	 */
	public long queryForLong() {
		List<Object> args = new ArrayList<Object>();
		String sql = buildStatementString(args);
		String[] stringArray = args.toArray(new String[args.size()]);
		Cursor cursor = helper.getReadableDatabase().rawQuery(sql, stringArray);
		long result = -1;
		if (cursor.moveToFirst()) {
			result = cursor.getLong(0);
		}
		cursor.close();
		return result;
	}

	/**
	 * Execute a statement that returns a 1 by 1 table with a text value. For
	 * example, SELECT COUNT(*) FROM table.
	 * 
	 * @return The result of the query.
	 */
	public String queryForString() {
		List<Object> args = new ArrayList<Object>();
		String sql = buildStatementString(args);
		String[] stringArray = args.toArray(new String[args.size()]);
		Cursor cursor = helper.getReadableDatabase().rawQuery(sql, stringArray);
		String result = null;
		if (cursor.moveToFirst()) {
			result = cursor.getString(0);
		}
		cursor.close();
		return result;
	}

	/**
	 * Update row(s) for the query. If the content provider supports
	 * transactions the update will be atomic.
	 * 
	 * @param values The new field values. The key is the column name for the
	 *            field. A null value will remove an existing field value.
	 * @return The number of rows updated.
	 */
	public int update(ContentValues values) {
		if (where != null) {
			StringBuilder sb = new StringBuilder();
			List<Object> args = new ArrayList<Object>();
			where.appendSql(null, sb, args);
			return helper.update(tableName, values, sb.toString(), args.toArray(new String[args.size()]));
		} else {
			return helper.update(tableName, values, null, null);
		}
	}

	/**
	 * Deletes row(s) specified by the query. If the content provider supports
	 * transactions, the deletion will be atomic.
	 * 
	 * @return The number of rows deleted.
	 */
	public int delete() {
		if (where != null) {
			StringBuilder sb = new StringBuilder();
			List<Object> args = new ArrayList<Object>();
			where.appendSql(null, sb, args);
			return helper.delete(tableName, sb.toString(), args.toArray(new String[args.size()]));
		} else {
			return helper.delete(tableName, null, null);
		}
	}

	@Override
	protected void appendStatementStart(StringBuilder sb, List<Object> argList) {
		if (joinList == null) {
			setAddTableName(false);
		} else {
			setAddTableName(true);
		}
		sb.append("SELECT ");
		if (distinct) {
			sb.append("DISTINCT ");
		}
		if (isCountOfQuery) {
			sb.append("COUNT(*) ");
		} else if (isMaxQuery) {
			Database.appendMaxQuery(sb, maxColumn);
		} else if (selectRawList != null && !selectRawList.isEmpty()) {
			appendSelectRaw(sb);
		} else {
			appendColumns(sb);
		}
		sb.append("FROM ");
		Database.appendEscapedEntityName(sb, tableName);
		sb.append(' ');
		if (joinList != null) {
			appendJoinSql(sb);
		}
	}

	@Override
	protected void appendWhereStatement(StringBuilder sb, List<Object> argList, boolean first) {
		if (this.where != null) {
			super.appendWhereStatement(sb, argList, first);
			first = false;
		}
		if (joinList != null) {
			for (JoinInfo joinInfo : joinList) {
				joinInfo.queryBuilder.appendWhereStatement(sb, argList, first);
				first = false;
			}
		}
	}

	@Override
	protected void appendStatementEnd(StringBuilder sb, List<Object> argList) {
		// 'group by' comes before 'order by'
		appendGroupBys(sb);
		appendHaving(sb);
		appendOrderBys(sb, argList);
		appendLimit(sb);
		appendOffset(sb);
		// clear the add-table name flag so we can reuse the builder
		setAddTableName(false);
	}

	@Override
	protected boolean shouldPrependTableNameToColumns() {
		return joinList != null;
	}

	private void setAddTableName(boolean addTableName) {
		this.addTableName = addTableName;
		if (joinList != null) {
			for (JoinInfo joinInfo : joinList) {
				joinInfo.queryBuilder.setAddTableName(addTableName);
			}
		}
	}

	/*
	 * private void addJoinInfo(String type, QueryBuilder joinedQueryBuilder)
	 * throws SQLException { JoinInfo joinInfo = new JoinInfo(type,
	 * joinedQueryBuilder); matchJoinedFields(joinInfo, joinedQueryBuilder); if
	 * (joinList == null) { joinList = new ArrayList<JoinInfo>(); }
	 * joinList.add(joinInfo); }
	 */
	/*
	 * private void matchJoinedFields(JoinInfo joinInfo, QueryBuilder
	 * joinedQueryBuilder) throws SQLException { for (FieldType fieldType :
	 * tableInfo.getFieldTypes()) { // if this is a foreign field and its
	 * foreign-id field is the same // as the other's id FieldType
	 * foreignIdField = fieldType.getForeignIdField(); if (fieldType.isForeign()
	 * && foreignIdField.equals(joinedQueryBuilder.tableInfo.getIdField())) {
	 * joinInfo.localField = fieldType; joinInfo.remoteField = foreignIdField;
	 * return; } } // if this other field is a foreign field and its foreign-id
	 * field is // our id for (FieldType fieldType :
	 * joinedQueryBuilder.tableInfo.getFieldTypes()) { if (fieldType.isForeign()
	 * && fieldType.getForeignIdField().equals(idField)) { joinInfo.localField =
	 * idField; joinInfo.remoteField = fieldType; return; } }
	 * 
	 * throw new SQLException("Could not find a foreign " +
	 * tableInfo.getDataClass() + " field in " +
	 * joinedQueryBuilder.tableInfo.getDataClass() + " or vice versa"); }
	 */
	private void addSelectColumnToList(String columnName) {
		selectColumnList.add(columnName);
	}

	private void appendJoinSql(StringBuilder sb) {
		for (JoinInfo joinInfo : joinList) {
			sb.append(joinInfo.type).append(" JOIN ");
			Database.appendEscapedEntityName(sb, joinInfo.queryBuilder.tableName);
			sb.append(" ON ");
			Database.appendEscapedEntityName(sb, tableName);
			sb.append('.');
			Database.appendEscapedEntityName(sb, joinInfo.localField);
			sb.append(" = ");
			Database.appendEscapedEntityName(sb, joinInfo.queryBuilder.tableName);
			sb.append('.');
			Database.appendEscapedEntityName(sb, joinInfo.remoteField);
			sb.append(' ');
			// keep on going down if multiple JOIN layers
			if (joinInfo.queryBuilder.joinList != null) {
				joinInfo.queryBuilder.appendJoinSql(sb);
			}
		}
	}

	private void appendSelectRaw(StringBuilder sb) {
		boolean first = true;
		for (String column : selectRawList) {
			if (first) {
				first = false;
			} else {
				sb.append(", ");
			}
			sb.append(column);
		}
		sb.append(' ');
	}

	private void appendColumns(StringBuilder sb) {
		// if no columns were specified then * is the default
		if (selectColumnList == null) {
			if (addTableName) {
				Database.appendEscapedEntityName(sb, tableName);
				sb.append('.');
			}
			sb.append("* ");
			return;
		}

		boolean first = true;
		boolean hasId;
		if (isInnerQuery) {
			hasId = true;
		} else {
			hasId = false;
		}
		for (String columnName : selectColumnList) {
			if (first) {
				first = false;
			} else {
				sb.append(',');
			}
			appendColumnName(sb, columnName);
			if (BaseColumns._ID.equals(columnName)) {
				hasId = true;
			}
		}

		// we have to add the idField even if it isn't in the columnNameSet
		if (!hasId && selectIdColumn) {
			if (!first) {
				sb.append(',');
			}
			appendColumnName(sb, BaseColumns._ID);
		}
		sb.append(' ');
	}

	private void appendLimit(StringBuilder sb) {
		if (limit != null) {
			Database.appendLimitValue(sb, limit, offset);
		}
	}

	private void appendOffset(StringBuilder sb) {
		if (offset == null) {
			return;
		}
		Database.appendOffsetValue(sb, offset);
	}

	private void appendGroupBys(StringBuilder sb) {
		boolean first = true;
		if (hasGroupStuff()) {
			appendGroupBys(sb, first);
			first = false;
		}
		/*
		 * NOTE: this may not be legal and doesn't seem to work with some
		 * database types but we'll check this out anyway.
		 */
		if (joinList != null) {
			for (JoinInfo joinInfo : joinList) {
				if (joinInfo.queryBuilder != null && joinInfo.queryBuilder.hasGroupStuff()) {
					joinInfo.queryBuilder.appendGroupBys(sb, first);
				}
			}
		}
	}

	private boolean hasGroupStuff() {
		return ((groupByList != null && !groupByList.isEmpty()) || groupByRaw != null);
	}

	private void appendGroupBys(StringBuilder sb, boolean first) {
		if (first) {
			sb.append("GROUP BY ");
		}
		if (groupByRaw != null) {
			if (!first) {
				sb.append(',');
			}
			sb.append(groupByRaw);
		} else {
			for (String columnName : groupByList) {
				if (first) {
					first = false;
				} else {
					sb.append(',');
				}
				appendColumnName(sb, columnName);
			}
		}
		sb.append(' ');
	}

	private void appendOrderBys(StringBuilder sb, List<Object> argList) {
		boolean first = true;
		if (hasOrderStuff()) {
			appendOrderBys(sb, first, argList);
			first = false;
		}
		/*
		 * NOTE: this may not be necessary since the inner results aren't at all
		 * returned but we'll leave this code here anyway.
		 */
		if (joinList != null) {
			for (JoinInfo joinInfo : joinList) {
				if (joinInfo.queryBuilder != null && joinInfo.queryBuilder.hasOrderStuff()) {
					joinInfo.queryBuilder.appendOrderBys(sb, first, argList);
				}
			}
		}
	}

	private boolean hasOrderStuff() {
		return ((orderByList != null && !orderByList.isEmpty()) || orderByRaw != null);
	}

	private void appendOrderBys(StringBuilder sb, boolean first, List<Object> argList) {
		if (first) {
			sb.append("ORDER BY ");
		}
		if (orderByRaw != null) {
			if (!first) {
				sb.append(',');
			}
			sb.append(orderByRaw);
			if (orderByArgs != null) {
				for (Object arg : orderByArgs) {
					argList.add(arg);
				}
			}
		} else {
			for (OrderBy orderBy : orderByList) {
				if (first) {
					first = false;
				} else {
					sb.append(',');
				}
				appendColumnName(sb, orderBy.getColumnName());
				if (orderBy.isAscending()) {
					// here for documentation purposes, ASC is the default
					// sb.append(" ASC");
				} else {
					sb.append(" DESC");
				}
			}
		}
		sb.append(' ');
	}

	private void appendColumnName(StringBuilder sb, String columnName) {
		if (addTableName) {
			Database.appendEscapedEntityName(sb, tableName);
			sb.append('.');
		}
		Database.appendEscapedEntityName(sb, columnName);
	}

	private void appendHaving(StringBuilder sb) {
		if (having != null) {
			sb.append("HAVING ").append(having).append(' ');
		}
	}

	/**
	 * Encapsulates our join information.
	 */
	private class JoinInfo {
		final String type;
		final QueryBuilder queryBuilder;
		String localField;
		String remoteField;

		public JoinInfo(String type, QueryBuilder queryBuilder) {
			this.type = type;
			this.queryBuilder = queryBuilder;
		}
	}

	/**
	 * Internal class used to expose methods to internal classes but through a
	 * wrapper instead of a builder.
	 */
	public static class InternalQueryBuilderWrapper {

		private final QueryBuilder queryBuilder;

		InternalQueryBuilderWrapper(QueryBuilder queryBuilder) {
			this.queryBuilder = queryBuilder;
		}

		public void appendStatementString(StringBuilder sb, List<Object> argList) {
			queryBuilder.appendStatementString(sb, argList);
		}

	}

}

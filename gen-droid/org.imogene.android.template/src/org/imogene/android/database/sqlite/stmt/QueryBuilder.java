package org.imogene.android.database.sqlite.stmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.database.sqlite.stmt.query.ColumnNameOrRawSql;
import org.imogene.android.database.sqlite.stmt.query.Database;
import org.imogene.android.database.sqlite.stmt.query.OrderBy;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Assists in building sql query (SELECT) statements for a particular table in a particular database.
 * 
 * @author Medes-IMPS
 * @see <a href="https://github.com/j256/ormlite-core.git">https://github.com/j256/ormlite-core.git</a>
 */
public class QueryBuilder extends StatementBuilder {

	private boolean distinct;
	private boolean selectIdColumn = true;
	private List<ColumnNameOrRawSql> selectList;
	private List<OrderBy> orderByList;
	private List<ColumnNameOrRawSql> groupByList;
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
		super(tableName, StatementType.SELECT);
		this.helper = helper;
	}

	/**
	 * Sets the cursor factory to be used for the query. You can use one factory for all queries on a database but it is
	 * normally easier to specify the factory when doing this query.
	 * 
	 * @param factory The factory to use.
	 * @return This QueryBuilder object to allow for chaining of calls to set methods.
	 */
	public QueryBuilder setCursorFactory(CursorFactory factory) {
		this.factory = factory;
		return this;
	}

	/**
	 * Register to watch a content URI for changes. This can be the URI of a specific data row (for example,
	 * "content://my_provider_type/23"), or a a generic URI for a content type.
	 * 
	 * @param uri The content URI to watch.
	 * @return This QueryBuilder object to allow for chaining of calls to set methods.
	 */
	public QueryBuilder setUri(Uri uri) {
		this.uri = uri;
		return this;
	}

	/**
	 * This is used by the internal call structure to note when a query builder is being used as an inner query. This is
	 * necessary because by default, we add in the ID column on every query. When you are returning a data item, its ID
	 * field must be set otherwise you can't do a refresh() or update(). But internal queries must have 1 select column
	 * set so we can't add the ID.
	 */
	void enableInnerQuery() {
		this.isInnerQuery = true;
	}

	/**
	 * Return the number of selected columns in the query.
	 */
	int getSelectColumnCount() {
		if (isCountOfQuery) {
			return 1;
		} else if (selectList == null) {
			return 0;
		} else {
			return selectList.size();
		}
	}

	/**
	 * Return the selected columns in the query or an empty list if none were specified.
	 */
	String getSelectColumnsAsString() {
		if (isCountOfQuery) {
			return "COUNT(*)";
		} else if (selectList == null) {
			return "";
		} else {
			return selectList.toString();
		}
	}

	/**
	 * Add columns to be returned by the SELECT query. If no columns are selected then all columns are returned by
	 * default. For classes with id columns, the id column is added to the select list automagically. This can be called
	 * multiple times to add more columns to select.
	 * 
	 * <p>
	 * <b>WARNING:</b> If you specify any columns to return, then any foreign-collection fields will be returned as null
	 * <i>unless</i> their {@link ForeignCollectionField#columnName()} is also in the list.
	 * </p>
	 */
	public QueryBuilder selectColumns(String... columns) {
		for (String column : columns) {
			addSelectColumnToList(column);
		}
		return this;
	}

	/**
	 * Same as {@link #selectColumns(String...)} except the columns are specified as an iterable -- probably will be a
	 * {@link Collection}. This can be called multiple times to add more columns to select.
	 */
	public QueryBuilder selectColumns(Iterable<String> columns) {
		for (String column : columns) {
			addSelectColumnToList(column);
		}
		return this;
	}

	/**
	 * Add raw columns or aggregate functions (COUNT, MAX, ...) to the query. This will turn the query into something
	 * only suitable for the {@link Dao#queryRaw(String, String...)} type of statement. This can be called multiple
	 * times to add more columns to select.
	 */
	public QueryBuilder selectRaw(String... columns) {
		for (String column : columns) {
			addSelectToList(ColumnNameOrRawSql.withRawSql(column));
		}
		return this;
	}

	/**
	 * Add "GROUP BY" clause to the SQL query statement. This can be called multiple times to add additional "GROUP BY"
	 * clauses.
	 * 
	 * <p>
	 * NOTE: Use of this means that the resulting objects may not have a valid ID column value so cannot be deleted or
	 * updated.
	 * </p>
	 */
	public QueryBuilder groupBy(String columnName) {
		addGroupBy(ColumnNameOrRawSql.withColumnName(columnName));
		return this;
	}

	/**
	 * Add a raw SQL "GROUP BY" clause to the SQL query statement. This should not include the "GROUP BY".
	 */
	public QueryBuilder groupByRaw(String rawSql) {
		addGroupBy(ColumnNameOrRawSql.withRawSql(rawSql));
		return this;
	}

	/**
	 * Add "ORDER BY" clause to the SQL query statement. This can be called multiple times to add additional "ORDER BY"
	 * clauses. Ones earlier are applied first.
	 */
	public QueryBuilder orderBy(String columnName, boolean ascending) {
		addOrderBy(new OrderBy(columnName, ascending));
		return this;
	}

	/**
	 * Add raw SQL "ORDER BY" clause to the SQL query statement.
	 * 
	 * @param rawSql The raw SQL order by clause. This should not include the "ORDER BY".
	 */
	public QueryBuilder orderByRaw(String rawSql) {
		addOrderBy(new OrderBy(rawSql, (Object[]) null));
		return this;
	}

	/**
	 * Add raw SQL "ORDER BY" clause to the SQL query statement.
	 * 
	 * @param rawSql The raw SQL order by clause. This should not include the "ORDER BY".
	 * @param args Optional arguments that correspond to any ? specified in the rawSql. Each of the arguments must have
	 *        the sql-type set.
	 */
	public QueryBuilder orderByRaw(String rawSql, Object... args) {
		addOrderBy(new OrderBy(rawSql, args));
		return this;
	}

	/**
	 * Add "DISTINCT" clause to the SQL query statement. Use of this means that the resulting objects may not have a
	 * valid ID column value so cannot be deleted or updated.
	 * 
	 * @return This QueryBuilder object to allow for chaining of calls to set methods.
	 */
	public QueryBuilder distinct() {
		distinct = true;
		selectIdColumn = false;
		return this;
	}

	/**
	 * Limit the output to maxRows maximum number of rows. Set to null for no limit (the default).
	 * 
	 * @param maxRows The maximum number of rows returned by the query.
	 * @return This QueryBuilder object to allow for chaining of calls to set methods.
	 */
	public QueryBuilder limit(Long maxRows) {
		limit = maxRows;
		return this;
	}

	/**
	 * Start the output at this row number. Set to null for no offset (the default). If you are paging you probably want
	 * to specify a {@link #orderBy(String, boolean)}.
	 * 
	 * @param startRow Row number to start the output.
	 * @return This QueryBuilder object to allow for chaining of calls to set methods.
	 */
	public QueryBuilder offset(Long startRow) {
		offset = startRow;
		return this;
	}

	/**
	 * Set whether or not we should only return the count of the results. To get the count-of directly, use
	 * {@link #countOf()}.
	 * 
	 * @param {@code true} for a count-of query, {@code false} otherwise.
	 * 
	 * @return This QueryBuilder object to allow for chaining of calls to set methods.
	 */
	public QueryBuilder setCountOf(boolean countOf) {
		this.isCountOfQuery = countOf;
		return this;
	}

	/**
	 * Makes the query return the largest value of the selected column.
	 * 
	 * @param column The column to find the largest value.
	 * @return This QueryBuilder object to allow for chaining of calls to set methods.
	 */
	public QueryBuilder setMaxColumn(String column) {
		this.isMaxQuery = true;
		this.maxColumn = column;
		return this;
	}

	/**
	 * Add raw SQL "HAVING" clause to the SQL query statement.
	 * 
	 * @param The raw SQL "HAVING" clause. This should not include the "HAVING" string.
	 * @return This QueryBuilder object to allow for chaining of calls to set methods.
	 */
	public QueryBuilder having(String having) {
		this.having = having;
		return this;
	}

	/**
	 * Join with another query builder. This will add into the SQL something close to " INNER JOIN other-table ...".
	 * This allows you to link two tables that share a field of the same type. So even if there is NOT a foreign-object
	 * relationship between the tables, you can JOIN them.
	 * 
	 * <p>
	 * <b>NOTE:</b> This will do combine the WHERE statement of the two query builders with a SQL "AND".
	 * </p>
	 */
	public QueryBuilder join(String localColumnName, String joinedColumnName, QueryBuilder joinedQueryBuilder) {
		addJoinInfo(JoinType.INNER, localColumnName, joinedColumnName, joinedQueryBuilder, JoinWhereOperation.AND);
		return this;
	}

	/**
	 * Similar to {@link #join(String, String, QueryBuilder)} but allows you to specify the join type and the operation
	 * used to combine the WHERE statements.
	 */
	public QueryBuilder join(String localColumnName, String joinedColumnName, QueryBuilder joinedQueryBuilder,
			JoinType type, JoinWhereOperation operation) {
		addJoinInfo(type, localColumnName, joinedColumnName, joinedQueryBuilder, operation);
		return this;
	}

	/**
	 * Query the given statement, returning a {@link Cursor} over the result set.
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
	 * Execute a statement that returns a 1 by 1 table with a numeric value. For example, SELECT COUNT(*) FROM table,
	 * see {@link #countOf()}.
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
	 * Execute a statement that returns a 1 by 1 table with a text value. For example, SELECT COUNT(*) FROM table.
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
	 * Update row(s) for the query. If the content provider supports transactions the update will be atomic.
	 * 
	 * @param values The new field values. The key is the column name for the field. A null value will remove an
	 *        existing field value.
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
	 * Deletes row(s) specified by the query. If the content provider supports transactions, the deletion will be
	 * atomic.
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

	/**
	 * Sets the count-of query flag using {@link #setCountOf(boolean)} to true.
	 * 
	 * @return This QueryBuilder object to allow for chaining of calls to set methods.
	 */
	public QueryBuilder countOf() {
		setCountOf(true);
		return this;
	}

	@Override
	public void clear() {
		super.clear();
		distinct = false;
		selectIdColumn = true;
		if (selectList != null) {
			selectList.clear();
			selectList = null;
		}
		if (orderByList != null) {
			orderByList.clear();
			orderByList = null;
		}
		if (groupByList != null) {
			groupByList.clear();
			groupByList = null;
		}
		isInnerQuery = false;
		isCountOfQuery = false;
		isMaxQuery = false;
		maxColumn = null;
		having = null;
		limit = null;
		offset = null;
		if (joinList != null) {
			joinList.clear();
			joinList = null;
		}
		addTableName = false;
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
			type = StatementType.SELECT_LONG;
			sb.append("COUNT(*) ");
		} else if (isMaxQuery) {
			Database.appendMaxQuery(sb, maxColumn);
		} else {
			appendSelects(sb);
		}
		sb.append("FROM ");
		Database.appendEscapedEntityName(sb, tableName);
		sb.append(' ');
		if (joinList != null) {
			appendJoinSql(sb);
		}
	}

	@Override
	protected boolean appendWhereStatement(StringBuilder sb, List<Object> argList, WhereOperation operation) {
		boolean first = (operation == WhereOperation.FIRST);
		if (this.where != null) {
			first = super.appendWhereStatement(sb, argList, operation);
		}
		if (joinList != null) {
			for (JoinInfo joinInfo : joinList) {
				if (first) {
					operation = WhereOperation.FIRST;
				} else {
					operation = joinInfo.operation.whereOperation;
				}
				first = joinInfo.queryBuilder.appendWhereStatement(sb, argList, operation);
			}
		}
		return first;
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

	private void addOrderBy(OrderBy orderBy) {
		if (orderByList == null) {
			orderByList = new ArrayList<OrderBy>();
		}
		orderByList.add(orderBy);
	}

	private void addGroupBy(ColumnNameOrRawSql groupBy) {
		if (groupByList == null) {
			groupByList = new ArrayList<ColumnNameOrRawSql>();
		}
		groupByList.add(groupBy);
		selectIdColumn = false;
	}

	private void setAddTableName(boolean addTableName) {
		this.addTableName = addTableName;
		if (joinList != null) {
			for (JoinInfo joinInfo : joinList) {
				joinInfo.queryBuilder.setAddTableName(addTableName);
			}
		}
	}

	/**
	 * Add join info to the query. This can be called multiple times to join with more than one table.
	 */
	private void addJoinInfo(JoinType type, String localColumnName, String joinedColumnName,
			QueryBuilder joinedQueryBuilder, JoinWhereOperation operation) {
		JoinInfo joinInfo = new JoinInfo(type, joinedQueryBuilder, operation);
		joinInfo.localField = localColumnName;
		joinInfo.remoteField = joinedColumnName;
		if (joinList == null) {
			joinList = new ArrayList<JoinInfo>();
		}
		joinList.add(joinInfo);
	}

	private void addSelectColumnToList(String columnName) {
		addSelectToList(ColumnNameOrRawSql.withColumnName(columnName));
	}

	private void addSelectToList(ColumnNameOrRawSql select) {
		if (selectList == null) {
			selectList = new ArrayList<ColumnNameOrRawSql>();
		}
		selectList.add(select);
	}

	private void appendJoinSql(StringBuilder sb) {
		for (JoinInfo joinInfo : joinList) {
			sb.append(joinInfo.type.sql).append(" JOIN ");
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

	private void appendSelects(StringBuilder sb) {
		// the default
		type = StatementType.SELECT;

		// if no columns were specified then * is the default
		if (selectList == null) {
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
		for (ColumnNameOrRawSql select : selectList) {
			if (select.getRawSql() != null) {
				// if any are raw-sql then that's our type
				type = StatementType.SELECT_RAW;
				if (first) {
					first = false;
				} else {
					sb.append(", ");
				}
				sb.append(select.getRawSql());
				continue;
			}
			if (first) {
				first = false;
			} else {
				sb.append(',');
			}
			appendColumnName(sb, select.getColumnName());
			if (BaseColumns._ID.equals(select.getColumnName())) {
				hasId = true;
			}
		}

		if (type != StatementType.SELECT_RAW) {
			// we have to add the idField even if it isn't in the columnNameSet
			if (!hasId && selectIdColumn) {
				if (!first) {
					sb.append(',');
				}
				appendColumnName(sb, BaseColumns._ID);
			}
			sb.append(' ');
		}
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
		 * NOTE: this may not be legal and doesn't seem to work with some database types but we'll check this out
		 * anyway.
		 */
		if (joinList != null) {
			for (JoinInfo joinInfo : joinList) {
				if (joinInfo.queryBuilder != null && joinInfo.queryBuilder.hasGroupStuff()) {
					joinInfo.queryBuilder.appendGroupBys(sb, first);
					first = false;
				}
			}
		}
	}

	private boolean hasGroupStuff() {
		return (groupByList != null && !groupByList.isEmpty());
	}

	private void appendGroupBys(StringBuilder sb, boolean first) {
		if (first) {
			sb.append("GROUP BY ");
		}
		for (ColumnNameOrRawSql groupBy : groupByList) {
			if (first) {
				first = false;
			} else {
				sb.append(',');
			}
			if (groupBy.getRawSql() == null) {
				appendColumnName(sb, groupBy.getColumnName());
			} else {
				sb.append(groupBy.getRawSql());
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
		 * NOTE: this may not be necessary since the inner results aren't at all returned but we'll leave this code here
		 * anyway.
		 */
		if (joinList != null) {
			for (JoinInfo joinInfo : joinList) {
				if (joinInfo.queryBuilder != null && joinInfo.queryBuilder.hasOrderStuff()) {
					joinInfo.queryBuilder.appendOrderBys(sb, first, argList);
					first = false;
				}
			}
		}
	}

	private boolean hasOrderStuff() {
		return (orderByList != null && !orderByList.isEmpty());
	}

	private void appendOrderBys(StringBuilder sb, boolean first, List<Object> argList) {
		if (first) {
			sb.append("ORDER BY ");
		}
		for (OrderBy orderBy : orderByList) {
			if (first) {
				first = false;
			} else {
				sb.append(',');
			}
			if (orderBy.getRawSql() == null) {
				appendColumnName(sb, orderBy.getColumnName());
				if (orderBy.isAscending()) {
					// here for documentation purposes, ASC is the default
					// sb.append(" ASC");
				} else {
					sb.append(" DESC");
				}
			} else {
				sb.append(orderBy.getRawSql());
				if (orderBy.getOrderByArgs() != null) {
					for (Object arg : orderBy.getOrderByArgs()) {
						argList.add(arg);
					}
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
		final JoinType type;
		final QueryBuilder queryBuilder;
		String localField;
		String remoteField;
		JoinWhereOperation operation;

		public JoinInfo(JoinType type, QueryBuilder queryBuilder, JoinWhereOperation operation) {
			this.type = type;
			this.queryBuilder = queryBuilder;
			this.operation = operation;
		}
	}

	/**
	 * Internal class used to expose methods to internal classes but through a wrapper instead of a builder.
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

	/**
	 * Type of the JOIN that we are adding.
	 * 
	 * <p>
	 * <b>NOTE:</b> RIGHT and FULL JOIN SQL commands are not supported because we are only returning objects from the
	 * "left" table.
	 */
	public enum JoinType {
		/**
		 * The most common type of join.
		 * "An SQL INNER JOIN return all rows from multiple tables where the join condition is met."
		 * 
		 * <p>
		 * See <a href="http://www.w3schools.com/sql/sql_join.asp" >SQL JOIN</a>
		 * </p>
		 */
		INNER("INNER"),
		/**
		 * "The LEFT JOIN keyword returns all rows from the left table (table1), with the matching rows in the right
		 * table (table2). The result is NULL in the right side when there is no match."
		 * 
		 * <p>
		 * See: <a href="http://www.w3schools.com/sql/sql_join_left.asp" >LEFT JOIN SQL docs</a>
		 * </p>
		 */
		LEFT("LEFT"),
		// end
		;

		private String sql;

		private JoinType(String sql) {
			this.sql = sql;
		}
	}

	/**
	 * When we are combining WHERE statements from the two joined query-builders, this determines the operator to use to
	 * do so.
	 */
	public enum JoinWhereOperation {
		/** combine the two WHERE parts of the JOINed queries with an AND */
		AND(WhereOperation.AND),
		/** combine the two WHERE parts of the JOINed queries with an OR */
		OR(WhereOperation.OR),
		// end
		;

		private WhereOperation whereOperation;

		private JoinWhereOperation(WhereOperation whereOperation) {
			this.whereOperation = whereOperation;
		}
	}

}

package org.imogene.android.database.sqlite.stmt;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Assists in building of SQL statements for a particular table in a particular database.
 * 
 * @author Medes-IMPS
 */
public abstract class StatementBuilder {

	protected final String tableName;
	protected StatementType type;
	protected boolean addTableName;

	protected Where where = null;

	public StatementBuilder(String tableName, StatementType type) {
		this.tableName = tableName;
		this.type = type;
		if (!type.isOkForStatementBuilder()) {
			throw new IllegalStateException("Building a statement from a " + type + " statement is not allowed");
		}
	}

	/**
	 * Returns a {@link Where} object that should be used to add SQL where clauses to the statement. This will also
	 * reset the where object so you can use the same query builder with a different where statement.
	 * 
	 * @return The {@link Where} object.
	 */
	public Where where() {
		where = new Where();
		return where;
	}

	/**
	 * Set the {@link Where} object on the query. This allows someone to use the same Where object on multiple queries.
	 * 
	 * @param where The {@link Where} object on the query.
	 */
	public void setWhere(Where where) {
		this.where = where;
	}

	/**
	 * Build and return a string version of the query. If you change the where or make other calls you will need to
	 * re-call this method to re-prepare the query for execution.
	 * 
	 * @return The string version of the query.
	 */
	public String prepareStatementString() throws SQLException {
		List<Object> argList = new ArrayList<Object>();
		return buildStatementString(argList);
	}

	/**
	 * Clear out all of the statement settings so we can reuse the builder.
	 */
	public void clear() {
		where = null;
	}

	/**
	 * Build and return a string version of the query. If you change the where or make other calls you will need to
	 * re-call this method to re-prepare the query for execution.
	 * 
	 * @param argList The list of arguments. New arguments will be automatically added to this list, it must not be
	 *        {@code null}.
	 * @return The string version version of this query.
	 */
	public String buildStatementString(List<Object> argList) {
		StringBuilder sb = new StringBuilder(128);
		appendStatementString(sb, argList);
		String statement = sb.toString();
		return statement;
	}

	/**
	 * Internal method to build a query while tracking various arguments. Users should use the
	 * {@link #prepareStatementString()} method instead. This needs to be protected because of InternalQueryBuilder.
	 * 
	 * @param sb The StringBuilder to append the statement to.
	 * @param argList The list of arguments. New arguments will be automatically added to this list, it must not be
	 *        {@code null}.
	 */
	protected void appendStatementString(StringBuilder sb, List<Object> argList) {
		appendStatementStart(sb, argList);
		appendWhereStatement(sb, argList, WhereOperation.FIRST);
		appendStatementEnd(sb, argList);
	}

	/**
	 * Append the start of our statement string to the StringBuilder.
	 * 
	 * @param sb The StringBuilder to add the statement string to.
	 * @param argList The list of arguments. New arguments will be automatically added to this list, it must not be
	 *        {@code null}.
	 */
	protected abstract void appendStatementStart(StringBuilder sb, List<Object> argList);

	/**
	 * Append the WHERE part of the statement to the StringBuilder.
	 * 
	 * @param sb The StringBuilder to add the where statement string to.
	 * @param argList The list of arguments. New arguments will be automatically added to this list, it must not be
	 *        {@code null}.
	 * @param operation defines which type of where operation we are appending.
	 * @return {@code true} if a {@link WhereOperation#FIRST} operation was appended, {@code false} otherwise.
	 */
	protected boolean appendWhereStatement(StringBuilder sb, List<Object> argList, WhereOperation operation) {
		if (where == null) {
			return operation == WhereOperation.FIRST;
		}
		operation.appendBefore(sb);
		where.appendSql((addTableName ? tableName : null), sb, argList);
		operation.appendAfter(sb);
		return false;
	}

	/**
	 * Append the end of our statement string to the StringBuilder.
	 * 
	 * @param sb The StringBuilder to add the end statement string to.
	 * @param argList The list of arguments. New arguments will be automatically added to this list, it must not be
	 *        {@code null}.
	 */
	protected abstract void appendStatementEnd(StringBuilder sb, List<Object> argList);

	/**
	 * Return if we need to prepend table-name to columns.
	 * 
	 * @return {@code true} if we nedd to prepend table-name, {@code false} otherwise.
	 */
	protected boolean shouldPrependTableNameToColumns() {
		return false;
	}

	/**
	 * Return the type of the statement.
	 */
	StatementType getType() {
		return type;
	}

	/**
	 * Types of statements that we are building.
	 */
	public static enum StatementType {
		/** SQL statement in the form of SELECT ... */
		SELECT(true, true, false, false),
		/** SQL statement in the form of SELECT COUNT(*)... or something */
		SELECT_LONG(true, true, false, false),
		/** SQL statement in the form of SELECT... with aggregate functions or something */
		SELECT_RAW(true, true, false, false),
		/** SQL statement in the form of UPDATE ... */
		UPDATE(true, false, true, false),
		/** SQL statement in the form of DELETE ... */
		DELETE(true, false, true, false),
		/** SQL statement in the form of CREATE TABLE, ALTER TABLE, or something returning the number of rows affected */
		EXECUTE(false, false, false, true),
		// end
		;

		private final boolean okForStatementBuilder;
		private final boolean okForQuery;
		private final boolean okForUpdate;
		private final boolean okForExecute;

		private StatementType(boolean okForStatementBuilder, boolean okForQuery, boolean okForUpdate,
				boolean okForExecute) {
			this.okForStatementBuilder = okForStatementBuilder;
			this.okForQuery = okForQuery;
			this.okForUpdate = okForUpdate;
			this.okForExecute = okForExecute;
		}

		public boolean isOkForStatementBuilder() {
			return okForStatementBuilder;
		}

		public boolean isOkForQuery() {
			return okForQuery;
		}

		public boolean isOkForUpdate() {
			return okForUpdate;
		}

		public boolean isOkForExecute() {
			return okForExecute;
		}
	}

	/**
	 * Enum which defines which type of where operation we are appending.
	 */
	protected enum WhereOperation {
		FIRST("WHERE ", null), AND("AND (", ") "), OR("OR (", ") "),
		// end
		;

		private final String before;
		private final String after;

		private WhereOperation(String before, String after) {
			this.before = before;
			this.after = after;
		}

		/**
		 * Append the necessary operators before the where statement.
		 */
		public void appendBefore(StringBuilder sb) {
			if (before != null) {
				sb.append(before);
			}
		}

		/**
		 * Append the necessary operators after the where statement.
		 */
		public void appendAfter(StringBuilder sb) {
			if (after != null) {
				sb.append(after);
			}
		}
	}

}

package org.imogene.android.database.sqlite.stmt;

import java.util.Arrays;
import java.util.List;

import org.imogene.android.database.sqlite.stmt.QueryBuilder.InternalQueryBuilderWrapper;
import org.imogene.android.database.sqlite.stmt.query.Between;
import org.imogene.android.database.sqlite.stmt.query.Clause;
import org.imogene.android.database.sqlite.stmt.query.Exists;
import org.imogene.android.database.sqlite.stmt.query.In;
import org.imogene.android.database.sqlite.stmt.query.InSubQuery;
import org.imogene.android.database.sqlite.stmt.query.IsNotNull;
import org.imogene.android.database.sqlite.stmt.query.IsNull;
import org.imogene.android.database.sqlite.stmt.query.ManyClause;
import org.imogene.android.database.sqlite.stmt.query.NeedsFutureClause;
import org.imogene.android.database.sqlite.stmt.query.Not;
import org.imogene.android.database.sqlite.stmt.query.Raw;
import org.imogene.android.database.sqlite.stmt.query.SimpleComparison;

import android.provider.BaseColumns;

/**
 * Manages the various clauses that make up the WHERE part of a SQL statement.
 * You get one of these when you call {@link StatementBuilder#where} or you can
 * set the where clause by calling {@link StatementBuilder#setWhere}.
 * 
 * @author Medes-IMPS
 */
public class Where implements Clause {

	private static final int START_CLAUSE_SIZE = 4;

	private Clause[] clauseStack = new Clause[START_CLAUSE_SIZE];
	private int clauseStackLevel = 0;
	private NeedsFutureClause needsFuture = null;

	/**
	 * AND operation which takes the previous clause and the next clause and
	 * AND's them together.
	 * 
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where and() {
		addNeedsFuture(new ManyClause(pop("AND"), ManyClause.AND_OPERATION));
		return this;
	}

	/**
	 * AND operation which takes 2 (or more) arguments and AND's them together.
	 * There is no guarantee of the order of the clauses that are generated in
	 * the final query.
	 * 
	 * @param first The first Where clause.
	 * @param second The second Where clause.
	 * @param others Others Where clauses.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where and(Where first, Where second, Where... others) {
		Clause[] clauses = buildClauseArray(others, "AND");
		Clause secondClause = pop("AND");
		Clause firstClause = pop("AND");
		addClause(new ManyClause(firstClause, secondClause, clauses, ManyClause.AND_OPERATION));
		return this;
	}

	/**
	 * This method needs to be used carefully. This will absorb a number of
	 * clauses that were registered previously with calls to
	 * {@link Where#eq(String, Object)} or other methods and will string them
	 * together with AND's. There is no way to verify the number of previous
	 * clauses so the programmer has to count precisely. There is no guarantee
	 * of the order of the clauses that are generated in the final query.
	 * 
	 * @param numClauses The number of clauses to absorb.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where and(int numClauses) {
		if (numClauses == 0) {
			throw new IllegalArgumentException("Must have at least one clause in and(numClauses)");
		}
		Clause[] clauses = new Clause[numClauses];
		for (int i = numClauses - 1; i >= 0; i--) {
			clauses[i] = pop("AND");
		}
		addClause(new ManyClause(clauses, ManyClause.AND_OPERATION));
		return this;
	}

	/**
	 * Add a BETWEEN clause so the column must be between the low and high
	 * parameters.
	 * 
	 * @param columnName Column to apply the BETWEEN clause.
	 * @param low The lower value.
	 * @param high The higher value.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where between(String columnName, Object low, Object high) {
		addClause(new Between(columnName, low, high));
		return this;
	}

	/**
	 * Add a '=' clause so the column must be equal to the value.
	 * 
	 * @param columnName The column to apply the clause.
	 * @param value The comparison value.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where eq(String columnName, Object value) {
		addClause(new SimpleComparison(columnName, value, SimpleComparison.EQUAL_TO_OPERATION));
		return this;
	}

	/**
	 * Add a '&gt;=' clause so the column must be greater-than or equals-to the
	 * value.
	 * 
	 * @param columnName The column to apply the clause.
	 * @param value The comparison value.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where ge(String columnName, Object value) {
		addClause(new SimpleComparison(columnName, value, SimpleComparison.GREATER_THAN_EQUAL_TO_OPERATION));
		return this;
	}

	/**
	 * Add a '&gt;' clause so the column must be greater-than the value.
	 * 
	 * @param columnName The column to apply the clause.
	 * @param value The comparison value.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where gt(String columnName, Object value) {
		addClause(new SimpleComparison(columnName, value, SimpleComparison.GREATER_THAN_OPERATION));
		return this;
	}

	/**
	 * Add a IN clause so the column must be equal-to one of the objects from
	 * the list passed in.
	 * 
	 * @param columnName The column to apply the clause.
	 * @param objects The list of values to compare with.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where in(String columnName, Iterable<?> objects) {
		addClause(new In(columnName, objects, true));
		return this;
	}

	/**
	 * Same as {@link #in(String, Iterable)} except with a NOT IN clause.
	 * 
	 * @param columnName The column to apply the clause.
	 * @param objects The list of values to compare with.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where notIn(String columnName, Iterable<?> objects) {
		addClause(new In(columnName, objects, false));
		return this;
	}

	/**
	 * Add a IN clause so the column must be equal-to one of the objects passed
	 * in.
	 * 
	 * @param columnName The column to apply the clause.
	 * @param objects The list of values to compare with.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where in(String columnName, Object... objects) {
		return in(true, columnName, objects);
	}

	/**
	 * Same as {@link #in(String, Object...)} except with a NOT IN clause.
	 * 
	 * @param columnName The column to apply the clause.
	 * @param objects The list of values to compare with.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where notIn(String columnName, Object... objects) {
		return in(false, columnName, objects);
	}

	/**
	 * Add a IN clause which makes sure the column is in one of the columns
	 * returned from a sub-query inside of parenthesis. The QueryBuilder must
	 * return 1 and only one column which can be set with the
	 * {@link QueryBuilder#selectColumns(String...)} method calls. That 1
	 * argument must match the SQL type of the column-name passed to this
	 * method. The sub-query will be prepared at the same time that the outside
	 * query is.
	 * 
	 * @param columnName The column to apply the clause.
	 * @param subQueryBuilder The sub-query which results will be used for
	 *            comparison.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where in(String columnName, QueryBuilder subQueryBuilder) {
		return in(true, columnName, subQueryBuilder);
	}

	/**
	 * Same as {@link #in(String, QueryBuilder)} except with a NOT IN clause.
	 * 
	 * @param columnName The column to apply the clause.
	 * @param subQueryBuilder The sub-query which results will be used for
	 *            comparison.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where notIn(String columnName, QueryBuilder subQueryBuilder) {
		return in(false, columnName, subQueryBuilder);
	}

	/**
	 * Add a EXISTS clause with a sub-query inside of parenthesis. The sub-query
	 * will be prepared at the same time that the outside query is.
	 * 
	 * @param subQueryBuilder The sub-query which results will be used for
	 *            determining existence.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where exists(QueryBuilder subQueryBuilder) {
		// we do this to turn off the automatic addition of the ID column in the
		// select column list
		subQueryBuilder.enableInnerQuery();
		addClause(new Exists(new InternalQueryBuilderWrapper(subQueryBuilder)));
		return this;
	}

	/**
	 * Add a 'IS NULL' clause so the column must be null. '=' NULL does not
	 * work.
	 * 
	 * @param columnName The column name which value must be tested.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where isNull(String columnName) {
		addClause(new IsNull(columnName));
		return this;
	}

	/**
	 * Add a 'IS NOT NULL' clause so the column must not be null. '<>' NULL does
	 * not work.
	 * 
	 * @param columnName The column name which value must be tested.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where isNotNull(String columnName) {
		addClause(new IsNotNull(columnName));
		return this;
	}

	/**
	 * Add a '&lt;=' clause so the column must be less-than or equals-to the
	 * value.
	 * 
	 * @param columnName The column to apply the clause.
	 * @param value The comparison value.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where le(String columnName, Object value) {
		addClause(new SimpleComparison(columnName, value, SimpleComparison.LESS_THAN_EQUAL_TO_OPERATION));
		return this;
	}

	/**
	 * Add a '&lt;' clause so the column must be less-than the value.
	 * 
	 * @param columnName The column to apply the clause.
	 * @param value The comparison value.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where lt(String columnName, Object value) {
		addClause(new SimpleComparison(columnName, value, SimpleComparison.LESS_THAN_OPERATION));
		return this;
	}

	/**
	 * Add a LIKE clause so the column must match the value using '%' patterns.
	 * 
	 * @param columnName The column to apply the clause.
	 * @param value The comparison value.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where like(String columnName, Object value) {
		addClause(new SimpleComparison(columnName, value, SimpleComparison.LIKE_OPERATION));
		return this;
	}

	/**
	 * Add a '&lt;&gt;' clause so the column must be not-equal-to the value.
	 * 
	 * @param columnName The column to apply the clause.
	 * @param value The comparison value.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where ne(String columnName, Object value) {
		addClause(new SimpleComparison(columnName, value, SimpleComparison.NOT_EQUAL_TO_OPERATION));
		return this;
	}

	/**
	 * Used to NOT the next clause specified.
	 * 
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where not() {
		addNeedsFuture(new Not());
		return this;
	}

	/**
	 * Used to NOT the argument clause specified.
	 * 
	 * @param comparison The clause to NOT.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where not(Where comparison) {
		addClause(new Not(pop("NOT")));
		return this;
	}

	/**
	 * OR operation which takes the previous clause and the next clause and OR's
	 * them together.
	 * 
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where or() {
		addNeedsFuture(new ManyClause(pop("OR"), ManyClause.OR_OPERATION));
		return this;
	}

	/**
	 * OR operation which takes 2 arguments and OR's them together. There is no
	 * guarantee of the order of the clauses that are generated in the final
	 * query.
	 * 
	 * @param first The first Where clause.
	 * @param second The second Where clause.
	 * @param others Others Where clauses.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where or(Where left, Where right, Where... others) {
		Clause[] clauses = buildClauseArray(others, "OR");
		Clause secondClause = pop("OR");
		Clause firstClause = pop("OR");
		addClause(new ManyClause(firstClause, secondClause, clauses, ManyClause.OR_OPERATION));
		return this;
	}

	/**
	 * This method needs to be used carefully. This will absorb a number of
	 * clauses that were registered previously with calls to
	 * {@link Where#eq(String, Object)} or other methods and will string them
	 * together with OR's. There is no way to verify the number of previous
	 * clauses so the programmer has to count precisely. There is no guarantee
	 * of the order of the clauses that are generated in the final query.
	 * 
	 * @param numClauses The number of clauses to absorb.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where or(int numClauses) {
		if (numClauses == 0) {
			throw new IllegalArgumentException("Must have at least one clause in or(numClauses)");
		}
		Clause[] clauses = new Clause[numClauses];
		for (int i = numClauses - 1; i >= 0; i--) {
			clauses[i] = pop("OR");
		}
		addClause(new ManyClause(clauses, ManyClause.OR_OPERATION));
		return this;
	}

	/**
	 * Add a clause where the ID is equal to the argument.
	 * 
	 * @param id The value of the ID to test.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where idEq(Object id) {
		addClause(new SimpleComparison(BaseColumns._ID, id, SimpleComparison.EQUAL_TO_OPERATION));
		return this;
	}

	/**
	 * Add a raw statement as part of the where that can be anything that the
	 * database supports. Using more structured methods is recommended but this
	 * gives more control over the query and allows you to utilize database
	 * specific features.
	 * 
	 * @param rawStatement The statement that we should insert into the WHERE.
	 * @param args Optional arguments that correspond to any ? specified in the
	 *            rawStatement. Each of the arguments must have either the
	 *            corresponding columnName or the sql-type set.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where raw(String rawStatement, Object... args) {
		addClause(new Raw(rawStatement, args));
		return this;
	}

	/**
	 * Make a comparison where the operator is specified by the caller. It is up
	 * to the caller to specify an appropriate operator for the database and
	 * that it be formatted correctly.
	 * 
	 * @param columnName The column name to apply the clause.
	 * @param rawOperator The operator for comparison.
	 * @param value The value to compare the column with.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where rawComparison(String columnName, String rawOperator, Object value) {
		addClause(new SimpleComparison(columnName, value, rawOperator));
		return this;
	}

	/**
	 * Add a clause to this clause.
	 * @param clause The Clause to add.
	 * @return This Where object to allow for chaining of calls to set methods.
	 */
	public Where clause(Clause clause) {
		addClause(clause);
		return this;
	}

	private Where in(boolean in, String columnName, Object... objects) {
		if (objects.length == 1) {
			if (objects[0].getClass().isArray()) {
				throw new IllegalArgumentException("Object argument to " + (in ? "IN" : "notId")
						+ " seems to be an array within an array");
			}
			if (objects[0] instanceof Where) {
				throw new IllegalArgumentException("Object argument to " + (in ? "IN" : "notId")
						+ " seems to be a Where object, did you mean the QueryBuilder?");
			}
		}
		addClause(new In(columnName, objects, in));
		return this;
	}

	private Where in(boolean in, String columnName, QueryBuilder subQueryBuilder) {
		if (subQueryBuilder.getSelectColumnCount() != 1) {
			throw new IllegalArgumentException("Inner query must have only 1 select column specified instead of "
					+ subQueryBuilder.getSelectColumnCount() + ": "
					+ Arrays.toString(subQueryBuilder.getSelectColumns().toArray(new String[0])));
		}
		// we do this to turn off the automatic addition of the ID column in the
		// select column list
		subQueryBuilder.enableInnerQuery();
		addClause(new InSubQuery(columnName, new InternalQueryBuilderWrapper(subQueryBuilder), in));
		return this;
	}

	@Override
	public void appendSql(String tableName, StringBuilder sb, List<Object> columnArgList) {
		if (clauseStackLevel == 0) {
			throw new IllegalStateException("No where clauses defined.  Did you miss a where operation?");
		}
		if (clauseStackLevel != 1) {
			throw new IllegalStateException(
					"Both the \"left-hand\" and \"right-hand\" clauses have been defined.  Did you miss an AND or OR?");
		}

		// we don't pop here because we may want to run the query multiple times
		peek().appendSql(tableName, sb, columnArgList);
	}

	private Clause[] buildClauseArray(Where[] others, String label) {
		Clause[] clauses;
		if (others.length == 0) {
			clauses = null;
		} else {
			clauses = new Clause[others.length];
			// fill in reverse order
			for (int i = others.length - 1; i >= 0; i--) {
				clauses[i] = pop(label);
			}
		}
		return clauses;
	}

	private void addNeedsFuture(NeedsFutureClause clause) {
		if (needsFuture != null) {
			throw new IllegalStateException(needsFuture + " is already waiting for a future clause, can't add: " + clause);
		}
		needsFuture = clause;
		push(clause);
	}

	private void addClause(Clause clause) {
		if (needsFuture == null) {
			push(clause);
		} else {
			// we have a binary statement which was called before the right
			// clause was defined
			needsFuture.setMissingClause(clause);
			needsFuture = null;
		}
	}

	private void push(Clause clause) {
		// if the stack is full then we need to grow it
		if (clauseStackLevel == clauseStack.length) {
			// double its size each time
			Clause[] newStack = new Clause[clauseStackLevel * 2];
			// copy the entries over to the new stack
			for (int i = 0; i < clauseStackLevel; i++) {
				newStack[i] = clauseStack[i];
				// to help gc
				clauseStack[i] = null;
			}
			clauseStack = newStack;
		}
		clauseStack[clauseStackLevel++] = clause;
	}

	private Clause pop(String label) {
		if (clauseStackLevel == 0) {
			throw new IllegalStateException("Expecting there to be a clause already defined for '" + label + "' operation");
		}
		Clause clause = clauseStack[--clauseStackLevel];
		// to help gc
		clauseStack[clauseStackLevel] = null;
		return clause;
	}

	private Clause peek() {
		return clauseStack[clauseStackLevel - 1];
	}

}

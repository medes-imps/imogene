package org.imogene.android.database.sqlite.stmt.query;

import java.util.List;

/**
 * Internal class handling the SQL 'NOT' boolean comparison operation. Used by
 * {@link Where#not}.
 * 
 * @author Medes-IMPS
 */
public class Not implements Clause, NeedsFutureClause {

	private Comparison comparison = null;
	private Exists exists = null;

	/**
	 * In this case we will consume a future clause.
	 */
	public Not() {
	}

	/**
	 * Create a Not from a {@link Clause}.
	 * 
	 * @param clause The clause to NOT.
	 * 
	 * @throws IllegalArgumentException If the {@link Clause} is not a
	 *             {@link Comparison}.
	 */
	public Not(Clause clause) {
		setMissingClause(clause);
	}

	@Override
	public void setMissingClause(Clause clause) {
		if (this.comparison != null) {
			throw new IllegalArgumentException("NOT operation already has a comparison set");
		} else if (clause instanceof Comparison) {
			this.comparison = (Comparison) clause;
		} else if (clause instanceof Exists) {
			this.exists = (Exists) clause;
		} else {
			throw new IllegalArgumentException("NOT operation can only work with comparison SQL clauses, not " + clause);
		}
	}

	@Override
	public void appendSql(String tableName, StringBuilder sb, List<Object> selectArgList) {
		if (comparison == null && exists == null) {
			throw new IllegalStateException("Clause has not been set in NOT operation");
		}
		// this generates: (NOT 'x' = 123 )
		if (comparison == null) {
			sb.append("(NOT ");
			exists.appendSql(tableName, sb, selectArgList);
		} else {
			sb.append("(NOT ");
			if (tableName != null) {
				Database.appendEscapedEntityName(sb, tableName);
				sb.append('.');
			}
			Database.appendEscapedEntityName(sb, comparison.getColumnName());
			sb.append(' ');
			comparison.appendOperation(sb);
			comparison.appendValue(sb, selectArgList);
		}
		sb.append(") ");
	}

	@Override
	public String toString() {
		if (comparison == null) {
			return "NOT without comparison";
		} else {
			return "NOT comparison " + comparison;
		}
	}
}

package org.imogene.android.database.sqlite.stmt.query;

import java.util.List;

import org.imogene.android.database.sqlite.stmt.QueryBuilder.InternalQueryBuilderWrapper;
import org.imogene.android.database.sqlite.stmt.Where;

/**
 * Internal class handling the SQL 'in' query part. Used by {@link Where#in}.
 * 
 * @author Medes-IMPS
 */
public class InSubQuery extends BaseComparison {

	private final InternalQueryBuilderWrapper subQueryBuilder;
	private final boolean in;

	public InSubQuery(String columnName, InternalQueryBuilderWrapper subQueryBuilder, boolean in) {
		super(columnName, null, true);
		this.subQueryBuilder = subQueryBuilder;
		this.in = in;
	}

	@Override
	public void appendOperation(StringBuilder sb) {
		if (in) {
			sb.append("IN ");
		} else {
			sb.append("NOT IN ");
		}
	}

	@Override
	public void appendValue(StringBuilder sb, List<Object> argList) {
		sb.append('(');
		subQueryBuilder.appendStatementString(sb, argList);
		sb.append(") ");
	}
}

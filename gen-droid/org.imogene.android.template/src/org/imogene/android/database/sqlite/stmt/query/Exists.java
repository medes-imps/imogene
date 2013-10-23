package org.imogene.android.database.sqlite.stmt.query;

import java.util.List;

import org.imogene.android.database.sqlite.stmt.QueryBuilder.InternalQueryBuilderWrapper;
import org.imogene.android.database.sqlite.stmt.Where;

/**
 * Internal class handling the SQL 'EXISTS' query part. Used by
 * {@link Where#exists}.
 * 
 * @author Medes-IMPS
 */
public class Exists implements Clause {

	private final InternalQueryBuilderWrapper subQueryBuilder;

	public Exists(InternalQueryBuilderWrapper subQueryBuilder) {
		this.subQueryBuilder = subQueryBuilder;
	}

	@Override
	public void appendSql(String tableName, StringBuilder sb, List<Object> argList) {
		sb.append("EXISTS (");
		subQueryBuilder.appendStatementString(sb, argList);
		sb.append(") ");
	}
}

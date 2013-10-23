package org.imogene.android.database.sqlite.stmt.query;

import java.util.List;

import org.imogene.android.database.sqlite.stmt.Where;
/**
 * Internal class handling the SQL 'between' query part. Used by {@link Where#between}.
 * 
 * @author Medes-IMPS
 */

public class Between extends BaseComparison {

	private Object low;
	private Object high;

	public Between(String columnName, Object low, Object high) {
		super(columnName, null, true);
		this.low = low;
		this.high = high;
	}

	@Override
	public void appendOperation(StringBuilder sb) {
		sb.append("BETWEEN ");
	}

	@Override
	public void appendValue(StringBuilder sb, List<Object> argList) {
		if (low == null) {
			throw new IllegalArgumentException("BETWEEN low value for '" + columnName + "' is null");
		}
		if (high == null) {
			throw new IllegalArgumentException("BETWEEN high value for '" + columnName + "' is null");
		}
		appendArgOrValue(sb, argList, low);
		sb.append("AND ");
		appendArgOrValue(sb, argList, high);
	}
}

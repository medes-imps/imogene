package org.imogene.android.database.sqlite.stmt.query;

import java.util.List;

import org.imogene.android.database.sqlite.stmt.Where;

/**
 * Internal class handling the SQL 'IS NOT NULL' comparison query part. Used by
 * {@link Where#isNotNull}.
 * 
 * @author Medes-IMPS
 */
public class IsNotNull extends BaseComparison {

	public IsNotNull(String columnName) {
		super(columnName, null, true);
	}

	@Override
	public void appendOperation(StringBuilder sb) {
		sb.append("IS NOT NULL ");
	}

	@Override
	public void appendValue(StringBuilder sb, List<Object> argList) {
		// there is no value
	}
}

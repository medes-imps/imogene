package org.imogene.android.database.sqlite.stmt.query;

import java.util.Arrays;
import java.util.List;

import org.imogene.android.database.sqlite.stmt.Where;

/**
 * Internal class handling the SQL 'in' query part. Used by {@link Where#in}.
 * 
 * @author Medes-IMPS
 */
public class In extends BaseComparison {

	private Iterable<?> objects;
	private final boolean in;

	public In(String columnName, Iterable<?> objects, boolean in) {
		super(columnName, null, true);
		this.objects = objects;
		this.in = in;
	}

	public In(String columnName, Object[] objects, boolean in) {
		super(columnName, null, true);
		// grrrr, Object[] should be Iterable
		this.objects = Arrays.asList(objects);
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
	public void appendValue(StringBuilder sb, List<Object> columnArgList) {
		sb.append('(');
		boolean first = true;
		for (Object value : objects) {
			if (first) {
				first = false;
			} else {
				sb.append(',');
			}
			// for each of our arguments, add it to the output
			super.appendArgOrValue(sb, columnArgList, value);
		}
		sb.append(") ");
	}
}

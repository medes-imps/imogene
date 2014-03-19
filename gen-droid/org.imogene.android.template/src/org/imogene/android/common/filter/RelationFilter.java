package org.imogene.android.common.filter;

import org.imogene.android.database.sqlite.ClientFilterCursor;

import android.text.TextUtils;

public class RelationFilter extends ClientFilter {

	public static final Creator<RelationFilter> FILTER_CREATOR = new DefaultCreator<RelationFilter>() {

		@Override
		protected RelationFilter newFilter() {
			return new RelationFilter();
		}

		@Override
		protected RelationFilter newFilter(ClientFilterCursor c) {
			return new RelationFilter(c);
		}
	};

	private RelationFilter(ClientFilterCursor c) {
		super(c);
	}

	private RelationFilter() {
		super();
	}

	@Override
	protected void preCommit() {
		super.preCommit();
		setOperator(TextUtils.isEmpty(getFieldValue()) ? OPERATOR_UNDEF : RELATIONFIELD_OPERATOR_EQUAL);
	}

}

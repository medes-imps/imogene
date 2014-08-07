package org.imogene.android.widget.field.view;

import java.util.List;

import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.template.R;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import fr.medes.android.util.content.ContentUrisUtils;

public class NestedRowFieldView<T extends ImogBean> extends RelationManyFieldView<T> {

	private final ViewGroup mEntries;

	public NestedRowFieldView(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.imog__field_view_nested_row);

		mEntries = (ViewGroup) findViewById(R.id.imog__nested_rows);

		setOnClickListener(null);
	}

	@Override
	public String getFieldDisplay() {
		return null;
	}

	@Override
	protected void updateView() {
		super.updateView();
		List<T> value = getValue();
		mEntries.removeAllViews();
		if (value == null) {
			return;
		}
		for (T bean : value) {
			ViewGroup entry = (ViewGroup) inflate(mEntries.getContext(), R.layout.imog__entity_row, null);

			((TextView) entry.findViewById(android.R.id.text1)).setText(bean.getMainDisplay(getContext()));
			((TextView) entry.findViewById(android.R.id.text2)).setText(bean.getSecondaryDisplay(getContext()));

			entry.setTag(bean.getId());
			entry.findViewById(android.R.id.background).setBackgroundDrawable(drawable);
			entry.setOnClickListener(mOnClickViewListener);

			mEntries.addView(entry);
		}
	}

	private final OnClickListener mOnClickViewListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			startActivity(new Intent(Intent.ACTION_VIEW, ContentUrisUtils.withAppendedId(contentUri,
					(String) v.getTag())));
		}
	};

}

package org.imogene.android.widget.field.view;

import java.util.List;

import org.imogene.android.database.ImogBeanCursor;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.template.R;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NestedRowFieldView extends RelationManyFieldView {

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
	protected void onValueChange() {
		super.onValueChange();
		List<Uri> uris = getValue();
		mEntries.removeAllViews();
		if (uris == null) {
			return;
		}
		for (Uri uri : uris) {
			ViewGroup entry = (ViewGroup) inflate(mEntries.getContext(), R.layout.imog__entity_row, null);

			ImogBeanCursor cursor = (ImogBeanCursor) ImogOpenHelper.getHelper().query(uri);
			if (cursor.moveToFirst()) {
				((TextView) entry.findViewById(android.R.id.text1)).setText(cursor.getMainDisplay(getContext()));
				((TextView) entry.findViewById(android.R.id.text2)).setText(cursor.getSecondaryDisplay(getContext()));
			}
			cursor.close();

			entry.setTag(uri);
			entry.findViewById(android.R.id.background).setBackgroundDrawable(drawable);
			entry.setOnClickListener(mOnClickViewListener);

			mEntries.addView(entry);
		}
	}

	private final OnClickListener mOnClickViewListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			startActivity(new Intent(Intent.ACTION_VIEW, (Uri) v.getTag()));
		}
	};

}

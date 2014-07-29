package org.imogene.android.widget.field.edit;

import java.util.ArrayList;
import java.util.List;

import org.imogene.android.Constants.Categories;
import org.imogene.android.Constants.Extras;
import org.imogene.android.database.ImogBeanCursor;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.preference.Preferences;
import org.imogene.android.template.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class NestedRowFieldEdit extends RelationManyFieldEdit {

	private final ViewGroup mEntries;

	public NestedRowFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.imog__field_edit_nested_row);

		mEntries = (ViewGroup) findViewById(R.id.imog__nested_rows);

		setOnClickListener(null);

		findViewById(R.id.imog__nested_add_row).setOnClickListener(this);
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
			entry.setTag(uri);
			mEntries.addView(entry);

			ImogBeanCursor cursor = (ImogBeanCursor) ImogOpenHelper.getHelper().query(uri);
			if (cursor.moveToFirst()) {
				((TextView) entry.findViewById(android.R.id.text1)).setText(cursor.getMainDisplay(getContext()));
				((TextView) entry.findViewById(android.R.id.text2)).setText(cursor.getSecondaryDisplay(getContext()));
			}
			cursor.close();

			entry.findViewById(android.R.id.background).setBackgroundDrawable(mDrawable);

			final ImageView deleteIcon = (ImageView) entry.findViewById(android.R.id.icon);
			deleteIcon.setImageResource(R.drawable.imog__ic_minus);
			deleteIcon.setTag(uri);
			deleteIcon.setOnClickListener(mOnClickDeleteListener);

			entry.setOnClickListener(mOnClickViewListener);
		}
	}

	@Override
	protected void dispatchClick(View v) {
		boolean wizard = Preferences.getPreferences(getContext()).isWizardEnabled();
		Intent intent = new Intent(Intent.ACTION_INSERT, mContentUri);
		intent.putExtra(Extras.EXTRA_ENTITY, createBundle());
		intent.addCategory(wizard ? Categories.CATEGORY_WIZARD : Categories.CATEGORY_CLASSIC);
		startActivityForResult(intent, mRequestCode);
	}

	@Override
	public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == mRequestCode && resultCode != Activity.RESULT_CANCELED) {
			Uri uri = data.getData();
			List<Uri> values = getValue();
			if (values == null) {
				values = new ArrayList<Uri>();
			}
			if (!values.contains(uri)) {
				values.add(uri);
			}
			setValue(values);
			return true;
		}
		return false;
	}

	private final OnClickListener mOnClickDeleteListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Uri uri = (Uri) v.getTag();
			List<Uri> uris = getValue();
			if (uri != null) {
				if (uris.remove(uri)) {
					setValue(uris);
				}
			}
		}
	};

	private final OnClickListener mOnClickViewListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			startActivityForResult(new Intent(Intent.ACTION_EDIT, (Uri) v.getTag()), mRequestCode);
		}
	};

}

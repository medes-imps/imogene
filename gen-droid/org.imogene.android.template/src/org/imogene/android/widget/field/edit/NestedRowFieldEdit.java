package org.imogene.android.widget.field.edit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.imogene.android.Constants.Categories;
import org.imogene.android.Constants.Extras;
import org.imogene.android.common.entity.ImogBean;
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
import fr.medes.android.util.content.ContentUrisUtils;

public class NestedRowFieldEdit<T extends ImogBean> extends RelationManyFieldEdit<T> {

	private final ViewGroup mEntries;

	public NestedRowFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.imog__field_edit_nested_row);

		mEntries = (ViewGroup) findViewById(R.id.imog__nested_rows);

		setOnClickListener(null);
		setOnLongClickListener(null);

		findViewById(R.id.imog__nested_add_row).setOnClickListener(this);
	}

	@Override
	public String getFieldDisplay() {
		return null;
	}

	@Override
	protected void updateView() {
		super.updateView();
		mEntries.removeAllViews();
		List<T> value = getValue();
		if (value == null) {
			return;
		}
		for (T bean : value) {
			ViewGroup entry = (ViewGroup) inflate(mEntries.getContext(), R.layout.imog__entity_row, null);
			entry.setTag(bean.getId());
			mEntries.addView(entry);

			((TextView) entry.findViewById(android.R.id.text1)).setText(bean.getMainDisplay(getContext()));
			((TextView) entry.findViewById(android.R.id.text2)).setText(bean.getSecondaryDisplay(getContext()));

			entry.findViewById(android.R.id.background).setBackgroundDrawable(mDrawable);

			final ImageView deleteIcon = (ImageView) entry.findViewById(android.R.id.icon);
			deleteIcon.setImageResource(R.drawable.imog__ic_action_remove);
			deleteIcon.setTag(bean.getId());
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
		onPrepareIntent(intent);
		startActivityForResult(intent, mRequestCode);
	}

	@Override
	public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == mRequestCode && resultCode != Activity.RESULT_CANCELED) {
			Uri uri = data.getData();
			T value = ImogOpenHelper.fromUri(uri);
			if (value == null) {
				return true;
			}
			List<T> values = getValue();
			if (values == null) {
				values = new ArrayList<T>();
			}
			for (Iterator<T> it = values.iterator(); it.hasNext();) {
				T bean = it.next();
				if (bean.getId().equals(value.getId())) {
					it.remove();
				}
			}
			values.add(value);
			setValueInternal(values, true);
			return true;
		}
		return false;
	}

	private final OnClickListener mOnClickDeleteListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String id = (String) v.getTag();
			List<T> beans = getValue();
			if (beans != null) {
				for (Iterator<T> it = beans.iterator(); it.hasNext();) {
					T bean = it.next();
					if (bean.getId().equals(id)) {
						it.remove();
					}
				}
				setValueInternal(beans, true);
			}
		}
	};

	private final OnClickListener mOnClickViewListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Uri uri = ContentUrisUtils.withAppendedId(mContentUri, (String) v.getTag());
			startActivityForResult(new Intent(Intent.ACTION_EDIT, uri), mRequestCode);
		}
	};

}

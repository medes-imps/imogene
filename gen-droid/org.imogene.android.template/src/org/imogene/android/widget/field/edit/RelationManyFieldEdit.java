package org.imogene.android.widget.field.edit;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.imogene.android.Constants.Extras;
import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.util.IntentUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import fr.medes.android.database.sqlite.stmt.Where;
import fr.medes.android.util.content.ContentUrisUtils;

public class RelationManyFieldEdit<T extends ImogBean> extends RelationFieldEdit<List<T>> {

	public RelationManyFieldEdit(Context context, AttributeSet attrs, int layoutId) {
		super(context, attrs, layoutId);
	}

	public RelationManyFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean isEmpty() {
		final List<T> value = getValue();
		return value == null || value.size() == 0;
	}

	@Override
	public boolean isValid() {
		if (isRequired()) {
			final List<T> value = getValue();
			return value != null && !value.isEmpty();
		}
		return true;
	}

	@Override
	public String getFieldDisplay() {
		final List<T> value = getValue();
		if (value != null && !value.isEmpty()) {
			int size = value.size();
			String fmt = getResources().getString(mDisplayRes);
			return MessageFormat.format(fmt, size);
		} else {
			return getEmptyText();
		}
	}

	@Override
	protected void dispatchClick(View v) {
		if (isReadOnly()) {
			final List<T> value = getValue();

			if (value == null || value.size() == 0) {
				return;
			}

			final int size = value.size();
			if (size == 1) {
				startActivity(new Intent(Intent.ACTION_VIEW, ContentUrisUtils.withAppendedId(mContentUri, value.get(0)
						.getId())));
			} else {
				Intent intent = new Intent(Intent.ACTION_VIEW, mContentUri);
				Object[] ids = new String[size];
				for (int i = 0; i < size; i++) {
					ids[i] = value.get(i).getId();
				}
				Where where = new Where();
				where.in(ImogBean.Columns._ID, ids);
				IntentUtils.putWhereExtras(intent, where);
				startActivity(intent);
			}
			return;
		}
		super.dispatchClick(v);
	}

	@Override
	protected void onPrepareIntent(Intent intent) {
		super.onPrepareIntent(intent);
		final List<T> value = getValue();
		if (value != null && !value.isEmpty()) {
			ArrayList<String> selected = new ArrayList<String>(value.size());
			for (T bean : value) {
				selected.add(bean.getId());
			}
			intent.putStringArrayListExtra(Extras.EXTRA_SELECTED, selected);
		}
		intent.putExtra(Extras.EXTRA_MULTIPLE, true);
	}

	@Override
	protected Where onPrepareWhere() {
		if (mHasReverse && mOppositeCardinality == 1) {
			String parentId = mRelationManager.getParentBean().getId();
			if (parentId != null) {
				return new Where().eq(mOppositeRelationField, parentId).or().isNotNull(mOppositeRelationField);
			} else {
				return new Where().isNull(mOppositeRelationField);
			}
		}
		return null;
	}

	@Override
	public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == mRequestCode && resultCode != Activity.RESULT_CANCELED) {
			ArrayList<String> result = data.getStringArrayListExtra(Extras.EXTRA_SELECTED);
			if (result == null || result.isEmpty()) {
				setValueInternal(null, true);
				return true;
			}
			ArrayList<T> value = new ArrayList<T>(result.size());
			for (String id : result) {
				T bean = ImogOpenHelper.fromUri(ContentUrisUtils.withAppendedId(mContentUri, id));
				if (bean != null) {
					value.add(bean);
				}
			}
			setValueInternal(value, true);
			return true;
		}
		return false;
	}

}

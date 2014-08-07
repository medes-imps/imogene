package org.imogene.android.widget.field.view;

import java.text.MessageFormat;
import java.util.List;

import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.util.IntentUtils;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import fr.medes.android.database.sqlite.stmt.Where;
import fr.medes.android.util.content.ContentUrisUtils;

public class RelationManyFieldView<T extends ImogBean> extends RelationFieldView<List<T>> {

	public RelationManyFieldView(Context context, AttributeSet attrs, int layoutId) {
		super(context, attrs, layoutId);
	}

	public RelationManyFieldView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean isEmpty() {
		final List<T> value = getValue();
		return value == null || value.size() == 0;
	}

	@Override
	public String getFieldDisplay() {
		final List<T> value = getValue();
		if (value != null && !value.isEmpty()) {
			int size = value.size();
			String fmt = getResources().getString(displayRes);
			return MessageFormat.format(fmt, size);
		}
		return super.getFieldDisplay();
	}

	@Override
	protected void dispatchClick(View v) {
		final List<T> value = getValue();

		if (value == null || value.size() == 0) {
			return;
		}

		final int size = value.size();
		if (size == 1) {
			startActivity(new Intent(Intent.ACTION_VIEW, ContentUrisUtils.withAppendedId(contentUri, value.get(0)
					.getId())));
		} else {
			Intent intent = new Intent(Intent.ACTION_VIEW, contentUri);
			Object[] ids = new String[size];
			for (int i = 0; i < size; i++) {
				ids[i] = value.get(i).getId();
			}
			Where where = new Where();
			where.in(ImogBean.Columns._ID, ids);
			IntentUtils.putWhereExtras(intent, where);
			startActivity(intent);
		}
	}

}

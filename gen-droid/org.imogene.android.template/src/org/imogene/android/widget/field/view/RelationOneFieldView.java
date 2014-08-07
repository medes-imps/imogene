package org.imogene.android.widget.field.view;

import org.imogene.android.common.entity.ImogBean;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import fr.medes.android.util.content.ContentUrisUtils;

public class RelationOneFieldView<T extends ImogBean> extends RelationFieldView<T> {

	public RelationOneFieldView(Context context, AttributeSet attrs, int layoutId) {
		super(context, attrs, layoutId);
	}

	public RelationOneFieldView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public String getFieldDisplay() {
		final T value = getValue();
		if (value != null) {
			return value.getMainDisplay(getContext());
		}
		return super.getFieldDisplay();
	}

	@Override
	protected void dispatchClick(View v) {
		final T value = getValue();
		if (value != null) {
			startActivity(new Intent(Intent.ACTION_VIEW, ContentUrisUtils.withAppendedId(contentUri, value.getId())));
		}
	}

}

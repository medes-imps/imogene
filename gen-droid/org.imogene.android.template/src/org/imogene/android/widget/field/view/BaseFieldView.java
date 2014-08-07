package org.imogene.android.widget.field.view;

import org.imogene.android.template.R;
import org.imogene.android.widget.field.BaseField;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public abstract class BaseFieldView<T> extends BaseField<T> {

	private ImageView mIconView;

	public BaseFieldView(Context context, int layoutId) {
		super(context, layoutId);
		init();
	}

	public BaseFieldView(Context context, AttributeSet attrs, int layoutId) {
		super(context, attrs, layoutId);
		init();
	}

	private void init() {
		mIconView = (ImageView) findViewById(R.id.imog__icon);
	}

	@Override
	protected void updateView() {
		super.updateView();
		final boolean visible = !isEmpty() && isDependentVisible();
		setVisibility(visible ? View.VISIBLE : View.GONE);
	}

	public void setIconId(int iconId) {
		if (mIconView != null) {
			if (iconId > 0) {
				mIconView.setImageResource(iconId);
			}
			mIconView.setVisibility(iconId > 0 ? View.VISIBLE : View.GONE);
		}
	}

}

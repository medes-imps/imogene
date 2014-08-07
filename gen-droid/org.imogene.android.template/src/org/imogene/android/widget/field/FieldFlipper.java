package org.imogene.android.widget.field;

import org.imogene.android.widget.field.edit.BaseFieldEdit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

public class FieldFlipper extends ViewFlipper {

	public interface Controller {

		public void onFieldChanged();
	}

	private Controller mController;

	public FieldFlipper(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setController(Controller controller) {
		mController = controller;
	}

	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params) {
		super.addView(child, index, params);
		// TODO
		// if (child instanceof BaseField<?>) {
		// final BaseField<?> field = (BaseField<?>) child;
		// field.registerDependent(this, null);
		// }
	}

	@Override
	public void setDisplayedChild(int whichChild) {
		super.setDisplayedChild(whichChild);
		if (mController != null) {
			mController.onFieldChanged();
		}
	}

	@Override
	public void showNext() {
		if (hasNext()) {
			super.showNext();
			final View displayed = getCurrentView();
			if (displayed != null && displayed instanceof BaseField<?>) {
				final BaseField<?> field = (BaseField<?>) displayed;
				if (!field.isDependentVisible()) {
					showNext();
				}
			}
		}
	}

	@Override
	public void showPrevious() {
		if (hasPrevious()) {
			super.showPrevious();
			final View displayed = getCurrentView();
			if (displayed != null && displayed instanceof BaseField<?>) {
				final BaseField<?> field = (BaseField<?>) displayed;
				if (!field.isDependentVisible()) {
					showPrevious();
				}
			}
		}
	}

	public BaseFieldEdit<?> getCurrentField() {
		return (BaseFieldEdit<?>) getCurrentView();
	}

	public boolean hasNext() {
		final int count = getChildCount();
		final int displayed = getDisplayedChild();
		for (int i = displayed + 1; i < count; i++) {
			final View child = getChildAt(i);
			if (child != null && child instanceof BaseField<?>) {
				if (((BaseField<?>) child).isDependentVisible()) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasPrevious() {
		final int displayed = getDisplayedChild();
		for (int i = 0; i < displayed; i++) {
			final View child = getChildAt(i);
			if (child != null && child instanceof BaseField<?>) {
				if (((BaseField<?>) child).isDependentVisible()) {
					return true;
				}
			}
		}
		return false;
	}
	// TODO
	// @Override
	// public void onDependencyChanged() {
	// if (mController != null) {
	// mController.onFieldChanged();
	// }
	// }
	//
	// @Override
	// public void registerDependsOn(DependencyMatcher matcher, String dependencyValue) {
	//
	// }

}

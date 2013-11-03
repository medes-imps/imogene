package org.imogene.android.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;

public class BaseDialogPreference extends DialogPreference {

	/**
	 * Interface used to allow the creator to run some code when the dialog of the preference is dismissed.
	 */
	public interface OnDialogCloseListener {
		/**
		 * Called when the dialog is dismissed and should be used to save data to the {@link SharedPreferences}.
		 * 
		 * @param preference The preference which displayed the dialog.
		 * @param positiveResult Whether the positive button was clicked (true), or the negative button was clicked or
		 *            the dialog was canceled (false).
		 */
		public void onDialogClosed(Preference preference, boolean positiveResult);
	}

	private OnDialogCloseListener mListener;

	public BaseDialogPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BaseDialogPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setOnDialogCloseListener(OnDialogCloseListener listener) {
		mListener = listener;
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		if (mListener != null) {
			mListener.onDialogClosed(this, positiveResult);
		}
	}

	@Override
	public boolean persistString(String value) {
		return super.persistString(value);
	}

	@Override
	public void notifyChanged() {
		super.notifyChanged();
	}

}

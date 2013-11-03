package org.imogene.android.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;

/**
 * A base class for {@link Preference} objects that are dialog-based. These preferences will, when clicked, open a
 * dialog showing the actual preference controls.
 */
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

	private OnDialogCloseListener mOnDialogCloseListener;

	public BaseDialogPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BaseDialogPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * Sets the callback to be invoked when the dialog has been closed by the user.
	 * 
	 * @param onDialogCloseListener The callback to be invoked.
	 */
	public void setOnDialogCloseListener(OnDialogCloseListener onDialogCloseListener) {
		mOnDialogCloseListener = onDialogCloseListener;
	}

	/**
	 * Returns the callback to be invoked when the dialog has been closed by the user.
	 */
	public OnDialogCloseListener getOnDialogCloseListener() {
		return mOnDialogCloseListener;
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		if (mOnDialogCloseListener != null) {
			mOnDialogCloseListener.onDialogClosed(this, positiveResult);
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

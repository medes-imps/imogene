package org.imogene.android.widget.field;

import java.util.ArrayList;

import org.imogene.android.template.R;
import org.imogene.android.widget.field.FieldManager.OnActivityDestroyListener;
import org.imogene.android.widget.field.FieldManager.OnActivityResultListener;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Basic view that represents a class.
 * 
 * @param <T> The field value type.
 */
public abstract class BaseField<T> extends LinearLayout implements OnClickListener, OnLongClickListener,
		OnDismissListener, OnActivityDestroyListener {

	/**
	 * Interface to listen for changes of the current value.
	 */
	public interface OnValueChangeListener {
		/**
		 * Called upon a change of the current value.
		 * 
		 * @param field The {@link BaseField} associated with this listener.
		 */
		public void onValueChange(BaseField<?> field);
	}

	private TextView mValueView;
	private TextView mTitleView;
	private View mDependentView;

	private FieldManager mManager;
	private DialogFactory mFactory;
	private Dialog mDialog;

	private boolean mHidden = false;
	private String mEmptyText;

	private OnValueChangeListener mOnValueChangeListener;

	private boolean mDependent = false;
	private ArrayList<BaseField<?>> mDependents;
	private ArrayList<Pair<BaseField<?>, String>> mDependencies;

	private boolean shouldUpdateValueView = true;

	private T mValue;

	public BaseField(Context context, int layoutId) {
		super(context);
		setOrientation(LinearLayout.VERTICAL);
		setFocusable(true);
		init(context, layoutId);
	}

	public BaseField(Context context, AttributeSet attrs, int layoutId) {
		super(context, attrs);
		init(context, layoutId);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BaseField, 0, 0);
		setEmptyText(a.getResourceId(R.styleable.BaseField_emptyText, R.string.imog__select));
		setTitle(a.getResourceId(R.styleable.BaseField_title, android.R.string.unknownName));
		setDependent(a.getBoolean(R.styleable.BaseField_dependent, false));
		setHidden(a.getBoolean(R.styleable.BaseField_hidden, false));
		a.recycle();
	}

	private void init(Context context, int layoutId) {
		inflate(context, layoutId, this);
		inflate(context, R.layout.imog__divider_layout, this);

		mValueView = (TextView) findViewById(R.id.imog__value);
		if (mValueView == null) {
			throw new NullPointerException();
		}

		mTitleView = (TextView) findViewById(R.id.imog__title);
		if (mTitleView == null) {
			throw new NullPointerException();
		}

		mDependentView = findViewById(R.id.imog__arrow);
	}

	@Override
	public void setVisibility(int visibility) {
		super.setVisibility(mHidden ? View.GONE : visibility);
	}

	/**
	 * Returns the view containing the displayed value of the field.
	 * 
	 * @return The field display value view.
	 */
	public TextView getValueView() {
		return mValueView;
	}

	/**
	 * Returns whether the field is hidden or not.
	 * 
	 * @return {@code true} if the field is hidden, {@code false} otherwise.
	 */
	public boolean isHidden() {
		return mHidden;
	}

	/**
	 * Set the hidden state of this field.
	 * 
	 * @param hidden The hidden state.
	 */
	public void setHidden(boolean hidden) {
		mHidden = hidden;
		setVisibility(hidden ? View.GONE : View.VISIBLE);
	}

	/**
	 * Set the title resource identifier of the field.
	 * 
	 * @param titleId The title resource identifier.
	 */
	public void setTitle(int titleId) {
		mTitleView.setText(titleId);
	}

	/**
	 * Set the title string representation of the field.
	 * 
	 * @param title The title.
	 */
	public void setTitle(CharSequence title) {
		mTitleView.setText(title);
	}

	/**
	 * Returns the title of the field.
	 * 
	 * @return The title.
	 */
	public CharSequence getTitle() {
		return mTitleView.getText();
	}

	/**
	 * Set the text to display when the field is empty.
	 * 
	 * @param emptyText The empty text.
	 */
	public void setEmptyText(String emptyText) {
		mEmptyText = emptyText;
	}

	/**
	 * Set the text resource identifier to display when the field is empty.
	 * 
	 * @param emptyTextId The text resource identifier.
	 */
	public void setEmptyText(int emptyTextId) {
		mEmptyText = getResources().getString(emptyTextId);
	}

	/**
	 * Returns the text to be displayed when the field is empty.
	 * 
	 * @return The text when field is empty.
	 */
	public String getEmptyText() {
		return mEmptyText;
	}

	/**
	 * Sets the listener to be notified on change of the current value.
	 * 
	 * @param onValueChangedListener The listener.
	 */
	public void setOnValueChangedListener(OnValueChangeListener onValueChangedListener) {
		mOnValueChangeListener = onValueChangedListener;
	}

	/**
	 * Set whether the field visibility is dependent of an other field value.
	 * 
	 * @param dependent {@code true} if the field visibility depends on an other field value, {@code false} if not.
	 */
	public void setDependent(boolean dependent) {
		mDependent = dependent;
		if (mDependentView != null) {
			mDependentView.setVisibility(dependent ? View.VISIBLE : View.GONE);
		}
	}

	/**
	 * Returns whether the field visibility is dependent of an other field value.
	 * 
	 * @return {@code true} if the field visibility depends on an other field value, {@code false} if not.
	 */
	public boolean isDependent() {
		return mDependent;
	}

	/**
	 * Set the value of this field. This method will update dependent fields and everything that need to be notified.
	 * 
	 * @see BaseField#init(Object)
	 * 
	 * @param value The value.
	 */
	public final void setValue(T value) {
		setValueInternal(value, false);
	}

	/**
	 * Convenient method to set the field value specifying the notification behavior.
	 * 
	 * @param value The value
	 * @param notifyChange whether listeners should be notified or not
	 */
	public void setValueInternal(T value, boolean notifyChange) {
		// if (mValue == value) {
		// return;
		// }
		mValue = value;
		updateView();
		if (notifyChange) {
			notifyChange();
		}
		notifyDependencyChange();
	}

	/**
	 * Returns the current value of this field.
	 * 
	 * @return The current value.
	 */
	public final T getValue() {
		return mValue;
	}

	/**
	 * Returns whether this field is empty or not.
	 * 
	 * @return {@code true} if the field is empty, {@code false} otherwise.
	 */
	public boolean isEmpty() {
		return mValue == null;
	}

	protected FieldManager getFieldManager() {
		return mManager;
	}

	protected void startActivity(Intent intent) {
		mManager.getActivity().startActivity(intent);
	}

	protected void startActivityForResult(Intent intent, int requestCode) {
		mManager.getActivity().startActivityForResult(intent, requestCode);
	}

	protected void setDialogFactory(DialogFactory factory) {
		mFactory = factory;
	}

	protected String getFieldDisplay() {
		return getResources().getString(android.R.string.unknownName);
	}

	/**
	 * Method to be called when a field is attached to a field manager. This will ensure
	 * {@link OnActivityDestroyListener} and {@link OnActivityResultListener} will be dispatch correctly to targets.
	 * 
	 * @param manager The manager who is managing the fields.
	 */
	public void onAttachedToHierarchy(FieldManager manager) {
		mManager = manager;
	}

	protected void notifyChange() {
		if (mOnValueChangeListener != null) {
			mOnValueChangeListener.onValueChange(this);
		}
	}

	/**
	 * Sets whether this {@link BaseField} value view should be updated when its value changes.
	 * 
	 * @param shouldUpdateValueView Set true if this {@link BaseField} value view should update when the value is
	 *            updated.
	 */
	public void setShouldUpdateValueView(boolean shouldUpdateValueView) {
		this.shouldUpdateValueView = shouldUpdateValueView;
	}

	/**
	 * Checks whether this {@link BaseField} value view should be updated when it's value changed.
	 * 
	 * @see #setShouldDisableView(boolean)
	 * @return True if the value view should be updated.
	 */
	public boolean getShouldUpdateValueView() {
		return shouldUpdateValueView;
	}

	protected void updateView() {
		if (shouldUpdateValueView) {
			mValueView.setText(getFieldDisplay());
		}
	}

	protected boolean isDependentVisible() {
		if (mDependent && mDependencies != null) {
			for (Pair<BaseField<?>, String> entry : mDependencies) {
				if (!entry.first.matchesDependencyValue(entry.second)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Adds a dependent {@link BaseField} on this {@link BaseField} so we can notify it.
	 * 
	 * @param dependent The dependent {@link BaseField} which visibility will be updated according to the value of this
	 *            {@link BaseField}.
	 * @param dependencyValue The visibility dependency field value.
	 */
	public void registerDependent(BaseField<?> dependent, String dependencyValue) {
		if (mDependents == null) {
			mDependents = new ArrayList<BaseField<?>>();
		}

		mDependents.add(dependent);

		dependent.registerDependency(this, dependencyValue);

		dependent.onDependencyChanged();
	}

	private void notifyDependencyChange() {
		final ArrayList<BaseField<?>> dependents = mDependents;

		if (dependents == null) {
			return;
		}

		final int size = dependents.size();
		for (int i = 0; i < size; i++) {
			dependents.get(i).onDependencyChanged();
		}
	}

	/**
	 * Register the {@link BaseField} that this {@link BaseField} will depend on.
	 * 
	 * @param dependency The {@link BaseField} that this depends on.
	 * @param dependencyValue The dependency value.
	 */
	private void registerDependency(BaseField<?> dependency, String dependencyValue) {
		if (mDependencies == null) {
			mDependencies = new ArrayList<Pair<BaseField<?>, String>>();
			// First time mark the field as dependent
			setDependent(true);
		}

		mDependencies.add(new Pair<BaseField<?>, String>(dependency, dependencyValue));
	}

	protected boolean matchesDependencyValue(String dependencyValue) {
		return mValue != null;
	}

	public void onDependencyChanged() {
		final boolean visible = isDependentVisible();
		setVisibility(visible ? View.VISIBLE : View.GONE);
	}

	@Override
	public void onClick(View v) {
		dispatchClick(v);
	}

	protected void dispatchClick(View v) {

	}

	@Override
	public boolean onLongClick(View v) {
		setValue(null);
		return true;
	}

	protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {

	}

	protected void showDialog(Bundle state) {
		final Dialog dialog;
		if (mFactory == null) {
			final Builder builder = new AlertDialog.Builder(getContext());
			builder.setTitle(getTitle());

			onPrepareDialogBuilder(builder);

			getFieldManager().registerOnActivityDestroyListener(this);
			dialog = mDialog = builder.create();
		} else {
			getFieldManager().registerOnActivityDestroyListener(this);
			dialog = mDialog = mFactory.createDialog();
		}
		if (state != null) {
			dialog.onRestoreInstanceState(state);
		}
		dialog.setOnDismissListener(this);
		dialog.show();
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		mManager.unregisterOnActivityDestroyListener(this);
		if (dialog.equals(mDialog)) {
			mDialog = null;
		}
	}

	@Override
	public void onActivityDestroy() {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		final Parcelable superState = super.onSaveInstanceState();
		final boolean hasDialog = mDialog != null && mDialog.isShowing();
		if (!hasDialog) {
			return superState;
		}

		final SavedState myState = new SavedState(superState);
		myState.isDialogShowing = hasDialog;
		myState.dialogBundle = hasDialog ? mDialog.onSaveInstanceState() : null;
		return myState;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (state == null || !state.getClass().equals(SavedState.class)) {
			// Didn't save state for us in onSaveInstanceState
			super.onRestoreInstanceState(state);
			return;
		}

		final SavedState myState = (SavedState) state;
		super.onRestoreInstanceState(myState.getSuperState());
		if (myState.isDialogShowing) {
			showDialog(myState.dialogBundle);
		}
	}

	private static class SavedState extends BaseSavedState {

		private boolean isDialogShowing;
		private Bundle dialogBundle;

		public SavedState(Parcel source) {
			super(source);
			isDialogShowing = source.readInt() == 1;
			dialogBundle = source.readBundle();
		}

		public SavedState(Parcelable superState) {
			super(superState);
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			dest.writeInt(isDialogShowing ? 1 : 0);
			dest.writeBundle(dialogBundle);
		}

		public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

			@Override
			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}

			@Override
			public SavedState createFromParcel(Parcel source) {
				return new SavedState(source);
			}
		};

	}

	/**
	 * Interface to define a dialog factory to be used when the field must open a dialog box when clicked.
	 */
	public interface DialogFactory {
		/**
		 * Creates the dialog box to be displayed.
		 * 
		 * @return The dialog box.
		 */
		public Dialog createDialog();
	}

}

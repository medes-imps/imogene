package org.imogene.android.preference.filter;

import org.imogene.android.common.filter.ClientFilter;
import org.imogene.android.preference.Preferences;
import org.imogene.android.template.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;

public abstract class FilterPreference<T extends ClientFilter> extends DialogPreference {

	private final ClientFilter.Creator<T> mCreator;

	private final String mUserId;
	private final String mTerminalId;
	private final String mEntityField;

	private T mFilter;

	protected final String mCardEntity;

	private boolean persisted;

	public FilterPreference(Context context, AttributeSet attrs, ClientFilter.Creator<T> creator) {
		super(context, attrs);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FilterPreference, 0, 0);
		mCardEntity = a.getString(R.styleable.FilterPreference_filterEntity);
		mEntityField = a.getString(R.styleable.FilterPreference_filterField);
		a.recycle();

		Preferences prefs = Preferences.getPreferences(context);
		mUserId = prefs.getSyncLogin();
		mTerminalId = prefs.getSyncTerminal();

		mCreator = creator;

		mFilter = getPersistedFilter();
	}

	public final T getFilter() {
		return mFilter;
	}

	public final boolean persisted() {
		return persisted;
	}

	protected final boolean persistFilter() {
		callChangeListener(mFilter);
		mFilter.prepareForSave(getContext());
		mFilter.saveOrUpdate(getContext());
		persisted = true;
		notifyDependencyChange(shouldDisableDependents());
		persisted = false;
		notifyChanged();
		return true;
	}

	protected final T getPersistedFilter() {
		return mCreator.create(getContext(), mUserId, mTerminalId, mCardEntity, mEntityField);
	}

	public void notifyFilter() {
		mFilter = getPersistedFilter();
		notifyDependencyChange(shouldDisableDependents());
		notifyChanged();
	}

}

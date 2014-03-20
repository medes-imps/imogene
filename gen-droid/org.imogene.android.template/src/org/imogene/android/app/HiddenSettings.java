package org.imogene.android.app;

import java.util.UUID;

import org.imogene.android.common.entity.SyncHistory;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.preference.Preferences;
import org.imogene.android.template.R;
import org.imogene.android.util.DatabaseUtils;
import org.imogene.android.util.NTPClock;

import android.content.Context;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.text.TextUtils;

import com.actionbarsherlock.app.SherlockPreferenceActivity;

import fr.medes.android.os.BaseAsyncTask;
import fr.medes.android.os.BaseAsyncTask.Callback;
import fr.medes.android.preference.BaseDialogPreference;
import fr.medes.android.preference.BaseDialogPreference.OnDialogCloseListener;

public class HiddenSettings extends SherlockPreferenceActivity implements OnDialogCloseListener,
		OnPreferenceClickListener, OnPreferenceChangeListener {

	private BaseDialogPreference mDeleteDatabase;
	private BaseDialogPreference mDeleteSyncHistory;
	private BaseDialogPreference mSyncTerminal;
	private EditTextPreference mNtpHost;
	private Preference mNtpOffset;

	private Preferences mPreferences;

	private SntpOffsetTask mSntpOffsetTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.hidden_settings);

		mPreferences = Preferences.getPreferences(this);

		mDeleteDatabase = (BaseDialogPreference) findPreference(Preferences.DELETE_DATABASE);
		mDeleteSyncHistory = (BaseDialogPreference) findPreference(Preferences.DELETE_HISTORY);
		mSyncTerminal = (BaseDialogPreference) findPreference(Preferences.SYNC_TERMINAL);
		mNtpHost = (EditTextPreference) findPreference(Preferences.NTP_HOST);
		mNtpOffset = findPreference(Preferences.NTP_OFFSET);

		mNtpOffset.setOnPreferenceClickListener(this);

		mDeleteDatabase.setOnDialogCloseListener(this);
		mDeleteSyncHistory.setOnDialogCloseListener(this);
		mSyncTerminal.setOnDialogCloseListener(this);

		mSntpOffsetTask = (SntpOffsetTask) getLastNonConfigurationInstance();

		if (mSntpOffsetTask != null) {
			mSntpOffsetTask.setCallback(mSntpOffsetCallback);
		}

		updateNtpOffsetVisibility();
		updateSyncHardwareSummary();
		updateNtpOffsetSummary();
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return mSntpOffsetTask;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (mSntpOffsetTask != null) {
			mSntpOffsetTask.setCallback(null);
		}
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		if (preference == mNtpOffset) {
			executeNtpOffsetUpdate();
			return true;
		}
		return false;
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		if (preference == mNtpHost) {
			updateNtpOffsetVisibility();
			return true;
		}
		return false;
	}

	@Override
	public void onDialogClosed(Preference preference, boolean positiveResult) {
		if (preference == mDeleteDatabase) {
			if (positiveResult) {
				new Thread() {
					@Override
					public void run() {
						DatabaseUtils.deleteAll(HiddenSettings.this);
					}
				}.start();
			}
		} else if (preference == mDeleteSyncHistory) {
			if (positiveResult) {
				new Thread() {
					@Override
					public void run() {
						ImogOpenHelper.getHelper().delete(SyncHistory.Columns.TABLE_NAME, null, null);
					};
				}.start();
			}
		} else if (preference == mSyncTerminal) {
			if (positiveResult) {
				mPreferences.setSyncTerminal(UUID.randomUUID().toString());
				updateSyncHardwareSummary();
			}
		}
	}

	private void updateNtpOffsetVisibility() {
		mNtpOffset.setEnabled(!TextUtils.isEmpty(mNtpHost.getText()));
	}

	private void updateSyncHardwareSummary() {
		mSyncTerminal.setSummary(mPreferences.getSyncTerminal());
	}

	private void updateNtpOffsetSummary() {
		mNtpOffset.setSummary(String.valueOf(mPreferences.getNtpOffset()));
	}

	private void executeNtpOffsetUpdate() {
		if (mSntpOffsetTask == null) {
			mSntpOffsetTask = new SntpOffsetTask(this);
			mSntpOffsetTask.setCallback(mSntpOffsetCallback);
			mSntpOffsetTask.execute(mNtpHost.getText());
		}
	}

	private final Callback<String, Void, Boolean> mSntpOffsetCallback = new Callback<String, Void, Boolean>() {

		@Override
		public void onAttachedToTask(Status status, Boolean result) {
			if (status == Status.FINISHED) {
				onPostExecute(result);
			}
		}

		@Override
		public void onCancelled() {
		}

		@Override
		public void onPostExecute(Boolean result) {
			mNtpOffset.setEnabled(true);
			updateNtpOffsetSummary();

			if (mSntpOffsetTask != null) {
				mSntpOffsetTask.setCallback(null);
				mSntpOffsetTask = null;
			}
		}

		@Override
		public void onPreExecute() {
			mNtpOffset.setEnabled(false);
		}

		@Override
		public void onProgressUpdate(Void... values) {
		}

	};

	private static class SntpOffsetTask extends BaseAsyncTask<String, Void, Boolean> {

		private final Context context;

		public SntpOffsetTask(Context context) {
			this.context = context.getApplicationContext();
		}

		@Override
		protected Boolean doInBackground(String... params) {
			return NTPClock.getInstance(context).updateOffsetSync(params[0]);
		}

	}

}

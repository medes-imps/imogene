package org.imogene.android.app;

import java.io.File;
import java.text.MessageFormat;

import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.preference.Preferences;
import org.imogene.android.push.PushService;
import org.imogene.android.sync.SyncManager;
import org.imogene.android.template.R;
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.text.TextUtils;

import com.actionbarsherlock.app.SherlockPreferenceActivity;

import fr.medes.android.app.MyProgressDialog;
import fr.medes.android.app.MyProgressDialog.Formatter;
import fr.medes.android.app.WakefulIntentService;
import fr.medes.android.database.sqlite.stmt.QueryBuilder;
import fr.medes.android.maps.MapsConstants;
import fr.medes.android.maps.database.PreCache;
import fr.medes.android.maps.database.sqlite.PreCacheCursor;
import fr.medes.android.maps.offline.TileLoaderService;
import fr.medes.android.os.BaseAsyncTask;
import fr.medes.android.update.CheckUpdateTask;
import fr.medes.android.update.MarketApp;
import fr.medes.android.util.file.DownloadFileTask;
import fr.medes.android.util.file.FileUtils;

public class GeneralSettings extends SherlockPreferenceActivity implements OnPreferenceChangeListener,
		OnPreferenceClickListener {

	private static final int DIALOG_DOWNLOAD_ID = 1;
	private ContentResolver mContentResolver;

	private Preference mSyncEnabled;
	private Preference mSyncCheck;
	private Preference mPushEnabled;
	private EditTextPreference mPushHost;
	private EditTextPreference mPushPort;
	private Preference mMapAutomaticCache;
	private Preference mMapClearCache;
	private Preference mMapPrecacheArea;
	private Preference mUpdateAvailable;
	private EditTextPreference mUpdateServer;

	private MyProgressDialog mProgressDialog;

	private RetainObject retain;

	private final ContentObserver mPrecacheAreaObserver = new ContentObserver(new Handler()) {

		@Override
		public void onChange(boolean selfChange) {
			executeUpdatePrecacheArea();
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.general_settings);

		mContentResolver = getContentResolver();

		mSyncEnabled = findPreference(Preferences.SYNC_ENABLED);
		mSyncCheck = findPreference(Preferences.SYNC_CHECK);
		mPushEnabled = findPreference(Preferences.PUSH_ENABLED);
		mPushHost = (EditTextPreference) findPreference(Preferences.PUSH_HOST);
		mPushPort = (EditTextPreference) findPreference(Preferences.PUSH_PORT);
		mMapAutomaticCache = findPreference(Preferences.MAP_AUTOMATIC_CACHE);
		mMapClearCache = findPreference(Preferences.MAP_CLEAR_CACHE);
		mMapPrecacheArea = findPreference(Preferences.MAP_PRECACHE_AREA);
		mUpdateServer = (EditTextPreference) findPreference(Preferences.UPDATE_SERVER);
		mUpdateAvailable = findPreference(Preferences.UPDATE_AVAILABLE);

		mSyncEnabled.setOnPreferenceChangeListener(this);
		mPushEnabled.setOnPreferenceChangeListener(this);
		mPushHost.setOnPreferenceChangeListener(this);
		mPushPort.setOnPreferenceChangeListener(this);
		mUpdateServer.setOnPreferenceChangeListener(this);

		mSyncCheck.setOnPreferenceClickListener(this);
		mMapAutomaticCache.setOnPreferenceClickListener(this);
		mMapClearCache.setOnPreferenceClickListener(this);
		mUpdateAvailable.setOnPreferenceClickListener(this);

		mContentResolver.registerContentObserver(PreCache.Columns.CONTENT_URI, true, mPrecacheAreaObserver);

		boolean init = false;
		retain = (RetainObject) getLastNonConfigurationInstance();
		if (retain == null) {
			retain = new RetainObject();
			init = true;
		}

		if (retain.checkUpdateTask != null) {
			retain.checkUpdateTask.setCallback(mCheckUpdateCallback);
		}

		if (retain.downloadFileTask != null) {
			retain.downloadFileTask.setCallback(mDownloadFileCallback);
		}

		if (retain.clearCacheTask != null) {
			retain.clearCacheTask.setCallback(mClearCacheCallback);
		}

		if (retain.updateCacheSizeTask != null) {
			retain.updateCacheSizeTask.setCallback(mUpdateCacheSizeCallback);
		}

		if (retain.updatePrecacheAreaTask != null) {
			retain.updatePrecacheAreaTask.setCallback(mUpdatePrecacheAreaCallback);
		}

		if (init) {
			executeUpdateCacheSize();
			executeUpdatePrecacheArea();
			executeCheckUpdate(mUpdateServer.getText());
		}
		updatePushEnabledVisibility();
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return retain;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mContentResolver.unregisterContentObserver(mPrecacheAreaObserver);

		if (retain.checkUpdateTask != null) {
			retain.checkUpdateTask.setCallback(null);
		}

		if (retain.downloadFileTask != null) {
			retain.downloadFileTask.setCallback(null);
		}

		if (retain.clearCacheTask != null) {
			retain.clearCacheTask.setCallback(null);
		}

		if (retain.updateCacheSizeTask != null) {
			retain.updateCacheSizeTask.setCallback(null);
		}

		if (retain.updatePrecacheAreaTask != null) {
			retain.updatePrecacheAreaTask.setCallback(null);
		}
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		if (preference == mSyncEnabled) {
			setSyncEnabled((Boolean) newValue);
			return true;
		} else if (preference == mPushEnabled) {
			setPushEnabled((Boolean) newValue);
			return true;
		} else if (preference == mUpdateServer) {
			setUpdateAvailable(null);
			executeCheckUpdate((String) newValue);
			return true;
		} else if (preference == mPushHost) {
			updatePushEnabledVisibility();
			return true;
		} else if (preference == mPushPort) {
			updatePushEnabledVisibility();
			return true;
		}
		return false;
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		if (preference == mSyncCheck) {
			SyncManager.startManualSync(this);
			return true;
		} else if (preference == mMapAutomaticCache) {
			executeAutomaticCache();
			return true;
		} else if (preference == mMapClearCache) {
			executeClearCache();
			return true;
		} else if (preference == mUpdateAvailable) {
			executeUpdateApplication();
			return true;
		}
		return false;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_DOWNLOAD_ID:
			mProgressDialog = new MyProgressDialog(this);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setTitle(R.string.imog__update_app_title);
			mProgressDialog.setMessage(getString(R.string.imog__update_app_message));
			mProgressDialog.setFormatter(new Formatter() {
				@Override
				public String format(int progress, int max) {
					String readableProgress = FileUtils.readableFileSize(progress);
					String readableMax = FileUtils.readableFileSize(max);
					return String.format("%s / %s", readableProgress, readableMax);
				}
			});
			mProgressDialog.setCancelable(true);
			mProgressDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					if (retain.downloadFileTask != null) {
						retain.downloadFileTask.cancel(true);
					}
				}
			});
			mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(android.R.string.cancel),
					new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			return mProgressDialog;
		default:
			return super.onCreateDialog(id);
		}
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case DIALOG_DOWNLOAD_ID:
			mProgressDialog.setMax(retain.marketApp.getSize());
			mProgressDialog.setProgress(0);
			break;
		default:
			super.onPrepareDialog(id, dialog);
			break;
		}
	}

	private void setSyncEnabled(boolean enabled) {
		if (enabled) {
			SyncManager.start(this);
		} else {
			SyncManager.stop(this);
		}
	}

	private void setPushEnabled(boolean enabled) {
		if (enabled) {
			PushService.actionStart(this);
		} else {
			PushService.actionStop(this);
		}
	}

	private void executeAutomaticCache() {
		retain.automaticCacheTask = new AutomaticCacheTask(this);
		retain.automaticCacheTask.execute();
	}

	private void executeUpdateCacheSize() {
		retain.updateCacheSizeTask = new UpdateCacheSizeTask();
		retain.updateCacheSizeTask.setCallback(mUpdateCacheSizeCallback);
		retain.updateCacheSizeTask.execute();
	}

	private void executeClearCache() {
		retain.clearCacheTask = new ClearCacheTask();
		retain.clearCacheTask.setCallback(mClearCacheCallback);
		retain.clearCacheTask.execute();
	}

	private void executeUpdatePrecacheArea() {
		retain.updatePrecacheAreaTask = new UpdatePrecacheAreaTask();
		retain.updatePrecacheAreaTask.setCallback(mUpdatePrecacheAreaCallback);
		retain.updatePrecacheAreaTask.execute();
	}

	private void executeCheckUpdate(String url) {
		if (TextUtils.isEmpty(url)) {
			return;
		}
		retain.checkUpdateTask = new CheckUpdateTask();
		retain.checkUpdateTask.setCallback(mCheckUpdateCallback);
		retain.checkUpdateTask.execute(url, getPackageName());
	}

	private void executeUpdateApplication() {
		String server = mUpdateServer.getText();
		String url = server + (server.endsWith("/") ? "" : "/") + "apk/" + retain.marketApp.getName();
		File dir = new File(Environment.getExternalStorageDirectory(), retain.marketApp.getName());
		retain.downloadFileTask = new DownloadFileTask();
		retain.downloadFileTask.setCallback(mDownloadFileCallback);
		retain.downloadFileTask.execute(url, dir.getPath());
	}

	private void updatePushEnabledVisibility() {
		mPushEnabled.setEnabled(isPushEnabledVisible());
	}

	private boolean isPushEnabledVisible() {
		return !TextUtils.isEmpty(mPushHost.getText()) && !TextUtils.isEmpty(mPushPort.getText());
	}

	private void setCacheSizeSummary(long size) {
		String readableSize = FileUtils.readableFileSize(size);
		String summary = getString(R.string.imog__precache_clear_summary, readableSize);
		mMapClearCache.setSummary(summary);
	}

	private void setCacheSizeEnabled(boolean enabled) {
		mMapClearCache.setEnabled(enabled);
	}

	private void setPrecacheAreaSummary(int count) {
		String fmt = getResources().getString(R.string.imog__precache_area_summary);
		mMapPrecacheArea.setSummary(MessageFormat.format(fmt, count));
	}

	private void setCheckUpdateEnabled(boolean enabled) {
		mUpdateAvailable.setEnabled(enabled);
	}

	private void setUpdateAvailable(MarketApp app) {
		retain.marketApp = app;

		if (retain.checkUpdateTask != null) {
			retain.checkUpdateTask.setCallback(null);
			retain.checkUpdateTask = null;
		}

		if (app != null && app.isUpdatable(this)) {
			mUpdateAvailable.setEnabled(true);
			mUpdateAvailable.setSummary(R.string.imog__update_available_summary_enabled);
		} else {
			mUpdateAvailable.setEnabled(false);
			mUpdateAvailable.setSummary(R.string.imog__update_available_summary_disabled);
		}
	}

	private void applicationReceived(File result) {
		dismissDialog(DIALOG_DOWNLOAD_ID);

		if (retain.downloadFileTask != null) {
			retain.downloadFileTask.setCallback(null);
			retain.downloadFileTask = null;
		}

		if (result != null) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
			startActivity(intent);
		}
	}

	private static long blockingCacheSize() {
		return FileUtils.getDirectorySize(OpenStreetMapTileProviderConstants.TILE_PATH_BASE);
	}

	private final BaseCallback<Void, Void, Long> mUpdateCacheSizeCallback = new BaseCallback<Void, Void, Long>() {

		@Override
		public void onAttachedToTask(Status status, Long result) {
			if (status == Status.FINISHED) {
				setCacheSizeSummary(result);
			}
		}

		@Override
		public void onPostExecute(Long result) {
			setCacheSizeSummary(result);
		}

	};

	// ////////////////////
	// Calbacks
	// ////////////////////

	private final BaseCallback<Void, Void, Long> mClearCacheCallback = new BaseCallback<Void, Void, Long>() {

		@Override
		public void onAttachedToTask(Status status, Long result) {
			if (status == Status.FINISHED) {
				onPostExecute(result);
			} else if (status == Status.RUNNING) {
				setCacheSizeEnabled(false);
			}
		}

		@Override
		public void onPostExecute(Long result) {
			setCacheSizeEnabled(true);
			setCacheSizeSummary(result);
		}

		@Override
		public void onPreExecute() {
			setCacheSizeEnabled(false);
		}

	};

	private final BaseCallback<Void, Void, Integer> mUpdatePrecacheAreaCallback = new BaseCallback<Void, Void, Integer>() {

		@Override
		public void onAttachedToTask(Status status, Integer result) {
			if (status == Status.FINISHED) {
				setPrecacheAreaSummary(result);
			}
		}

		@Override
		public void onPostExecute(Integer result) {
			setPrecacheAreaSummary(result);
		}
	};

	private final BaseCallback<String, Void, MarketApp> mCheckUpdateCallback = new BaseCallback<String, Void, MarketApp>() {

		@Override
		public void onAttachedToTask(android.os.AsyncTask.Status status, MarketApp result) {
			if (status == Status.FINISHED) {
				setUpdateAvailable(result);
			}
		}

		@Override
		public void onPreExecute() {
			setCheckUpdateEnabled(false);
		}

		@Override
		public void onPostExecute(MarketApp result) {
			setUpdateAvailable(result);
		}
	};

	private final BaseCallback<String, Integer, File> mDownloadFileCallback = new BaseCallback<String, Integer, File>() {

		@Override
		public void onAttachedToTask(Status status, File file) {
			if (status == Status.FINISHED) {
				// may not have been detached if no callback was registered when task ended.
				onPostExecute(file);
			} else if (status == Status.RUNNING) {
				mUpdateAvailable.setEnabled(false);
			}
		}

		@Override
		public void onCancelled() {
			applicationReceived(null);
		}

		@Override
		public void onPostExecute(File result) {
			applicationReceived(result);
		}

		@Override
		public void onPreExecute() {
			showDialog(DIALOG_DOWNLOAD_ID);
		}

		@Override
		public void onProgressUpdate(Integer... values) {
			mProgressDialog.setProgress(values[0]);
		}
	};

	// ////////////////////
	// Classes
	// ////////////////////

	private static class AutomaticCacheTask extends BaseAsyncTask<Void, Void, Void> {

		private final Context mContext;

		public AutomaticCacheTask(Context context) {
			mContext = context.getApplicationContext();
		}

		@Override
		protected Void doInBackground(Void... params) {
			PreCacheCursor c = (PreCacheCursor) ImogOpenHelper.getHelper().query(PreCache.Columns.CONTENT_URI);
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				Intent intent = new Intent(mContext, TileLoaderService.class);
				intent.putExtra(MapsConstants.EXTRA_REFRESH_PRECACHE, true);
				intent.putExtra(MapsConstants.EXTRA_TILE_SOURCE, c.getProvider());
				intent.putExtra(MapsConstants.EXTRA_LAT_NORTH, c.getNorth());
				intent.putExtra(MapsConstants.EXTRA_LAT_SOUTH, c.getSouth());
				intent.putExtra(MapsConstants.EXTRA_LON_EAST, c.getEast());
				intent.putExtra(MapsConstants.EXTRA_LON_WEST, c.getWest());
				intent.putExtra(MapsConstants.EXTRA_ZOOM_MIN, c.getZoomMin());
				intent.putExtra(MapsConstants.EXTRA_ZOOM_MAX, c.getZoomMax());

				WakefulIntentService.sendWakefulWork(mContext, intent);
			}
			c.close();
			return null;
		}

	}

	private static class UpdateCacheSizeTask extends BaseAsyncTask<Void, Void, Long> {

		@Override
		protected Long doInBackground(Void... params) {
			return blockingCacheSize();
		}

	}

	private static class ClearCacheTask extends BaseAsyncTask<Void, Void, Long> {

		@Override
		protected Long doInBackground(Void... params) {
			FileUtils.deleteDirectory(OpenStreetMapTileProviderConstants.TILE_PATH_BASE);
			return blockingCacheSize();
		}

	}

	private static class UpdatePrecacheAreaTask extends BaseAsyncTask<Void, Void, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			QueryBuilder builder = ImogOpenHelper.getHelper().queryBuilder(PreCache.Columns.TABLE_NAME);
			builder.setCountOf(true);
			return (int) builder.queryForLong();
		}

	}

	private static class RetainObject {
		private CheckUpdateTask checkUpdateTask;
		private DownloadFileTask downloadFileTask;
		private UpdatePrecacheAreaTask updatePrecacheAreaTask;
		private ClearCacheTask clearCacheTask;
		private AutomaticCacheTask automaticCacheTask;
		private UpdateCacheSizeTask updateCacheSizeTask;
		private MarketApp marketApp;
	}

	private static class BaseCallback<Params, Progress, Result> implements
			BaseAsyncTask.Callback<Params, Progress, Result> {

		@Override
		public void onAttachedToTask(Status status, Result result) {
		}

		@Override
		public void onCancelled() {
		}

		@Override
		public void onPostExecute(Result result) {
		}

		@Override
		public void onPreExecute() {
		}

		@Override
		public void onProgressUpdate(Progress... values) {
		}

	}

}

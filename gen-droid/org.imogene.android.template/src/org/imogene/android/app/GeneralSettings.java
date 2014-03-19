package org.imogene.android.app;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.preference.Preferences;
import org.imogene.android.push.PushService;
import org.imogene.android.sync.SynchronizationService;
import org.imogene.android.template.R;
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.ContentObserver;
import android.net.Uri;
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
import fr.medes.android.update.MarketApp;
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
	private MarketApp mMarketApp;

	private TaskManager<GeneralSettings> mTaskManager;

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
		mTaskManager = (TaskManager<GeneralSettings>) getLastNonConfigurationInstance();
		if (mTaskManager == null) {
			mTaskManager = new TaskManager<GeneralSettings>();
			init = true;
		}
		mTaskManager.attach(this);

		if (init) {
			executeUpdateCacheSize();
			executeUpdatePrecacheArea();
			executeCheckUpdate(mUpdateServer.getText());
		}
		updatePushEnabledVisibility();
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		mTaskManager.detach();
		return mTaskManager;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mContentResolver.unregisterContentObserver(mPrecacheAreaObserver);
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
			SynchronizationService.actionCheck(this);
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
			mProgressDialog.setTitle(R.string.ig_update_app_title);
			mProgressDialog.setMessage(getString(R.string.ig_update_app_message));
			mProgressDialog.setFormatter(new Formatter() {
				@Override
				public String format(int progress, int max) {
					String readableProgress = FileUtils.readableFileSize(progress);
					String readableMax = FileUtils.readableFileSize(max);
					return String.format("%s / %s", readableProgress, readableMax);
				}
			});
			mProgressDialog.setCancelable(false);
			return mProgressDialog;
		default:
			return super.onCreateDialog(id);
		}
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case DIALOG_DOWNLOAD_ID:
			mProgressDialog.setMax(mMarketApp.getSize());
			mProgressDialog.setProgress(0);
			break;
		default:
			super.onPrepareDialog(id, dialog);
			break;
		}
	}

	private void setSyncEnabled(boolean enabled) {
		if (enabled) {
			SynchronizationService.actionReschedule(this);
		} else {
			SynchronizationService.actionCancel(this);
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
		mTaskManager.execute(1, new AutomaticCacheTask(this));
	}

	private void executeUpdateCacheSize() {
		mTaskManager.execute(2, new UpdateCacheSizeTask());
	}

	private void executeClearCache() {
		mTaskManager.execute(3, new ClearCacheTask());
	}

	private void executeUpdatePrecacheArea() {
		mTaskManager.execute(4, new UpdatePrecacheAreaTask());
	}

	private void executeCheckUpdate(String url) {
		if (TextUtils.isEmpty(url)) {
			return;
		}
		mTaskManager.execute(5, new CheckUpdateTask(), url, getPackageName());
	}

	private void executeUpdateApplication() {
		mTaskManager.execute(6, new DownloadFileTask(), mUpdateServer.getText(), mMarketApp.getFile());
	}

	private void updatePushEnabledVisibility() {
		mPushEnabled.setEnabled(isPushEnabledVisible());
	}

	private boolean isPushEnabledVisible() {
		return !TextUtils.isEmpty(mPushHost.getText()) && !TextUtils.isEmpty(mPushPort.getText());
	}

	private void setCacheSizeSummary(long size) {
		String readableSize = FileUtils.readableFileSize(size);
		String summary = getString(R.string.ig_precache_clear_summary, readableSize);
		mMapClearCache.setSummary(summary);
	}

	private void setCacheSizeEnabled(boolean enabled) {
		mMapClearCache.setEnabled(enabled);
	}

	private void setPrecacheAreaSummary(int count) {
		String summary = getResources().getQuantityString(R.plurals.ig_precache_area_summary, count, count);
		mMapPrecacheArea.setSummary(summary);
	}

	private void setCheckUpdateEnabled(boolean enabled) {
		mUpdateAvailable.setEnabled(enabled);
	}

	private void setUpdateAvailable(MarketApp app) {
		mMarketApp = app;
		if (app != null && isUpdatable(app.getPackage(), app.getVersionCode())) {
			mUpdateAvailable.setEnabled(true);
			mUpdateAvailable.setSummary(R.string.ig_update_available_summary_enabled);
		} else {
			mUpdateAvailable.setEnabled(false);
			mUpdateAvailable.setSummary(R.string.ig_update_available_summary_disabled);
		}
	}

	private boolean isUpdatable(String packageName, int versionCode) {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(packageName, 0);
			if (info.versionCode < versionCode) {
				return true;
			}
		} catch (NameNotFoundException e) {
		}
		return false;
	}

	private void incrementProgressBy(int diff) {
		if (mProgressDialog != null) {
			mProgressDialog.incrementProgressBy(diff);
		}
	}

	private void applicationReceived(File file) {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
		if (file != null) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
			startActivity(intent);
		}
	}

	private static long blockingCacheSize() {
		return FileUtils.getDirectorySize(OpenStreetMapTileProviderConstants.TILE_PATH_BASE);
	}

	private static class AutomaticCacheTask extends BaseAsyncTask<GeneralSettings, Void, Void, Void> {

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
			return null;
		}

	}

	private static class UpdateCacheSizeTask extends BaseAsyncTask<GeneralSettings, Void, Void, Long> {

		@Override
		protected Long doInBackground(Void... params) {
			return blockingCacheSize();
		}

		@Override
		public void onPostExecute(Long result) {
			super.onPostExecute(result);
			if (callback != null) {
				callback.setCacheSizeSummary(result);
			}
		}

	}

	private static class ClearCacheTask extends BaseAsyncTask<GeneralSettings, Void, Void, Long> {

		@Override
		protected void onPreExecute() {
			if (callback != null) {
				callback.setCacheSizeEnabled(false);
			}
		}

		@Override
		protected Long doInBackground(Void... params) {
			FileUtils.deleteDirectory(OpenStreetMapTileProviderConstants.TILE_PATH_BASE);
			return blockingCacheSize();
		}

		@Override
		protected void onPostExecute(Long result) {
			super.onPostExecute(result);
			if (callback != null) {
				callback.setCacheSizeEnabled(true);
				callback.setCacheSizeSummary(result);
			}
		}
	}

	private static class UpdatePrecacheAreaTask extends BaseAsyncTask<GeneralSettings, Void, Void, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			QueryBuilder builder = ImogOpenHelper.getHelper().queryBuilder(PreCache.Columns.TABLE_NAME);
			builder.setCountOf(true);
			return (int) builder.queryForLong();
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (callback != null) {
				callback.setPrecacheAreaSummary(result);
			}
		}

	}

	private static class CheckUpdateTask extends BaseAsyncTask<GeneralSettings, String, Void, MarketApp> {

		@Override
		protected void onPreExecute() {
			if (callback != null) {
				callback.setCheckUpdateEnabled(false);
			}
		}

		@Override
		protected MarketApp doInBackground(String... params) {
			String server = params[0];
			String packageName = params[1];
			String url = server + (server.endsWith("/") ? "" : "/") + "package/" + packageName + ".xml";
			try {
				return MarketApp.Parser.parse((InputStream) new URL(url).getContent());
			} catch (Exception e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(MarketApp result) {
			super.onPostExecute(result);
			if (callback != null) {
				callback.setUpdateAvailable(result);
			}
		}

	}

	private static class DownloadFileTask extends BaseAsyncTask<GeneralSettings, String, Integer, File> {

		private final Locker mLocker = new Locker();
		private boolean installed = false;

		@Override
		protected void onPreExecute() {
			if (callback != null) {
				callback.showDialog(DIALOG_DOWNLOAD_ID);
			}
		}

		@Override
		protected File doInBackground(String... params) {
			try {
				String server = params[0];
				String fileName = params[1];
				String urlString = server + (server.endsWith("/") ? "" : "/") + "apk/" + fileName;
				URL url = new URL(urlString);
				URLConnection connection = url.openConnection();
				connection.connect();
				// this will be useful so that you can show a tipical 0-100%
				// progress bar

				// download the file
				File dir = new File(Environment.getExternalStorageDirectory(), "imogenemarket");
				dir.mkdirs();
				File file = new File(dir, fileName);
				if (file.exists()) {
					file.delete();
				}
				file.createNewFile();
				InputStream input = new BufferedInputStream(url.openStream());
				OutputStream output = new FileOutputStream(file);

				byte data[] = new byte[1024];

				int count;
				int bigCount = 0;
				while ((count = input.read(data)) != -1) {
					if (isCancelled()) {
						break;
					}
					bigCount += count;
					if (!mLocker.isLocked()) {
						// publishing the progress....
						publishProgress(bigCount);
						bigCount = 0;
						mLocker.lock();
					}
					// publishing the progress....
					output.write(data, 0, count);
				}
				mLocker.cancel();
				publishProgress(bigCount);

				output.flush();
				output.close();
				input.close();
				if (isCancelled()) {
					file.delete();
					return null;
				}
				return file;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			if (callback != null) {
				callback.incrementProgressBy(values[0]);
			}
		}

		@Override
		protected void onPostExecute(File result) {
			super.onPostExecute(result);
			if (callback != null && !installed) {
				callback.applicationReceived(result);
				installed = true;
			}
		}

	}

}

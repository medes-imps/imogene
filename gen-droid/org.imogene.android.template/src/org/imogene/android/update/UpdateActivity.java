package org.imogene.android.update;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.imogene.android.app.MyProgressDialog;
import org.imogene.android.app.MyProgressDialog.Formatter;
import org.imogene.android.template.R;
import org.imogene.android.update.PackageHelper.State;
import org.imogene.android.util.file.FileUtils;
import org.imogene.android.util.os.Locker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Pair;

public class UpdateActivity extends Activity {
	
	private static final String EXTRA_APPLICATION = "UpdateAvailableActivity_application";
	
	private static final int DIALOG_AVAILABLE_ID = 1;
	private static final int DIALOG_UNAVAILABLE_ID = 2;
	private static final int DIALOG_DOWNLOADING_ID = 3;
	
	private MarketApp mApplication;
	private MyProgressDialog mProgressDialog;
	
	Pair<CheckUpdateTask, DownloadFileTask> mPair;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ig_media_content);
		
		mPair = (Pair<CheckUpdateTask, DownloadFileTask>) getLastNonConfigurationInstance();
		if (mPair == null) {
			CheckUpdateTask checkTask = new CheckUpdateTask();
			DownloadFileTask fileTask = new DownloadFileTask();
			mPair = new Pair<CheckUpdateTask, DownloadFileTask>(checkTask, fileTask);
		}
		mPair.first.setActitity(this);
		mPair.second.setActivity(this);
		
		if (mPair.first.getStatus() == AsyncTask.Status.PENDING) {
			String url = UpdatePreferenceHelper.getApplicationUrl(this, getPackageName());
			mPair.first.execute(url);
		}
	}
	
	@Override
	public Object onRetainNonConfigurationInstance() {
		mPair.first.setActitity(null);
		mPair.second.setActivity(null);
		return mPair;
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable(EXTRA_APPLICATION, mApplication);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mApplication = savedInstanceState.getParcelable(EXTRA_APPLICATION);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_AVAILABLE_ID:
			return new AlertDialog.Builder(this)
			.setTitle(R.string.ig_check_available_title)
			.setMessage(R.string.ig_check_available_message)
			.setCancelable(false)
			.setPositiveButton(android.R.string.ok, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					launchDownload();
				}
			})
			.setNegativeButton(android.R.string.no, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			})
			.create();
		case DIALOG_UNAVAILABLE_ID:
			return new AlertDialog.Builder(this)
			.setTitle(R.string.ig_check_available_title)
			.setMessage(R.string.ig_check_unavailable_message)
			.setCancelable(false)
			.setPositiveButton(android.R.string.ok, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			})
			.create();
		case DIALOG_DOWNLOADING_ID:
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
            mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(android.R.string.cancel), 
            		new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							mPair.second.cancel(true);
							finish();
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
		case DIALOG_DOWNLOADING_ID:
			mProgressDialog.setMax(mApplication.getSize());
			mProgressDialog.setProgress(0);
			break;
		default:
			super.onPrepareDialog(id, dialog);
			break;
		}
	}
	
	private void launchDownload() {
		showDialog(DIALOG_DOWNLOADING_ID);
		if (mPair.second.getStatus() == AsyncTask.Status.PENDING) {
			mPair.second.execute(UpdatePreferenceHelper.getUpdateServer(this), mApplication.getFile());
		}
	}
	
	private void onApplicationReceived(MarketApp app) {
		if (app == null) {
			finish();
			return;
		}
		mApplication = app;
		State state = PackageHelper.getPackageState(this, app.getPackage(), app.getVersionCode());
		if (state == State.UPDATE_AVAILABLE) {
			showDialog(DIALOG_AVAILABLE_ID);
		} else {
			showDialog(DIALOG_UNAVAILABLE_ID);
		}
	}
	
	private void onIncrementProgressBy(int diff) {
		mProgressDialog.incrementProgressBy(diff);
	}
	
	private void onApkReceived(File file) {
		mProgressDialog.setIndeterminate(true);
		if (file != null) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
			startActivity(intent);
		}
		finish();
	}
	
	private static class CheckUpdateTask extends AsyncTask<String, Void, MarketApp> {
		
		private UpdateActivity mActivity;
		
		public synchronized void setActitity(UpdateActivity activity) {
			mActivity = activity;
		}
		
		@Override
		protected MarketApp doInBackground(String... params) {
			try {
				String url = params[0];
				MarketApp app = MarketApp.Parser.parse((InputStream) new URL(url).getContent());
				return app;
			} catch (Exception e) {
			}
			return null;
		}
		
		@Override
		protected synchronized void onPostExecute(MarketApp result) {
			if (mActivity != null) {
				mActivity.onApplicationReceived(result);
			}
		}
		
	}
	
	private static class DownloadFileTask extends AsyncTask<String, Integer, File> {
		
		private final Locker mLocker = new Locker();
		
		private UpdateActivity mActivity = null;
		
		public synchronized void setActivity(UpdateActivity activity) {
			mActivity = activity;
		}

		@Override
		protected File doInBackground(String... params) {
			try {
				String urlString = params[0];
				final String fileName = params[1];
				if (!urlString.endsWith("/")) {
					urlString += "/";
				}
				urlString += "apk/" + fileName;
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
		protected synchronized void onProgressUpdate(Integer... values) {
			if (mActivity != null) {
				mActivity.onIncrementProgressBy(values[0]);
			}
		}
		
		@Override
		protected synchronized void onPostExecute(File result) {
			if (mActivity != null) {
				mActivity.onApkReceived(result);
			}
		}
		
	}
	
}

package org.imogene.android.app;

import org.imogene.android.app.SntpOffsetTask.OnSntpOffsetListener;
import org.imogene.android.preference.PreferenceHelper;
import org.imogene.android.template.R;
import org.imogene.android.util.ntp.SntpException;
import org.imogene.android.util.ntp.SntpProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

public class OffsetActivity extends Activity {
	
	private static final int DIALOG_SNTPING_ID = 1;
	private static final int DIALOG_SNTP_FAILED_ID = 2;

	private String mServerUrl;
	private SntpOffsetTask mTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mServerUrl = PreferenceHelper.getNtpServerUrl(this);
		
		mTask = (SntpOffsetTask) getLastNonConfigurationInstance();
		if (mTask == null) {
			mTask = new SntpOffsetTask();
		}
		
		mTask.setOnSntpOffsetListener(mSntpListener);
		if (mTask.getStatus() == AsyncTask.Status.PENDING) {
			mTask.execute(mServerUrl);
		}
	}
	
	@Override
	public Object onRetainNonConfigurationInstance() {
		mTask.setOnSntpOffsetListener(null);
		return mTask;
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_SNTPING_ID:
			ProgressDialog dialog = new ProgressDialog(this);
			dialog.setMessage(getString(R.string.ig_obtaining_time_offset));
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			return dialog;
		case DIALOG_SNTP_FAILED_ID:
			return new AlertDialog.Builder(this)
			.setTitle(android.R.string.dialog_alert_title)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setMessage(R.string.ig_obtaining_time_offset_failed)
			.setCancelable(false)
			.setPositiveButton(android.R.string.ok, mFailedDialogListener)
			.setNegativeButton(android.R.string.no, mFailedDialogListener)
			.create();
		default:
			return super.onCreateDialog(id);
		}
	}
	
	private final DialogInterface.OnClickListener mFailedDialogListener = new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				mTask = new SntpOffsetTask();
				mTask.setOnSntpOffsetListener(mSntpListener);
				mTask.execute(mServerUrl);
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				setResult(RESULT_CANCELED);
				finish();
				break;
			}
		}
	};
	
	private final OnSntpOffsetListener mSntpListener = new OnSntpOffsetListener() {
		
		@Override
		public void onResult(Long offset) {
			dismissDialog(DIALOG_SNTPING_ID);
			if (offset == null) {
				showDialog(DIALOG_SNTP_FAILED_ID);
				return;
			}
			
			PreferenceHelper.getSharedPreferences(OffsetActivity.this)
			.edit().putLong(getString(R.string.ig_ntp_offset_key), offset).commit();
			
			finish();
		}
		
		@Override
		public void onPreLaunch() {
			showDialog(DIALOG_SNTPING_ID);
		}
		
	};
	
}

class SntpOffsetTask extends AsyncTask<String, Void, Long> {
	
	public interface OnSntpOffsetListener {
		public void onPreLaunch();
		public void onResult(Long offset);
	}
	
	private OnSntpOffsetListener mListener;
	
	public void setOnSntpOffsetListener(OnSntpOffsetListener listener) {
		mListener = listener;
	}
	
	@Override
	protected void onPreExecute() {
		if (mListener != null) {
			mListener.onPreLaunch();
		}
	}
	
	@Override
	protected Long doInBackground(String... params) {
		try {
			return SntpProvider.getTimeOffsetFromNtp(params[0]);
		} catch (SntpException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Long result) {
		if (mListener != null) {
			mListener.onResult(result);
		}
	}
}

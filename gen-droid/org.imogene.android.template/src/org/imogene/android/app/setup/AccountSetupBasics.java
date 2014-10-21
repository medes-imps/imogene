package org.imogene.android.app.setup;

import org.imogene.android.notification.NotificationController;
import org.imogene.android.preference.Preferences;
import org.imogene.android.sync.OptimizedSyncClient;
import org.imogene.android.sync.http.OptimizedSyncClientHttp;
import org.imogene.android.template.R;
import org.imogene.android.util.NTPClock;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

import fr.medes.android.os.BaseAsyncTask;
import fr.medes.android.os.BaseAsyncTask.Callback;

public class AccountSetupBasics extends SherlockActivity implements OnClickListener, TextWatcher {

	private static final String TAG = AccountSetupBasics.class.getName();

	private static final int DIALOG_SNTPING_ID = 1;
	private static final int DIALOG_SNTP_FAILED_ID = 2;
	private static final int DIALOG_AUTHING_ID = 3;

	private static final String EXTRA_CHANGE_ACCOUNT = "change-account";

	public static final void actionNewAccount(Activity fromActivity) {
		Intent i = new Intent(fromActivity, AccountSetupBasics.class);
		fromActivity.startActivity(i);
	}

	public static final void actionModifyAccount(Activity fromActivity) {
		Intent i = new Intent(fromActivity, AccountSetupBasics.class);
		i.putExtra(EXTRA_CHANGE_ACCOUNT, true);
		fromActivity.startActivity(i);
	}

	private EditText mLoginView;
	private EditText mPasswordView;
	private EditText mServerView;
	private Button mNextButton;

	private Preferences mPreferences;

	private RetainObject retain;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.imog__account_setup_basics);

		mPreferences = Preferences.getPreferences(this);

		mLoginView = (EditText) findViewById(R.id.imog__login);
		mPasswordView = (EditText) findViewById(R.id.imog__password);
		mServerView = (EditText) findViewById(R.id.imog__account_server);
		mNextButton = (Button) findViewById(R.id.imog__next);

		mNextButton.setOnClickListener(this);

		mLoginView.addTextChangedListener(this);
		mPasswordView.addTextChangedListener(this);
		mServerView.addTextChangedListener(this);

		if (getIntent().getBooleanExtra(EXTRA_CHANGE_ACCOUNT, false)) {
			mServerView.setText(Preferences.getPreferences(this).getSyncServer());

			validateFields();

			if (mNextButton.isEnabled()) {
				onNext();
			}
		} else {
			mLoginView.setText(null);
			mPasswordView.setText(null);
		}

		retain = (RetainObject) getLastNonConfigurationInstance();
		if (retain == null) {
			retain = new RetainObject();
		}

		if (retain.authenticationTask != null) {
			retain.authenticationTask.setCallback(mAuthenticationCallback);
		}

		if (retain.sntpOffsetTask != null) {
			retain.sntpOffsetTask.setCallback(mSntpOffsetCallback);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		validateFields();
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return retain;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (retain.authenticationTask != null) {
			retain.authenticationTask.setCallback(null);
		}

		if (retain.sntpOffsetTask != null) {
			retain.sntpOffsetTask.setCallback(null);
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_SNTPING_ID: {
			ProgressDialog dialog = new ProgressDialog(this);
			dialog.setMessage(getString(R.string.imog__obtaining_time_offset));
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			return dialog;
		}
		case DIALOG_SNTP_FAILED_ID: {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(android.R.string.dialog_alert_title);
			builder.setMessage(R.string.imog__obtaining_time_offset_failed);
			builder.setCancelable(false);
			builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					launchSntpOffsetTask();
				}
			});
			builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					AccountSetupShortPassword.actionNewShortPassword(AccountSetupBasics.this);
					finish();
				}
			});
			return builder.create();
		}
		case DIALOG_AUTHING_ID: {
			ProgressDialog dialog = new ProgressDialog(this);
			dialog.setMessage(getString(R.string.imog__authing));
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			return dialog;
		}
		default:
			return super.onCreateDialog(id);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		validateFields();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	private void validateFields() {
		boolean valid = required(mLoginView) && required(mPasswordView) && required(mServerView);
		mNextButton.setEnabled(valid);
	}

	private void onNext() {
		launchAuthenticationTask();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imog__next:
			onNext();
			break;
		}
	}

	private void launchAuthenticationTask() {
		String login = mLoginView.getText().toString();
		String password = mPasswordView.getText().toString();
		String serverUrl = mServerView.getText().toString();
		retain.authenticationTask = new AuthenticationTask(serverUrl, login, password);
		retain.authenticationTask.setCallback(mAuthenticationCallback);
		retain.authenticationTask.execute();
	}

	private void launchSntpOffsetTask() {
		retain.sntpOffsetTask = new SntpOffsetTask(this);
		retain.sntpOffsetTask.setCallback(mSntpOffsetCallback);
		retain.sntpOffsetTask.execute(mPreferences.getNtpHost());
	}

	private void onSntpOffsetUpdated(Boolean success) {
		dismissDialog(DIALOG_SNTPING_ID);

		if (retain.sntpOffsetTask != null) {
			retain.sntpOffsetTask.setCallback(null);
			retain.sntpOffsetTask = null;
		}

		if (success != null && success) {
			AccountSetupShortPassword.actionNewShortPassword(this);
			finish();
		} else {
			showDialog(DIALOG_SNTP_FAILED_ID);
		}

	}

	private void onAuthenticationResult(boolean authenticated) {
		dismissDialog(DIALOG_AUTHING_ID);

		if (retain.authenticationTask != null) {
			retain.authenticationTask.setCallback(null);
			retain.authenticationTask = null;
		}

		if (authenticated) {
			String login = mLoginView.getText().toString();
			String password = mPasswordView.getText().toString();
			String server = mServerView.getText().toString();
			mPreferences.setSyncLogin(login);
			mPreferences.setSyncPassword(password);
			mPreferences.setSyncServer(server);
			NotificationController.cancelNotification(NotificationController.NOTIFICATION_AUTHFAILED_ID);
			Toast.makeText(this, R.string.imog__auth_success, Toast.LENGTH_SHORT).show();
			launchSntpOffsetTask();
		} else {
			Toast.makeText(this, R.string.imog__auth_failed, Toast.LENGTH_SHORT).show();
		}
	}

	private static final boolean required(EditText editText) {
		return editText.getText() != null && editText.getText().length() != 0;
	}

	private final Callback<String, Void, Boolean> mSntpOffsetCallback = new Callback<String, Void, Boolean>() {

		@Override
		public void onAttachedToTask(Status status, Boolean result) {
			if (status == Status.FINISHED) {
				onSntpOffsetUpdated(result);
			}
		}

		@Override
		public void onCancelled() {
		}

		@Override
		public void onPostExecute(Boolean result) {
			onSntpOffsetUpdated(result);
		}

		@Override
		public void onPreExecute() {
			showDialog(DIALOG_SNTPING_ID);
		}

		@Override
		public void onProgressUpdate(Void... values) {
		}

	};

	private final Callback<Void, Void, Boolean> mAuthenticationCallback = new Callback<Void, Void, Boolean>() {

		@Override
		public void onAttachedToTask(Status status, Boolean result) {
			if (status == Status.FINISHED) {
				onAuthenticationResult(result);
			}
		}

		@Override
		public void onCancelled() {
		}

		@Override
		public void onPostExecute(Boolean result) {
			onAuthenticationResult(result);
		}

		@Override
		public void onPreExecute() {
			showDialog(DIALOG_AUTHING_ID);
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

	private static class AuthenticationTask extends BaseAsyncTask<Void, Void, Boolean> {

		private String server;
		private String login;
		private String password;

		public AuthenticationTask(String server, String login, String password) {
			this.server = server;
			this.login = login;
			this.password = password;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			OptimizedSyncClient sync = new OptimizedSyncClientHttp(server, login, password);
			try {
				return sync.authentication();
			} catch (Exception e) {
				Log.e(TAG, "Authentication error", e);
			}
			return false;
		}

	}

	private static class RetainObject {
		private AuthenticationTask authenticationTask;
		private SntpOffsetTask sntpOffsetTask;
	}
}

package org.imogene.android.app.setup;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBar;

import org.imogene.android.preference.Preferences;
import org.imogene.android.sync.OptimizedSyncClient;
import org.imogene.android.sync.SynchronizationException;
import org.imogene.android.sync.http.OptimizedSyncClientHttp;
import org.imogene.android.template.R;
import org.imogene.android.util.ntp.SntpException;
import org.imogene.android.util.ntp.SntpProvider;
import org.imogene.android.util.os.BaseAsyncTask;
import org.imogene.android.util.os.TaskManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AccountSetupBasics extends GDActivity implements OnClickListener, TextWatcher {

	private static final int DIALOG_SNTPING_ID = 1;
	private static final int DIALOG_SNTP_FAILED_ID = 2;
	private static final int DIALOG_AUTHING_ID = 3;
	private static final int DIALOG_AUTH_FAILED_ID = 4;

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

	private TaskManager<AccountSetupBasics> mTaskManager;

	public AccountSetupBasics() {
		super(ActionBar.Type.Empty);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setActionBarContentView(R.layout.ig_account_setup_basics);

		mPreferences = Preferences.getPreferences(this);

		mLoginView = (EditText) findViewById(R.id.ig_account_login);
		mPasswordView = (EditText) findViewById(R.id.ig_account_password);
		mServerView = (EditText) findViewById(R.id.ig_account_server);
		mNextButton = (Button) findViewById(R.id.ig_next);

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

		mTaskManager = (TaskManager<AccountSetupBasics>) getLastNonConfigurationInstance();
		if (mTaskManager == null) {
			mTaskManager = new TaskManager<AccountSetupBasics>();
		}
		mTaskManager.attach(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		validateFields();
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		mTaskManager.detach();
		return mTaskManager;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_SNTPING_ID: {
			ProgressDialog dialog = new ProgressDialog(this);
			dialog.setMessage(getString(R.string.ig_obtaining_time_offset));
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			return dialog;
		}
		case DIALOG_SNTP_FAILED_ID: {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(android.R.string.dialog_alert_title);
			builder.setIcon(android.R.drawable.ic_dialog_alert);
			builder.setMessage(R.string.ig_obtaining_time_offset_failed);
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
			dialog.setMessage(getString(R.string.ig_obtaining_roles));
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			return dialog;
		}
		case DIALOG_AUTH_FAILED_ID: {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(android.R.string.dialog_alert_title);
			builder.setIcon(android.R.drawable.ic_dialog_alert);
			builder.setMessage(R.string.ig_obtaining_roles_failed);
			builder.setCancelable(false);
			builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					launchAuthenticationTask();
				}
			});
			builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dismissDialog(DIALOG_AUTH_FAILED_ID);
				}
			});
			return builder.create();
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
		case R.id.ig_next:
			onNext();
			break;
		}
	}

	private void launchAuthenticationTask() {
		String login = mLoginView.getText().toString();
		String password = mPasswordView.getText().toString();
		String serverUrl = mServerView.getText().toString();
		String terminal = mPreferences.getSyncTerminal();
		boolean httpAuthentication = mPreferences.isHttpAuthenticationEnabled();
		mTaskManager.execute(1, new AuthenticationTask(serverUrl, login, password, terminal, httpAuthentication));
	}

	private void launchSntpOffsetTask() {
		mTaskManager.execute(2, new SntpOffsetTask(), mPreferences.getNtpHost());
	}

	private void onSntpOffsetUpdated(Long offset) {
		dismissDialog(DIALOG_SNTPING_ID);
		if (offset != null) {
			mPreferences.setNtpOffset(offset);
			AccountSetupShortPassword.actionNewShortPassword(this);
			finish();
		} else {
			showDialog(DIALOG_SNTP_FAILED_ID);
		}
		mTaskManager.remove(2);
	}

	private void onRolesReceived(String server, String login, String password, String roles) {
		dismissDialog(DIALOG_AUTHING_ID);
		if (roles != null) {
			mPreferences.setSyncLogin(login);
			mPreferences.setSyncPassword(password);
			mPreferences.setSyncRoles(roles);
			mPreferences.setSyncServer(server);
			launchSntpOffsetTask();
		} else {
			showDialog(DIALOG_AUTH_FAILED_ID);
		}
		mTaskManager.remove(1);
	}

	private static final boolean required(EditText editText) {
		return editText.getText() != null && editText.getText().length() != 0;
	}

	private static class SntpOffsetTask extends BaseAsyncTask<AccountSetupBasics, String, Void, Long> {

		@Override
		protected void onPreExecute() {
			if (callback != null) {
				callback.showDialog(DIALOG_SNTPING_ID);
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
			super.onPostExecute(result);
			if (callback != null) {
				callback.onSntpOffsetUpdated(result);
			}
		}
	}

	private static class AuthenticationTask extends BaseAsyncTask<AccountSetupBasics, Void, Void, String> {

		private String server;
		private String login;
		private String password;
		private String terminal;
		private boolean httpAuthentication;

		public AuthenticationTask(String server, String login, String password, String terminal,
				boolean httpAuthentication) {
			this.server = server;
			this.login = login;
			this.password = password;
			this.terminal = terminal;
			this.httpAuthentication = httpAuthentication;
		}

		@Override
		protected void onPreExecute() {
			if (callback != null) {
				callback.showDialog(DIALOG_AUTHING_ID);
			}
		}

		@Override
		protected String doInBackground(Void... params) {
			OptimizedSyncClient sync;
			if (httpAuthentication) {
				sync = new OptimizedSyncClientHttp(server, login, password);
			} else {
				sync = new OptimizedSyncClientHttp(server);
			}
			try {
				String auth = sync.authentication(login, password, terminal);
				if (auth != null) {
					String id = auth.split(";")[0];
					String roles = auth.replaceFirst(id + ";", "");
					return roles;
				}
			} catch (SynchronizationException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (callback != null) {
				callback.onRolesReceived(server, login, password, result);
			}
		}
	}

}

package org.imogene.android.app.setup;

import org.imogene.android.notification.NotificationController;
import org.imogene.android.preference.Preferences;
import org.imogene.android.sync.OptimizedSyncClient;
import org.imogene.android.sync.http.OptimizedSyncClientHttp;
import org.imogene.android.template.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import fr.medes.android.os.BaseAsyncTask;
import fr.medes.android.os.BaseAsyncTask.Callback;

public class AccountPasswordChanged extends Activity implements OnClickListener {

	private static final String TAG = AccountPasswordChanged.class.getName();

	private static final int DIALOG_AUTHING_ID = 3;

	private Preferences mPreferences;

	private RetainObject retain;

	private TextView mLoginView;
	private TextView mPasswordView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imog__account_password_changed);

		mLoginView = (TextView) findViewById(R.id.imog__login);
		mPasswordView = (TextView) findViewById(R.id.imog__password);

		findViewById(android.R.id.button1).setOnClickListener(this);
		findViewById(android.R.id.button2).setOnClickListener(this);

		mPreferences = Preferences.getPreferences(this);
		String login = mPreferences.getSyncLogin();

		mLoginView.setText(login);

		retain = (RetainObject) getLastNonConfigurationInstance();
		if (retain == null) {
			retain = new RetainObject();
		}

		if (retain.authenticationTask != null) {
			retain.authenticationTask.setCallback(mAuthenticationCallback);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (retain.authenticationTask != null) {
			retain.authenticationTask.setCallback(null);
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
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

	private void launchAuthenticationTask() {
		String login = mLoginView.getText().toString();
		String password = mPasswordView.getText().toString();
		String serverUrl = mPreferences.getSyncServer();
		String terminal = mPreferences.getSyncTerminal();
		boolean httpAuthentication = mPreferences.isHttpAuthenticationEnabled();
		retain.authenticationTask = new AuthenticationTask(serverUrl, login, password, terminal, httpAuthentication);
		retain.authenticationTask.setCallback(mAuthenticationCallback);
		retain.authenticationTask.execute();
	}

	private void onRolesReceived(String roles) {
		dismissDialog(DIALOG_AUTHING_ID);

		if (retain.authenticationTask != null) {
			retain.authenticationTask.setCallback(null);
			retain.authenticationTask = null;
		}

		if (roles != null) {
			String login = mLoginView.getText().toString();
			String password = mPasswordView.getText().toString();
			mPreferences.setSyncLogin(login);
			mPreferences.setSyncPassword(password);
			NotificationController.cancelNotification(NotificationController.NOTIFICATION_AUTHFAILED_ID);
			Toast.makeText(this, R.string.imog__auth_success, Toast.LENGTH_SHORT).show();
			finish();
		} else {
			Toast.makeText(this, R.string.imog__auth_failed, Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == android.R.id.button1) {
			launchAuthenticationTask();
		} else if (id == android.R.id.button2) {
			finish();
		}
	}

	private final Callback<Void, Void, String> mAuthenticationCallback = new Callback<Void, Void, String>() {

		@Override
		public void onAttachedToTask(Status status, String result) {
			if (status == Status.FINISHED) {
				onRolesReceived(result);
			}
		}

		@Override
		public void onCancelled() {
		}

		@Override
		public void onPostExecute(String result) {
			onRolesReceived(result);
		}

		@Override
		public void onPreExecute() {
			showDialog(DIALOG_AUTHING_ID);
		}

		@Override
		public void onProgressUpdate(Void... values) {
		}

	};

	private static class AuthenticationTask extends BaseAsyncTask<Void, Void, String> {

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
			} catch (Throwable t) {
				Log.e(TAG, "Authentication error", t);
			}
			return null;
		}

	}

	private static class RetainObject {
		private AuthenticationTask authenticationTask;
	}

}

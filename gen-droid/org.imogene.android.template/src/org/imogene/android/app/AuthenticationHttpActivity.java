package org.imogene.android.app;

import org.imogene.android.preference.PreferenceHelper;
import org.imogene.android.sync.OptimizedSyncClient;
import org.imogene.android.sync.SynchronizationException;
import org.imogene.android.sync.http.OptimizedSyncClientHttp;
import org.imogene.android.template.R;
import org.imogene.android.util.base64.Base64;
import org.imogene.android.util.encryption.EncryptionManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class AuthenticationHttpActivity extends Activity implements OnClickListener {
	
	private static final String EXTRA_SERVER = "Authentication_server";
	private static final String EXTRA_LOGIN = "Authentication_login";
	private static final String EXTRA_PASSWORD = "Authentication_password";
	private static final String EXTRA_HARDWARE = "Authentication_hardware";
	
	private static final int DIALOG_AUTHING_ID = 1;
	private static final int DIALOG_AUTH_FAILED_ID = 2;
	
		
	public static final Intent getAuthenticationIntent(Context context, String server, String login, String password, String hardware) {
		Intent intent = new Intent(context, AuthenticationHttpActivity.class);
		intent.putExtra(EXTRA_SERVER, server);
		intent.putExtra(EXTRA_LOGIN, login);
		intent.putExtra(EXTRA_PASSWORD, password);
		intent.putExtra(EXTRA_HARDWARE, hardware);
		return intent;
	}
	
	private String mServer;
	private String mLogin;
	private String mPassword;
	private String mHardware;

	private Thread mAuthenticationThread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		mServer = intent.getStringExtra(EXTRA_SERVER);
		mLogin = intent.getStringExtra(EXTRA_LOGIN);
		mPassword = intent.getStringExtra(EXTRA_PASSWORD);
		mHardware = intent.getStringExtra(EXTRA_HARDWARE);

		if (PreferenceHelper.isAdmin(this, mLogin, mPassword))
			onAuthSucceed(PreferenceHelper.getAdminRoles(this));
		else
			launchAuthentication();
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_AUTHING_ID:
			ProgressDialog dialog = new ProgressDialog(this);
			dialog.setMessage(getString(R.string.ig_obtaining_roles));
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			return dialog;
		case DIALOG_AUTH_FAILED_ID:
			return new AlertDialog.Builder(this)
			.setTitle(android.R.string.dialog_alert_title)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setMessage(R.string.ig_obtaining_roles_failed)
			.setCancelable(false)
			.setPositiveButton(android.R.string.ok, this)
			.setNegativeButton(android.R.string.no, this)
			.create();
		default:
			return super.onCreateDialog(id);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mAuthenticationThread != null) {
			mAuthenticationThread.interrupt();
			try {
				mAuthenticationThread.join();
			} catch (InterruptedException e) {
				// Don't care
			}
		}
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case DialogInterface.BUTTON_POSITIVE:
			launchAuthentication();
			break;
		case DialogInterface.BUTTON_NEGATIVE:
			setResult(RESULT_CANCELED);
			finish();
			break;
		}
	}
	
	private void launchAuthentication() {
		mAuthenticationThread = new Thread(new AuthenticationRunnable(), "AuthenticationThread");
		mAuthenticationThread.setPriority(Thread.MAX_PRIORITY);
		mAuthenticationThread.start();
	}
	
	private void onAuthRunning() {
		showDialog(DIALOG_AUTHING_ID);
	}
	
	private void onAuthSucceed(String roles) {
		EncryptionManager em = EncryptionManager.getInstance(this);
		String encLogin = new String(Base64.encodeBase64(em.encrypt(mLogin.getBytes())));
		String encPassword = new String(Base64.encodeBase64(em.encrypt(mPassword.getBytes())));
		PreferenceHelper.getSharedPreferences(this).edit()
		.remove(getString(R.string.ig_current_login_key))
		.remove(getString(R.string.ig_current_roles_key))
		.putString(getString(R.string.ig_sync_login_key), encLogin)
		.putString(getString(R.string.ig_sync_password_key), encPassword)
		.putString(getString(R.string.ig_sync_roles_key), roles)
		.putString(getString(R.string.ig_sync_server_url_key), mServer)
		.commit();
		removeDialog(DIALOG_AUTHING_ID);
		setResult(RESULT_OK);
		finish();
	}
	
	private void onAuthFailed() {
		removeDialog(DIALOG_AUTHING_ID);
		showDialog(DIALOG_AUTH_FAILED_ID);
	}
	
	private class AuthenticationRunnable implements Runnable {
		
		@Override
		public void run() {
			reportAuthRunning();
			OptimizedSyncClient sync;
			if(PreferenceHelper.isHttpAuthenticated(AuthenticationHttpActivity.this)){
				sync = new OptimizedSyncClientHttp(mServer, mLogin, mPassword);
			}else{
				sync = new OptimizedSyncClientHttp(mServer);
			}
			try {
				String auth = sync.authentication(mLogin, mPassword, mHardware);
				if (auth != null) {
					String id = auth.split(";")[0];
					String roles = auth.replaceFirst(id + ";", "");
					reportAuthSucceed(roles);
				} else {
					reportAuthFailed();
				}
			} catch (SynchronizationException e) {
				reportAuthFailed();
			}
		}
		
		private void reportAuthRunning() {
			mHandler.sendEmptyMessage(MSG_AUTHING);
		}
		
		private void reportAuthSucceed(String roles) {
			mHandler.sendMessage(Message.obtain(mHandler, MSG_AUTH_SUCCES, roles));
		}
		
		private void reportAuthFailed() {
			mHandler.sendEmptyMessage(MSG_AUTH_FAILED);
		}

	}
	
	private static final int MSG_AUTHING = 1;
	private static final int MSG_AUTH_SUCCES = 2;
	private static final int MSG_AUTH_FAILED = 3;
	
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MSG_AUTHING:
				onAuthRunning();
				break;
			case MSG_AUTH_SUCCES:
				onAuthSucceed((String) msg.obj);
				break;
			case MSG_AUTH_FAILED:
				onAuthFailed();
				break;
			}
			super.handleMessage(msg);
		};
	};

}

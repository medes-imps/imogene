package org.imogene.android.app.setup;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBar;

import org.imogene.android.app.AuthenticationHttpActivity;
import org.imogene.android.app.OffsetActivity;
import org.imogene.android.preference.PreferenceHelper;
import org.imogene.android.template.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AccountSetupBasics extends GDActivity implements OnClickListener,
		TextWatcher {
	
	private static final int ACTIVITY_OFFSET_ID = 1;
	private static final int ACTIVITY_AUTHENTICATION_ID = 2;

	private static final String EXTRA_CHANGE_ACCOUNT = "change-account";

	private EditText mLoginView;
	private EditText mPasswordView;
	private EditText mServerView;
	private Button mNextButton;
	
	private String mHardwareId = null;

	public static final void actionNewAccount(Activity fromActivity) {
		Intent i = new Intent(fromActivity, AccountSetupBasics.class);
		fromActivity.startActivity(i);
	}

	public static final void actionModifyAccount(Activity fromActivity) {
		Intent i = new Intent(fromActivity, AccountSetupBasics.class);
		i.putExtra(EXTRA_CHANGE_ACCOUNT, true);
		fromActivity.startActivity(i);
	}
	
	public AccountSetupBasics() {
		super(ActionBar.Type.Empty);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setActionBarContentView(R.layout.ig_account_setup_basics);
		mLoginView = (EditText) findViewById(R.id.ig_account_login);
		mPasswordView = (EditText) findViewById(R.id.ig_account_password);
		mServerView = (EditText) findViewById(R.id.ig_account_server);
		mNextButton = (Button) findViewById(R.id.ig_next);

		mNextButton.setOnClickListener(this);

		mLoginView.addTextChangedListener(this);
		mPasswordView.addTextChangedListener(this);
		mServerView.addTextChangedListener(this);
		
		mHardwareId = PreferenceHelper.getHardwareId(this);
		
		if (getIntent().getBooleanExtra(EXTRA_CHANGE_ACCOUNT, false)) {
			mServerView.setText(PreferenceHelper.getServerUrl(this));
			
			validateFields();

			if (mNextButton.isEnabled()) {
				onNext();
			}
		} else {
			mLoginView.setText(null);
			mPasswordView.setText(null);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ACTIVITY_OFFSET_ID) {
			AccountSetupShortPassword.actionNewShortPassword(this);
			finish();
		} else if (resultCode == RESULT_OK && requestCode == ACTIVITY_AUTHENTICATION_ID) {
			startActivityForResult(new Intent(this, OffsetActivity.class), ACTIVITY_OFFSET_ID);
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		validateFields();
	}

	@Override
	public void afterTextChanged(Editable s) {
		validateFields();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	private void validateFields() {
		boolean valid = required(mLoginView) && required(mPasswordView) && required(mServerView);
		mNextButton.setEnabled(valid);
	}

	private void onNext() {
		String login = mLoginView.getText().toString();
		String password = mPasswordView.getText().toString();
		if (!mServerView.getText().toString().endsWith("/")) {
			mServerView.getText().append('/');
		}
		String serverUrl = mServerView.getText().toString();
		
		startActivityForResult(AuthenticationHttpActivity.getAuthenticationIntent(this, serverUrl, login, password, mHardwareId), ACTIVITY_AUTHENTICATION_ID);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ig_next:
			onNext();
			break;
		}
	}

	private static final boolean required(EditText editText) {
		return editText.getText() != null && editText.getText().length() != 0;
	}

}

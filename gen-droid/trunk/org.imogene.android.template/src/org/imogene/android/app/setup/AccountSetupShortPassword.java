package org.imogene.android.app.setup;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBar;

import org.imogene.android.preference.PreferenceHelper;
import org.imogene.android.template.R;
import org.imogene.android.util.base64.Base64;
import org.imogene.android.util.encryption.EncryptionManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AccountSetupShortPassword extends GDActivity implements
		OnClickListener, TextWatcher {

	private static final int ERROR_DIALOG_ID = 1;

	private EditText mShortpwView;
	private EditText mShortpwConfirmView;
	private Button mNextButton;

	public static final void actionNewShortPassword(Activity fromActivity) {
		Intent i = new Intent(fromActivity, AccountSetupShortPassword.class);
		fromActivity.startActivity(i);
	}

	public AccountSetupShortPassword() {
		super(ActionBar.Type.Empty);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.ig_account_setup_short_password);

		mShortpwView = (EditText) findViewById(R.id.ig_account_shortpw);
		mShortpwConfirmView = (EditText) findViewById(R.id.ig_account_shotpw_confirm);
		mNextButton = (Button) findViewById(R.id.ig_next);

		mNextButton.setOnClickListener(this);

		mShortpwView.setTransformationMethod(PasswordTransformationMethod
				.getInstance());
		mShortpwConfirmView
				.setTransformationMethod(PasswordTransformationMethod
						.getInstance());

		mShortpwView.addTextChangedListener(this);
		mShortpwConfirmView.addTextChangedListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		validateFields();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		if (id == ERROR_DIALOG_ID) {
			return new AlertDialog.Builder(this).setTitle(
					android.R.string.dialog_alert_title).setIcon(
					android.R.drawable.ic_dialog_alert).setMessage(
					R.string.ig_account_setup_shortpw_error).setCancelable(false)
					.setPositiveButton(android.R.string.ok, null).create();
		} else {
			return super.onCreateDialog(id);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ig_next:
			onNext();
			break;
		}
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		validateFields();
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
	}

	private static final boolean required(EditText editText) {
		return editText.getText() != null && editText.getText().length() >= 4;
	}

	private void validateFields() {
		boolean valid = required(mShortpwView) && required(mShortpwConfirmView);
		mNextButton.setEnabled(valid);
	}

	private void onNext() {
		String shortpw = mShortpwView.getText().toString();
		String shortpwConfirm = mShortpwConfirmView.getText().toString();
		if (shortpw != null && shortpw.equals(shortpwConfirm)) {
			String encsp = new String(Base64.encodeBase64(EncryptionManager.getInstance(this).encrypt(shortpw.getBytes())));
			PreferenceHelper.getSharedPreferences(this).edit().putString(
					getString(R.string.ig_sync_shortpw_key), encsp).commit();

			Intent intent = new Intent(this, getGDApplication().getHomeActivityClass());
			startActivity(intent);
			finish();
		} else {
			showDialog(ERROR_DIALOG_ID);
		}
	}

}

package org.imogene.android.app.setup;

import org.imogene.android.common.entity.ImogHelper;
import org.imogene.android.preference.Preferences;
import org.imogene.android.template.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class AccountSetupShortPassword extends SherlockActivity implements OnClickListener, TextWatcher {

	private EditText mShortpwView;
	private EditText mShortpwConfirmView;
	private Button mNextButton;

	public static final void actionNewShortPassword(Activity fromActivity) {
		Intent i = new Intent(fromActivity, AccountSetupShortPassword.class);
		fromActivity.startActivity(i);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imog__account_setup_short_password);

		mShortpwView = (EditText) findViewById(R.id.imog__account_shortpw);
		mShortpwConfirmView = (EditText) findViewById(R.id.imog__account_shotpw_confirm);
		mNextButton = (Button) findViewById(R.id.imog__next);

		mNextButton.setOnClickListener(this);

		mShortpwView.setTransformationMethod(PasswordTransformationMethod.getInstance());
		mShortpwConfirmView.setTransformationMethod(PasswordTransformationMethod.getInstance());

		mShortpwView.addTextChangedListener(this);
		mShortpwConfirmView.addTextChangedListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		validateFields();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imog__next:
			onNext();
			break;
		}
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		validateFields();
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
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
			Preferences.getPreferences(this).setShortPassword(shortpw);
			Toast.makeText(this, R.string.imog__account_setup_shortpw_success, Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, ImogHelper.getInstance().getHomeActivityClass());
			startActivity(intent);
			finish();
		} else {
			Toast.makeText(this, R.string.imog__account_setup_shortpw_failed, Toast.LENGTH_SHORT).show();
		}
	}

}

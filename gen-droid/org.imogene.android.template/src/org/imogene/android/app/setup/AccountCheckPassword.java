package org.imogene.android.app.setup;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBar;

import org.imogene.android.preference.PreferenceHelper;
import org.imogene.android.template.R;

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

public class AccountCheckPassword extends GDActivity implements OnClickListener, TextWatcher {
	
	private static final int ERROR_DIALOG_ID = 1;
	
	public static final void accountCheckShortPassword(Activity fromActivity) {
		Intent intent = new Intent(fromActivity, AccountCheckPassword.class);
		fromActivity.startActivity(intent);
	}
	
	private EditText mShortpwView;
	private Button mStartButton;
	private Button mChangeUserView;
	
	public AccountCheckPassword() {
		super(ActionBar.Type.Empty);
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       	setActionBarContentView(R.layout.ig_account_check_shortpw);
        	
       	mShortpwView = (EditText) findViewById(R.id.ig_check_shortpw);
       	mStartButton = (Button) findViewById(R.id.ig_start);
       	mChangeUserView = (Button) findViewById(R.id.ig_change_user);
        	
       	mShortpwView.setTransformationMethod(PasswordTransformationMethod.getInstance());
       	
       	mStartButton.setOnClickListener(this);
       	mChangeUserView.setOnClickListener(this);
        	
       	mShortpwView.addTextChangedListener(this);
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
    	super.onPostCreate(savedInstanceState);
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
    	if (id == ERROR_DIALOG_ID) {
			return new AlertDialog.Builder(this)
			.setTitle(android.R.string.dialog_alert_title)
			.setIcon(android.R.drawable.ic_dialog_alert)
    		.setMessage(R.string.ig_account_setup_shortpw_error)
    		.setCancelable(false)
    		.setPositiveButton(android.R.string.ok, null)
    		.create();
    	} else {
    		return super.onCreateDialog(id);
    	}
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	validateFields();
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
        boolean valid = mShortpwView.getText() != null && mShortpwView.getText().length() >= 4;
        mStartButton.setEnabled(valid);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ig_start:
			final String shortpw = PreferenceHelper.getShortPassword(this);
			if (shortpw != null && shortpw.equals(mShortpwView.getText().toString())) {
				Intent intent = new Intent(this, getGDApplication().getHomeActivityClass());
				startActivity(intent);
				finish();
			} else {
				showDialog(ERROR_DIALOG_ID);
			}
			break;
		case R.id.ig_change_user:
			AccountSetupBasics.actionNewAccount(this);
			finish();
		}
	}
}

package org.imogene.android.app.setup;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBar;

import org.imogene.android.template.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AccountCreationIntroduction extends GDActivity implements OnClickListener{
	
	private Button mNextButton;
	
	public static final void accountCreationIntroduction(Activity fromActivity) {
		Intent i = new Intent(fromActivity, AccountCreationIntroduction.class);
		fromActivity.startActivity(i);
	}
	
	public AccountCreationIntroduction() {
		super(ActionBar.Type.Empty);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.ig_account_creation_introduction);
		
		mNextButton = (Button) findViewById(R.id.ig_next);
		
		mNextButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		AccountSetupBasics.actionNewAccount(this);
		finish();
	}

}

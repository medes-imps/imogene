package org.imogene.android.app.setup;

import org.imogene.android.template.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;

public class AccountCreationIntroduction extends SherlockActivity implements OnClickListener {

	private Button mNextButton;

	public static final void accountCreationIntroduction(Activity fromActivity) {
		Intent i = new Intent(fromActivity, AccountCreationIntroduction.class);
		fromActivity.startActivity(i);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imog__account_creation_introduction);

		mNextButton = (Button) findViewById(R.id.imog__next);

		mNextButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		AccountSetupBasics.actionNewAccount(this);
		finish();
	}

}

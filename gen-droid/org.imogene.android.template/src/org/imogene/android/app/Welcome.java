package org.imogene.android.app;

import org.imogene.android.app.setup.AccountCheckPassword;
import org.imogene.android.app.setup.AccountCreationIntroduction;
import org.imogene.android.preference.Preferences;

import android.app.Activity;
import android.os.Bundle;

public class Welcome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final String shortpw = Preferences.getPreferences(this).getShortPassword();
		if (shortpw != null) {
			AccountCheckPassword.accountCheckShortPassword(this);
		} else {
			AccountCreationIntroduction.accountCreationIntroduction(this);
		}
		finish();
	}

}

package org.imogene.android.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ProxyServiceActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String targetClass = getIntent().getStringExtra("targetClass");
		String targetAction = getIntent().getStringExtra("action");
		try {
			Class<?> clazz = Class.forName(targetClass);
			Intent intent = new Intent(this, clazz);
			intent.setAction(targetAction);
			startService(intent);
		} catch (ClassNotFoundException e) {
		}
		setResult(RESULT_OK);
		finish();
	}

}

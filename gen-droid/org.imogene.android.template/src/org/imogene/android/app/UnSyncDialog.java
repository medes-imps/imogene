package org.imogene.android.app;

import org.imogene.android.Constants.Extras;
import org.imogene.android.Constants.Intents;
import org.imogene.android.template.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class UnSyncDialog extends Activity implements DialogInterface.OnClickListener {
	
	private static final int DIALOG_UNSYNC_ID = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showDialog(DIALOG_UNSYNC_ID);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (DIALOG_UNSYNC_ID) {
		case DIALOG_UNSYNC_ID:
			return new AlertDialog.Builder(this)
			.setTitle(android.R.string.dialog_alert_title)
			.setMessage(R.string.ig_renew_database_unsync_message)
			.setPositiveButton(android.R.string.yes, this)
			.setNegativeButton(android.R.string.no, this)
			.create();
		default:
			return super.onCreateDialog(id);
		}
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == DialogInterface.BUTTON_POSITIVE) {
			sendBroadcast(new Intent(Intents.ACTION_RM_DATABASE).putExtra(Extras.EXTRA_FORCE, true));
			finish();
		} else {
			finish();
		}
	}

}

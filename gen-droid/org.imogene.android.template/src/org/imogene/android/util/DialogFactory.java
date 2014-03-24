package org.imogene.android.util;

import org.imogene.android.template.R;

import android.app.AlertDialog;
import android.content.Context;

public class DialogFactory {

	public static AlertDialog createActivityNotFoundDialog(Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.imog__anf_title);
		builder.setMessage(R.string.imog__anf);
		builder.setPositiveButton(android.R.string.ok, null);
		builder.setCancelable(false);
		return builder.create();
	}

	public static AlertDialog createIamLostDialog(Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.imog__iamlost_title);
		builder.setItems(IamLost.getInstance().get(), null);
		builder.setPositiveButton(android.R.string.ok, null);
		return builder.create();
	}
}

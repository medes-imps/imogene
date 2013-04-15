package org.imogene.android.util.content;

import java.util.ArrayList;
import java.util.List;

import org.imogene.android.Constants.Extras;
import org.imogene.android.Constants.Intents;
import org.imogene.android.Constants.Packages;
import org.imogene.android.database.sqlite.stmt.Where;
import org.imogene.android.util.dialog.DialogFactory;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * Contains static methods which operate on {@link Intent}.
 * 
 * @author MEDES-IMPS
 */
public class IntentUtils {

	/**
	 * Method to catch {@link ActivityNotFoundException}. If the {@link Intent}
	 * initiated is known it opens the market page of the application that can
	 * handle it. If not it nicely opens an information dialog.
	 * 
	 * @param exception The catch exception.
	 * @param context The current context.
	 * @param intent The initial intent.
	 */
	public static void treatException(ActivityNotFoundException exception, Context context, Intent intent) {
		if (Intents.ACTION_SCAN.equals(intent.getAction())) {
			Uri uri = Uri.parse("market://search?q=pname:" + Packages.PACKAGE_ZXING);
			Intent i = new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(i);
		} else {
			Log.e(IntentUtils.class.getName(), "error launching an activity", exception);
			DialogFactory.createActivityNotFoundDialog(context).show();
		}
	}

	/**
	 * Append a where query to an intent using {@link Extras#EXTRA_WHERE} and
	 * {@link Extras#EXTRA_ARGS}.
	 * 
	 * @param intent The intent to append the where clause.
	 * @param where The where clause.
	 */
	public static void putWhereExtras(Intent intent, Where where) {
		StringBuilder sb = new StringBuilder();
		List<Object> args = new ArrayList<Object>();
		where.appendSql(null, sb, args);
		intent.putExtra(Extras.EXTRA_WHERE, sb.toString());
		intent.putExtra(Extras.EXTRA_ARGS, args.toArray(new String[args.size()]));
	}
}

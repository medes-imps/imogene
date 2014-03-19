package org.imogene.android.util;

import java.util.ArrayList;
import java.util.List;

import org.imogene.android.Constants.Extras;
import org.imogene.android.Constants.Intents;
import org.imogene.android.Constants.Packages;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import fr.medes.android.database.sqlite.stmt.Where;

/**
 * Contains static methods which operate on {@link Intent}.
 * 
 * @author MEDES-IMPS
 */
public class IntentUtils {

	/**
	 * Launch a new activity. You will not receive any information about when the activity exits.
	 * <p>
	 * This method catches the {@link ActivityNotFoundException} for our greatest pleasure.
	 * 
	 * @param context The context from where to start the activity.
	 * @param intent The description of the activity to start.
	 */
	public static void starActivity(Context context, Intent intent) {
		try {
			context.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			IntentUtils.treatException(e, context, intent);
		}
	}

	/**
	 * Launch an activity for which you would like a result when it finished. When this activity exits, your
	 * onActivityResult() method will be called with the given requestCode. Using a negative requestCode is the same as
	 * calling {@link #starActivity(Context, Intent)} (the activity is not launched as a sub-activity).
	 * <p>
	 * This method catches the {@link ActivityNotFoundException} for our greatest pleasure.
	 * 
	 * @param activity The context from where to start the activity and waiting for a result.
	 * @param intent The intent to start.
	 * @param requestCode If >= 0, this code will be returned in onActivityResult() when the activity exits.
	 */
	public static void startActivityForResult(Activity activity, Intent intent, int requestCode) {
		try {
			activity.startActivityForResult(intent, requestCode);
		} catch (ActivityNotFoundException e) {
			IntentUtils.treatException(e, activity, intent);
		}
	}

	/**
	 * Method to catch {@link ActivityNotFoundException}. If the {@link Intent} initiated is known it opens the market
	 * page of the application that can handle it. If not it nicely opens an information dialog.
	 * 
	 * @param exception The catch exception.
	 * @param context The current context.
	 * @param intent The initial intent.
	 */
	public static void treatException(ActivityNotFoundException e, Context context, Intent intent) {
		if (Intents.ACTION_SCAN.equals(intent.getAction())) {
			Uri uri = Uri.parse("market://search?q=pname:" + Packages.PACKAGE_ZXING);
			Intent i = new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(i);
		} else {
			e.printStackTrace();
			DialogFactory.createActivityNotFoundDialog(context).show();
		}
	}

	/**
	 * Append a where query to an intent using {@link Extras#EXTRA_WHERE} and {@link Extras#EXTRA_ARGS}.
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

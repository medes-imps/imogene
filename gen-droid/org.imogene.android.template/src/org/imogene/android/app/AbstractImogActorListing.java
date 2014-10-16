package org.imogene.android.app;

import java.util.Arrays;

import org.imogene.android.database.ImogActorCursorJoiner;
import org.imogene.android.preference.Preferences;
import org.imogene.android.template.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import fr.medes.android.util.encryption.EncryptionManager;

public abstract class AbstractImogActorListing extends ListActivity implements OnClickListener {

	private static final String EXTRA_DISPLAY = "AbstractUserListing_display";
	private static final String EXTRA_LOGIN = "AbstractUserListing_login";
	private static final String EXTRA_PASSWORD = "AbstractUserListing_password";

	private static final int DIALOG_PASSWORD_ID = 1;

	private String display;
	private String login;
	private byte[] password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ImogActorCursorJoiner joiner = getCursor();
		startManagingCursor(joiner);
		setListAdapter(new JoinerAdapter(this, joiner));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(EXTRA_DISPLAY, display);
		outState.putString(EXTRA_LOGIN, login);
		outState.putByteArray(EXTRA_PASSWORD, password);
	}

	@Override
	protected void onRestoreInstanceState(Bundle state) {
		super.onRestoreInstanceState(state);
		display = state.getString(EXTRA_DISPLAY);
		login = state.getString(EXTRA_LOGIN);
		password = state.getByteArray(EXTRA_PASSWORD);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		ImogActorCursorJoiner cursor = (ImogActorCursorJoiner) l.getAdapter().getItem(position);
		display = cursor.getUserDisplay(this);
		login = cursor.getLogin();
		password = cursor.getPassword();
		showDialog(DIALOG_PASSWORD_ID);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_PASSWORD_ID:
			final View textEntryView = LayoutInflater.from(this).inflate(R.layout.imog__alert_dialog_text_entry, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getDisplay());
			builder.setView(textEntryView);
			builder.setPositiveButton(android.R.string.ok, this);
			builder.setNegativeButton(android.R.string.cancel, null);
			return builder.create();
		}
		return super.onCreateDialog(id);
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case DIALOG_PASSWORD_ID:
			dialog.setTitle(getDisplay());
			((TextView) dialog.findViewById(R.id.imog__login)).setText(login);
			((TextView) dialog.findViewById(R.id.imog__password)).setText(null);
			break;
		default:
			super.onPrepareDialog(id, dialog);
			break;
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		final String pwd = ((TextView) ((Dialog) dialog).findViewById(R.id.imog__password)).getText().toString();
		if (validate(pwd)) {
			Preferences prefs = Preferences.getPreferences(this);
			prefs.setCurrentLogin(login);
			setResult(RESULT_OK);
			Toast.makeText(this, R.string.imog__auth_success, Toast.LENGTH_SHORT).show();
			finish();
		} else {
			Toast.makeText(this, R.string.imog__auth_failed, Toast.LENGTH_SHORT).show();
		}
	}

	private String getDisplay() {
		return TextUtils.isEmpty(display) ? getString(android.R.string.unknownName) : display;
	}

	private boolean validate(String pwd) {
		return pwd != null ? Arrays.equals(EncryptionManager.getInstance().encrypt(pwd.getBytes()), password) : false;
	}

	protected abstract ImogActorCursorJoiner getCursor();

	private static class JoinerAdapter extends CursorAdapter {

		public JoinerAdapter(Context context, ImogActorCursorJoiner c) {
			super(context, c);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			ImogActorCursorJoiner c = (ImogActorCursorJoiner) cursor;

			view.findViewById(android.R.id.background).setBackgroundDrawable(c.getDrawable());

			TextView main = (TextView) view.findViewById(android.R.id.text1);
			TextView secondary = (TextView) view.findViewById(android.R.id.text2);

			main.setText(c.getUserDisplay(context));
			secondary.setText(c.getLogin());
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return LayoutInflater.from(context).inflate(R.layout.imog__entity_row, parent, false);
		}

	}

}

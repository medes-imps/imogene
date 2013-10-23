package org.imogene.android.app;

import java.util.Arrays;

import org.imogene.android.database.ImogActorCursorJoiner;
import org.imogene.android.preference.PreferenceHelper;
import org.imogene.android.template.R;
import org.imogene.android.util.base64.Base64;
import org.imogene.android.util.encryption.EncryptionManager;

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

public abstract class AbstractImogActorListing extends ListActivity implements OnClickListener {
	
	private static final String EXTRA_DISPLAY = "AbstractUserListing_display";
	private static final String EXTRA_LOGIN = "AbstractUserListing_login";
	private static final String EXTRA_PASSWORD = "AbstractUserListing_password";
	private static final String EXTRA_ROLES = "AbstractUserListing_roles";
	
	private static final int DIALOG_PASSWORD_ID = 1;
	
	private String display;
	private String login;
	private String roles;
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
		outState.putString(EXTRA_ROLES, roles);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle state) {
		super.onRestoreInstanceState(state);
		display = state.getString(EXTRA_DISPLAY);
		login = state.getString(EXTRA_LOGIN);
		roles = state.getString(EXTRA_ROLES);
		password = state.getByteArray(EXTRA_PASSWORD);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		ImogActorCursorJoiner cursor = (ImogActorCursorJoiner) l.getAdapter().getItem(position);
		display = cursor.getUserDisplay(this);
		login = cursor.getLogin();
		password = cursor.getPassword();
		roles = cursor.getRoles();
		showDialog(DIALOG_PASSWORD_ID);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_PASSWORD_ID :
			final View textEntryView = LayoutInflater.from(this).inflate(R.layout.ig_alert_dialog_text_entry, null);
			return new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle(getDisplay())
			.setView(textEntryView)
			.setPositiveButton(android.R.string.ok, this)
			.setNegativeButton(android.R.string.cancel, null)
			.create();
		}
		return super.onCreateDialog(id);
	}
	
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case DIALOG_PASSWORD_ID:
			dialog.setTitle(getDisplay());
			((TextView) dialog.findViewById(R.id.ig_username_edit)).setText(login);
			((TextView) dialog.findViewById(R.id.ig_password_edit)).setText(null);
			break;
		default:
			super.onPrepareDialog(id, dialog);
			break;
		}
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		final String pwd = ((TextView)((Dialog) dialog).findViewById(R.id.ig_password_edit)).getText().toString();
		if (validate(pwd)) {
			EncryptionManager em = EncryptionManager.getInstance(this);
			String encLogin = new String(Base64.encodeBase64(em.encrypt(login.getBytes())));
			PreferenceHelper.getSharedPreferences(this).edit()
			.putString(getString(R.string.ig_current_login_key), encLogin)
			.putString(getString(R.string.ig_current_roles_key), roles)
			.commit();
			setResult(RESULT_OK);
			finish();
		} else {
			Toast.makeText(this, R.string.ig_alert_dialog_wrong_password, Toast.LENGTH_SHORT).show();
		}
	}
	
	private String getDisplay() {
		return TextUtils.isEmpty(display)?getString(android.R.string.unknownName):display;
	}
	
	private boolean validate(String pwd) {
		return pwd != null ? Arrays.equals(EncryptionManager.getInstance(this).encrypt(pwd.getBytes()), password) : false;
	}
		
	
	protected abstract ImogActorCursorJoiner getCursor();
	
	private static class JoinerAdapter extends CursorAdapter {

		public JoinerAdapter(Context context, ImogActorCursorJoiner c) {
			super(context, c);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			ImogActorCursorJoiner c = (ImogActorCursorJoiner) cursor;

			view.findViewById(R.id.ig_list_color).setBackgroundDrawable(c.getDrawable());

			TextView main = (TextView) view.findViewById(R.id.ig_list_main);
			TextView secondary = (TextView) view.findViewById(R.id.ig_list_secondary);

			main.setText(c.getUserDisplay(context));
			secondary.setText(c.getLogin());
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return LayoutInflater.from(context).inflate(R.layout.ig_entity_row, parent, false);
		}
		
	}

}

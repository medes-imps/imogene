package org.imogene.android.database;


import android.content.Context;

public interface ImogActorCursor extends ImogEntityCursor {
	
	public String getLogin();
	
	public byte[] getPassword();
	
	public String getRoles();

	public String getUserDisplay(Context context);
}

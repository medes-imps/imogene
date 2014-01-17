package org.imogene.android.database;

import java.util.List;

import android.content.Context;
import android.net.Uri;

public interface ImogActorCursor extends ImogEntityCursor {

	public String getLogin();

	public byte[] getPassword();

	public List<Uri> getProfiles();

	public String getUserDisplay(Context context);
}

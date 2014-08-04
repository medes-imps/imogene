package org.imogene.android.database;

import java.util.List;

import org.imogene.android.common.entity.ImogActor;
import org.imogene.android.common.profile.Profile;

import android.content.Context;

public interface ImogActorCursor<T extends ImogActor> extends ImogEntityCursor<T> {

	public String getLogin();

	public byte[] getPassword();

	public List<Profile> getProfiles();

	public String getUserDisplay(Context context);
}

package org.imogene.client.ui;

import java.util.UUID;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.imogene.client.Activator;

public class SyncPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();

		if (!preferences.contains(ISyncConstants.SYNC_TERMINAL)) {
			preferences.setValue(ISyncConstants.SYNC_TERMINAL, UUID.randomUUID().toString());
		}

		/* default value */
		preferences.setDefault(ISyncConstants.SYNC_URL, ISyncConstants.DEFAULT_SYNC_URL);
		preferences.setDefault(ISyncConstants.SYNC_PERIOD, ISyncConstants.DEFAULT_SYNC_PERIOD);
		preferences.setDefault(ISyncConstants.SYNC_AUTO, ISyncConstants.DEFAULT_SYNC_AUTO);
		preferences.setDefault(ISyncConstants.NTP_HOST, ISyncConstants.DEFAULT_NTP_HOST);
		preferences.setDefault(ISyncConstants.NTP_RATE, ISyncConstants.DEFAULT_NTP_RATE);
		preferences.setDefault(ISyncConstants.NTP_OFFSET, ISyncConstants.DEFAULT_NTP_OFFSET);

		String terminal = preferences.getString(ISyncConstants.SYNC_TERMINAL);
		if (terminal == null || terminal.isEmpty()) {
			terminal = UUID.randomUUID().toString();
			preferences.setValue(ISyncConstants.SYNC_TERMINAL, terminal);
		}
	}

}

package org.imogene.sync.client.ui;

import java.util.UUID;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.imogene.sync.client.SyncActivator;

public class SyncPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore preferences = SyncActivator.getDefault().getPreferenceStore();

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

		notifySyncParametersUpdated();
		notifyNtpParametersUpdated();

		preferences.addPropertyChangeListener(new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (ISyncConstants.SYNC_AUTO.equals(event.getProperty())
						|| ISyncConstants.SYNC_PERIOD.equals(event.getProperty())) {
					notifySyncParametersUpdated();
				} else if (ISyncConstants.NTP_HOST.equals(event.getProperty())
						|| ISyncConstants.NTP_RATE.equals(event.getProperty())) {
					notifyNtpParametersUpdated();
				} else if (ISyncConstants.NTP_OFFSET.equals(event.getProperty())) {
					notifyOffsetUpdated();
				}
			}
		});
	}

	private static void notifySyncParametersUpdated() {
		IPreferenceStore preferences = SyncActivator.getDefault().getPreferenceStore();
		long period = preferences.getLong(ISyncConstants.SYNC_PERIOD);
		boolean loop = preferences.getBoolean(ISyncConstants.SYNC_AUTO);
		SyncActivator.getDefault().setSyncParameters(loop, period * 60 * 1000);
	}

	private static void notifyNtpParametersUpdated() {
		IPreferenceStore preferences = SyncActivator.getDefault().getPreferenceStore();
		String host = preferences.getString(ISyncConstants.NTP_HOST);
		long rate = preferences.getLong(ISyncConstants.NTP_RATE);
		SyncActivator.getDefault().setNTPParameters(host, rate * 60 * 1000);
	}

	private static void notifyOffsetUpdated() {
//		IPreferenceStore preferences = SyncActivator.getDefault().getPreferenceStore();
//		long offset = preferences.getLong(ISyncConstants.NTP_OFFSET);
//		SyncActivator.getDefault().setOffset(offset);
	}

}

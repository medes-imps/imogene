package org.imogene.rcp.container.bugreport;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.imogene.rcp.container.Activator;

public class BugReportPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();

		preferences.setDefault(IBugReportConstants.LOG_DATABASE, true);
		preferences.setDefault(IBugReportConstants.LOG_RUNTIME, true);
		preferences.setDefault(IBugReportConstants.LOG_SERVER, true);
		preferences.setDefault(IBugReportConstants.LOG_SYNC, true);
		preferences.setDefault(IBugReportConstants.LOG_WEBAPP, true);
		preferences.setDefault(IBugReportConstants.LOG_MAX_FILES, 1);

		preferences.setDefault(IBugReportConstants.SMTP_DESTINATION, "support@i-mogene.org"); //$NON-NLS-1$
		preferences.setDefault(IBugReportConstants.SMTP_AUTH, true);
		preferences.setDefault(IBugReportConstants.SMTP_HOST, "smtp.gmail.com"); //$NON-NLS-1$
		preferences.setDefault(IBugReportConstants.SMTP_PORT, 587);
		preferences.setDefault(IBugReportConstants.SMTP_TLS, true);
		preferences.setDefault(IBugReportConstants.SMTP_USER, "imogene.medes@gmail.com"); //$NON-NLS-1$
		preferences.setDefault(IBugReportConstants.SMTP_PASSWORD, "epidefender"); //$NON-NLS-1$
	}

}

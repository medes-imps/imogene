package org.imogene.sync.client.ui;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.imogene.sync.client.SyncActivator;
import org.imogene.sync.client.i18n.Messages;

public class SyncPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public static final String ID = "org.imogene.sync.client.ui.SyncPreferencePage"; //$NON-NLS-1$

	public static String getHumanReadablePeriod(long period) {
		for (String[] values : VALUES) {
			if (Long.parseLong(values[1]) == period) {
				return values[0];
			}
		}
		return Messages.sync_pref_period_15min;
	}

	private static String[][] VALUES = new String[][] { { Messages.sync_pref_period_15min, "15" }, //$NON-NLS-1$
			{ Messages.sync_pref_period_1hour, "60" }, //$NON-NLS-1$
			{ Messages.sync_pref_period_2hours, "120" }, //$NON-NLS-1$
			{ Messages.sync_pref_period_4hours, "240" }, //$NON-NLS-1$
			{ Messages.sync_pref_period_8hours, "480" }, //$NON-NLS-1$
			{ Messages.sync_pref_period_1day, "1440" } //$NON-NLS-1$
	};

	// private Group group;
	private Group sync;
	private FieldEditor period;

	public SyncPreferencePage() {
		super(GRID);
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(SyncActivator.getDefault().getPreferenceStore());
	}

	@Override
	protected void createFieldEditors() {
		createSyncFieldEditors();
		createNtpGroup();
	}

	private void createSyncFieldEditors() {
		sync = new Group(getFieldEditorParent(), SWT.NULL);
		sync.setText(Messages.sync_pref_title);

		GridLayout gridLayout = new GridLayout(2, false);
		GridData gridData = new GridData(SWT.FILL, SWT.TOP, true, false);
		gridData.horizontalSpan = 2;

		StringFieldEditor url = new StringFieldEditor(ISyncConstants.SYNC_URL, Messages.sync_pref_url,
				StringFieldEditor.UNLIMITED, StringFieldEditor.VALIDATE_ON_KEY_STROKE, sync);
		url.setEmptyStringAllowed(false);
		url.setErrorMessage(Messages.sync_pref_url_error);

		StringFieldEditor terminal = new StringFieldEditor(ISyncConstants.SYNC_TERMINAL, Messages.sync_pref_terminal,
				sync);
		terminal.setEnabled(false, sync);

		BooleanFieldEditor automatic = new BooleanFieldEditor(ISyncConstants.SYNC_AUTO, Messages.sync_pref_auto, sync);

		period = new ComboFieldEditor(ISyncConstants.SYNC_PERIOD, Messages.sync_pref_period, VALUES, sync);

		addField(url);
		addField(automatic);
		addField(period);
		addField(terminal);

		sync.setLayout(gridLayout);
		sync.setLayoutData(gridData);
	}

	private void createNtpGroup() {
		Group ntp = new Group(getFieldEditorParent(), SWT.NULL);
		ntp.setText(Messages.ntp_pref_title);

		GridLayout gridLayout = new GridLayout(2, false);
		GridData gridData = new GridData(SWT.FILL, SWT.TOP, true, false);
		gridData.horizontalSpan = 2;

		StringFieldEditor host = new StringFieldEditor(ISyncConstants.NTP_HOST, Messages.ntp_pref_host,
				StringFieldEditor.UNLIMITED, StringFieldEditor.VALIDATE_ON_KEY_STROKE, ntp);
		host.setEmptyStringAllowed(false);
		host.setErrorMessage(Messages.ntp_pref_host_error);

		ComboFieldEditor rate = new ComboFieldEditor(ISyncConstants.NTP_RATE, Messages.ntp_pref_rate, VALUES, ntp);

		addField(host);
		addField(rate);

		ntp.setLayout(gridLayout);
		ntp.setLayoutData(gridData);
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		boolean enabled = getPreferenceStore().getDefaultBoolean(ISyncConstants.SYNC_AUTO);
		period.setEnabled(enabled, sync);
	}

	@Override
	protected void initialize() {
		super.initialize();
		boolean enabled = getPreferenceStore().getBoolean(ISyncConstants.SYNC_AUTO);
		period.setEnabled(enabled, sync);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
		if (event.getSource() != null && event.getSource() instanceof FieldEditor) {
			if (ISyncConstants.SYNC_AUTO.equals(((FieldEditor) event.getSource()).getPreferenceName())) {
				boolean enabled = (Boolean) event.getNewValue();
				period.setEnabled(enabled, sync);
			}
		}
	}

}

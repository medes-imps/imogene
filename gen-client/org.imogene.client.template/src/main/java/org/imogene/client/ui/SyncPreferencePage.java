package org.imogene.client.ui;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.imogene.client.Activator;
import org.imogene.client.i18n.Messages;

public class SyncPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public static final String ID = "org.imogene.client.ui.SyncPreferencePage"; //$NON-NLS-1$

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
	private FieldEditor automatic;

	public SyncPreferencePage() {
		super(GRID);
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
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

		Link link = new Link(sync, SWT.PUSH);
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.horizontalSpan = 2;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		link.setLayoutData(gd);
		link.setText(Messages.sync_pref_user);
		link.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				Activator.getDefault().authenticate(new Runnable() {

					@Override
					public void run() {
						Display.getDefault().asyncExec(new Runnable() {

							@Override
							public void run() {
								automatic.setEnabled(true, sync);
							}
						});
					}
				});
			}
		});

		automatic = new BooleanFieldEditor(ISyncConstants.SYNC_AUTO, Messages.sync_pref_auto, sync);
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
		period.setEnabled(getPreferenceStore().getBoolean(ISyncConstants.SYNC_AUTO), sync);
		automatic.setEnabled(getPreferenceStore().contains(ISyncConstants.SYNC_LOGIN), sync);
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

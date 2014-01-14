package org.imogene.client.bugreport;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.imogene.client.Activator;
import org.imogene.client.i18n.Messages;

public class BugReportPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private Group mSmtpGroup;
	private StringFieldEditor mUserEditor;
	private StringFieldEditor mPasswordEditor;

	public BugReportPreferencePage() {
		super(GRID);
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	@Override
	protected void createFieldEditors() {
		createLogsFieldEditors();
		createSmtpFieldEditors();
	}

	private void createLogsFieldEditors() {
		Group logs = new Group(getFieldEditorParent(), SWT.NULL);
		logs.setText(Messages.bugreport_prefs_logs_title);

		GridLayout gridLayout = new GridLayout(2, false);
		GridData gridData = new GridData(SWT.FILL, SWT.TOP, true, false);
		gridData.horizontalSpan = 2;

		addField(new BooleanFieldEditor(IBugReportConstants.LOG_SERVER, Messages.bugreport_prefs_logs_server, logs));
		addField(new BooleanFieldEditor(IBugReportConstants.LOG_DATABASE, Messages.bugreport_prefs_logs_database, logs));
		addField(new BooleanFieldEditor(IBugReportConstants.LOG_WEBAPP, Messages.bugreport_prefs_logs_webapp, logs));
		addField(new BooleanFieldEditor(IBugReportConstants.LOG_SYNC, Messages.bugreport_prefs_logs_sync, logs));
		addField(new BooleanFieldEditor(IBugReportConstants.LOG_RUNTIME, Messages.bugreport_prefs_logs_runtime, logs));

		IntegerFieldEditor editor = new IntegerFieldEditor(IBugReportConstants.LOG_MAX_FILES,
				Messages.bugreport_prefs_logs_maxfiles, logs);
		editor.setValidRange(1, 10);
		editor.setErrorMessage(Messages.bugreport_prefs_logs_maxfiles_error);
		addField(editor);

		logs.setLayout(gridLayout);
		logs.setLayoutData(gridData);
	}

	private void createSmtpFieldEditors() {
		mSmtpGroup = new Group(getFieldEditorParent(), SWT.NONE);
		mSmtpGroup.setText(Messages.bugreport_prefs_smtp_title);

		GridLayout gridLayout = new GridLayout(2, false);
		GridData gridData = new GridData(SWT.FILL, SWT.TOP, true, false);
		gridData.horizontalSpan = 2;

		StringFieldEditor host = new StringFieldEditor(IBugReportConstants.SMTP_HOST, Messages.bugreport_prefs_smtp_host,
				mSmtpGroup);
		IntegerFieldEditor port = new IntegerFieldEditor(IBugReportConstants.SMTP_PORT, Messages.bugreport_prefs_smtp_port,
				mSmtpGroup);
		StringFieldEditor destination = new StringFieldEditor(IBugReportConstants.SMTP_DESTINATION,
				Messages.bugreport_prefs_smtp_destination, mSmtpGroup);
		BooleanFieldEditor auth = new BooleanFieldEditor(IBugReportConstants.SMTP_AUTH, Messages.bugreport_prefs_smtp_auth,
				mSmtpGroup);
		mUserEditor = new StringFieldEditor(IBugReportConstants.SMTP_USER, Messages.bugreport_prefs_smtp_user, mSmtpGroup);
		mPasswordEditor = new StringFieldEditor(IBugReportConstants.SMTP_PASSWORD, Messages.bugreport_prefs_smtp_password,
				mSmtpGroup);
		BooleanFieldEditor tls = new BooleanFieldEditor(IBugReportConstants.SMTP_TLS, Messages.bugreport_prefs_smtp_tls,
				mSmtpGroup);

		port.setValidateStrategy(IntegerFieldEditor.VALIDATE_ON_KEY_STROKE);
		port.setValidRange(1, 65535);

		mPasswordEditor.getTextControl(mSmtpGroup).setEchoChar('*');

		addField(host);
		addField(port);
		addField(destination);
		addField(auth);
		addField(mUserEditor);
		addField(mPasswordEditor);
		addField(tls);

		mSmtpGroup.setLayout(gridLayout);
		mSmtpGroup.setLayoutData(gridData);
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		boolean enabled = getPreferenceStore().getDefaultBoolean(IBugReportConstants.SMTP_AUTH);
		mUserEditor.setEnabled(enabled, mSmtpGroup);
		mPasswordEditor.setEnabled(enabled, mSmtpGroup);
	}

	@Override
	protected void initialize() {
		super.initialize();
		boolean enabled = getPreferenceStore().getBoolean(IBugReportConstants.SMTP_AUTH);
		mUserEditor.setEnabled(enabled, mSmtpGroup);
		mPasswordEditor.setEnabled(enabled, mSmtpGroup);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
		if (event.getSource() != null && event.getSource() instanceof FieldEditor) {
			if (IBugReportConstants.SMTP_AUTH.equals(((FieldEditor) event.getSource()).getPreferenceName())) {
				boolean enabled = (Boolean) event.getNewValue();
				mUserEditor.setEnabled(enabled, mSmtpGroup);
				mPasswordEditor.setEnabled(enabled, mSmtpGroup);
			}
		}
	}

}

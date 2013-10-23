package org.imogene.studio.contrib.properties;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

public class GenerationTestPropertiesPage extends PropertyPage {
	public static final String ICONSPATH_TITLE = Messages.GenerationTestPropertiesPage_0;

	public static final String ICONSPATH_PROPERTY = "ICONS_PATH"; //$NON-NLS-1$

	public static final String ICONSPATH_DEFAULT = "icons"; //$NON-NLS-1$

	// -----------------

	private static final String JDBCDRIVER_TITLE = Messages.GenerationTestPropertiesPage_3;

	public static final String JDBCDRIVER_PROPERTY = "TESTJDBCDRIVER"; //$NON-NLS-1$

	public static final String JDBCDRIVER_DEFAULT = "org.postgresql.Driver"; //$NON-NLS-1$

	// -----------------

	private static final String JDBCPATH_TITLE = Messages.GenerationTestPropertiesPage_6;

	public static final String JDBCPATH_PROPERTY = "TESTJDBCPATH"; //$NON-NLS-1$

	public static final String JDBCPATH_DEFAULT = "jdbc:postgresql://localhost/dbName"; //$NON-NLS-1$

	// -----------------

	private static final String JDBCLOGIN_TITLE = Messages.GenerationTestPropertiesPage_9;

	public static final String JDBCLOGIN_PROPERTY = "TESTJDBCLOGIN"; //$NON-NLS-1$

	public static final String JDBCLOGIN_DEFAULT = "login"; //$NON-NLS-1$

	// -----------------

	private static final String JDBCPASSWORD_TITLE = Messages.GenerationTestPropertiesPage_12;

	public static final String JDBCPASSWORD_PROPERTY = "TESTJDBCPASSWORD"; //$NON-NLS-1$

	public static final String JDBCPASSWORD_DEFAULT = "passwrd"; //$NON-NLS-1$
	
	// -----------------

	private static final String HIBERNATEDIALECT_TITLE = Messages.GenerationTestPropertiesPage_15;

	public static final String HIBERNATEDIALECT_PROPERTY = "HIBERNATEDIALECT"; //$NON-NLS-1$

	public static final String HIBERNATEDIALECT_DEFAULT = "org.hibernate.dialect.PostgreSQLDialect"; //$NON-NLS-1$

	// -----------------

	private static final String SMTP_TITLE = Messages.GenerationTestPropertiesPage_18;

	public static final String SMTP_PROPERTY = "TESTSMTPSERVER"; //$NON-NLS-1$

	public static final String SMTP_DEFAULT = "dumbo.medes.fr"; //$NON-NLS-1$

	// -----------------

	private static final String SMTPLOGIN_TITLE = Messages.GenerationTestPropertiesPage_21;

	public static final String SMTPLOGIN_PROPERTY = "TESTSMTPLOGIN"; //$NON-NLS-1$

	public static final String SMTPLOGIN_DEFAULT = ""; //$NON-NLS-1$

	// -----------------

	private static final String SMTPPASSWD_TITLE = Messages.GenerationTestPropertiesPage_24;

	public static final String SMTPPASSWD_PROPERTY = "TESTSMTPPASSWORD"; //$NON-NLS-1$

	public static final String SMTPPASSWD_DEFAULT = ""; //$NON-NLS-1$

	// -----------------

	// Web site utilis� pour r�f�rencer la fiche dans la notification.
	private static final String WEBSITE_TITLE = Messages.GenerationTestPropertiesPage_27;

	public static final String WEBSITE_PROPERTY = "TESTWEBSITE"; //$NON-NLS-1$

	public static final String WEBSITE_DEFAULT = "http://localhost:8080/XXXStruts"; //$NON-NLS-1$

	// Synchro server utilis�.
	private static final String SYNCHROSERVER_TITLE = Messages.GenerationTestPropertiesPage_30;

	public static final String SYNCHROSERVER_PROPERTY = "TESTSYNCHROSERVER"; //$NON-NLS-1$

	public static final String SYNCHROSERVER_DEFAULT = "http://localhost:8080/XXXSynchro"; //$NON-NLS-1$

	// SMS server
	private static final String SMSSERVER_TITLE = Messages.GenerationTestPropertiesPage_33;

	public static final String SMSSERVER_PROPERTY = "TESTSMSSERVER"; //$NON-NLS-1$

	public static final String SMSSERVER_DEFAULT = "http://localhost:8080/SMSDaemon"; //$NON-NLS-1$

	private static final int TEXT_FIELD_WIDTH = 50;

	private Text jdbcdriverText;

	private Text jdbcpathText;

	private Text jdbcloginText;

	private Text jdbcpasswordText;
	
	private Text hibernatedialectText;

	private Text smtpPasswordText;

	private Text smtpLoginText;

	private Text smtpServerText;

	private Text webSiteText;

	private Text synchroServerText;

	private Text smsServerText;

	/**
	 * Constructor for SamplePropertyPage.
	 */
	public GenerationTestPropertiesPage() {
		super();
	}

	private Text createField(Composite parent, String title, String property,
			String defaultValue) {
		// Label for owner field
		Label jdbcpathLabel = new Label(parent, SWT.NONE);
		jdbcpathLabel.setText(title);

		// Owner text field
		Text txt = new Text(parent, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData();
		gd.widthHint = convertWidthInCharsToPixels(TEXT_FIELD_WIDTH);
		txt.setLayoutData(gd);

		// Populate text field (with its current value (or default value if
		// never set))
		try {
			String value = ((IResource) getElement())
					.getPersistentProperty(new QualifiedName("", property)); //$NON-NLS-1$
			txt.setText((value != null) ? value : defaultValue);
		} catch (CoreException e) {
			txt.setText(defaultValue);
		}
		return txt;
	}

	private void addFirstSection(Composite parent) {
		Composite composite = createDefaultComposite(parent);

		jdbcdriverText = createField(composite, JDBCDRIVER_TITLE,
				JDBCDRIVER_PROPERTY, JDBCDRIVER_DEFAULT);
		jdbcpathText = createField(composite, JDBCPATH_TITLE,
				JDBCPATH_PROPERTY, JDBCPATH_DEFAULT);
		jdbcloginText = createField(composite, JDBCLOGIN_TITLE,
				JDBCLOGIN_PROPERTY, JDBCLOGIN_DEFAULT);
		jdbcpasswordText = createField(composite, JDBCPASSWORD_TITLE,
				JDBCPASSWORD_PROPERTY, JDBCPASSWORD_DEFAULT);
		jdbcpasswordText.setEchoChar('*');
		hibernatedialectText = createField(composite, HIBERNATEDIALECT_TITLE,
				HIBERNATEDIALECT_PROPERTY, HIBERNATEDIALECT_DEFAULT);	
		
		smtpServerText = createField(composite, SMTP_TITLE, SMTP_PROPERTY,
				SMTP_DEFAULT);
		smtpLoginText = createField(composite, SMTPLOGIN_TITLE,
				SMTPLOGIN_PROPERTY, SMTPLOGIN_DEFAULT);
		smtpPasswordText = createField(composite, SMTPPASSWD_TITLE,
				SMTPPASSWD_PROPERTY, SMTPPASSWD_DEFAULT);
		smtpPasswordText.setEchoChar('*');
		webSiteText = createField(composite, WEBSITE_TITLE, WEBSITE_PROPERTY,
				WEBSITE_DEFAULT);
		synchroServerText = createField(composite, SYNCHROSERVER_TITLE,
				SYNCHROSERVER_PROPERTY, SYNCHROSERVER_DEFAULT);
		smsServerText = createField(composite, SMSSERVER_TITLE,
				SMSSERVER_PROPERTY, SMSSERVER_DEFAULT);

	}

	/**
	 * @see PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);

		addFirstSection(composite);
		return composite;
	}

	private Composite createDefaultComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);

		return composite;
	}

	protected void performDefaults() {
		// Populate the owner text field with the default value
		jdbcpathText.setText(JDBCPATH_DEFAULT);
		jdbcdriverText.setText(JDBCDRIVER_DEFAULT);
		jdbcloginText.setText(JDBCLOGIN_DEFAULT);
		jdbcpasswordText.setText(JDBCPASSWORD_DEFAULT);
		hibernatedialectText.setText(HIBERNATEDIALECT_DEFAULT);

		smtpServerText.setText(SMTP_DEFAULT);
		smtpPasswordText.setText(SMTPPASSWD_DEFAULT);
		smtpLoginText.setText(SMTPLOGIN_DEFAULT);
		webSiteText.setText(WEBSITE_DEFAULT);
		synchroServerText.setText(SYNCHROSERVER_DEFAULT);
		smsServerText.setText(SMSSERVER_DEFAULT);

	}

	public boolean performOk() {
		// store the values in the owner text field
		try {
			((IResource) getElement()).setPersistentProperty(new QualifiedName(
					"", JDBCDRIVER_PROPERTY), jdbcdriverText.getText()); //$NON-NLS-1$
			((IResource) getElement()).setPersistentProperty(new QualifiedName(
					"", JDBCPATH_PROPERTY), jdbcpathText.getText()); //$NON-NLS-1$
			((IResource) getElement()).setPersistentProperty(new QualifiedName(
					"", JDBCLOGIN_PROPERTY), jdbcloginText.getText()); //$NON-NLS-1$
			((IResource) getElement()).setPersistentProperty(new QualifiedName(
					"", JDBCPASSWORD_PROPERTY), jdbcpasswordText.getText()); //$NON-NLS-1$
			((IResource) getElement()).setPersistentProperty(new QualifiedName(
					"", HIBERNATEDIALECT_PROPERTY), hibernatedialectText.getText()); //$NON-NLS-1$

			((IResource) getElement()).setPersistentProperty(new QualifiedName(
					"", SMTP_PROPERTY), smtpServerText.getText()); //$NON-NLS-1$
			((IResource) getElement()).setPersistentProperty(new QualifiedName(
					"", SMTPLOGIN_PROPERTY), smtpLoginText.getText()); //$NON-NLS-1$
			((IResource) getElement()).setPersistentProperty(new QualifiedName(
					"", SMTPPASSWD_PROPERTY), smtpPasswordText.getText()); //$NON-NLS-1$
			((IResource) getElement()).setPersistentProperty(new QualifiedName(
					"", WEBSITE_PROPERTY), webSiteText.getText()); //$NON-NLS-1$
			((IResource) getElement()).setPersistentProperty(new QualifiedName(
					"", SYNCHROSERVER_PROPERTY), synchroServerText.getText()); //$NON-NLS-1$
			((IResource) getElement()).setPersistentProperty(new QualifiedName(
					"", SMSSERVER_PROPERTY), smsServerText.getText()); //$NON-NLS-1$

		} catch (CoreException e) {
			ErrorDialog.openError(getShell(),
					Messages.GenerationTestPropertiesPage_48, e
							.getLocalizedMessage(), e.getStatus());
			return false;
		}
		return true;
	}

}
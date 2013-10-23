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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

public class GenerationPropertiesPage extends PropertyPage {
	
	private static final String ADMINPASSWORD_TITLE = "Admnistrator password";
	public static final String ADMINPASSWORD_PROPERTY = "ADMINPASSWORD"; //$NON-NLS-1$
	public static final String ADMINPASSWORD_DEFAULT = "epipassword";
	
	private static final String AUDIOCONVERTER_TITLE = Messages.GenerationPropertiesPage_0;
	public static final String AUDIOCONVERTER_PROPERTY = "audioConverter"; //$NON-NLS-1$
	public static final String AUDIOCONVERTER_DEFAULT = "ffmpeg -i %IN% %OUT%"; //$NON-NLS-1$
	
	// -----------------
	private static final String VIDEOCONVERTER_TITLE = Messages.GenerationPropertiesPage_3;
	public static final String VIDEOCONVERTER_PROPERTY = "videoConverter"; //$NON-NLS-1$
	public static final String VIDEOCONVERTER_DEFAULT = "ffmpeg -i %IN% -f flv %OUT%"; //$NON-NLS-1$
	
	// -----------------

	private static final String BINARIESPATH_TITLE = Messages.GenerationPropertiesPage_6;

	public static final String BINARIESPATH_PROPERTY = "BINARIESPATH"; //$NON-NLS-1$

	public static final String BINARIESPATH_DEFAULT = "icons"; //$NON-NLS-1$

	// -----------------

	private static final String PROJECTVERSION_TITLE = Messages.GenerationPropertiesPage_9;

	public static final String PROJECTVERSION_PROPERTY = "VERSIONPATH"; //$NON-NLS-1$

	public static final String PROJECTVERSION_DEFAULT = "1"; //$NON-NLS-1$

	// -----------------

	private static final String JDBCDRIVER_TITLE = Messages.GenerationPropertiesPage_12;

	public static final String JDBCDRIVER_PROPERTY = "JDBCDRIVER"; //$NON-NLS-1$

	public static final String JDBCDRIVER_DEFAULT = "org.postgresql.Driver"; //$NON-NLS-1$

	// -----------------

	private static final String JDBCPATH_TITLE = Messages.GenerationPropertiesPage_15;

	public static final String JDBCPATH_PROPERTY = "JDBCPATH"; //$NON-NLS-1$

	public static final String JDBCPATH_DEFAULT = "jdbc:postgresql://localhost/dbName"; //$NON-NLS-1$

	// -----------------

	private static final String JDBCLOGIN_TITLE = Messages.GenerationPropertiesPage_18;

	public static final String JDBCLOGIN_PROPERTY = "JDBCLOGIN"; //$NON-NLS-1$

	public static final String JDBCLOGIN_DEFAULT = "login"; //$NON-NLS-1$

	// -----------------

	private static final String JDBCPASSWORD_TITLE = Messages.GenerationPropertiesPage_21;

	public static final String JDBCPASSWORD_PROPERTY = "JDBCPASSWORD"; //$NON-NLS-1$

	public static final String JDBCPASSWORD_DEFAULT = "passwd"; //$NON-NLS-1$

	// -----------------

	private static final String HIBERNATEDIALECT_TITLE = Messages.GenerationPropertiesPage_24;

	public static final String HIBERNATEDIALECT_PROPERTY = "HIBERNATEDIALECT"; //$NON-NLS-1$

	public static final String HIBERNATEDIALECT_DEFAULT = "org.hibernate.dialect.PostgreSQLDialect"; //$NON-NLS-1$

	// -----------------

	private static final String SMTP_TITLE = Messages.GenerationPropertiesPage_27;

	public static final String SMTP_PROPERTY = "SMTPSERVER"; //$NON-NLS-1$

	public static final String SMTP_DEFAULT = "smtp.imogene.org"; //$NON-NLS-1$

	// -----------------

	private static final String SMTPLOGIN_TITLE = Messages.GenerationPropertiesPage_30;

	public static final String SMTPLOGIN_PROPERTY = "SMTPLOGIN"; //$NON-NLS-1$

	public static final String SMTPLOGIN_DEFAULT = ""; //$NON-NLS-1$

	// -----------------

	private static final String SMTPPASSWD_TITLE = Messages.GenerationPropertiesPage_33;

	public static final String SMTPPASSWD_PROPERTY = "SMTPPASSWORD"; //$NON-NLS-1$

	public static final String SMTPPASSWD_DEFAULT = ""; //$NON-NLS-1$

	// -----------------

	private static final String WEBSITE_TITLE = Messages.GenerationPropertiesPage_36;

	public static final String WEBSITE_PROPERTY = "WEBSITE"; //$NON-NLS-1$

	public static final String WEBSITE_DEFAULT = "http://localhost:8080/ImogeneWeb"; //$NON-NLS-1$

	private static final String SYNCHROSERVER_TITLE = Messages.GenerationPropertiesPage_39;

	public static final String SYNCHROSERVER_PROPERTY = "SYNCHROSERVER"; //$NON-NLS-1$

	public static final String SYNCHROSERVER_DEFAULT = "http://localhost:8080/ImogeneSync"; //$NON-NLS-1$

	private static final String SMSSERVER_TITLE = Messages.GenerationPropertiesPage_42;

	public static final String SMSSERVER_PROPERTY = "SMSSERVER"; //$NON-NLS-1$

	public static final String SMSSERVER_DEFAULT = "http://localhost:8080/ImogeneNotif"; //$NON-NLS-1$
	
	private static final String WEBSERVICE_TITLE = Messages.GenerationPropertiesPage_45;

	public static final String WEBSERVICE_PROPERTY = "WEBSERVICE"; //$NON-NLS-1$

	public static final String WEBSERVICE_DEFAULT = "http://localhost:8080/ImogeneWebService"; //$NON-NLS-1$

	@SuppressWarnings("unused")
	private static final int TEXT_FIELD_WIDTH = 50;

	/* widgets */
	
	private Text adminPasswordText;

	private Text versionText;

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
	
	private Text webServiceText;

	private Text binariesPathText;
	
	private Text videoConverterText;
	
	private Text audioConverterText;

	/**
	 * Constructor for SamplePropertyPage.
	 */
	public GenerationPropertiesPage() {
		super();
	}

	private Text createField(Composite parent, String title, String property,
			String defaultValue) {
		// Label for owner field
		Label jdbcpathLabel = new Label(parent, SWT.NONE);
		jdbcpathLabel.setText(title);
		Text txt = new Text(parent, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		txt.setLayoutData(gd);
		try {
			String value = ((IResource) getElement())
					.getPersistentProperty(new QualifiedName("", property)); //$NON-NLS-1$
			txt.setText((value != null) ? value : defaultValue);
		} catch (CoreException e) {
			txt.setText(defaultValue);
		}
		return txt;
	}

	private Group createGroup(Composite parent, String title) {
		Group group = new Group(parent, SWT.TITLE);
		group.setText(title);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setLayout(new GridLayout(2, false));
		return group;
	}

	private void addFirstSection(Composite parent) {
		Composite composite = createDefaultComposite(parent);

		/* admin password */
		Group adminPasswordGroup = createGroup(composite, "Administrator account");
		adminPasswordText = createField(adminPasswordGroup, ADMINPASSWORD_TITLE,
				ADMINPASSWORD_PROPERTY, ADMINPASSWORD_DEFAULT);
		adminPasswordText.setEchoChar('*');
		
		/* version properties */
		Group versionGroup = createGroup(composite, Messages.GenerationPropertiesPage_49);
		versionText = createField(versionGroup, PROJECTVERSION_TITLE,
				PROJECTVERSION_PROPERTY, PROJECTVERSION_DEFAULT);

		/* db properties */
		Group dbGroup = createGroup(composite, Messages.GenerationPropertiesPage_50);
		jdbcdriverText = createField(dbGroup, JDBCDRIVER_TITLE,
				JDBCDRIVER_PROPERTY, JDBCDRIVER_DEFAULT);
		jdbcpathText = createField(dbGroup, JDBCPATH_TITLE, JDBCPATH_PROPERTY,
				JDBCPATH_DEFAULT);
		jdbcloginText = createField(dbGroup, JDBCLOGIN_TITLE,
				JDBCLOGIN_PROPERTY, JDBCLOGIN_DEFAULT);
		jdbcpasswordText = createField(dbGroup, JDBCPASSWORD_TITLE,
				JDBCPASSWORD_PROPERTY, JDBCPASSWORD_DEFAULT);
		jdbcpasswordText.setEchoChar('*');
		hibernatedialectText = createField(dbGroup, HIBERNATEDIALECT_TITLE,
				HIBERNATEDIALECT_PROPERTY, HIBERNATEDIALECT_DEFAULT);

		/* binaries group */
		Group binariesGroup = createGroup(composite, Messages.GenerationPropertiesPage_51);
		binariesPathText = createField(binariesGroup, BINARIESPATH_TITLE,
				BINARIESPATH_PROPERTY, BINARIESPATH_DEFAULT);
		videoConverterText = createField(binariesGroup, VIDEOCONVERTER_TITLE,
				VIDEOCONVERTER_PROPERTY, VIDEOCONVERTER_DEFAULT);
		audioConverterText = createField(binariesGroup, AUDIOCONVERTER_TITLE,
				AUDIOCONVERTER_PROPERTY, AUDIOCONVERTER_DEFAULT);

		/* smtp properties */
		Group smtpGroup = createGroup(composite, Messages.GenerationPropertiesPage_52);
		smtpServerText = createField(smtpGroup, SMTP_TITLE, SMTP_PROPERTY,
				SMTP_DEFAULT);
		smtpLoginText = createField(smtpGroup, SMTPLOGIN_TITLE,
				SMTPLOGIN_PROPERTY, SMTPLOGIN_DEFAULT);
		smtpPasswordText = createField(smtpGroup, SMTPPASSWD_TITLE,
				SMTPPASSWD_PROPERTY, SMTPPASSWD_DEFAULT);
		smtpPasswordText.setEchoChar('*');

		/* url used in deployment */
		Group urlGroup = createGroup(composite, Messages.GenerationPropertiesPage_53);
		webSiteText = createField(urlGroup, WEBSITE_TITLE, WEBSITE_PROPERTY,
				WEBSITE_DEFAULT);
		synchroServerText = createField(urlGroup, SYNCHROSERVER_TITLE,
				SYNCHROSERVER_PROPERTY, SYNCHROSERVER_DEFAULT);
		smsServerText = createField(urlGroup, SMSSERVER_TITLE,
				SMSSERVER_PROPERTY, SMSSERVER_DEFAULT);		
		webServiceText = createField(urlGroup, WEBSERVICE_TITLE,
				WEBSERVICE_PROPERTY, WEBSERVICE_DEFAULT);
		
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
		layout.numColumns = 1;
		composite.setLayout(layout);

		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);

		return composite;
	}

	protected void performDefaults() {
		// Populate the owner text field with the default value
		adminPasswordText.setText(ADMINPASSWORD_DEFAULT);
		versionText.setText(PROJECTVERSION_DEFAULT);
		jdbcpathText.setText(JDBCPATH_DEFAULT);
		jdbcdriverText.setText(JDBCDRIVER_DEFAULT);
		jdbcloginText.setText(JDBCLOGIN_DEFAULT);
		jdbcpasswordText.setText(JDBCPASSWORD_DEFAULT);
		hibernatedialectText.setText(HIBERNATEDIALECT_DEFAULT);

		binariesPathText.setText(BINARIESPATH_DEFAULT);
		videoConverterText.setText(VIDEOCONVERTER_DEFAULT);
		audioConverterText.setText(AUDIOCONVERTER_DEFAULT);
		
		smtpServerText.setText(SMTP_DEFAULT);
		smtpPasswordText.setText(SMTPPASSWD_DEFAULT);
		smtpLoginText.setText(SMTPLOGIN_DEFAULT);

		webSiteText.setText(WEBSITE_DEFAULT);
		synchroServerText.setText(SYNCHROSERVER_DEFAULT);
		smsServerText.setText(SMSSERVER_DEFAULT);
		webServiceText.setText(WEBSERVICE_DEFAULT);

	}

	public boolean performOk() {
		if (isValid()) {
			try {
				((IResource) getElement()).setPersistentProperty(
						new QualifiedName("", ADMINPASSWORD_PROPERTY), //$NON-NLS-1$
						adminPasswordText.getText());
				
				((IResource) getElement()).setPersistentProperty(
						new QualifiedName("", PROJECTVERSION_PROPERTY), //$NON-NLS-1$
						versionText.getText());
				
				((IResource) getElement()).setPersistentProperty(
						new QualifiedName("", JDBCDRIVER_PROPERTY), //$NON-NLS-1$
						jdbcdriverText.getText());
				((IResource) getElement()).setPersistentProperty(
						new QualifiedName("", JDBCPATH_PROPERTY), jdbcpathText //$NON-NLS-1$
								.getText());
				((IResource) getElement()).setPersistentProperty(
						new QualifiedName("", JDBCLOGIN_PROPERTY), //$NON-NLS-1$
						jdbcloginText.getText());
				((IResource) getElement()).setPersistentProperty(
						new QualifiedName("", JDBCPASSWORD_PROPERTY), //$NON-NLS-1$
						jdbcpasswordText.getText());
				((IResource) getElement()).setPersistentProperty(
						new QualifiedName("", HIBERNATEDIALECT_PROPERTY), //$NON-NLS-1$
						hibernatedialectText.getText());

				((IResource) getElement()).setPersistentProperty(
						new QualifiedName("", SMTP_PROPERTY), smtpServerText //$NON-NLS-1$
								.getText());
				((IResource) getElement()).setPersistentProperty(
						new QualifiedName("", SMTPLOGIN_PROPERTY), //$NON-NLS-1$
						smtpLoginText.getText());
				((IResource) getElement()).setPersistentProperty(
						new QualifiedName("", SMTPPASSWD_PROPERTY), //$NON-NLS-1$
						smtpPasswordText.getText());

				((IResource) getElement()).setPersistentProperty(
						new QualifiedName("", BINARIESPATH_PROPERTY), //$NON-NLS-1$
						binariesPathText.getText().replace("\\", "/")); //$NON-NLS-1$ //$NON-NLS-2$
				
				((IResource) getElement()).setPersistentProperty(
						new QualifiedName("", VIDEOCONVERTER_PROPERTY), //$NON-NLS-1$
						videoConverterText.getText().replace("\\", "/")); //$NON-NLS-1$ //$NON-NLS-2$
				
				((IResource) getElement()).setPersistentProperty(
						new QualifiedName("", AUDIOCONVERTER_PROPERTY), //$NON-NLS-1$
						audioConverterText.getText().replace("\\", "/")); //$NON-NLS-1$ //$NON-NLS-2$
				
				((IResource) getElement()).setPersistentProperty(
						new QualifiedName("", WEBSITE_PROPERTY), webSiteText //$NON-NLS-1$
								.getText());
				((IResource) getElement()).setPersistentProperty(
						new QualifiedName("", SYNCHROSERVER_PROPERTY), //$NON-NLS-1$
						synchroServerText.getText());
				((IResource) getElement()).setPersistentProperty(
						new QualifiedName("", SMSSERVER_PROPERTY), //$NON-NLS-1$
						smsServerText.getText());
				((IResource) getElement()).setPersistentProperty(
						new QualifiedName("", WEBSERVICE_PROPERTY), //$NON-NLS-1$
						webServiceText.getText());

			} catch (CoreException e) {
				ErrorDialog.openError(getShell(),
						Messages.GenerationPropertiesPage_76, e
								.getLocalizedMessage(), e.getStatus());
				return false;
			}
			return true;
		}
		return false;

	}

	@Override
	public boolean isValid() {
		boolean isValid=true;
		try {
			Integer.parseInt(versionText.getText());
			setErrorMessage(null);			
			isValid = isValid && true;
		} catch (Exception e) {
			setErrorMessage(Messages.GenerationPropertiesPage_77);
			isValid = isValid && false;
		}
		if(adminPasswordText.getText()==null && adminPasswordText.getText().length()<1){
			isValid = isValid && false;
		}else{
			isValid = isValid && true;
		}
		return isValid;
	}

}
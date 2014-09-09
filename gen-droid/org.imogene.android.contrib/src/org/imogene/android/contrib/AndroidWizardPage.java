package org.imogene.android.contrib;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.imogene.studio.contrib.ImogeneStudioPlugin;
import org.imogene.studio.contrib.ui.generation.GenerationWizardPage;

public class AndroidWizardPage extends WizardPage implements GenerationWizardPage {

	private Button multiloginButton;
	private Button debuggableButton;
	private Text sntpAddressText;
	private Text hiddenCodeText;
	private Text versionCodeText;
	private Text versionNameText;

	protected AndroidWizardPage() {
		super("AndroidProject");
		setPageComplete(false);
		setTitle("Android application properties");
		setDescription("Set android generated project properties.");
		setImageDescriptor(ImogeneStudioPlugin.getImageDescriptor("icons/gears.png"));
	}

	@Override
	public Map<String, String> getProperties() {
		HashMap<String, String> result = new HashMap<String, String>();
		result.put("Android_multilogin", Boolean.toString(multiloginButton.getSelection()));
		result.put("Android_sntpServer", sntpAddressText.getText());
		result.put("Android_codeHidden", hiddenCodeText.getText());
		result.put("Android_debuggable", Boolean.toString(debuggableButton.getSelection()));
		result.put("Android_versionCode", versionCodeText.getText());
		result.put("Android_versionName", versionNameText.getText());
		return result;
	}

	@Override
	public void createControl(Composite parent) {
		Composite layout = new Composite(parent, SWT.NONE);
		layout.setLayout(new GridLayout());

		createMultiloginComponent(layout);
		createApplicationGroup(layout);

		setControl(layout);
		validate();
	}

	private void createMultiloginComponent(Composite parent) {
		multiloginButton = new Button(parent, SWT.CHECK);
		multiloginButton.setText("Allow multilogin on the device");
		multiloginButton.setSelection(false);
	}

	private void createApplicationGroup(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setLayout(new GridLayout());
		group.setText("Application options");

		debuggableButton = new Button(group, SWT.CHECK);
		debuggableButton.setText("Whether or not the application can be debugged");
		debuggableButton.setSelection(true);
		debuggableButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				validate();
			}
		});

		Composite component = new Composite(group, SWT.NULL);
		component.setLayout(new GridLayout(2, false));
		component.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		createHiddenCodeComponent(component);
		createVersionCodeComponent(component);
		createVersionNameComponent(component);
		createSntpComponent(component);
	}

	private void createHiddenCodeComponent(Composite parent) {
		Label hiddenCodeLabel = new Label(parent, SWT.NONE);
		hiddenCodeLabel.setText("Hidden preferences activation code: ");

		hiddenCodeText = new Text(parent, SWT.BORDER);
		hiddenCodeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		hiddenCodeText.setText("1234");
		hiddenCodeText.setMessage("4 digits activation code (ex: 1234)");
		hiddenCodeText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
	}

	private void createVersionCodeComponent(Composite parent) {
		Label versionCodeLabel = new Label(parent, SWT.NONE);
		versionCodeLabel.setText("Version Code: ");

		versionCodeText = new Text(parent, SWT.BORDER);
		versionCodeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		versionCodeText.setText("1");
		versionCodeText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
	}

	private void createVersionNameComponent(Composite parent) {
		Label versionNameLabel = new Label(parent, SWT.NONE);
		versionNameLabel.setText("Version Name: ");

		versionNameText = new Text(parent, SWT.BORDER);
		versionNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		versionNameText.setText("1.0");
		versionNameText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
	}

	private void createSntpComponent(Composite parent) {
		Label sntpAddressLabel = new Label(parent, SWT.NONE);
		sntpAddressLabel.setText("NTP server address: ");

		sntpAddressText = new Text(parent, SWT.BORDER);
		sntpAddressText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		sntpAddressText.setText("europe.pool.ntp.org");
		sntpAddressText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
	}

	private void validate() {

		String sntpRegex = "^\\w+[\\w-\\.-:]*\\w$";
		String sntpAddress = sntpAddressText.getText();
		if (sntpAddress == null || !sntpAddress.matches(sntpRegex)) {
			setErrorMessage("The sntp address is not valid.");
			setPageComplete(false);
			return;
		}

		String codeRegex = "^\\d{4}$";
		String code = hiddenCodeText.getText();
		if (code == null || !code.matches(codeRegex)) {
			setErrorMessage("The activation code is not valid (4 digits required).");
			setPageComplete(false);
			return;
		}

		Integer versionCode = null;
		try {
			versionCode = Integer.valueOf(versionCodeText.getText());
		} catch (Exception e) {
		}
		if (versionCode == null) {
			setErrorMessage("The version code is not valid");
			setPageComplete(false);
			return;
		}

		String versionName = versionNameText.getText();
		if (versionName == null || versionName.length() < 1) {
			setErrorMessage("The version name is not valid");
			setPageComplete(false);
			return;
		}

		if (debuggableButton.getSelection()) {
			setMessage("You cannot export a debuggable application", WARNING);
		} else {
			setMessage(null);
		}

		setErrorMessage(null);
		setPageComplete(true);
	}

}

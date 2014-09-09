package org.imogene.web.contrib;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.imogene.studio.contrib.ui.generation.GenerationWizardPage;

public class SpecificWizardPage extends WizardPage implements GenerationWizardPage, SelectionListener {

	private static final String BROWER_GECKO_18 = "gecko1_8";
	private static final String BROWER_IE8 = "ie8";
	private static final String BROWER_IE9 = "ie9";
	private static final String BROWER_IE10 = "ie10";
	private static final String BROWER_OPERA = "opera";
	private static final String BROWER_SAFARI = "safari";

	private static final String SEPARATOR = ",";

	private static final String KEY_NAVIGATORS = "navigators";
	private static final String KEY_EMBEDDED = "embedded";

	public SpecificWizardPage() {
		super("Web application properties");
		setTitle("Web application properties");
		setDescription("Specific properties for the web generation process.");
	}

	private Button gecko18Button;
	private Button ie8Button;
	private Button ie9Button;
	private Button ie10Button;
	private Button operaButton;
	private Button safariButton;

	private Button embeddedButton;

	@Override
	public void createControl(Composite parent) {
		Composite mainComposite = new Composite(parent, SWT.NONE);
		mainComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));
		mainComposite.setLayout(new GridLayout());
		Group navigatorGroup = new Group(mainComposite, SWT.BORDER);
		navigatorGroup.setText("Select the target web browsers");
		navigatorGroup.setLayout(new GridLayout());
		navigatorGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));

		gecko18Button = new Button(navigatorGroup, SWT.CHECK);
		gecko18Button.setText("Firefox (Mozilla, ...)");
		gecko18Button.setSelection(true);
		gecko18Button.addSelectionListener(this);

		ie8Button = new Button(navigatorGroup, SWT.CHECK);
		ie8Button.setText("Internet Explorer 8");
		ie8Button.setSelection(true);
		ie8Button.addSelectionListener(this);

		ie9Button = new Button(navigatorGroup, SWT.CHECK);
		ie9Button.setText("Internet Explorer 9");
		ie9Button.setSelection(true);
		ie9Button.addSelectionListener(this);

		ie10Button = new Button(navigatorGroup, SWT.CHECK);
		ie10Button.setText("Internet Explorer 10");
		ie10Button.setSelection(true);
		ie10Button.addSelectionListener(this);

		operaButton = new Button(navigatorGroup, SWT.CHECK);
		operaButton.setText("Opera");
		operaButton.setSelection(true);
		operaButton.addSelectionListener(this);

		safariButton = new Button(navigatorGroup, SWT.CHECK);
		safariButton.setText("Chrome, Safari");
		safariButton.setSelection(true);
		safariButton.addSelectionListener(this);

		Group embbededGroup = new Group(mainComposite, SWT.BORDER);
		embbededGroup.setText("Embedding web application");
		embbededGroup.setLayout(new GridLayout());
		embbededGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));

		embeddedButton = new Button(embbededGroup, SWT.CHECK);
		embeddedButton.setText("Embeddded");
		embeddedButton.setSelection(false);
		embeddedButton.addSelectionListener(this);

		setControl(mainComposite);
		setPageComplete(true);
	}

	@Override
	public Map<String, String> getProperties() {
		Map<String, String> prop = new HashMap<String, String>();
		prop.put(KEY_NAVIGATORS, getProperty());
		prop.put(KEY_EMBEDDED, Boolean.toString(embeddedButton.getSelection()));
		return prop;
	}

	private String getProperty() {
		StringBuffer buffer = new StringBuffer();
		if (gecko18Button.getSelection()) {
			buffer.append(BROWER_GECKO_18).append(SEPARATOR);
		}
		if (ie8Button.getSelection()) {
			buffer.append(BROWER_IE8).append(SEPARATOR);
		}
		if (ie9Button.getSelection()) {
			buffer.append(BROWER_IE9).append(SEPARATOR);
		}
		if (ie10Button.getSelection()) {
			buffer.append(BROWER_IE10).append(SEPARATOR);
		}
		if (operaButton.getSelection()) {
			buffer.append(BROWER_OPERA).append(SEPARATOR);
		}
		if (safariButton.getSelection()) {
			buffer.append(BROWER_SAFARI).append(SEPARATOR);
		}
		return buffer.toString().substring(0, buffer.length() - 1);
	}

	private boolean validate() {
		boolean valid = gecko18Button.getSelection() || operaButton.getSelection() || safariButton.getSelection()
				|| ie8Button.getSelection() || ie9Button.getSelection() || ie10Button.getSelection();
		if (!valid) {
			setErrorMessage("You have to choose at least one web browser.");
			setPageComplete(false);
		} else {
			setErrorMessage(null);
			setPageComplete(true);
		}
		return valid;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		validate();
	}

}

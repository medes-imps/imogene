package org.imogene.client.contrib;

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
	
	//private static final String GECKO = "gecko";	
	private static final String SYNC_CLIENT = "sync.client";
	
	private static final String SYNC_PLUGIN = "sync.plugin";
	
	private static final String PROPERTY_KEY = "applicationType";
	
	
	public SpecificWizardPage(){
		super("Sync client properties");
		setTitle("Sync client properties");
		setDescription("Specific properties for the sync client generation process.");
	}
	
	private Button syncClientButton;
	
	private Button syncPluginButton;
		


	@Override
	public void createControl(Composite parent) {		
		Composite mainComposite = new Composite(parent, SWT.NONE);
		mainComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));
		mainComposite.setLayout(new GridLayout());
		Group navigatorGroup = new Group(mainComposite, SWT.BORDER);		
		navigatorGroup.setText("Select the target client");		
		navigatorGroup.setLayout(new GridLayout());
		navigatorGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL|GridData.GRAB_HORIZONTAL));		
		
		syncClientButton = new Button(navigatorGroup, SWT.RADIO);
		syncClientButton.setText("External client (Java project)");			
		syncClientButton.addSelectionListener(this);
				
		syncPluginButton = new Button(navigatorGroup, SWT.RADIO);
		syncPluginButton.setText("Eclipse plugin client");
		syncPluginButton.setSelection(true);
		syncPluginButton.addSelectionListener(this);
			
		setControl(mainComposite);
		setPageComplete(true);
	}


	@Override
	public Map<String, String> getProperties() {
		Map<String, String> prop = new HashMap<String, String>();
		prop.put(PROPERTY_KEY, getProperty());
		return prop;
	}
	
	private String getProperty(){
		if(syncClientButton.getSelection())
			return SYNC_CLIENT;		
		return SYNC_PLUGIN;
	}
	
	private boolean validate(){	
		boolean valid = syncClientButton.getSelection()||syncPluginButton.getSelection();
		if(!valid){
			setErrorMessage("You have to choose at least one target.");
			setPageComplete(false);
		}else{
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

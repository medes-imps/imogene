package org.imogene.studio.contrib.ui.generation;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.imogene.studio.contrib.ImogeneStudioPlugin;


/**
 * Common wizard page used for the creation of a generated project.
 * Able the user to set the project name and the workflow to use.
 * @author Medes-IMPS 
 */
public class CommonGenerationWizardPage extends WizardPage implements GenerationWizardPage {	

	private Map<String, String> workflows = new HashMap<String, String>();	
	
	private Group specificWorkflowGroup;
	
	private Button specificYesButton;

	private Button specificNoButton;
	
	private Combo specificsList;
	
	private Group projectNameGroup;
	
	private Label projectNameLabel;
	
	private Text projectNameBox;
	
	
	public CommonGenerationWizardPage(String pageName){
		super(pageName);	
		setPageComplete(false);
		setTitle(Messages.CommonGenerationWizardPage_0);
		setDescription(Messages.CommonGenerationWizardPage_1);
		setImageDescriptor(ImogeneStudioPlugin.getImageDescriptor("icons/gears.png")); //$NON-NLS-1$
	}
	
	@Override
	public void createControl(Composite parent) {
		Composite mainComposite = new Composite(parent, SWT.NONE);
		mainComposite.setLayout(new GridLayout());
		createSpecificGroup(mainComposite);
		createProjectNameGroup(mainComposite);
		setControl(mainComposite);		
	}
	
	/** Create the group that permits to select a specific workflow */
	private void createSpecificGroup(Composite parent){
		specificWorkflowGroup = new Group(parent, SWT.NONE);
		specificWorkflowGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		specificWorkflowGroup.setLayout(new GridLayout());
		specificWorkflowGroup.setText(Messages.CommonGenerationWizardPage_3);

		specificNoButton = new Button(specificWorkflowGroup, SWT.RADIO);
		specificYesButton = new Button(specificWorkflowGroup, SWT.RADIO);

		specificNoButton.setText(Messages.CommonGenerationWizardPage_4);
		specificNoButton.setSelection(true);
		specificNoButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				specificYesButton.setSelection(false);
				specificNoButton.setSelection(true);
				specificsList.setEnabled(false);
			}

		});

		specificYesButton.setText(Messages.CommonGenerationWizardPage_5);
		specificYesButton.setSelection(false);
		specificYesButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				specificNoButton.setSelection(false);		
				specificYesButton.setSelection(true);
				specificsList.setEnabled(true);
			}

		});

		Composite keyComposite = new Composite(specificWorkflowGroup, SWT.NONE);
		keyComposite.setLayout(new GridLayout(2, false));
		keyComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Label keyLabel = new Label(keyComposite, SWT.NONE);
		keyLabel.setText(Messages.CommonGenerationWizardPage_6);
		specificsList = new Combo(keyComposite, SWT.DROP_DOWN | SWT.READ_ONLY);		
		specificsList.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		specificsList.setEnabled(false);
		for (String key : workflows.keySet()) {
			specificsList.add(key);
		}
		specificGroupVisibiltiy();
	}
	
	/** create the group that permits to set the project name */
	private void createProjectNameGroup(Composite parent){
		projectNameGroup = new Group(parent, SWT.NONE);
		projectNameGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		projectNameGroup.setLayout(new GridLayout(2, false));
		projectNameGroup.setText(Messages.CommonGenerationWizardPage_7);
		projectNameLabel = new Label(projectNameGroup, SWT.NONE);
		projectNameLabel.setText(Messages.CommonGenerationWizardPage_8);
		projectNameBox = new Text(projectNameGroup, SWT.BORDER);
		projectNameBox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		projectNameBox.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {				
				validate();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {								
			}
		});
	}
	
	/** enable or not the specific workflow selection */
	private void specificGroupVisibiltiy(){
		if(workflows.size()>0){
			specificWorkflowGroup.setEnabled(true);			
		} else {
			specificWorkflowGroup.setEnabled(false);
		}
	}
	
	
	@Override
	public Map<String, String> getProperties() {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("javaProjectName", getProjectName()); //$NON-NLS-1$
		return properties;
	}
	
	

	/**
	 * Add a specific workflow path choice.
	 * @param label the label
	 * @param path the workflow path
	 */
	public void addSpecificWorkflow(String label, String path) {
		workflows.put(label, path);
//		specificsList.add(label);
//		specificGroupVisibiltiy();
	}
			
	/** validate the page and displays the error message */
	private void validate(){
		String name = projectNameBox.getText().trim();
		if(!name.equals("")){ //$NON-NLS-1$
			setPageComplete(true);
			setErrorMessage(null);
		} else{
			setPageComplete(false);			
			setErrorMessage(Messages.CommonGenerationWizardPage_11);
		}
	}
	
	/**
	 * Return the specific workflow path is one is selected.
	 * @return the selected workflow path.
	 */
	public String getSpecificWorkflow(){
		if(specificYesButton.getSelection()){
			String selection = specificsList.getItem(specificsList.getSelectionIndex());
			return workflows.get(selection);
		}
		return null;
	}
	
	/**
	 * Return the selected project name.
	 * @return the selected project name
	 */
	public String getProjectName(){
		return projectNameBox.getText();
	}
	
	
	
}

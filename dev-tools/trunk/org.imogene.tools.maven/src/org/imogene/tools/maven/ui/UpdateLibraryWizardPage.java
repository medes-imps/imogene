package org.imogene.tools.maven.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.imogene.tools.maven.Activator;
import org.imogene.tools.maven.LibraryInfo;
import org.imogene.tools.maven.internal.LibraryUtils;
import org.xml.sax.SAXException;

public class UpdateLibraryWizardPage extends WizardPage {

	private Composite mainComposite;

	private Text filePath;

	private Button fileDialogButton;

	private Button updateButton;

	private Button addButton;

	private Combo librariesCombo;

	private Composite addComposite;

	private Label idLabel;

	private Text idText;

	private Label nameLabel;

	private Text nameText;

	private Label descLabel;

	private Text descText;
	
	private Button cleanButton;

	private boolean update = true;

	private DefaultSelectionAdapter selectionAdapter = new DefaultSelectionAdapter();
	
	private DefaultKeyListener keyAdapter = new DefaultKeyListener();
	
	private IProject project;
	
	private Map<String, LibraryInfo> librariesFiles = new HashMap<String, LibraryInfo>();
	
	public UpdateLibraryWizardPage(String pageName, IProject selection) {
		super(pageName);
		project = selection;		
		setTitle("Add/Update an imogene library");
		setDescription("Add/Update an imogene library from a maven pom.xml file");		
		setPageComplete(false);
	}

	@Override
	public void createControl(Composite parent) {
		mainComposite = new Composite(parent, SWT.NONE);
		mainComposite.setLayout(new GridLayout());
		createPomGroup(mainComposite);
		createLibGroup(mainComposite);
		createCleanOption();
		setControl(mainComposite);
		try {
			fillLibrariesCombo();
		} catch (CoreException e) {			
			e.printStackTrace();
			logError(e.getLocalizedMessage());
		}
		setAddSectionEnabled(false);
	}

	private void createPomGroup(final Composite parent) {
		Group pomGroup = new Group(parent, SWT.NONE);
		pomGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		pomGroup.setLayout(new GridLayout(2, false));
		pomGroup.setText("pom.xml file");

		filePath = new Text(pomGroup, SWT.READ_ONLY | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		filePath.setLayoutData(gd);

		fileDialogButton = new Button(pomGroup, SWT.PUSH);
		fileDialogButton.setText("...");
		fileDialogButton.addSelectionListener(selectionAdapter);
	}

	private void createLibGroup(final Composite parent) {
		Group libGroup = new Group(parent, SWT.NONE);
		libGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		libGroup.setLayout(new GridLayout());

		updateButton = new Button(libGroup, SWT.RADIO);
		updateButton.addSelectionListener(selectionAdapter);
		updateButton.setText("Update an exising library.");
		updateButton.setSelection(true);
		addButton = new Button(libGroup, SWT.RADIO);
		addButton.addSelectionListener(selectionAdapter);
		addButton.setText("Add a new library.");

		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		
		librariesCombo = new Combo(libGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
		librariesCombo.setLayoutData(gd);
		librariesCombo.addSelectionListener(selectionAdapter);

		addComposite = new Composite(libGroup, SWT.NONE);
		addComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		addComposite.setLayout(new GridLayout(2, false));

		idLabel = new Label(addComposite, SWT.NORMAL);
		idLabel.setText("Library ID:");
		idText = new Text(addComposite, SWT.BORDER);
		idText.addKeyListener(keyAdapter);
		idText.setLayoutData(gd);

		nameLabel = new Label(addComposite, SWT.NORMAL);
		nameLabel.setText("Library name:");
		nameText = new Text(addComposite, SWT.BORDER);
		nameText.addKeyListener(keyAdapter);
		nameText.setLayoutData(gd);

		descLabel = new Label(addComposite, SWT.NORMAL);
		descLabel.setText("Library description");
		descText = new Text(addComposite, SWT.BORDER | SWT.MULTI);
		descText.addKeyListener(keyAdapter);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint=100;
		descText.setLayoutData(gd);
	}	
	
	private void createCleanOption(){
		cleanButton = new Button(mainComposite, SWT.CHECK);
		cleanButton.setText("Delete unused jars");
	}
	
	private void fillLibrariesCombo() throws CoreException {
		IFolder folder = project.getFolder("libs");
		IResource[] members = folder.members();
		for(IResource m : members){
			if(m.getType() == IResource.FILE && m.getName().endsWith(".xml")){
				
				LibraryInfo libInfo = new LibraryInfo();
				try {
					libInfo = LibraryUtils.getLibraryInfo((IFile)m);
				} catch (ParserConfigurationException e) {					
					e.printStackTrace();
					logError(e.getMessage());
				} catch (SAXException e) {
					e.printStackTrace();
					logError(e.getMessage());
				} catch (IOException e) {
					e.printStackTrace();
					logError(e.getMessage());
				}
				librariesCombo.add(libInfo.getId());				
				librariesFiles.put(libInfo.getId(), libInfo);
			}
		}
	}
	
	private void setAddSectionEnabled(boolean enabled){
		idText.setEnabled(enabled);
		nameText.setEnabled(enabled);
		//descText.setEnabled(enabled);
	}	
	
	private void logError(String errorMessage){
		Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, errorMessage));
	}
	
	
	private void validate(){
		
		boolean fileSelection = checkTextNotEmpty(filePath);
		boolean libSelection = librariesCombo.getSelectionIndex()>-1;
		boolean libId = checkTextNotEmpty(idText);
		boolean uniqueId = checkLibExists(idText);
		boolean libName = checkNameField(nameText);
		boolean uniqueName = checkLibNameUnique(nameText);

		
		if(update){
			
			if(fileSelection && libSelection){
				setPageComplete(true);
				setErrorMessage(null);
			}else{
				setPageComplete(false);
				setErrorMessage("Please fill the mandatory fields.");
			}
			
		}else{
			if(fileSelection && libId && libName && uniqueId && uniqueName){
				setPageComplete(true);
				setErrorMessage(null);
			}else{
				setPageComplete(false);
				if(!fileSelection || !libId)
					setErrorMessage("Please fill the mandatory fields.");
				if(!uniqueName)
					setErrorMessage("This lib name already exists");
				if(!uniqueId)
					setErrorMessage("This lib id already exists");
				if(!libName){
					setErrorMessage("The name should be a valid file name (without extension) [a-z,A-Z,-,_,0-9]");
				}
				
			}
		}

	}
	
	private void populateWithSelectionInfo(){
		int selectedIndex = librariesCombo.getSelectionIndex();
		if(selectedIndex>-1){
			LibraryInfo info = librariesFiles.get(librariesCombo.getItem(selectedIndex));
			idText.setText(info.getId());
			nameText.setText(info.getName());
			descText.setText(info.getDescription());
		}
	}
	
	private void cleanSelectionInfo(){
		idText.setText("");
		nameText.setText("");
		descText.setText("");
	}
	
	private boolean checkTextNotEmpty(Text txt){
		return txt.getText()!=null && !txt.getText().equals("");
	}
	
	private boolean checkNameField(Text txt){
		if(checkTextNotEmpty(txt)){
			String name = txt.getText();
			return name.matches("[_a-zA-Z0-9\\-\\.]+");
		}
		return false;
	}
	
	private boolean checkLibExists(Text tx){
		String id = tx.getText();
		if(id!=null){
			if(librariesFiles.containsKey(id))
				return false;
		}
		return true;
	}
	
	private boolean checkLibNameUnique(Text txt){
		String name = txt.getText();
		if(name!=null){
			for(LibraryInfo info : librariesFiles.values()){
				if(String.valueOf(name+".xml").equals(info.getFileName()))
					return false;
			}			
		}
		return true;
	}

	public String getPomPath(){
		return filePath.getText();
	}
	
	public boolean isUpdate(){
		return update;
	}
	
	public LibraryInfo getLibInfo(){
		LibraryInfo libInfo;
		if(isUpdate()){
			libInfo = librariesFiles.get(librariesCombo.getItem(librariesCombo.getSelectionIndex()));
			libInfo.setDescription(descText.getText());
		}else{
			libInfo = new LibraryInfo();
			libInfo.setDescription(descText.getText());
			libInfo.setName(nameText.getText());
			libInfo.setId(idText.getText());
			libInfo.setFileName(libInfo.getName()+".xml");
		}	
		return libInfo;
	}
	
	public boolean cleanUnused(){
		return cleanButton.getSelection();
	}
	
	
	/** 
	 * 	internal classes 
	 * 						**/
	private class DefaultSelectionAdapter extends SelectionAdapter {

		@Override
		public void widgetSelected(SelectionEvent e) {
			if (e.getSource().equals(fileDialogButton)) {
				FileDialog fd = new FileDialog(mainComposite.getShell(),
						SWT.OPEN);
				fd.setFilterNames(new String[] { "Maven pom" });
				fd.setFilterExtensions(new String[] { "pom.xml" });
				String fullPath = fd.open();
				if(fullPath!=null)
					filePath.setText(fullPath);
				validate();
			} 
			else if (e.getSource().equals(addButton)) {
				if (addButton.getSelection()) {
					librariesCombo.setEnabled(false);
					setAddSectionEnabled(true);
					update = false;
					cleanSelectionInfo();
					validate();
				}
			} 
			else if (e.getSource().equals(updateButton)) {
				if (updateButton.getSelection()) {
					librariesCombo.setEnabled(true);
					setAddSectionEnabled(false);
					update = true;
					cleanSelectionInfo();
					validate();
				}
			}
			else if(e.getSource().equals(librariesCombo)) {
				validate();
				populateWithSelectionInfo();
			}
		}
	}	
	
	private class DefaultKeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			validate();
		}
	}
}

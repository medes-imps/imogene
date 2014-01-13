package org.imogene.model.core.descriptions;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.imogene.model.core.Description;
import org.imogene.model.core.ImogenePackage;

public class DescriptionWidget extends Composite implements MyItem {
	
	private Description description;
	private EditingDomain editingDomain;
	
	private LabelWidget label;
	
	private Text text;

	public DescriptionWidget(Composite parent, int style) {
		super(parent, style);
	}
	
	public void setDescription(Description description) {
		this.description = description;
		onDescriptionChanged();
	}
	
	public void setEditingDomain(EditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}
	
	public void setLabel(LabelWidget label) {
		this.label = label;
	}
	
	protected void onDescriptionChanged() {
		if (text == null) {
			createDescription();
		} else {
			updateText();
		}
		if (label != null) {
			label.setDescription(description);
		}
	}
	
	private void updateText() {
		text.removeModifyListener(mListener);
		text.setText(description.getDisplay() != null ? description.getDisplay() : "");
		text.addModifyListener(mListener);
	}
	
	@Override
	public void updateItem() {
		updateText();
		if (label != null) {
			label.setDescription(description);
		}
	}
	
	@Override
	public void refreshItem() {
		onDescriptionChanged();
	}
	
	private void createDescription() {
		FormToolkit toolkit = new FormToolkit(getDisplay());

		setLayout(new GridLayout());
		
		text = toolkit.createText(this, "", SWT.NONE);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.setText(description.getDisplay() != null ? description.getDisplay() : "");
		text.addModifyListener(mListener);
	}
	
	@Override
	public List<MyItem> getChildrenItem() {
		return null;
	}
	
	@Override
	public EObject getEObject() {
		return description;
	}
	
	@Override
	public boolean hasChildren() {
		return false;
	}
	
	private final ModifyListener mListener = new ModifyListener() {
		
		@Override
		public void modifyText(ModifyEvent e) {
			SetCommand set = new SetCommand(editingDomain, description, ImogenePackage.eINSTANCE.getDescription_Display(), text.getText());
			editingDomain.getCommandStack().execute(set);
		}
	};

}

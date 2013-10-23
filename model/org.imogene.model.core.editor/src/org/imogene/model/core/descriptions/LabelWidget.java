package org.imogene.model.core.descriptions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.imogene.model.core.Description;

public class LabelWidget extends Composite {
	
	private Description description;
	private Label label;

	public LabelWidget(Composite parent, int style) {
		super(parent, style);
	}
	
	public void setDescription(Description description) {
		this.description = description;
		onDescriptionChanged();
	}
	
	protected void onDescriptionChanged() {
		if (label == null) {
			createLabel();
		} else {
			updateLabel();
		}
	}
	
	private void updateLabel() {
		label.setText(description.getLocale() != null ? description.getLocale() : "");
		this.getParent().layout();
	}
	
	private void createLabel() {
		FormToolkit toolkit = new FormToolkit(getDisplay());

		setLayout(new GridLayout());
		
		label = toolkit.createLabel(this, description.getLocale(), SWT.NONE);
		label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER));
	}

}

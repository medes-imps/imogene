package org.imogene.studio.contrib.action;

import java.io.IOException;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.Description;
import org.imogene.model.core.EnumField;
import org.imogene.model.core.EnumValue;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FieldGroup;
import org.imogene.model.core.ImogeneFactory;
import org.imogene.model.core.Language;
import org.imogene.model.core.Project;
import org.imogene.model.core.StringField;
import org.imogene.model.core.Thema;
import org.imogene.model.core.ValidationRule;
import org.imogene.model.core.impl.ImogeneFactoryImpl;


public class AddDescriptionsAction implements IObjectActionDelegate {
	
    // Store the current model resource selection
    protected IStructuredSelection _selection;

    // Store the currently selected project
    protected IFile _selectedFile;
	
    @Override
	public void setActivePart(IAction action, IWorkbenchPart workbench) {
	}

	@Override
	public void run(IAction action) {
        String fullPath = _selectedFile.getFullPath().toPortableString();
        URI resourceURI = URI.createPlatformResourceURI(fullPath, true);
        Resource r = loadModel(resourceURI);
        
        Project model = (Project) r.getContents().get(0);
        Vector<String> isos = new Vector<String>();
        for (Language language : model.getLanguages()) {
        	if (!isos.contains(language.getIsoCode()))
        		isos.add(language.getIsoCode());
        }
        
        ImogeneFactory factory = ImogeneFactoryImpl.init();
        for (String iso : isos) {
        	if (!containIso(model.getDescription(), iso)) {
    			Description description = factory.createDescription();
    			description.setDisplay(model.getName());
    			description.setLocale(iso);
    			model.getDescription().add(description);
        	}
        	for (Thema thema : model.getThemas()) {
        		if (!containIso(thema.getDescriptions(), iso)) {
        			Description description = factory.createDescription();
        			description.setDisplay(thema.getName());
        			description.setLocale(iso);
        			thema.getDescriptions().add(description);
        		}
        	}
        	for (CardEntity card : model.getEntities()) {
        		if (!containIso(card.getDescriptions(), iso)) {
        			Description description = factory.createDescription();
        			description.setDisplay(card.getName());
        			description.setLocale(iso);
        			card.getDescriptions().add(description);
        		}
        		for (FieldGroup group : card.getGroups()) {
        			if (!containIso(group.getDescriptions(), iso)) {
        				Description description = factory.createDescription();
            			description.setDisplay(group.getName());
            			description.setLocale(iso);
            			group.getDescriptions().add(description);
        			}
        			for (FieldEntity field : group.getFields()) {
        				if (!containIso(field.getDescriptions(), iso)) {
            				Description description = factory.createDescription();
                			description.setDisplay(field.getName());
                			description.setLocale(iso);
                			field.getDescriptions().add(description);
        				}
        				if (field instanceof EnumField) {
        					for (EnumValue value : ((EnumField) field).getEnumValues()) {
        						if (!containIso(value.getDescriptions(), iso)) {
        							Description description = factory.createDescription();
                        			description.setDisplay(value.getName());
                        			description.setLocale(iso);
                        			value.getDescriptions().add(description);
        						}
        					}
        				} else if (field instanceof StringField) {
        					for (ValidationRule rule : ((StringField) field).getValidationRules()) {
        						if (!containIso(rule.getDescriptions(), iso)) {
        							Description description = factory.createDescription();
        							description.setDisplay(rule.getValidationRegularExpression());
        							description.setLocale(iso);
        							rule.getDescriptions().add(description);
        						}
        					}
        				}
        			}
        		}
        	}
        }
        try {
            r.save(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
        _selection = (IStructuredSelection) selection;
        _selectedFile = (IFile) (_selection.getFirstElement());  
	}
	
    public static Resource loadModel(final URI resUri) {
        return new ResourceSetImpl().getResource(resUri, true);
    }
    
    private boolean containIso(EList<Description> descriptions, final String iso) {
    	for (Description description : descriptions) {
    		if (iso.equals(description.getLocale()))
    			return true;
    	}
    	return false;
    }

}

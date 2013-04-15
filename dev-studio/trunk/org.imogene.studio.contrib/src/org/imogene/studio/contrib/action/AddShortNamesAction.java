package org.imogene.studio.contrib.action;

import java.io.IOException;
import java.util.UUID;

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
import org.imogene.model.core.Actor;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FieldGroup;
import org.imogene.model.core.FilterField;
import org.imogene.model.core.Project;
import org.imogene.studio.contrib.internal.ShortNameGenerator;



/**
 * Action that permits to generate the not 
 * field short names on a medoo model file.
 */
public class AddShortNamesAction implements IObjectActionDelegate {
	// Store the current model resource selection
	protected IStructuredSelection _selection;

	// Store the currently selected project
	protected IFile _selectedFile;

	private ShortNameGenerator shortNameGenerator;

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	public void run(IAction action) {
		String fullPath = _selectedFile.getFullPath().toPortableString();
		URI resourceURI = URI.createPlatformResourceURI(fullPath, true);
		Resource r = loadModel(resourceURI);

		Project model = (Project) r.getContents().get(0);
		EList<CardEntity> entities = model.getEntities();

		shortNameGenerator = new ShortNameGenerator(entities);
		
		// remove old shortnames 
		for (CardEntity entity : entities) {
			for (FieldGroup group : entity.getGroups()) {
				group.setShortName(null);
				for (FieldEntity field : group.getFields()) {
					field.setShortName(null);
				}
			}
			
			if (entity instanceof Actor) {
				for (FilterField filterField : ((Actor) entity).getFilters()) {
					filterField.setShortName(null);
				}
			}
		}
		
		// list and remove duplicates
		shortNameGenerator.listShortNames(true);

		// --- attributes shortnames to cards, groups and fields
		for (CardEntity cardEntity : entities) {
			cardEntity.setShortName(shortNameGenerator.getNextCardShortName());
			for (FieldGroup fieldGroup : cardEntity.getGroups()) {
				fieldGroup.setShortName(shortNameGenerator
						.getNextGroupShortName());
				for (FieldEntity fieldEntity : fieldGroup.getFields()) {
					fieldEntity.setShortName(shortNameGenerator
							.getNextFieldShortName());
				}
			}
			if (cardEntity instanceof Actor) {
				for (FilterField filterField : ((Actor) cardEntity).getFilters()) {
					filterField.setShortName(UUID.randomUUID().toString());
				}
			}
			
		}
		try {
			r.save(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		_selection = (IStructuredSelection) selection;
		_selectedFile = (IFile) (_selection.getFirstElement());
	}

	public static Resource loadModel(final URI resUri) {
		return new ResourceSetImpl().getResource(resUri, true);
	}
}

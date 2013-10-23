package org.imogene.model.validation.constraints;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.Project;


/**
 * Check that two entity dont have the same name
 */
public class DuplicateEntityName extends AbstractMedanyModelConstraint {

	private String ERROR_MESSAGE="Two entities have the same name \"%ENTITY_NAME%\"";
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
	 */
	public IStatus validate(IValidationContext ctx) {
		
		List<String> entityNames = new Vector<String>();
		
		if(ctx.getTarget() instanceof Project){
			Project project = (Project)ctx.getTarget();
			for(Iterator<CardEntity> it = project.getEntities().iterator(); it.hasNext();){
				CardEntity entity = it.next();
				if(entity.getName()!=null){
					if(entityNames.contains(entity.getName()))
						return ctx.createFailureStatus(new Object[]{this.formatMessage(ERROR_MESSAGE, entity)});
					else
						entityNames.add(entity.getName());
				}
			}
		}
		return ctx.createSuccessStatus();
	}
	

}

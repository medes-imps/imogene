package org.imogene.model.validation.constraints;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.FieldGroup;


/**
 * check that two groups in the same entity don't have the same name.
 * @author MEDES-IMPS 
 */
public class DuplicateGroupName extends AbstractMedanyModelConstraint {	
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
	 */
	public IStatus validate(IValidationContext ctx) {
		List<String> groupNames = new Vector<String>();
		try{
		if(ctx.getTarget() instanceof CardEntity){
			CardEntity card = (CardEntity)ctx.getTarget();
			for(Iterator<FieldGroup> itg = card.getGroups().iterator(); itg.hasNext();){
				FieldGroup group = itg.next();
				if(group.getName()!=null){
					if(groupNames.contains(group.getName())){
						return ctx.createFailureStatus(new Object[]{getErrorMessage(card.getName(), group.getName())});
					}else{
						groupNames.add(group.getName());
					}
				}
			}
		}
		}catch(Exception ex){
			ex.printStackTrace();
			return ctx.createSuccessStatus();
		}
		return ctx.createSuccessStatus();
	}

	/** create the error message */
	private String getErrorMessage(String entityName, String groupName){
		return new String("two groups have the same name \"" + groupName + "\" in the entity \"" + entityName + "\".");
	}
	
}

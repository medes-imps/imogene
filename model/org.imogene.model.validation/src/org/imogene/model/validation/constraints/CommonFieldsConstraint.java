package org.imogene.model.validation.constraints;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.imogene.model.core.RelationFieldEntity;


/**
 * CHeck that common fields on a relation are correctly set.
 * @author MEDES-IMPS 
 */
public class CommonFieldsConstraint extends AbstractMedanyModelConstraint {

	private static String INCONSISTENT_COMMONFIELDS = "CommonFields of the relation \"%FIELD_NAME%\" of the entity \"%ENTITY_NAME%\" are inconsistent, please correct it.";
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
	 */
	public IStatus validate(IValidationContext ctx) {
		if(ctx.getTarget() instanceof RelationFieldEntity){
			RelationFieldEntity relation = (RelationFieldEntity)ctx.getTarget();
			if(relation.getCommonFields().size()%2!=0){
				return ctx.createFailureStatus(new Object[] {this.formatMessage(INCONSISTENT_COMMONFIELDS, relation.getParentCard(), relation)});
			}
		}
		return  ctx.createSuccessStatus();
	}
	

}

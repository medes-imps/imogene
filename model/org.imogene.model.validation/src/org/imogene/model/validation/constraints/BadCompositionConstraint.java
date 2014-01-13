package org.imogene.model.validation.constraints;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.imogene.model.core.MainRelationFieldEntity;
import org.imogene.model.core.RelationFieldEntity;
import org.imogene.model.core.RelationType;


/**
 * Check COMOSITION constraints
 * @author MEDES-IMPS
 */
public class BadCompositionConstraint extends AbstractMedanyModelConstraint{

	public IStatus validate(IValidationContext ctx) {
		try{
			
			if(ctx.getTarget() instanceof MainRelationFieldEntity){
				RelationFieldEntity relation = (RelationFieldEntity)ctx.getTarget();
				if(relation.getType().getValue() == RelationType.COMPOSITION){
					if(((MainRelationFieldEntity)relation).getInverseCardinality()!=1 && relation.getOppositeRelationField()==null){
						return ctx.createFailureStatus(new Object[]{getErrorMessage(relation.getParentCard().getName(), relation.getName())});
					}else if(relation.getOppositeRelationField()!=null && relation.getOppositeRelationField().getCardinality()!=1){
						RelationFieldEntity opposite = relation.getOppositeRelationField();
						return ctx.createFailureStatus(new Object[]{getErrorMessageOpposite(opposite.getParentCard().getName(), relation.getName(), opposite.getName())});
					}
					
				}
				
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return ctx.createSuccessStatus();
		}
		return ctx.createSuccessStatus();
	}
	
	
	/** create error message*/
	private String getErrorMessage(String entityName, String relationName){
		return new String("In the case of a COMPOSITION the inverse cardinality can not be different from 1, check the relation \"" + relationName + "\" of the entity \"" + entityName + "\".");
	}
	
	/** create error message*/
	private String getErrorMessageOpposite(String entityName, String relationName, String oppositeName){
		return new String("The opposite relation \"" + oppositeName + "\" of the entity \"" + entityName + "\", that is the opposite of a COMPOSITION \"" + relationName +  "\"can not declare a cardinality different of 1.");
	}

}

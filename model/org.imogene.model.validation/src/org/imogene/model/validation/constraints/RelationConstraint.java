/**
 *
 */

package org.imogene.model.validation.constraints;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.imogene.model.core.MainRelationFieldEntity;
import org.imogene.model.core.RelationType;
import org.imogene.model.core.ReverseRelationFieldEntity;


/**
 * Check constraints about <code>RelationFieldEntity</code>
 */
public class RelationConstraint
	extends AbstractMedanyModelConstraint {
	
	private static String INVERSE_CARDINALITY_ERROR = "The main relation field \"%FIELD_NAME%\" of the entity \"%ENTITY_NAME%\" should reference an opposite relation field or declare an inverse cardinality.";

	private static String REVERSE_WITHOUT_OPPOSITE = "The reverse relation field \"%FIELD_NAME%\" of the entity \"%ENTITY_NAME%\" shoud reference an opposite relation field.";
	
	private static String MAIN_REVERSE_IS_MAIN = "The opposite relation of the main relation field \"%FIELD_NAME%\" of the entity \"%ENTITY_NAME%\" shoud be a ReverseRelationField one and not a MainRelationField.";
	
	private static String REVERSE_REVERSE_IS_REVERSE = "The opposite relation of the reverse relation field \"%FIELD_NAME%\" of the entity \"%ENTITY_NAME%\" shoud be a MainRelationField one and not a ReverseRelationField.";
	
	private static String REVERSE_COMPOSITION = "The reverse relation \"%FIELD_NAME%\" of the entity \"%ENTITY_NAME%\" can't be of COMPOSITION type.";
	
	private static String SAME_TYPE_MAIN = "The relation \"%FIELD_NAME%\" and its opposite should not be of the same type (MainRelationField).";
	
	private static String SAME_TYPE_OPPOSITE = "The relation \"%FIELD_NAME%\" and its opposite should not be of the same type (OppositeRelationField).";
	
	private static String CARDINALITIES_ERROR = "The main relation field \"%FIELD_NAME%\" of the entity \"%ENTITY_NAME%\" cardinality is n, the inverse cardinality is 1 but the opposite is not declared";
	
	/**
	 * Initializes me.
	 */
	public RelationConstraint() {
		super();
	}

	/**
	 * I fail when constraints on RelationFieldEntity are not correct.
	 */
	public IStatus validate(IValidationContext ctx) {
		
		if(ctx.getTarget() instanceof MainRelationFieldEntity){
		
			MainRelationFieldEntity mainField = (MainRelationFieldEntity)ctx.getTarget();
			
			/* test the cardinality is n, the inverse cardinality is 1 and the opposite doesn't exist */
			if (mainField.getCardinality() != 1 && mainField.getInverseCardinality() == 1 && mainField.getOppositeRelationField() == null)
				return ctx.createFailureStatus(new Object[] {formatMessage(CARDINALITIES_ERROR, mainField.getParentCard(), mainField)});
			
			/* test the inverse relation is not an inverse relation */
			if(mainField.getOppositeRelationField()!=null && mainField.getOppositeRelationField() instanceof MainRelationFieldEntity)
				return ctx.createFailureStatus(new Object[] {formatMessage(SAME_TYPE_MAIN, mainField.getParentCard(), mainField)});
			
			/* test if inverse cardinality is present if it is mandatory */			
			if(mainField.getInverseCardinality() == 0 && mainField.getOppositeRelationField()==null){				
				return ctx.createFailureStatus(new Object[] {formatMessage(INVERSE_CARDINALITY_ERROR, mainField.getParentCard(), mainField)});
			}	
			
			/* test that the opposite relation is a reverse one and not a main*/
			if(mainField.getOppositeRelationField()!=null && mainField.getOppositeRelationField() instanceof MainRelationFieldEntity){
				return ctx.createFailureStatus(new Object[] {formatMessage(MAIN_REVERSE_IS_MAIN, mainField.getParentCard(), mainField)});
			}						
			
		}else if(ctx.getTarget() instanceof ReverseRelationFieldEntity){
			ReverseRelationFieldEntity reverseField = (ReverseRelationFieldEntity)ctx.getTarget();

			/* test the inverse relation is not an inverse relation */
			if(reverseField.getOppositeRelationField()!=null && reverseField.getOppositeRelationField() instanceof ReverseRelationFieldEntity)
				return ctx.createFailureStatus(new Object[] {formatMessage(SAME_TYPE_OPPOSITE, reverseField.getParentCard(), reverseField)});
			
			/* test if the opposite field is set, because it is mandatory in the case of a ReverseRelationField*/			
			if(reverseField.getOppositeRelationField()==null){
				return ctx.createFailureStatus(new Object[] {formatMessage(REVERSE_WITHOUT_OPPOSITE, reverseField.getParentCard(), reverseField)});
			}
			
			/* test if the opposite field is a MainRelationField and not a ReverseRelationField too */
			if(reverseField.getOppositeRelationField()!=null && reverseField.getOppositeRelationField() instanceof ReverseRelationFieldEntity){
				return ctx.createFailureStatus(new Object[] {formatMessage(REVERSE_REVERSE_IS_REVERSE, reverseField.getParentCard(), reverseField)});
			}
			
			/* Check that this reverse relationisn't a composition */
			if(reverseField.getType().getValue() == RelationType.COMPOSITION){
				return ctx.createFailureStatus(new Object[] {formatMessage(REVERSE_COMPOSITION, reverseField.getParentCard(), reverseField)});
			}			
			
		}
		return ctx.createSuccessStatus();
	}
}

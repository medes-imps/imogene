package org.imogene.model.validation.constraints;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.FieldEntity;

/**
 * Check that main fields and secondary fields appear in the column fields.
 * 
 * @author MEDES-IMPS
 */
public class ColumnFieldsValidation extends AbstractMedanyModelConstraint {
	
	private static String FIELD_MISSING = "In the entity \"%ENTITY_NAME%\" all main and secondary fields must appear in the column fields";
	
	public IStatus validate(IValidationContext ctx) {
		if(ctx.getTarget() instanceof CardEntity){
			CardEntity cardEntity = (CardEntity) ctx.getTarget();
			
			Set<FieldEntity> fields = new HashSet<FieldEntity>();
			fields.addAll(cardEntity.getMainFields());
			fields.addAll(cardEntity.getSecondaryFields());
			
			for (FieldEntity field : fields) {
				if (!cardEntity.getColumnFields().contains(field)) {
					return ctx.createFailureStatus(new Object[]{formatMessage(FIELD_MISSING, cardEntity)});					
				}
			}
			
		}
		return ctx.createSuccessStatus();
	}
	
}

package org.imogene.model.validation.constraints;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FieldGroup;


/**
 * Chack that two fields of a CardEntity don't have the same name
 * 
 * @author MEDEs-IMPS
 * 
 */
public class DuplicateFieldName extends AbstractMedanyModelConstraint {
	

	private static String DUPLICATE_FIELDNAME = "duplicate field name \"%FIELD_NAME%\" in the entity \"%ENTITY_NAME%\".";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
	 */
	public IStatus validate(IValidationContext ctx) {
		List<String> fieldNames = new Vector<String>();
		try {
			if (ctx.getTarget() instanceof CardEntity) {				
				CardEntity entity = (CardEntity) ctx.getTarget();
				for (Iterator<FieldGroup> it = entity.getGroups().iterator(); it.hasNext();) {
					FieldGroup group = it.next();
					for (Iterator<FieldEntity> itf = group.getFields().iterator(); itf
							.hasNext();) {
						FieldEntity field = itf.next();					
						if (field.getName() != null) {
							if (fieldNames.contains(field.getName())) {
								return ctx.createFailureStatus(new Object[] { this
										.formatMessage(DUPLICATE_FIELDNAME,
												entity, field)});
							} else {
								fieldNames.add(field.getName());
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ctx.createSuccessStatus();
		}
		return ctx.createSuccessStatus();
	}

}

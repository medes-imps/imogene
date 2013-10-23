package org.imogene.model.validation.constraints;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FieldGroup;


/**
 * Check that an entity and one of its fields dont have the same name.
 * 
 * @author MEDES-IMPS
 */
public class DuplicateNameEntityFieldConstraint extends
		AbstractMedanyModelConstraint {	

	private static String DUPLICATE_NAME = "duplicate name between entity \"%FIELD_NAME%\" and one of its fields";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
	 */
	public IStatus validate(IValidationContext ctx) {
		
		try {
			if (ctx.getTarget() instanceof CardEntity) {
				CardEntity card = (CardEntity) ctx.getTarget();
				List<FieldGroup> groups = card.getGroups();
				if (groups != null) {
					for (Iterator<FieldGroup> it = groups.iterator(); it.hasNext();) {
						FieldGroup group = it.next();
						for (Iterator<FieldEntity> itf = group.getFields().iterator(); itf
								.hasNext();) {
							FieldEntity field = itf.next();
							if (field.getName() != null
									&& card.getName() != null) {
								if (field.getName().equals(card.getName()))
									return ctx
											.createFailureStatus(new Object[] { this
													.formatMessage(
															DUPLICATE_NAME,
															field) });
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

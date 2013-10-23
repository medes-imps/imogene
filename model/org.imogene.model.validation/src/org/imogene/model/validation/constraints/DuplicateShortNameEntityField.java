package org.imogene.model.validation.constraints;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FieldGroup;


/**
 * Check that a group dont have the same name that the card one.
 * 
 * @author MEDES-IMPS
 * 
 */
public class DuplicateShortNameEntityField extends
		AbstractMedanyModelConstraint {

	private static String DUPLICATE_SHORTNAME = "duplicate shortname between entity \"%FIELD_NAME%\" and one of its fields";

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
						FieldGroup group = (FieldGroup) it.next();
						for (Iterator<FieldEntity> itf = group.getFields().iterator(); itf
								.hasNext();) {
							FieldEntity field = itf.next();
							if (field.getShortName() != null
									&& card.getShortName() != null) {
								if (field.getShortName().equals(
										card.getShortName()))
									return ctx
											.createFailureStatus(new Object[] { this
													.formatMessage(
															DUPLICATE_SHORTNAME,
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

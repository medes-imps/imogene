package org.imogene.model.validation.constraints;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.imogene.model.core.EnumField;
import org.imogene.model.core.EnumValue;


/**
 * check that two enum value dont have the same name.
 * 
 * @author MEDES-IMPS
 */
public class DuplicateNameEnumValue extends AbstractMedanyModelConstraint {


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
	 */
	public IStatus validate(IValidationContext ctx) {
		List<String> valueNames = new Vector<String>();
		try {
			if (ctx.getTarget() instanceof EnumField) {
				EnumField field = (EnumField) ctx.getTarget();
				for (Iterator<EnumValue> itv = field.getEnumValues().iterator(); itv
						.hasNext();) {
					EnumValue value = itv.next();
					if (value.getName() != null) {
						if (valueNames.contains(value.getName())) {
							return ctx
									.createFailureStatus(new Object[] { getErrorMessage(
											field.getParentCard().getName(),
											field.getName(), value.getName()) });
						} else {
							valueNames.add(value.getName());
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

	private String getErrorMessage(String cardName, String enumName,
			String valueName) {
		return new String("Two values of the EnumField \"" + enumName
				+ "\" of the card \"" + cardName + "\" have the same name \""
				+ valueName + "\".");
	}

}

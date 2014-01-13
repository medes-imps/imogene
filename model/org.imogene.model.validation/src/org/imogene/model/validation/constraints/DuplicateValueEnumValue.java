package org.imogene.model.validation.constraints;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.imogene.model.core.EnumField;
import org.imogene.model.core.EnumValue;


/**
 * check that two enum value dont have the same value.
 * 
 * @author MEDES-IMPS
 */
public class DuplicateValueEnumValue extends AbstractMedanyModelConstraint {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
	 */
	public IStatus validate(IValidationContext ctx) {
		List<Integer> valueNames = new Vector<Integer>();
		try {
			if (ctx.getTarget() instanceof EnumField) {
				EnumField field = (EnumField) ctx.getTarget();
				for (Iterator<EnumValue> itv = field.getEnumValues().iterator(); itv
						.hasNext();) {
					EnumValue value = itv.next();
					if (valueNames.contains(value.getValue())) {
						return ctx
							.createFailureStatus(new Object[] { getErrorMessage(
									field.getParentCard().getName(),
									field.getName(), value.getValue()) });
					} else {
						valueNames.add(value.getValue());
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
			int value) {
		return new String("Two values of the EnumField \"" + enumName
				+ "\" of the card \"" + cardName + "\" have the same value \""
				+ value + "\".");
	}

}

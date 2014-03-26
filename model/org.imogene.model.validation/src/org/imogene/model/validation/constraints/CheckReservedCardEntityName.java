package org.imogene.model.validation.constraints;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.imogene.model.core.CardEntity;

/**
 * Check that entity name don't use reserved words
 * 
 * @author MEDES-IMPS
 */
public class CheckReservedCardEntityName extends AbstractMedanyModelConstraint {

	private String[] reservedNames = new String[] { "alert", "role", "defaultUser", "activity", "entity", "history",
			"case", "user" };

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
	 */
	public IStatus validate(IValidationContext ctx) {

		try {
			if (ctx.getTarget() instanceof CardEntity) {
				CardEntity card = (CardEntity) ctx.getTarget();
				for (int i = 0; i < reservedNames.length; i++)
					if (card.getName() != null && reservedNames[i].equals(card.getName().toLowerCase()))
						return ctx.createFailureStatus(new Object[] { getErrorMessage(card.getName()) });
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ctx.createSuccessStatus();
		}

		return ctx.createSuccessStatus();
	}

	/** create the error message */
	private String getErrorMessage(String cardName) {
		return new String("The card entity name \"" + cardName + "\" is a resrved word, please choose another.");
	}

}

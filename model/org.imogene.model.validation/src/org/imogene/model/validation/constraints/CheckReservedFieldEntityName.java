package org.imogene.model.validation.constraints;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.imogene.model.core.FieldEntity;

/**
 * Check that property names don't use reserved word.
 * 
 * @author MEDES-IMPS
 */
public class CheckReservedFieldEntityName extends AbstractMedanyModelConstraint {

	private String[] reservedNames = new String[] { "id", "Creator", "CreationDate", "LastModificationDate",
			"Latitude", "Longitude", "FieldComments", "FieldComment", "DisplayValue", "Login", "Password",
			"NotificationLocale", "Roles", "AuthorizedRoles", "NotificationMethod", "NotificationDataMethodName",
			"BeNotified", "NotificationDelegator", "Delegate", "modified", "modifiedBy", "modifiedFrom", "uploadDate",
			"created", "createdBy", "unread", "synchronized", "user" };

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse .emf.validation.IValidationContext)
	 */
	public IStatus validate(IValidationContext ctx) {
		try {
			if (ctx.getTarget() instanceof FieldEntity) {
				FieldEntity field = (FieldEntity) ctx.getTarget();
				for (int i = 0; i < reservedNames.length; i++)
					if (field.getName() != null && reservedNames[i].toLowerCase().equals(field.getName().toLowerCase()))
						return ctx.createFailureStatus(new Object[] { getErrorMessage(field.getName(), field
								.getParentCard().getName()) });
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return ctx.createSuccessStatus();
		}

		return ctx.createSuccessStatus();
	}

	/** create the error message */
	private String getErrorMessage(String fieldName, String cardName) {
		return new String("The field entity name \"" + fieldName + "\" (of the card entity \"" + cardName
				+ "\") is a reserved word, please choose another.");

	}

}

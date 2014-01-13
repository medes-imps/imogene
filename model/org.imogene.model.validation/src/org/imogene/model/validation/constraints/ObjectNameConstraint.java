/**
 * 
 */

package org.imogene.model.validation.constraints;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.EnumValue;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FieldGroup;
import org.imogene.model.core.Project;

/**
 * Check that object name match a regular expression
 */
public class ObjectNameConstraint extends AbstractMedanyModelConstraint {

	private static final String reg_ex = "^[a-zA-Z]+[a-zA-Z0-9_]*$";

	private static final String card_entity_reg_ex = "^[A-Z][a-z0-9][a-zA-Z0-9_]*$";

	private static String EMPTY_ENTITY_NAME = "The name of the entity \"%ENTITY_NAME%\" should not be empty";

	private static String EMPTY_GROUP_NAME = "The name of the field group \"%GROUP_NAME%\" should not be empty";

	private static String EMPTY_FIELD_NAME = "The name of the entity \"%FIELD_NAME%\" should not be empty";

	private static String EMPTY_PROJECT_NAME = "The name of the project is not valid and should only contain " + reg_ex;

	private static String BAD_ENTITY_NAME = "The name of the entity \"%ENTITY_NAME%\" should contain only "
			+ card_entity_reg_ex;

	private static String BAD_GROUP_NAME = "The name of the field group \"%GROUP_NAME%\" should contain only " + reg_ex;

	private static String BAD_FIELD_NAME = "The name of the field \"%FIELD_NAME%\" should contain only " + reg_ex;

	private static String BAD_PROJECT_NAME = "The name of the project is not valid and should only contain " + reg_ex;

	/**
	 * Initializes me.
	 */
	public ObjectNameConstraint() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse .emf.validation.IValidationContext)
	 */
	public IStatus validate(IValidationContext ctx) {
		try {
			if (ctx.getTarget() instanceof Project) {
				String projectName = ((Project) ctx.getTarget()).getName();
				if (projectName != null) {
					if (projectName.isEmpty())
						return ctx.createFailureStatus(new Object[] { EMPTY_PROJECT_NAME });
					else if (!projectName.matches(reg_ex))
						return ctx.createFailureStatus(new Object[] { BAD_PROJECT_NAME });
				}
			} else if (ctx.getTarget() instanceof CardEntity) {
				String entityName = ((CardEntity) ctx.getTarget()).getName();
				if (entityName != null)
					if (entityName.isEmpty())
						return ctx.createFailureStatus(new Object[] { formatMessage(EMPTY_ENTITY_NAME,
								(CardEntity) ctx.getTarget()) });
					else if (!entityName.matches(card_entity_reg_ex))
						return ctx.createFailureStatus(new Object[] { formatMessage(BAD_ENTITY_NAME,
								(CardEntity) ctx.getTarget()) });
			} else if (ctx.getTarget() instanceof FieldGroup) {
				String groupName = ((FieldGroup) ctx.getTarget()).getName();
				if (groupName != null) {
					if (groupName.isEmpty()) {
						return ctx.createFailureStatus(new Object[] { formatMessage(EMPTY_GROUP_NAME,
								(FieldGroup) ctx.getTarget()) });
					} else if (!groupName.matches(card_entity_reg_ex)) {
						return ctx.createFailureStatus(new Object[] { formatMessage(BAD_GROUP_NAME,
								(FieldGroup) ctx.getTarget()) });
					}
				}
			} else if (ctx.getTarget() instanceof FieldEntity) {
				String fieldName = ((FieldEntity) ctx.getTarget()).getName();
				if (fieldName != null)
					if (fieldName.isEmpty())
						return ctx.createFailureStatus(new Object[] { formatMessage(EMPTY_FIELD_NAME,
								(CardEntity) ctx.getTarget()) });
					else if (!fieldName.matches(reg_ex))
						return ctx.createFailureStatus(new Object[] { formatMessage(BAD_FIELD_NAME,
								(FieldEntity) ctx.getTarget()) });
			} else if (ctx.getTarget() instanceof EnumValue) {
				EnumValue enumValue = (EnumValue) ctx.getTarget();
				if (enumValue.getName() != null)
					if (!enumValue.getName().matches(reg_ex))
						return ctx.createFailureStatus(new Object[] { getErrorMessageEnumValue(enumValue.getName()) });
			}
			return ctx.createSuccessStatus();
		} catch (Exception e) {
			e.printStackTrace();
			return ctx.createSuccessStatus();
		}
	}

	/** get error message for the EnumValue validation */
	private String getErrorMessageEnumValue(String enumName) {
		return new String("The name of the name enum value \"" + enumName + "\" should contains only " + reg_ex + ".");
	}

}

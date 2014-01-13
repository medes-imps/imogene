package org.imogene.model.validation.constraints;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.imogene.model.core.BooleanField;
import org.imogene.model.core.DateField;
import org.imogene.model.core.DateTimeField;
import org.imogene.model.core.EnumField;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FloatField;
import org.imogene.model.core.IntegerField;
import org.imogene.model.core.TimeField;

/**
 * Checks that the FieldEntity defaultvalue has the proper formats
 * 
 * @author MEDES-IMPS
 * 
 */
public class DefaultValueFormatConstraint extends AbstractMedanyModelConstraint {

	private static String DEFAULT_VALUE_INT = "The default value of the field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should be a valid Integer";
	private static String DEFAULT_VALUE_FLOAT = "The default value of the field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should be a valid Float";
	private static String DEFAULT_VALUE_DATE = "The default value of the field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should be a valid Date at the format dd/MM/yyyy";
	private static String DEFAULT_VALUE_DATETIME = "The default value of the field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should be a valid DateTime at the format dd/MM/yyyy HH:mm";
	private static String DEFAULT_VALUE_TIME = "The default value of the field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should be a valid Time at the format HH:mm";
	private static String DEFAULT_VALUE_BOOL = "The default value of the field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should be a valid boolean, it should be 'true' or 'false'";
	private static String DEFAULT_VALUE_ENUM = "The default value of the field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should be a valid value";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
	 */
	public IStatus validate(IValidationContext ctx) {

		if (ctx.getTarget() instanceof FieldEntity) {
			FieldEntity target = (FieldEntity) ctx.getTarget();

			String defaultValue = target.getDefaultValue();

			if (target instanceof IntegerField) {
				if (defaultValue != null && !defaultValue.equals("") && !Tools.isInteger(defaultValue))
					return ctx.createFailureStatus(new Object[] { this.formatMessage(DEFAULT_VALUE_INT,
							((FieldEntity) ctx.getTarget()).getParentCard(), (FieldEntity) ctx.getTarget()) });
			} else if (target instanceof FloatField) {
				if (defaultValue != null && !defaultValue.equals("") && !Tools.isFloat(defaultValue))
					return ctx.createFailureStatus(new Object[] { this.formatMessage(DEFAULT_VALUE_FLOAT,
							((FieldEntity) ctx.getTarget()).getParentCard(), (FieldEntity) ctx.getTarget()) });
			} else if (target instanceof DateField) {
				if (defaultValue != null && !defaultValue.equals("") && !defaultValue.toLowerCase().equals("now")
						&& !Tools.isDate(defaultValue))
					return ctx.createFailureStatus(new Object[] { this.formatMessage(DEFAULT_VALUE_DATE,
							((FieldEntity) ctx.getTarget()).getParentCard(), (FieldEntity) ctx.getTarget()) });
			} else if (target instanceof DateTimeField) {
				if (defaultValue != null && !defaultValue.equals("") && !defaultValue.toLowerCase().equals("now")
						&& !Tools.isDateTime(defaultValue))
					return ctx.createFailureStatus(new Object[] { this.formatMessage(DEFAULT_VALUE_DATETIME,
							((FieldEntity) ctx.getTarget()).getParentCard(), (FieldEntity) ctx.getTarget()) });
			} else if (target instanceof TimeField) {
				if (defaultValue != null && !defaultValue.equals("") && !defaultValue.toLowerCase().equals("now")
						&& !Tools.isTime(defaultValue))
					return ctx.createFailureStatus(new Object[] { this.formatMessage(DEFAULT_VALUE_TIME,
							((FieldEntity) ctx.getTarget()).getParentCard(), (FieldEntity) ctx.getTarget()) });
			} else if (target instanceof BooleanField) {
				if (defaultValue != null && !defaultValue.equals("") && !Tools.isBoolean(defaultValue))
					return ctx.createFailureStatus(new Object[] { this.formatMessage(DEFAULT_VALUE_BOOL,
							((FieldEntity) ctx.getTarget()).getParentCard(), (FieldEntity) ctx.getTarget()) });
			} else if (target instanceof EnumField) {
				if (defaultValue != null && !defaultValue.equals("") && !Tools.isInteger(defaultValue)) {
					return ctx.createFailureStatus(new Object[] { formatMessage(DEFAULT_VALUE_ENUM,
							((FieldEntity) ctx.getTarget()).getParentCard(), (FieldEntity) ctx.getTarget()) });
				}

			}
		}
		return ctx.createSuccessStatus();
	}

}

package org.imogene.model.validation.constraints;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.imogene.model.core.BooleanField;
import org.imogene.model.core.DateField;
import org.imogene.model.core.DateTimeField;
import org.imogene.model.core.EnumField;
import org.imogene.model.core.FieldDependentVisibility;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FloatField;
import org.imogene.model.core.IntegerField;
import org.imogene.model.core.TimeField;


/**
 * Checks that the FieldDependentVisibility dependencyFieldValue has the proper
 * formats
 * 
 * @author MEDES-IMPS
 * 
 */
public class DependencyFieldValueFormatConstraint extends
		AbstractMedanyModelConstraint {

	private static String FIELDS_IN_SAME_GROUP = "The Dependency Field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" must be in the same field group as its parent";
	private static String DEPENDTFIELD_EMPTY = "The Dependency Field Value of the field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should contain something";
	private static String DEPENDTFIELD_INT = "The Dependency Field Value of the integer field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should start with one of the following expression '<', '<=', '>', '>=', '==', '!=' followed by a valid integer value";
	private static String DEPENDTFIELD_FLOAT = "The Dependency Field Value of the float field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should start with one of the following expression '<', '<=', '>', '>=', '==', '!=' followed by a valid float value";
	private static String DEPENDTFIELD_DATE = "The Dependency Field Value of the date field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should be a valid Date at the format dd/MM/yyyy";
	private static String DEPENDTFIELD_DATETIME = "The Dependency Field Value of the date-time field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should be a valid DateTime at the format dd/MM/yyyy HH:mm";
	private static String DEPENDTFIELD_TIME = "The Dependency Field Value of the time field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should be a valid Time at the format HH:mm";
	private static String DEPENDTFIELD_BOOL = "The Dependency Field Value of the boolean field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should be a valid boolean, it should be 'true' or 'false'";
	private static String DEPENDTFIELD_ENUM = "The Dependency Field Value of the enum field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should be a valid regular expression";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse
	 * .emf.validation.IValidationContext)
	 */
	public IStatus validate(IValidationContext ctx) {

		if (ctx.getTarget() instanceof FieldDependentVisibility) {
			FieldDependentVisibility target = (FieldDependentVisibility) ctx.getTarget();

			FieldEntity dependencyField = target.getDependencyField();
			String dependencyFieldValue = target.getDependencyFieldValue();
			
			if (!dependencyField.getParentGroup().equals(target.getParentFieldEntity().getParentGroup()))
				return ctx.createFailureStatus(new Object[]{formatMessage(FIELDS_IN_SAME_GROUP, dependencyField.getParentCard(), dependencyField)});
			else if (dependencyFieldValue == null || dependencyFieldValue.isEmpty())
				return ctx.createFailureStatus(new Object[] {formatMessage(DEPENDTFIELD_EMPTY, target.getParentFieldEntity().getParentCard(), target.getParentFieldEntity())});
			if (dependencyField instanceof IntegerField && !Tools.isDepValueForInteger(dependencyFieldValue))
				return ctx.createFailureStatus(new Object[] {formatMessage(DEPENDTFIELD_INT, target.getParentFieldEntity().getParentCard(), target.getParentFieldEntity())});
			else if (dependencyField instanceof FloatField && !Tools.isDepValueForFloat(dependencyFieldValue))
				return ctx.createFailureStatus(new Object[] {formatMessage(DEPENDTFIELD_FLOAT, target.getParentFieldEntity().getParentCard(), target.getParentFieldEntity())});
			else if (dependencyField instanceof DateField && !Tools.isDepValueForDate(dependencyFieldValue))
				return ctx.createFailureStatus(new Object[] {formatMessage(DEPENDTFIELD_DATE, target.getParentFieldEntity().getParentCard(), target.getParentFieldEntity())});
			else if (dependencyField instanceof DateTimeField && !Tools.isDateTime(dependencyFieldValue))
				return ctx.createFailureStatus(new Object[] {formatMessage(DEPENDTFIELD_DATETIME, target.getParentFieldEntity().getParentCard(), target.getParentFieldEntity())});
			else if (dependencyField instanceof TimeField && !Tools.isTime(dependencyFieldValue))
				return ctx.createFailureStatus(new Object[] {formatMessage(DEPENDTFIELD_TIME, target.getParentFieldEntity().getParentCard(), target.getParentFieldEntity())});
			else if (dependencyField instanceof BooleanField && !Tools.isBoolean(dependencyFieldValue))
				return ctx.createFailureStatus(new Object[] { formatMessage(DEPENDTFIELD_BOOL, target.getParentFieldEntity().getParentCard(), target.getParentFieldEntity()) });
			else if (dependencyField instanceof EnumField && !Tools.isRegex(dependencyFieldValue))
				return ctx.createFailureStatus(new Object[] {formatMessage(DEPENDTFIELD_ENUM, target.getParentFieldEntity().getParentCard(), target.getParentFieldEntity()) });
		}

		return ctx.createSuccessStatus();
	}

}

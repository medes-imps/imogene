package org.imogene.model.validation.constraints;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.imogene.model.core.StringField;
import org.imogene.model.core.ValidationRule;


public class ValidationRuleConstraint extends AbstractMedanyModelConstraint {

	private static String INVALID_REGEX = "The regex validation rule is invalid for the string field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\"";
	
	@Override
	public IStatus validate(IValidationContext ctx) {
		if (ctx.getTarget() instanceof StringField) {
			StringField field = (StringField) ctx.getTarget();
			for (ValidationRule rule : field.getValidationRules()) {
				String regex = rule.getValidationRegularExpression();
				if (!Tools.isRegex(regex))
					return ctx.createFailureStatus(new Object[] {formatMessage(INVALID_REGEX, field.getParentCard(), field)});
			}
		}
		return ctx.createSuccessStatus();
	}
}

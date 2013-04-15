package org.imogene.model.validation.constraints;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.imogene.model.core.TextField;

/**
 * Check that a TextField is valid
 * 
 * @author MEDES-IMPS
 * 
 */
public class TextFieldValidation extends AbstractMedanyModelConstraint {
	
	private static final String NON_SENS = "The text field \"%FIELD_NAME%\" cannot be both calculated and translatable";
	/**
	 * Initializes me.
	 */
	public TextFieldValidation() {
		super();
	}
	
	@Override
	public IStatus validate(IValidationContext ctx) {
		if(ctx.getTarget() instanceof TextField){
			TextField textField = (TextField) ctx.getTarget();
			String function = textField.getCalculationFunctionName();
			if (function != null && !function.isEmpty() && textField.isTranslatable()) {
				return ctx.createFailureStatus(new Object[] {formatMessage(NON_SENS, textField.getParentCard(), textField)});
			}
		}
		return ctx.createSuccessStatus();
	}

}

package org.imogene.web.server.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DoubleMinValidator implements
		ConstraintValidator<DoubleMin, Double> {

	private double minValue;

	public void initialize(DoubleMin constraintAnnotation) {
		minValue = constraintAnnotation.value();
	}

	public boolean isValid(Double object, ConstraintValidatorContext constraintContext) {

		if (object == null)
			return true;

		return (object>=minValue);
	}

}
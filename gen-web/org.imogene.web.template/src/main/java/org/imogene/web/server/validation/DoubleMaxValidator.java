package org.imogene.web.server.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DoubleMaxValidator implements
		ConstraintValidator<DoubleMax, Double> {

	private double maxValue;

	public void initialize(DoubleMax constraintAnnotation) {
		maxValue = constraintAnnotation.value();
	}

	public boolean isValid(Double object, ConstraintValidatorContext constraintContext) {

		if (object == null)
			return true;

		return (object<=maxValue);
	}

}
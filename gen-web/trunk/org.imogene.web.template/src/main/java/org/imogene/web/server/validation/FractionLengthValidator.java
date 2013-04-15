package org.imogene.web.server.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FractionLengthValidator implements
		ConstraintValidator<FractionLength, Double> {

	private int length;

	public void initialize(FractionLength constraintAnnotation) {
		length = constraintAnnotation.value();
	}

	public boolean isValid(Double object, ConstraintValidatorContext constraintContext) {

		if (object == null)
			return true;

		BigDecimal value = BigDecimal.valueOf(object);
		int fractionPartLength = value.scale() < 0 ? 0 : value.scale();

		return (length >= fractionPartLength);
	}

}
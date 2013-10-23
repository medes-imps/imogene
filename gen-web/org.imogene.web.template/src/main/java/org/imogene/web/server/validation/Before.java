package org.imogene.web.server.validation;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = BeforeValidator.class)
public @interface Before {
	
	
	String message() default "{jorg.imogene.web.server.validation.Before.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default {};

	/**
	 * @return value the element must be before or equal to
	 */
	String value();
}

package org.imogene.android.xml.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to define a serialized representation of a class or a field.
 * 
 * @author Medes-IMPS
 * 
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
public @interface XmlAlias {

	/**
	 * The value of the class or field value.
	 * 
	 * @return The serialized representation.
	 */
	public String value();

}

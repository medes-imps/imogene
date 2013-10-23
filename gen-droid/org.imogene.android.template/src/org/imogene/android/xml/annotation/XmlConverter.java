package org.imogene.android.xml.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.imogene.android.xml.converters.Converter;

/**
 * Annotation to declare a converter.
 * 
 * @author Medes-IMPS
 * 
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface XmlConverter {

	/**
	 * The converter for the field value.
	 * 
	 * @return the converter.
	 */
	public Class<? extends Converter> value();

	/**
	 * Provide integer as parameter for the converter.
	 * 
	 * @return the integer.
	 * @see Converter#setInteger(integer)
	 */
	public int integer() default 0;

}

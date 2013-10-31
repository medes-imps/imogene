package org.imogene.android.util.annotation;

import java.lang.reflect.Field;

/**
 * Simple utility class for working with the reflection API.
 * 
 * @author Medes-IMPS
 * 
 */
public class ReflectionUtils {

	/**
	 * Callback interface invoked on each field in the hierarchy.
	 * 
	 * @author Medes-IMPS
	 * 
	 */
	public static interface FieldCallback {

		/**
		 * Perform an operation using the given field.
		 * 
		 * @param field The field to operate on.
		 * @throws IllegalAccessException
		 * @throws IllegalArgumentException
		 */
		public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException;

	}

	/**
	 * Invoke the given callback on all fields in the target class, going up the class hierarchy to get all declared
	 * fields.
	 * 
	 * @param clazz The target class to analyze.
	 * @param callback The callback to invoke for each field.
	 * 
	 * @throws IllegalArgumentException
	 */
	public static void doWithFields(Class<?> clazz, FieldCallback callback) throws IllegalArgumentException {
		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null) {
			doWithFields(superClass, callback);
		}
		Field[] fields = clazz.getDeclaredFields();
		if (fields.length > 0) {
			for (Field field : fields) {
				try {
					callback.doWith(field);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Returns a {@link Field} object for the field with the given name which is declared in the a {@link Class}.
	 * 
	 * @param clazz the class containing the field
	 * @param fieldName the name of the field
	 * @return the Field object for the specified field in the given class
	 * @throws NoSuchFieldException if the requested field can not be found.
	 */
	public static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
		try {
			return clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
		}
		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null) {
			return getField(superClass, fieldName);
		}
		throw new NoSuchFieldException();
	}
	
	public static Class<?> getClass(Class<?> clazz, String name) {
		Class<?>[] classes = clazz.getClasses();
		if (classes != null && classes.length > 0) {
			for (Class<?> c : classes) {
				if (c.getSimpleName().equals(name)) {
					return c;
				}
			}
		}
		return null;
	}

}

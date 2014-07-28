package org.imogene.android.widget.field;

/**
 * Interface to represent a field dependent visibility matcher.
 */
interface DependencyMatcher {

	/**
	 * Whether the field value matches the dependency rules value.
	 * 
	 * @param dependencyValue The dependency visibility matching expression.
	 * @return Whether the field value matches the expression or not.
	 */
	boolean matchesDependencyValue(String dependencyValue);

}

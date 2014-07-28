package org.imogene.android.widget.field;

/**
 * Interface to implement when a field visibility depends on other fields values.
 */
interface OnDependencyChangeListener {

	/**
	 * Method called when the value of other fields has changed an visibility must be updated.
	 */
	void onDependencyChanged();

	/**
	 * Register a field {@link DependencyMatcher} and the visibility dependency rule value.
	 * 
	 * @param matcher The {@link DependencyMatcher}.
	 * @param dependencyValue The visibility dependency rule.
	 */
	void registerDependsOn(DependencyMatcher matcher, String dependencyValue);

}

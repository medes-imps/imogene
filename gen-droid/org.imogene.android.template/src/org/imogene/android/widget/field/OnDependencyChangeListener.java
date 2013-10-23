package org.imogene.android.widget.field;

interface OnDependencyChangeListener {
	
	void onDependencyChanged();
	
	void registerDependsOn(DependencyMatcher matcher, String dependencyValue);
	
}

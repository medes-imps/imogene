package org.imogene.android.widget.field;

import fr.medes.android.database.sqlite.stmt.Where;

/**
 * Interface used to manage hierarchical fields. This will allow filtering a list given the previously set value.
 */
public interface ConstraintBuilder {

	/**
	 * Create a where clause to filter the second list by the given column name.
	 * 
	 * @param column The column to filter on.
	 * @return The where clause to use.
	 */
	public Where onCreateConstraint(String column);

	/**
	 * Register to the parent field that we will listen on changes. When parent's value changes we must reset the value
	 * of the dependent field.
	 * 
	 * @param dependent The parent field.
	 */
	public void registerConstraintDependent(BaseField<?> dependent);
}

package org.imogene.android.widget.field;

import org.imogene.android.database.sqlite.stmt.Where;


public interface ConstraintBuilder {
	
	public Where onCreateConstraint(String column);

	public void registerConstraintDependent(BaseField<?> dependent);
}

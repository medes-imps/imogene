/**
 * 
 */

package org.imogene.model.validation;

import org.eclipse.emf.ecore.EValidator;
import org.eclipse.ui.IStartup;
import org.imogene.model.core.ImogenePackage;




/**
 * Installs an EMF <code>EValidator</code> on the Library package when we
 * start up.  This validator adapts EMF's <code>EValidator</code> API to the
 * EMF Model Validation Service API.
 */
public class Startup
	implements IStartup {

	/**
	 * Initializes me.
	 */
	public Startup() {
		super();
	}

	/**
	 * Install the validator.
	 */
	public void earlyStartup() {
		EValidator.Registry.INSTANCE.put(
			ImogenePackage.eINSTANCE,
			new EValidatorAdapter());
	}
}
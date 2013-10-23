package org.imogene.lib.sync.server.binary;

import java.io.File;
import java.util.Properties;

/**
 * Describe an operation that could by apply to a 
 * binary just after the persistence process
 * @author Medes-IMPS
 *
 */
public interface BinaryOperation {
	
	public void operate(File incoming, Properties param);
	
}

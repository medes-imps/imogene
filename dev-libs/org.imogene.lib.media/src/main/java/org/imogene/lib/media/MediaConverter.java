package org.imogene.lib.media;

import java.io.File;

/**
 * Interface that describes a media converter
 * 
 * @author Medes-IMPS
 */
public interface MediaConverter {

	/**
	 * Copy the input file to the output file, changing its format
	 * 
	 * @param input input file
	 * @param output ouput file
	 * @param mimeType not use
	 * @return what you want
	 */
	public String convert(File input, File output, String mimeType);

}
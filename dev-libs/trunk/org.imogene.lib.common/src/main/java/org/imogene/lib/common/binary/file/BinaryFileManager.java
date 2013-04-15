package org.imogene.lib.common.binary.file;

import java.io.File;

/**
 * Singleton to manage the binary files, to get the binary file directory
 * @author MEDES-IMPS
 */
public class BinaryFileManager {

	private static BinaryFileManager instance = new BinaryFileManager();

	private String binaryPath;

	/**
	 * Get the BinaryFile manager
	 */
	public static BinaryFileManager getInstance() {
		return instance;
	}

	/**
	 * Setter for bean injection
	 * @param binaryPath
	 */
	public void setBinaryPath(String binaryPath) {
		this.binaryPath = binaryPath + (binaryPath.endsWith(File.separator) ? "" : File.separatorChar);
	}

	/**
	 * Get the directory where binary are stored
	 */
	public String getBinaryPath() {
		return binaryPath;
	}

	/**
	 * Build a binary file path
	 * @param entityId binary id
	 * @param fileName file name
	 * @return absolute file path
	 */
	public String buildFilePath(String entityId, String fileName) {
		return binaryPath + entityId + "-" + fileName;
	}

}

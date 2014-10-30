package org.imogene.lib.common.binary.file;

import java.io.File;

import org.imogene.lib.common.binary.Binary;

/**
 * Singleton to manage the binary files, to get the binary file directory
 * 
 * @author MEDES-IMPS
 */
public class BinaryFileManager {

	private static BinaryFileManager instance = new BinaryFileManager();

	private File binaryFile;

	/**
	 * Get the BinaryFile manager
	 */
	public static BinaryFileManager getInstance() {
		return instance;
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param binaryPath
	 */
	public void setBinaryPath(String path) {
		this.binaryFile = new File(path);
		if (!binaryFile.exists()) {
			binaryFile.mkdirs();
		}
	}

	/**
	 * Build a binary file path
	 * 
	 * @param entityId binary id
	 * @param fileName file name
	 * @return absolute file path
	 */
	public File buildFilePath(String entityId, String fileName) {
		return new File(binaryFile, entityId + "-" + fileName);
	}

	public File buildFilePath(Binary binary) {
		return buildFilePath(binary.getId(), binary.getFileName());
	}

	public File buildFlvFilePath(Binary binary) {
		return new File(binaryFile, "flv/" + binary.getId() + "-" + binary.getFileName() + ".flv");
	}

	public File buildThumbFilePath(Binary binary) {
		return new File(binaryFile, "thumb_" + binary.getId() + "-" + binary.getFileName());
	}

	public File buildMp3FilePath(Binary binary) {
		return new File(binaryFile, /* "mp3/" + */binary.getId() + "-" + binary.getFileName() /* + ".mp3" */);
	}
}

package org.imogene.sync.photo;

import java.io.File;
import java.util.Properties;

import org.imogene.lib.common.binary.file.BinaryFileManager;
import org.imogene.lib.sync.server.binary.BinaryOperation;

public class PhotoToThumbnailOperation implements BinaryOperation {

	public void operate(File incoming) {
		throw new RuntimeException("Not impplemented use operate(File, Properties).");
	}

	@Override
	public void operate(File incoming, Properties params) {
		String mimetype = params.getProperty("mimetype");
		String binaryPath = BinaryFileManager.getInstance().getBinaryPath();
		if (mimetype.contains("image")) {
			File output = new File(binaryPath + "/" + "thumb_" + incoming.getName());
			PhotoConverter.convert(incoming, output, mimetype);
		}
	}

}

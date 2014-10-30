package org.imogene.lib.media;

import java.io.File;

public class PhotoConverter implements MediaConverter {

	private final static int HORIZONTAL_SIZE = 65;

	@Override
	public String convert(File input, File output, String mimeType) {
		ThumbnailUtility tool = new ThumbnailUtility(input.getAbsolutePath());
		tool.getThumbnail(HORIZONTAL_SIZE, ThumbnailUtility.HORIZONTAL);
		String type = getImageType(mimeType);
		tool.saveThumbnail(output, type);
		return null;
	}

	private static String getImageType(String mimeType) {
		if (mimeType.toLowerCase().contains("png")) {
			return ThumbnailUtility.IMAGE_PNG;
		}
		return ThumbnailUtility.IMAGE_JPG;
	}
}
package org.imogene.web.server.servlet.util;

import java.io.File;

public class PhotoConverter {
	
	private final static int HORIZONTAL_SIZE=65;
		
	
	public static String convert(File input, File output, String mimeType){
		ThumbnailTools tool = new ThumbnailTools(input.getAbsolutePath());
		tool.getThumbnail(HORIZONTAL_SIZE, ThumbnailTools.HORIZONTAL);
		String type = getImageType(mimeType);
		tool.saveThumbnail(output, type);
		return null;
	}
		
	private static String getImageType(String mimeType){
		if(mimeType.toLowerCase().contains("png"))
			return ThumbnailTools.IMAGE_PNG;
		return ThumbnailTools.IMAGE_JPG;
	}
}

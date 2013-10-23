package org.imogene.android.util.file;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.imogene.android.template.R;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.webkit.MimeTypeMap;

public class MimeType {
	
	private static MimeType sMimeTypes = null;
	
	public static MimeType getInstance(Context context) {
		if (sMimeTypes != null) {
			return sMimeTypes;
		}
		try {
			sMimeTypes = fromResource(context, R.xml.ig_mimetypes);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sMimeTypes;
	}

	private final Map<String, String> mMimeTypes = new HashMap<String,String>();

	public void put(String type, String extension) {
		// Convert extensions to lower case letters for easier comparison
		extension = extension.toLowerCase();
		
		mMimeTypes.put(type, extension);
	}
	
	public String getExtension(String mimeType) {
		return mMimeTypes.get(mimeType);
	}
	
	public String getMimeType(String extension) {
		for (Entry<String, String> entry : mMimeTypes.entrySet()) {
			if (entry.getValue().equals(extension)) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	public String guessMimeType(String filename) {
		
		String extension = FileUtils.getExtension(filename);
		
		// Let's check the official map first. Webkit has a nice extension-to-MIME map.
		// Be sure to remove the first character from the extension, which is the "." character.
		if (extension.length() > 0) {
			String webkitMimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.substring(1));
		
			if (webkitMimeType != null) {
				// Found one. Let's take it!
				return webkitMimeType;
			}
		}
		
		// Convert extensions to lower case letters for easier comparison
		extension = extension.toLowerCase();
		
		String mimetype = mMimeTypes.get(extension);
		
		if (mimetype != null) {
			return mimetype;
		}
		
		return "*/*";
	}
	
	public static MimeType fromResource(Context context, int resId) throws XmlPullParserException, IOException {
		XmlResourceParser parser = context.getResources().getXml(resId);
		
		MimeType result = new MimeType();

		while (parser.next() != XmlPullParser.END_DOCUMENT) {
			if (parser.getEventType() == XmlPullParser.START_TAG && "type".equals(parser.getName())) {
				String extension = parser.getAttributeValue(null, "extension");
				String mimetype = parser.getAttributeValue(null, "mimetype");
				result.put(mimetype, extension);
			}
		}
		
		return result;
	}
	

}

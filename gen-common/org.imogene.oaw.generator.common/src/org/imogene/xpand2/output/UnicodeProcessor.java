package org.imogene.xpand2.output;

import org.eclipse.xpand2.output.FileHandle;
import org.eclipse.xpand2.output.PostProcessor;

public class UnicodeProcessor implements PostProcessor {

	@Override
	public void beforeWriteAndClose(FileHandle impl) {
		impl.setBuffer(new StringBuffer(convert(impl.getBuffer().toString())));
	}

	@Override
	public void afterClose(FileHandle impl) {
		// Do nothing here.
	}

	private static String convert(String s) {
		if (s == null)
			return null;
		String result = new String();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0x80 || c == 0x3c || c == 0x26)
				result += "\\u" + UnicodeFormatter.charToHex(c); //$NON-NLS-1$
			else
				result += c;
		}
		return result;
	}

	static class UnicodeFormatter {

		static String byteToHex(byte b) {
			return Integer.toHexString((b >> 4) & 0x0f) + Integer.toHexString(b & 0x0f);
		}

		static String charToHex(char c) {
			byte hi = (byte) (c >>> 8);
			byte lo = (byte) (c & 0xff);
			return byteToHex(hi) + byteToHex(lo);
		}

	}

}

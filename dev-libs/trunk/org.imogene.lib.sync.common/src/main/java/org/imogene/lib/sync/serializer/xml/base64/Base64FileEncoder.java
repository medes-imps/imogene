// Sample program to encode a binary file into a Base64 text file.
// Author: Christian d'Heureuse (www.source-code.biz)
package org.imogene.lib.sync.serializer.xml.base64;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;

public class Base64FileEncoder {

	/**
	 * Encode incoming stream in base64.
	 * 
	 * @param in input stream
	 * @param out output stream
	 * @throws IOException
	 */
	public static void encodeStream(InputStream in, BufferedWriter out) throws IOException {
		// int lineLength = 72;
		int lineLength = 4096;
		byte[] buf = new byte[lineLength / 4 * 3];
		while (true) {
			int len = in.read(buf);
			if (len <= 0)
				break;
			out.write(Base64Coder.encode(buf, len));
			out.newLine();
		}
	}

}

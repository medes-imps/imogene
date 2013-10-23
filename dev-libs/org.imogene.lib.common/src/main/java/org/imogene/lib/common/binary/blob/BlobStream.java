package org.imogene.lib.common.binary.blob;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class BlobStream {

	// Stream
	private InputStream stream;

	// Length of the data to be read
	private int length;

	public BlobStream() {
	}

	public BlobStream(InputStream is) {
		stream = is;
	}

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public InputStream createInputStream() {
		return new BufferedInputStream(stream);
	}

}

package org.imogene.lib.sync.client.http;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.methods.multipart.PartSource;

public class SyncPartSource implements PartSource {

	private InputStream in;

	private String fileName;

	private int length;

	public SyncPartSource(InputStream ins, String pFileName, int pLength) {
		in = ins;
		fileName = pFileName;
		length = pLength;
	}

	@Override
	public InputStream createInputStream() throws IOException {
		return in;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public long getLength() {
		return length;
	}

}

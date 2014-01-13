package org.imogene.web.server.util.impl;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.imogene.web.server.util.SystemUtil;

public class SystemUtilEmbeddedImpl implements SystemUtil {

	@Override
	public long getCurrentTimeMillis() {
		try {
			GetMethod get = new GetMethod("http://localhost:8888/context/offset");
			HttpClient client = new HttpClient();
			client.executeMethod(get);
			String offsetString = get.getResponseBodyAsString();
			get.releaseConnection();
			long offset = Long.valueOf(offsetString);
			return System.currentTimeMillis() + offset;
		} catch (Exception e) {
		}
		return System.currentTimeMillis();
	}

	@Override
	public String getTerminal() {
		try {
			GetMethod get = new GetMethod("http://localhost:8888/context/terminal");
			HttpClient client = new HttpClient();
			client.executeMethod(get);
			String terminal = get.getResponseBodyAsString();
			get.releaseConnection();
			return terminal;
		} catch (Exception e) {
		}
		return null;
	}

}

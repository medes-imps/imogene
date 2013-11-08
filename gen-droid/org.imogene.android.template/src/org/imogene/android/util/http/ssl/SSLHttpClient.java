package org.imogene.android.util.http.ssl;

import org.apache.http.conn.scheme.Scheme;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class SSLHttpClient extends DefaultHttpClient {

	private static final String TAG = SSLHttpClient.class.getName();

	public SSLHttpClient() {
		super();
		try {
			TrustAllSSLSocketFactory sf = new TrustAllSSLSocketFactory();
			Scheme sch = new Scheme("https", sf, 443);
			getConnectionManager().getSchemeRegistry().register(sch);
		} catch (Exception e) {
			Log.e(TAG, "error while construction SSLHttpClient", e);
		}
	}

}

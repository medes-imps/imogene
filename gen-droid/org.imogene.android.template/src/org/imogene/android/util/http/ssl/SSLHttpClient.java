package org.imogene.android.util.http.ssl;

import org.apache.http.conn.scheme.Scheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;



public class SSLHttpClient extends DefaultHttpClient {
	
	private static final String TAG = SSLHttpClient.class.getName();
	
	private static final int TIMEOUT = 60000;
	
	private static final HttpParams params = new BasicHttpParams();
	
	static {
		HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, TIMEOUT);
	}
	
	public SSLHttpClient() {
		super(params);
		try {
			TrustAllSSLSocketFactory sf = new TrustAllSSLSocketFactory();
			Scheme sch = new Scheme("https", sf, 443);
			getConnectionManager().getSchemeRegistry().register(sch);
		} catch (Exception e) {
			Log.e(TAG, "error while construction SSLHttpClient", e);
		}
	}

}

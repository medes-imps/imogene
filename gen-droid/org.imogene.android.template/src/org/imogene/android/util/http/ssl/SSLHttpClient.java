package org.imogene.android.util.http.ssl;

import org.apache.http.conn.scheme.Scheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

public class SSLHttpClient extends DefaultHttpClient {

	private static final String TAG = SSLHttpClient.class.getName();

	private static final int CONN_TIMEOUT = 10 * 1000;
	private static final int SO_TIMEOUT = 60 * 1000;

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

	@Override
	protected HttpParams createHttpParams() {
		HttpParams params = super.createHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, CONN_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
		return params;
	}

}

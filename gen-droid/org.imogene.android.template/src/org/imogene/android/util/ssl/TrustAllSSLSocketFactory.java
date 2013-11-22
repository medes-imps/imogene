package org.imogene.android.util.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;

public class TrustAllSSLSocketFactory extends SSLSocketFactory {

	private static final TrustManager[] INSECURE_TRUST_MANAGER = new TrustManager[] { new X509TrustManager() {
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkClientTrusted(X509Certificate[] certs, String authType) {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] certs, String authType) {
		}
	} };

	private javax.net.ssl.SSLSocketFactory factory;

	public TrustAllSSLSocketFactory() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException,
			UnrecoverableKeyException {
		super((KeyStore) null);
		SSLContext sslcontext = SSLContext.getInstance("TLS");
		sslcontext.init(null, INSECURE_TRUST_MANAGER, null);
		factory = sslcontext.getSocketFactory();
		setHostnameVerifier(new AllowAllHostnameVerifier());
	}

	public SocketFactory getDefault() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException,
			UnrecoverableKeyException {
		return new TrustAllSSLSocketFactory();
	}

	@Override
	public Socket createSocket() throws IOException {
		return factory.createSocket();
	}

	@Override
	public Socket createSocket(Socket socket, String s, int i, boolean flag) throws IOException {
		return factory.createSocket(socket, s, i, flag);
	}

	public Socket createSocket(InetAddress inaddr, int i, InetAddress inaddr1, int j) throws IOException {
		return factory.createSocket(inaddr, i, inaddr1, j);
	}

	public Socket createSocket(InetAddress inaddr, int i) throws IOException {
		return factory.createSocket(inaddr, i);
	}

	public Socket createSocket(String s, int i, InetAddress inaddr, int j) throws IOException {
		return factory.createSocket(s, i, inaddr, j);
	}

	public Socket createSocket(String s, int i) throws IOException {
		return factory.createSocket(s, i);
	}

	public String[] getDefaultCipherSuites() {
		return factory.getDefaultCipherSuites();
	}

	public String[] getSupportedCipherSuites() {
		return factory.getSupportedCipherSuites();
	}
}
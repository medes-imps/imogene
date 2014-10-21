package org.imogene.android.sync.http;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.imogene.android.sync.OptimizedSyncClient;
import org.imogene.android.sync.SynchronizationException;

import fr.medes.android.util.file.FileUtils;
import fr.medes.android.util.ssl.SSLHttpClient;

public class OptimizedSyncClientHttp implements OptimizedSyncClient {

	private static final int CONN_TIMEOUT = 10 * 1000;
	private static final int SO_TIMEOUT = 60 * 1000;

	private String mUrl = null;
	private String mHttpLogin;
	private String mHttpPassword;

	/**
	 * By using this constructor, you specify that you want to use the HTTP authentication based on the specified login
	 * and password. This authentication will be set in all requests.
	 * 
	 * @param url the synchronization server
	 * @param httpLogin the user login
	 * @param httpPassword the user password
	 */
	public OptimizedSyncClientHttp(String url, String httpLogin, String httpPassword) {
		this.mUrl = url + (url.endsWith("/") ? "" : "/") + "sync.html";
		this.mHttpLogin = httpLogin;
		this.mHttpPassword = httpPassword;
	}

	@Override
	public boolean authentication() throws SynchronizationException, AuthenticationException {
		try {
			HttpClient client = new SSLHttpClient();

			if (mUrl == null) {
				return false;
			}

			// request construction
			StringBuilder builder = new StringBuilder(mUrl) //
					.append("?" + PARAM_CMD + "=" + CMD_AUTH);

			HttpGet get = createHttpGetMethod(builder.toString());

			// request execution
			HttpResponse response = client.execute(get);

			// Validate response
			validateResponse(response);

			// Read authentication response
			String result = readString(response.getEntity().getContent());
			return RESPONSE_OK.equals(result);
		} catch (AuthenticationException e) {
			return false;
		} catch (SynchronizationException e) {
			throw e;
		} catch (Exception e) {
			throw new SynchronizationException("Session auth -> ", e, SynchronizationException.ERROR_AUTH);
		}
	}

	@Override
	public void closeSession(UUID sessionId, boolean debug) throws SynchronizationException, AuthenticationException {
		try {
			HttpClient client = new SSLHttpClient();

			// request construction
			StringBuilder builder = new StringBuilder(mUrl) //
					.append("?" + PARAM_CMD + "=" + CMD_CLOSE) //
					.append("&" + PARAM_SESSION + "=" + sessionId) //
					.append("&" + PARAM_DEBUG + "=" + Boolean.toString(debug));

			HttpGet method = createHttpGetMethod(builder.toString());

			// request execution
			HttpResponse response = client.execute(method);

			// Validate response
			validateResponse(response);

			// TODO We should probably check the result, on the other hand it is not so important
			// Read acknowledgment
			// String result = readString(response.getEntity().getContent());
			// if (!RESPONSE_OK.equals(result)) {
			//
			// }
		} catch (AuthenticationException e) {
			throw e;
		} catch (SynchronizationException e) {
			throw e;
		} catch (Exception e) {
			throw new SynchronizationException("Closing session -> ", e, SynchronizationException.ERROR_CLOSING);
		}
	}

	@Override
	public String initSession(String terminalId) throws AuthenticationException, SynchronizationException {
		try {
			HttpClient client = new SSLHttpClient();

			if (mUrl == null) {
				return null;
			}

			// request construction
			StringBuilder builder = new StringBuilder(mUrl) //
					.append("?" + PARAM_CMD + "=" + CMD_INIT) //
					.append("&" + PARAM_TERMINAL + "=" + terminalId);

			HttpGet get = createHttpGetMethod(builder.toString());

			// request execution
			HttpResponse response = client.execute(get);

			// Validate response
			validateResponse(response);

			// Read session id
			return readString(response.getEntity().getContent());

		} catch (AuthenticationException e) {
			throw e;
		} catch (SynchronizationException e) {
			throw e;
		} catch (Exception e) {
			throw new SynchronizationException("Session init -> ", e, SynchronizationException.ERROR_INIT);
		}
	}

	/**
	 * Send data to the server
	 * 
	 * @param sessionId the synchronization session id
	 * @param cmd the command
	 * @param fis the data to send
	 * @return 0 if done with success, -1 otherwise
	 * @throws SynchronizationException
	 * @throws AuthenticationException
	 */
	private int sendData(UUID sessionId, String cmd, FileInputStream fis) throws SynchronizationException,
			AuthenticationException {
		try {
			HttpClient client = new SSLHttpClient();

			// request construction
			StringBuilder builder = new StringBuilder(mUrl) //
					.append("?" + PARAM_SESSION + "=" + sessionId) //
					.append("&" + PARAM_CMD + "=" + cmd);

			HttpPost method = createHttpPostMethod(builder.toString());

			String filename = sessionId + ".cmodif";
			MultipartEntity entity = new MultipartEntity();
			InputStreamBody part = new InputStreamBody(fis, filename);
			entity.addPart("data", part);
			method.setEntity(entity);

			// request execution
			HttpResponse response = client.execute(method);

			// Validate response
			validateResponse(response);

			// Read received entities acknowledgment (ACK_nbEntities)
			String res = readString(response.getEntity().getContent());
			if (!res.startsWith("ACK")) {
				throw new SynchronizationException("Command 'send' : Server error code returned.");
			}

			// Extract entities received number
			int result = Integer.parseInt(res.split("_")[1]);
			return result;

		} catch (AuthenticationException e) {
			throw e;
		} catch (SynchronizationException e) {
			throw e;
		} catch (Exception e) {
			throw new SynchronizationException("Command 'send' -> ", e, SynchronizationException.ERROR_SEND);
		}
	}

	@Override
	public void resumeReceive(String terminalId, UUID sessionId, long bytesReceived) throws SynchronizationException,
			AuthenticationException {
		try {
			HttpClient client = new SSLHttpClient();

			// request construction
			StringBuilder builder = new StringBuilder(mUrl) //
					.append("?" + PARAM_CMD + "=" + CMD_RESUME_RECEIVE_INIT) //
					.append("&" + PARAM_TERMINAL + "=" + terminalId) //
					.append("&" + PARAM_SESSION + "=" + sessionId) //
					.append("&" + PARAM_LENGTH + "=" + String.valueOf(bytesReceived));

			HttpGet get = createHttpGetMethod(builder.toString());

			// request execution
			HttpResponse response = client.execute(get);

			// Validate response
			validateResponse(response);

			// Read session id
			String result = readString(response.getEntity().getContent());
			if (!RESPONSE_OK.equals(result)) {
				throw new SynchronizationException("The server return an error code",
						SynchronizationException.ERROR_RECEIVE);
			}
		} catch (AuthenticationException e) {
			throw e;
		} catch (SynchronizationException e) {
			throw e;
		} catch (Exception e) {
			throw new SynchronizationException("Resume 'receive' session init -> ", e,
					SynchronizationException.ERROR_RECEIVE);
		}
	}

	@Override
	public void resumeRequestModification(UUID sessionId, OutputStream out, long bytesReceived)
			throws SynchronizationException, AuthenticationException {
		try {
			HttpClient client = new SSLHttpClient();

			// request construction
			StringBuilder builder = new StringBuilder(mUrl) //
					.append("?" + PARAM_SESSION + "=" + sessionId) //
					.append("&" + PARAM_CMD + "=" + CMD_RESUME_RECEIVE) //
					.append("&" + PARAM_LENGTH + "=" + String.valueOf(bytesReceived));

			HttpGet method = createHttpGetMethod(builder.toString());

			// request execution
			HttpResponse response = client.execute(method);

			// Validate response
			validateResponse(response);

			// Receive server modification
			long expectedLength = response.getEntity().getContentLength();
			InputStream is = response.getEntity().getContent();
			FileUtils.writeInFile(is, out, expectedLength);
		} catch (AuthenticationException e) {
			throw e;
		} catch (SynchronizationException e) {
			throw e;
		} catch (Exception e) {
			throw new SynchronizationException("Resume 'request' command ->HTTP code returned.", e,
					SynchronizationException.ERROR_RECEIVE);
		}
	}

	@Override
	public long resumeSend(String terminalId, UUID sessionId) throws SynchronizationException, AuthenticationException {
		try {
			HttpClient client = new SSLHttpClient();

			// request construction
			StringBuilder builder = new StringBuilder(mUrl) //
					.append("?" + PARAM_CMD + "=" + CMD_RESUME_SEND_INIT) //
					.append("&" + PARAM_TERMINAL + "=" + terminalId) //
					.append("&" + PARAM_SESSION + "=" + sessionId);

			HttpGet method = createHttpGetMethod(builder.toString());

			// request execution
			HttpResponse response = client.execute(method);

			// Validate response
			validateResponse(response);

			// read result
			return Long.parseLong(readString(response.getEntity().getContent()));
		} catch (AuthenticationException e) {
			throw e;
		} catch (SynchronizationException e) {
			throw e;
		} catch (Exception e) {
			throw new SynchronizationException("Resume 'send' session init -> ", e, SynchronizationException.ERROR_SEND);
		}
	}

	@Override
	public int resumeSendModification(UUID sessionId, FileInputStream fis) throws SynchronizationException,
			AuthenticationException {
		return sendData(sessionId, CMD_RESUME_SEND, fis);
	}

	@Override
	public int sendClientModification(UUID sessionId, FileInputStream fis) throws SynchronizationException,
			AuthenticationException {
		return sendData(sessionId, CMD_SENDMODIF, fis);
	}

	@Override
	public boolean requestServerModifications(UUID sessionId, OutputStream out) throws SynchronizationException,
			AuthenticationException {
		try {
			HttpClient client = new SSLHttpClient();

			// request construction
			StringBuilder builder = new StringBuilder(mUrl) //
					.append("?" + PARAM_SESSION + "=" + sessionId) //
					.append("&" + PARAM_CMD + "=" + CMD_SERVERMODIF);

			HttpGet method = createHttpGetMethod(builder.toString());

			// request execution
			HttpResponse response = client.execute(method);

			// Validate response
			validateResponse(response);

			// Receive server modifications
			long expectedLength = response.getEntity().getContentLength();
			InputStream is = response.getEntity().getContent();
			FileUtils.writeInFile(is, out, expectedLength);
			return true;

		} catch (AuthenticationException e) {
			throw e;
		} catch (SynchronizationException e) {
			throw e;
		} catch (Exception e) {
			throw new SynchronizationException("Command 'receive' -> ", e, SynchronizationException.ERROR_RECEIVE);
		}
	}

	@Override
	public int directSend(UUID sessionId, FileInputStream fis) throws SynchronizationException, AuthenticationException {
		return sendData(sessionId, CMD_DIRECTSEND, fis);
	}

	@Override
	public void searchEntity(String searcheId, OutputStream os) throws SynchronizationException,
			AuthenticationException {
		try {
			HttpClient client = new SSLHttpClient();

			// request construction
			StringBuilder builder = new StringBuilder(mUrl) //
					.append("?" + PARAM_CMD + "=" + CMD_SEARCH) //
					.append("&" + PARAM_SEARCH + "=" + searcheId);

			HttpGet method = createHttpGetMethod(builder.toString());

			// request execution
			HttpResponse response = client.execute(method);

			// Validate response
			validateResponse(response);

			// Read server response
			long expectedLength = response.getEntity().getContentLength();
			InputStream is = response.getEntity().getContent();
			FileUtils.writeInFile(is, os, expectedLength);
		} catch (AuthenticationException e) {
			throw e;
		} catch (SynchronizationException e) {
			throw e;
		} catch (Exception e) {
			throw new SynchronizationException("Command 'search' -> ", e, SynchronizationException.ERROR_SEARCH);
		}
	}

	private HttpPost createHttpPostMethod(String url) {
		HttpPost post = new HttpPost(url);
		prepareRequest(post);
		setBasicAuthentication(post);
		return post;
	}

	private HttpGet createHttpGetMethod(String url) {
		HttpGet get = new HttpGet(url);
		prepareRequest(get);
		setBasicAuthentication(get);
		return get;
	}

	private void setBasicAuthentication(HttpRequestBase method) {
		String strAuth = mHttpLogin + ":" + mHttpPassword;
		String encoding = new String(Base64.encodeBase64(strAuth.getBytes()));
		method.setHeader("Authorization", "Basic " + encoding);
	}

	// Common static methods

	private static void prepareRequest(HttpUriRequest request) {
		HttpParams params = request.getParams();
		HttpConnectionParams.setConnectionTimeout(params, CONN_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
		request.setHeader("Connection", "close");
	}

	private static String readString(InputStream is) throws IOException {
		StringBuilder sb = new StringBuilder();
		int i;
		while ((i = is.read()) != -1) {
			sb.append((char) i);
		}
		return sb.toString();
	}

	private static boolean validateResponse(HttpResponse response) throws SynchronizationException, HttpException {
		// Read http code response
		int code = response.getStatusLine().getStatusCode();
		if (code != HttpStatus.SC_OK) {
			if (code == HttpStatus.SC_UNAUTHORIZED) {
				throw new AuthenticationException("401 Unauthorized");
			}
			throw new HttpException("HTTP error code return " + code);
		}

		// Check header
		Header header = response.getFirstHeader(HEADER_NAME);
		if (header == null || !HEADER_VALUE.equals(header.getValue())) {
			throw new SynchronizationException("HTTP header is invalid", SynchronizationException.ERROR_AUTH);
		}

		return true;
	}
}

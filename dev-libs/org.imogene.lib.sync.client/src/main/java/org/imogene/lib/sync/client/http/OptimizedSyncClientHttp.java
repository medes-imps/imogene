package org.imogene.lib.sync.client.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.auth.AuthenticationException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.log4j.Logger;
import org.imogene.lib.sync.client.OptimizedSyncClient;
import org.imogene.lib.sync.client.SynchronizationException;

public class OptimizedSyncClientHttp implements OptimizedSyncClient {

	private static final Logger logger = Logger.getLogger("org.imogene.sync.client.http.SyncClientHttp");

	private static final int maxAttempts = 100;

	private String url = "http://localhost:8080/ImogSynchro/";
	private String login;
	private String password;
	private String terminalId;

	/**
	 * By using this constructor, you specify that you want to use the HTTP authentication based on the specified login
	 * and password. This authentication will be set in all requests.
	 * 
	 * @param url the synchronization server
	 * @param login the user login
	 * @param password the user password
	 * @param terminalId the terminal identifier
	 */
	public OptimizedSyncClientHttp(String url, String login, String password, String terminalId) {
		setUrl(url);
		this.login = login;
		this.password = password;
		this.terminalId = terminalId;
	}

	@Override
	public void setUrl(String url) {
		this.url = url.endsWith("html") ? url : (url.endsWith("/") ? url + "sync.html" : url + "/sync.html");
	}

	@Override
	public boolean authenticate() throws SynchronizationException {
		try {
			// request construction
			NameValuePair cmdParam = new NameValuePair(PARAM_CMD, CMD_AUTH);
			NameValuePair[] params = new NameValuePair[] { cmdParam };
			GetMethod method = httpGetMethod(url);
			method.setQueryString(params);

			HttpClient client = new HttpClient();
			int code = client.executeMethod(method);

			checkResponseCode(code);
			checkHeader(method);

			return true;
		} catch (AuthenticationException e) {
			return false;
		} catch (SynchronizationException e) {
			throw e;
		} catch (Exception e) {
			throw new SynchronizationException("Session init : " + e.getLocalizedMessage(), e,
					SynchronizationException.ERROR_INIT);
		}
	}

	@Override
	public void closeSession(String sessionId) throws SynchronizationException, AuthenticationException {
		try {
			// request construction
			NameValuePair cmdParam = new NameValuePair(PARAM_CMD, CMD_CLOSE);
			NameValuePair sessionParam = new NameValuePair(PARAM_SESSION, sessionId);
			NameValuePair debugParam = new NameValuePair("debug", "true");
			NameValuePair[] params = new NameValuePair[] { cmdParam, sessionParam, debugParam };
			GetMethod method = httpGetMethod(url);
			method.setQueryString(params);

			// request execution
			HttpClient client = new HttpClient();
			int code = client.executeMethod(method);

			// Check code and header
			checkResponseCode(code);
			checkHeader(method);

			// TODO We should probably check the result, on the other hand it is not so important
			// Read acknowledgment
			// if (method.getResponseBodyAsString().startsWith("ACK"))
		} catch (AuthenticationException e) {
			throw e;
		} catch (SynchronizationException e) {
			throw e;
		} catch (Exception e) {
			throw new SynchronizationException("Closing session -> ", e, SynchronizationException.ERROR_CLOSING);
		}
	}

	@Override
	public String initSession() throws SynchronizationException, AuthenticationException {
		try {
			/* request construction */
			NameValuePair cmdParam = new NameValuePair(PARAM_CMD, CMD_INIT);
			NameValuePair terminalParam = new NameValuePair(PARAM_TERMINALID, terminalId);
			NameValuePair[] params = new NameValuePair[] { cmdParam, terminalParam };
			GetMethod method = httpGetMethod(url);
			method.setQueryString(params);

			HttpClient client = new HttpClient();
			int code = client.executeMethod(method);

			// Check code and header
			checkResponseCode(code);
			checkHeader(method);

			String sessionId = method.getResponseBodyAsString();
			return sessionId;

		} catch (AuthenticationException e) {
			throw e;
		} catch (SynchronizationException e) {
			throw e;
		} catch (Exception e) {
			throw new SynchronizationException("Closing session -> ", e, SynchronizationException.ERROR_CLOSING);
		}
	}

	@Override
	public void resumeReceive(String sessionId, long bytesReceived) throws SynchronizationException,
			AuthenticationException {
		try {
			// request construction
			NameValuePair cmdParam = new NameValuePair(PARAM_CMD, CMD_RESUME_RECEIVE_INIT);
			NameValuePair terminalParam = new NameValuePair(PARAM_TERMINALID, terminalId);
			NameValuePair sessionParam = new NameValuePair(PARAM_SESSION, sessionId);
			NameValuePair lengthParam = new NameValuePair(PARAM_LENGTH, String.valueOf(bytesReceived));
			NameValuePair[] params = new NameValuePair[] { cmdParam, terminalParam, sessionParam, lengthParam };
			GetMethod method = httpGetMethod(url);
			method.setQueryString(params);

			HttpClient client = new HttpClient();
			int code = client.executeMethod(method);

			// Check code and header
			checkResponseCode(code);
			checkHeader(method);

			if (!RESPONSE_OK.equals(method.getResponseBodyAsString())) {
				throw new SynchronizationException("The server return an error code",
						SynchronizationException.ERROR_RECEIVE);
			}
		} catch (AuthenticationException e) {
			throw e;
		} catch (SynchronizationException e) {
			throw e;
		} catch (Exception e) {
			throw new SynchronizationException("Closing session -> ", e, SynchronizationException.ERROR_CLOSING);
		}
	}

	@Override
	public int resumeRequestModification(String sessionId, OutputStream out, long bytesReceived)
			throws SynchronizationException, AuthenticationException {
		try {
			// request construction
			NameValuePair sessionParam = new NameValuePair(PARAM_SESSION, sessionId);
			NameValuePair cmdParam = new NameValuePair(PARAM_CMD, CMD_RESUME_RECEIVE);
			NameValuePair lengthParam = new NameValuePair(PARAM_LENGTH, String.valueOf(bytesReceived));
			NameValuePair[] params = new NameValuePair[] { sessionParam, cmdParam, lengthParam };
			GetMethod method = httpGetMethod(url);
			method.setQueryString(params);
			HttpClient client = new HttpClient();

			// request execution
			int code = client.executeMethod(method);

			// Check code and header
			checkResponseCode(code);
			checkHeader(method);

			// read data sent by the server
			long expectedLength = method.getResponseContentLength();
			InputStream is = method.getResponseBodyAsStream();
			writeInFile(is, out, expectedLength);
			return 0;
		} catch (AuthenticationException e) {
			throw e;
		} catch (SynchronizationException e) {
			throw e;
		} catch (Exception e) {
			throw new SynchronizationException("Resume 'request' command : " + e.getLocalizedMessage(), e,
					SynchronizationException.ERROR_RECEIVE);
		}
	}

	@Override
	public long resumeSend(String sessionId) throws SynchronizationException, AuthenticationException {
		try {
			/* request construction */
			NameValuePair cmdParam = new NameValuePair(PARAM_CMD, CMD_RESUME_SEND_INIT);
			NameValuePair terminalParam = new NameValuePair(PARAM_TERMINALID, terminalId);
			NameValuePair sessionParam = new NameValuePair(PARAM_SESSION, sessionId);
			NameValuePair[] params = new NameValuePair[] { cmdParam, terminalParam, sessionParam };
			GetMethod method = httpGetMethod(url);
			method.setQueryString(params);

			HttpClient client = new HttpClient();
			int code = client.executeMethod(method);

			// Check code and header
			checkResponseCode(code);
			checkHeader(method);

			return Long.parseLong(method.getResponseBodyAsString());
		} catch (AuthenticationException e) {
			throw e;
		} catch (SynchronizationException e) {
			throw e;
		} catch (Exception e) {
			throw new SynchronizationException("Resume 'send' session init: " + e.getLocalizedMessage(), e,
					SynchronizationException.ERROR_SEND);
		}
	}

	@Override
	public int resumeSendModification(String sessionId, InputStream in) throws SynchronizationException,
			AuthenticationException {
		return sendData(sessionId, CMD_RESUME_SEND, in);
	}

	@Override
	public void requestServerModifications(String sessionId, OutputStream out) throws SynchronizationException,
			AuthenticationException {
		try {
			// request construction
			NameValuePair sessionParam = new NameValuePair(PARAM_SESSION, sessionId);
			NameValuePair cmdParam = new NameValuePair(PARAM_CMD, CMD_SERVERMODIF);
			NameValuePair[] params = new NameValuePair[] { sessionParam, cmdParam };
			GetMethod method = httpGetMethod(url);
			method.setQueryString(params);
			HttpClient client = new HttpClient();

			// request execution
			int code = client.executeMethod(method);

			// Check code and header
			checkResponseCode(code);
			checkHeader(method);

			// read data sent by the server
			long expectedLength = method.getResponseContentLength();
			// System.out.println("Lenght of the result :"+expectedLength);
			InputStream is = method.getResponseBodyAsStream();
			writeInFile(is, out, expectedLength);
		} catch (AuthenticationException e) {
			throw e;
		} catch (SynchronizationException e) {
			throw e;
		} catch (Exception e) {
			throw new SynchronizationException("Command 'receive': " + e.getLocalizedMessage(), e,
					SynchronizationException.ERROR_RECEIVE);
		}
	}

	@Override
	public int sendClientModification(String sessionId, InputStream data) throws SynchronizationException,
			AuthenticationException {
		return sendData(sessionId, CMD_SENDMODIF, data);
	}

	@Override
	public int directSend(String sessionId, InputStream data) throws AuthenticationException, SynchronizationException {
		return sendData(sessionId, CDM_DIRECTSEND, data);
	}

	/**
	 * Send data to the server
	 * 
	 * @param sessionId the synchronization session id
	 * @param cmd the command
	 * @param data the data to send
	 * @return 0 if done with success, -1 otherwise
	 * @throws AuthenticationException
	 */
	private int sendData(String sessionId, String cmd, InputStream data) throws SynchronizationException,
			AuthenticationException {
		try {
			/* request construction */
			PostMethod method = httpPostMethod(url);
			StringPart sessionParam = new StringPart(PARAM_SESSION, sessionId);
			StringPart cmdParam = new StringPart(PARAM_CMD, cmd);
			SyncPartSource dataSource = new SyncPartSource(data, sessionId + ".cmodif", data.available());
			FilePart dataFile = new FilePart("data", dataSource);
			Part[] parts = { sessionParam, cmdParam, dataFile };
			logger.debug("send data to url : " + url + " cmd : " + cmd);
			/* execute the request */
			MultipartRequestEntity mEntity = new MultipartRequestEntity(parts, method.getParams());
			method.setRequestEntity(mEntity);
			HttpClient client = new HttpClient();
			int code = client.executeMethod(method);

			// Check code and header
			checkResponseCode(code);
			checkHeader(method);

			String body = new String(method.getResponseBody());
			if (body.startsWith("ACK")) {
				return 0;
			} else {
				throw new SynchronizationException("Command 'send' : Server error code returned.");
			}
		} catch (AuthenticationException e) {
			throw e;
		} catch (SynchronizationException e) {
			throw e;
		} catch (Exception e) {
			throw new SynchronizationException("Command 'send': " + e.getLocalizedMessage(), e,
					SynchronizationException.ERROR_SEND);
		}
	}

	private PostMethod httpPostMethod(String url) {
		PostMethod post = new PostMethod(url);
		setBasicAuthentication(post);
		return post;
	}

	private GetMethod httpGetMethod(String url) {
		GetMethod get = new GetMethod(url);
		setBasicAuthentication(get);
		return get;
	}

	private void setBasicAuthentication(HttpMethodBase method) {
		String strAuth = login + ":" + password;
		String encoding = new String(Base64.encodeBase64(strAuth.getBytes()));
		method.setRequestHeader("Authorization", "Basic " + encoding);
	}

	private static void checkResponseCode(int code) throws HttpException {
		if (code != HttpStatus.SC_OK) {
			if (code == HttpStatus.SC_UNAUTHORIZED) {
				throw new AuthenticationException("401 Unauthorized");
			}
			throw new HttpException("HTTP error code returned " + code);
		}
	}

	private static void checkHeader(HttpMethod method) throws SynchronizationException {
		Header header = method.getResponseHeader(HEADER_NAME);
		if (header == null || !HEADER_VALUE.equals(header.getValue())) {
			throw new SynchronizationException("HTTP header is invalid", SynchronizationException.ERROR_AUTH);
		}
	}

	/**
	 * Write the request result data incoming in the input stream into the output stream
	 * 
	 * @param is the request result input stream
	 * @param out the outputStream
	 * @param expectedLength the data length expected
	 * @return 0 if done with success, -1 otherwise
	 */
	private static int writeInFile(InputStream is, OutputStream out, long expectedLength) throws IOException {
		boolean finished = false;
		long bytesRead = 0;
		int nbAttemps = 0;

		while (!finished) {
			/* while there is data to read, we read it */
			byte[] buffer;
			/*
			 * while (is.available() > 0) { if (is.available() < 1024) buffer = new byte[is.available()]; else buffer =
			 * new byte[1024];
			 * 
			 * bytesRead = bytesRead + is.read(buffer); out.write(buffer); nbAttemps=0; }
			 */
			buffer = new byte[1024];
			int i = 0;
			while ((i = is.read(buffer)) > 0) {
				bytesRead = bytesRead + i;
				out.write(buffer, 0, i);
				nbAttemps = 0;
			}
			/* is the reading process finished ? */
			finished = ((bytesRead >= expectedLength && expectedLength != -1) || ++nbAttemps > maxAttempts);

			/* if not finish we wait and we retry */
			if (!finished) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException ex) {
					logger.error(ex.getMessage());
				}
			} else {
				out.flush();
				out.close();
				is.close();
				if (bytesRead >= expectedLength)
					return 0;
				else
					return -1;
			}
		}
		/* return 0, if read all data with success, -1 otherwise */
		if (bytesRead >= expectedLength)
			return 0;
		else
			return -1;
	}

}

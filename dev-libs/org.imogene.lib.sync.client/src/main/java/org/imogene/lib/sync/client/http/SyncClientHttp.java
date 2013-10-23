package org.imogene.lib.sync.client.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.log4j.Logger;
import org.imogene.lib.sync.client.SyncClient;

@Deprecated
public class SyncClientHttp implements SyncClient {

	private Logger logger = Logger.getLogger("org.imogene.sync.client.http.SyncClientHttp");

	private String url = "http://localhost:8080/ImogSynchro/";

	private static final int maxAttempts = 100;

	public SyncClientHttp(String url) {
		this.url = url;
	}

	@Override
	public boolean closeSession(String sessionId) {

		/* request construction */
		NameValuePair cmdParam = new NameValuePair(CMD_PARAM, CMD_CLOSE);
		NameValuePair sessionParam = new NameValuePair(SESSION_PARAM, sessionId);
		NameValuePair[] params = new NameValuePair[] { cmdParam, sessionParam };
		GetMethod method = new GetMethod(url);
		method.setQueryString(params);

		/* request execution */
		try {
			HttpClient client = new HttpClient();
			client.executeMethod(method);
			if (method.getResponseBodyAsString().startsWith("ACK")) {
				return true;
			}
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
		}

		return false;
	}

	@Override
	public String initSession(String login, String passwd, String terminalId, String type) {

		/* request construction */
		NameValuePair cmdParam = new NameValuePair(CMD_PARAM, CMD_INIT);
		NameValuePair loginParam = new NameValuePair(LOGIN_PARAM, login);
		NameValuePair passwordParam = new NameValuePair(PASSWD_PARAM, passwd);
		NameValuePair terminalParam = new NameValuePair(TERMINALID_PARAM, terminalId);
		NameValuePair typeParam = new NameValuePair(TYPE_PARAM, type);
		NameValuePair[] params = new NameValuePair[] { cmdParam, loginParam, passwordParam, terminalParam, typeParam };
		GetMethod method = new GetMethod(url);
		method.setQueryString(params);

		try {
			/* request execution */
			HttpClient client = new HttpClient();
			int code = client.executeMethod(method);
			if (code == HttpStatus.SC_OK) {
				return method.getResponseBodyAsString();
			}
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
		}

		return null;
	}

	@Override
	public void requestServerModifications(String sessionId, OutputStream out) {
		/* request construction */
		NameValuePair sessionParam = new NameValuePair(SESSION_PARAM, sessionId);
		NameValuePair cmdParam = new NameValuePair(CMD_PARAM, CMD_SERVERMODIF);
		NameValuePair[] params = new NameValuePair[] { sessionParam, cmdParam };
		GetMethod method = new GetMethod(url);
		method.setQueryString(params);
		HttpClient client = new HttpClient();
		try {
			/* request execution */
			int code = client.executeMethod(method);
			if (code == HttpStatus.SC_OK) {
				/* read data sent by the server */
				long expectedLength = method.getResponseContentLength();
				InputStream is = method.getResponseBodyAsStream();
				writeInFile(is, out, expectedLength);
			}
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
		}
	}

	@Override
	public int sendClientModification(String sessionId, InputStream data) {

		return sendData(sessionId, CMD_SENDMODIF, data);
	}

	@Override
	public int directSend(String sessionId, InputStream data) {
		return sendData(sessionId, CDM_DIRECTSEND, data);
	}

	/**
	 * Write the request result data incoming in the input stream into the
	 * output stream
	 * 
	 * @param is the request result input stream
	 * @param out the outputStream
	 * @param expectedLength the data length expected
	 * @return 0 if done with success, -1 otherwise
	 */
	private int writeInFile(InputStream is, OutputStream out, long expectedLength) throws IOException {
		boolean finished = false;
		long bytesRead = 0;
		int nbAttemps = 0;

		while (!finished) {
			/* while there is data to read, we read it */
			byte[] buffer;
			while (is.available() > 0) {
				if (is.available() < 1024)
					buffer = new byte[is.available()];
				else
					buffer = new byte[1024];

				bytesRead = bytesRead + is.read(buffer);
				out.write(buffer);
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

	/**
	 * Send data to the server
	 * 
	 * @param sessionId the synchronization session id
	 * @param cmd the command
	 * @param data the data to send
	 * @return 0 if done with success, -1 otherwise
	 */
	private int sendData(String sessionId, String cmd, InputStream data) {
		try {
			/* request construction */
			PostMethod method = new PostMethod(url);
			StringPart sessionParam = new StringPart(SESSION_PARAM, sessionId);
			StringPart cmdParam = new StringPart(CMD_PARAM, cmd);
			SyncPartSource dataSource = new SyncPartSource(data, sessionId + ".cmodif", data.available());
			FilePart dataFile = new FilePart("data", dataSource);
			Part[] parts = { sessionParam, cmdParam, dataFile };

			/* execute the request */
			MultipartRequestEntity mEntity = new MultipartRequestEntity(parts, method.getParams());
			method.setRequestEntity(mEntity);
			HttpClient client = new HttpClient();
			int code = client.executeMethod(method);
			if (code == HttpStatus.SC_OK) {
				String body = new String(method.getResponseBody());
				if (body.startsWith("ACK")) {
					// String[] splits = body.split("_");
					// return Integer.parseInt(splits[1]);
					return 0;
				}
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return -1;
	}

}

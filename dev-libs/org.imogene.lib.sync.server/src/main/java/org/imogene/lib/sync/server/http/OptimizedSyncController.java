package org.imogene.lib.sync.server.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.imogene.lib.sync.serializer.ImogSerializationException;
import org.imogene.lib.sync.server.OptimizedSyncServer;
import org.imogene.lib.sync.server.http.command.ClientUploadCommand;
import org.imogene.lib.sync.server.http.command.InitializeCommand;
import org.imogene.lib.sync.server.http.command.SearchCommand;
import org.imogene.lib.sync.server.http.command.SessionCommand;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * This controller handles the synchronization process via the HTTP protocol
 * 
 * @author MEDES-IMPS
 */
public class OptimizedSyncController extends MultiActionController {

	/* headers */
	public static final String HEADER_NAME = "medoo-sync";
	public static final String HEADER_VALUE = "synchro";

	/* response messages */
	public static final String RESPONSE_OK = "OK";

	private Logger logger = Logger.getLogger("org.imogene.sync.server.http");

	private OptimizedSyncServer syncServer;

	private File workDirectory;

	/**
	 * Get the status of the active sessions
	 * 
	 * @param req http request
	 * @param resp http response
	 */
	public void status(HttpServletRequest req, HttpServletResponse resp) {
		setHeader(resp);

		// Get the session status
		try {
			ServletOutputStream out = resp.getOutputStream();
			logger.debug("SeSt: - list of active sessions - ");
			/*
			 * for (SyncSession session : SyncSessionManager.getInstance().getAllSessions()) { logger.debug("Session " +
			 * session.getId().toString() + " : termiId=" + session.getTerminalId() + " userId=" + session.getUserId() +
			 * " type=" + session.getType()); }
			 */
			out.close();
		} catch (IOException e) {
			logger.error("Status error", e);
		}
	}

	/**
	 * Authenticate a user.
	 * 
	 * @param req the servlet request
	 * @param resp the servlet response
	 */
	public void auth(HttpServletRequest req, HttpServletResponse resp) {
		setHeader(resp);

		try {
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentLength(RESPONSE_OK.getBytes().length);
			OutputStream out = resp.getOutputStream();
			out.write(RESPONSE_OK.getBytes());
			out.flush();
			out.close();
			logger.debug("Auth: Authentication successed user ");
		} catch (IOException e) {
			logger.error("Authentication error", e);
		}
	}

	/**
	 * Method that handles the session initialization command
	 * 
	 * @param req The HTTP servlet request
	 * @param resp The HTTP servlet response
	 * @param The command created from the request parameters
	 */
	public void init(HttpServletRequest req, HttpServletResponse resp, InitializeCommand command) {
		setHeader(resp);
		String sessionId = syncServer.initSession(command.getTerminal());
		try {
			resp.setContentLength(sessionId.getBytes().length);
			OutputStream out = resp.getOutputStream();
			out.write(sessionId.getBytes());
			out.flush();
			out.close();
			logger.debug("Init: Session initialized with id " + sessionId);
		} catch (IOException e) {
			logger.error("Init session error", e);
		}
	}

	/**
	 * Initialize a resume 'receive' session
	 * 
	 * @param req The HTTP servlet request
	 * @param resp The HTTP servlet response
	 */
	public void initresumereceive(HttpServletRequest req, HttpServletResponse resp) {
		setHeader(resp);
		try {
			resp.setContentLength(RESPONSE_OK.getBytes().length);
			OutputStream out = resp.getOutputStream();
			out.write(RESPONSE_OK.getBytes());
			out.flush();
			out.close();
			logger.debug("ResRec: Initialization of a resumed session ");
		} catch (IOException e) {
			logger.error("Init resume session receive error", e);
		}
	}

	/**
	 * Initialize a resume 'send' session
	 * 
	 * @param req The HTTP servlet request
	 * @param resp The HTTP servlet response
	 * @param The command created from the request parameters
	 */
	public void initresumesend(HttpServletRequest req, HttpServletResponse resp, SessionCommand command) {
		setHeader(resp);
		String sessionId = command.getSession();
		long bytesReceived = 0;
		File received = new File(getSyncWorkDirectory(req), sessionId + ".cmodif");
		logger.debug("ResSend: Initialize a resumed 'send' session");
		if (received.exists())
			bytesReceived = received.length();
		try {
			logger.debug("ResSend: I already received " + bytesReceived);
			resp.setContentLength(String.valueOf(bytesReceived).getBytes().length);
			OutputStream out = resp.getOutputStream();
			out.write(String.valueOf(bytesReceived).getBytes());
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error("Init resume session send error", e);
		}

	}

	public void searchEntity(HttpServletRequest req, HttpServletResponse resp, SearchCommand command) {
		setHeader(resp);

		logger.debug("Search: Looking for the entity with the id:" + command.getSearchedid());

		try {
			File tempFile = File.createTempFile("medoo", "search");
			OutputStream fos = new FileOutputStream(tempFile);
			syncServer.searchEntity(command.getSearchedid(), fos);

			/* sending the result */
			InputStream fis = new FileInputStream(tempFile);
			resp.setContentLength(fis.available());
			while (fis.available() > 0) {
				resp.getOutputStream().write(fis.read());
			}
			resp.getOutputStream().flush();
			resp.flushBuffer();
			fis.close();
		} catch (Exception e) {
			logger.error("Search entity error", e);
		}
	}

	/**
	 * Method that handles the server modification request command
	 * 
	 * @param req The HTTP servlet request
	 * @param resp The HTTP servlet response
	 * @param commandThe command created from the request parameters
	 */
	public void reqservmodif(HttpServletRequest req, HttpServletResponse resp, SessionCommand command) {
		setHeader(resp);
		try {
			logger.debug("SeMo: Requesting server modification for session " + command.getSession());
			/* write the result in a temporary file */
			File tempFile = new File(getSyncWorkDirectory(req), command.getSession() + ".smodif");
			OutputStream fos = new FileOutputStream(tempFile);
			syncServer.getServerModifications(command.getSession(), fos);
			/* read the file and wrote the result as response */
			InputStream fis = new FileInputStream(tempFile);
			resp.setContentLength(fis.available());
			while (fis.available() > 0) {
				resp.getOutputStream().write(fis.read());
			}
			resp.getOutputStream().flush();
			resp.flushBuffer();
			fis.close();
		} catch (Exception e) {
			logger.error("Request server modifications error", e);
		}
	}

	/**
	 * Resume a server modification sent
	 * 
	 * @param req the HTTP request
	 * @param resp the HTTP response
	 * @param command the associated command
	 */
	public void resumesend(HttpServletRequest req, HttpServletResponse resp) {
		setHeader(resp);
		logger.debug("Resume a 'send' session");
		clmodif(req, resp);
	}

	/**
	 * Resume a server modification sent
	 * 
	 * @param req the HTTP request
	 * @param resp the HTTP response
	 * @param command the associated command
	 */
	public void resumereceive(HttpServletRequest req, HttpServletResponse resp, SessionCommand command) {
		setHeader(resp);
		try {
			logger.debug("ResRec: Resume a 'receive' session with session id " + command.getSession()
					+ " this client already received " + command.getLen() + " bytes");
			/* write the result in a temporary file */
			File tempFile = new File(this.getSyncWorkDirectory(req), command.getSession() + ".smodif");
			/* create the file if it doesn't exist */
			if (!tempFile.exists()) {
				logger.debug("ResRec: the file doesn't exist, so we created it by serializing the entities");
				try {
					OutputStream fos = new FileOutputStream(tempFile);
					syncServer.getServerModifications(command.getSession(), fos);
					fos.close();
				} catch (ImogSerializationException e) {
					logger.error(e.getMessage(), e);
				}
			}
			/* read the file and wrote the result as response */
			InputStream fis = new FileInputStream(tempFile);
			long skipped = fis.skip(command.getLen());
			if (skipped != command.getLen()) {
				logger.error("Error skipping bytes: " + command.getLen() + " bytes to skip," + skipped
						+ " bytes skipped");
				fis.close();
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			resp.setContentLength(fis.available());
			while (fis.available() > 0) {
				resp.getOutputStream().write(fis.read());
			}
			resp.getOutputStream().flush();
			resp.flushBuffer();
			fis.close();
		} catch (IOException e) {
			logger.error("Error resuming a receive", e);
		}
	}

	/**
	 * The method handles the server modification acknowledge command.
	 * 
	 * @param req the HTTP servlet request
	 * @param resp the HTTP servlet response
	 * @param The command created from the request parameters
	 */
	public void ackservmodif(HttpServletRequest req, HttpServletResponse resp, SessionCommand command) {
		setHeader(resp);
		syncServer.closeSession(command.getSession(), command.getStatus());
		File cmodif = new File(getSyncWorkDirectory(req), command.getSession() + ".cmodif");
		File smodif = new File(getSyncWorkDirectory(req), command.getSession() + ".smodif");
		if (!command.getDebug()) {
			logger.debug("SeMo: Server modification ACK received, so i delete the temp files.");
			if (cmodif.exists())
				cmodif.delete();
			if (smodif.exists())
				smodif.delete();
		} else {
			logger.debug("SeMo: Debug mod activated, we don't delete the created files.");
		}
		try {
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentLength(RESPONSE_OK.getBytes().length);
			OutputStream out = resp.getOutputStream();
			out.write(RESPONSE_OK.getBytes());
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error("Error closing session", e);
		}
	}

	/**
	 * This method handles the reception of the client modification
	 * 
	 * @param req the HTTP servlet request
	 * @param resp the HTTP servlet response
	 */
	public void clmodif(HttpServletRequest req, HttpServletResponse resp) {
		setHeader(resp);
		logger.debug("ClMo: I receive the client modifications.");
		ClientUploadCommand command = new ClientUploadCommand();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(command);
		binder.bind(req);
		String sessionId = command.getSession();
		try {
			logger.debug("ClMo: Sarting to parse the received file");
			int code = syncServer.applyClientModifications(sessionId, command.getData().getInputStream());
			if (code != -1) {
				resp.getOutputStream().print("ACK_" + code);
			} else {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("Error receiving client modification", e);
		}
	}

	/**
	 * This method handles the reception of the client directsend
	 * 
	 * @param req the HTTP servlet request
	 * @param resp the HTTP servlet response
	 * @param command The command created from the request parameters
	 */
	public void directsend(HttpServletRequest req, HttpServletResponse resp, ClientUploadCommand command) {
		setHeader(resp);
		logger.debug("DiSen: Receive direct send command");
		clmodif(req, resp);
		syncServer.closeSession(command.getSession(), command.getStatus());
	}

	/**
	 * Process the exceptions
	 * 
	 * @param req the HTTP request
	 * @param resp the HTTP response
	 * @param ex The exception thrown
	 */
	public void processException(HttpServletRequest req, HttpServletResponse resp, Exception ex) {
		setHeader(resp);
		ex.printStackTrace();
		StringBuffer message = new StringBuffer();
		message.append("-error-").append(" ").append(ex.getMessage());
		try {
			resp.getOutputStream().write(message.toString().getBytes());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * Set the sync server implementation
	 * 
	 * @param syncServer the sync server
	 */
	public void setSyncServer(OptimizedSyncServer syncServer) {
		this.syncServer = syncServer;
	}

	@Override
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

	/**
	 * Create the sync work directory
	 * 
	 * @param req the request.
	 * @return the sync work directory
	 */
	private File getSyncWorkDirectory(HttpServletRequest req) {
		if (workDirectory == null) {
			String rootPath = req.getSession().getServletContext().getRealPath("WEB-INF");
			File syncWork = new File(rootPath + "/syncWork");
			syncWork.mkdir();
			workDirectory = syncWork;
		}
		return workDirectory;
	}

	private void setHeader(HttpServletResponse response) {
		response.setHeader(HEADER_NAME, HEADER_VALUE);
	}

}

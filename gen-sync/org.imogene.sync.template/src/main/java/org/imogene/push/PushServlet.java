package org.imogene.push;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.imogene.lib.push.messages.MessageHelper;
import org.springframework.web.HttpRequestHandler;

public class PushServlet implements HttpRequestHandler {

	private static final String PARAM_LOGIN = "login";
	private static final String PARAM_COMMAND = "command";
	private static final String PARAM_TEMRINAL = "terminal";

	private MessageHelper messageHelper;

	public void setMessageHelper(MessageHelper messageHelper) {
		this.messageHelper = messageHelper;
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String login = request.getParameter(PARAM_LOGIN);
		String command = request.getParameter(PARAM_COMMAND);
		String terminal = request.getParameter(PARAM_TEMRINAL);
		messageHelper.pushMessage(login, terminal, command);
		response.getOutputStream().write("OK".getBytes());
	}

}

package org.imogene.web.server.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 
 * @author MEDES-IMPS
 */
public class ImogAccessDeniedHandler implements AccessDeniedHandler {
	
	private String accessDeniedUrl;

	public ImogAccessDeniedHandler() {
	}

	public ImogAccessDeniedHandler(String accessDeniedUrl) {
		this.accessDeniedUrl = accessDeniedUrl;
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException,
			ServletException {
		
		String url = request.getRequestURL() + accessDeniedUrl;
		response.sendRedirect(url);
	}

	public void setAccessDeniedUrl(String accessDeniedUrl) {
		this.accessDeniedUrl = accessDeniedUrl;
	}
}
package org.imogene.lib.sync.server.session;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class SessionInvalidatorFilter implements Filter {

	Logger logger = Logger.getLogger(getClass());

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		chain.doFilter(req, res);
		HttpSession session = ((HttpServletRequest) req).getSession(false);
		if (session != null) {
			session.invalidate();
			logger.debug("Session " + session.getId() + " invalidated");
		} else {
			logger.debug("No session to invalidate");
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}

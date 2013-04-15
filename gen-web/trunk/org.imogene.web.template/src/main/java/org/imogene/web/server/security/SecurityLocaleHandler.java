package org.imogene.web.server.security;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.imogene.web.server.util.URLBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

public class SecurityLocaleHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	
	public static final String LOCALE_PARAM = "locale";
	
	public static final String TARGETURL_PARAM = "targetUrl";
	
	public static final String SERVICE_URL_EXTENSION = ".serv";
	
	private Logger logger = Logger.getLogger(SecurityLocaleHandler.class);	
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	private SavedRequest savedRequest;
	
	private String modifiedUrl;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication auth) throws IOException,
			ServletException {		
		
		savedRequest = requestCache.getRequest(request, response);				
		
		if(savedRequest!=null){
			String redirectUrl = savedRequest.getRedirectUrl();
			if(modifyReditectUrl(request, redirectUrl)){		
				logger.debug("SaveRequest is not null and we modified the url: " + modifiedUrl);
				clearAuthenticationAttributes(request);
				super.getRedirectStrategy().sendRedirect(request, response, modifiedUrl);
			}else{
				super.onAuthenticationSuccess(request, response, auth);
			}
		}else{		
			logger.debug("SaveRequest is null");			
			super.onAuthenticationSuccess(request, response, auth);
		}
	}
	
	/**
	 * Modify the redirect URL if needed
	 * @param request the authentication request
	 * @param redirectUrl the saved redirect URL
	 * @return true if the URL have been modified
	 * @throws MalformedURLException
	 */
	private boolean modifyReditectUrl(HttpServletRequest request, String redirectUrl) throws MalformedURLException{
		
		logger.debug("Request: " + request.getRequestURL());
		logger.debug("Query: " + request.getQueryString());
		logger.debug("Redirect: " + redirectUrl);
		
		URL url = new URL(redirectUrl);
		
		/* if it is a request to a RPC service 
		 * we redirect to the entry point page */
		if(url.getPath().endsWith(SERVICE_URL_EXTENSION)){
			redirectUrl = request.getContextPath() + "/";
			logger.debug("RPC service URL, we redirect to the context root");
			return true;
		}
		
		/* if a 'locale' parameter is present in the login page request, 
		 * we report it to the saved request URL */
		if(request.getParameter(LOCALE_PARAM)!=null){
			URLBuilder builder = new URLBuilder(url);
			if(builder.getFirstValueParameter(LOCALE_PARAM)==null){
				if(request.getParameter(LOCALE_PARAM)!=null){
					builder.setParameter(LOCALE_PARAM, request.getParameterValues(LOCALE_PARAM));
					logger.debug("Builder output: " + builder.buildString());
					modifiedUrl = builder.buildString();
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public void setRequestCache(RequestCache requestCache) {
		this.requestCache = requestCache;
		super.setRequestCache(requestCache);
	}
			
}

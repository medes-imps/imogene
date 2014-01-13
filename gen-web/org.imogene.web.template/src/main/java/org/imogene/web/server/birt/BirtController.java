package org.imogene.web.server.birt;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * 
 * @author MEDES-IMPS
 */
public class BirtController implements Controller {
	
	private static final Logger logger = Logger.getLogger(BirtController.class.getName());
	
	private BirtView birtView;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
				
		ModelAndView mv = null;
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			mv = new ModelAndView(birtView, model);
						
		} catch (Exception e) {
			logger.error(e);
		}
		return mv;
	}

	public void setBirtView(BirtView birtView) {
		this.birtView = birtView;
	}
	
	
}
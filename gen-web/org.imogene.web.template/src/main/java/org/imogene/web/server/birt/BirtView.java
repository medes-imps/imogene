package org.imogene.web.server.birt;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IPDFRenderOption;
import org.eclipse.birt.report.engine.api.IParameterDefnBase;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.IScalarParameterDefn;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.eclipse.birt.report.engine.emitter.csv.CSVRenderOption;
import org.imogene.web.shared.constants.BirtConstants;
import org.springframework.web.servlet.view.AbstractView;

/**
 * @author MEDES-IMPS
 */
public class BirtView extends AbstractView {


	private IReportEngine birtEngine;
	private IRenderOption renderOptions;
	

	/**
	 * 
	 */
	public BirtView() {
		super();
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	protected void renderMergedOutputModel(Map<String,Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String reportName = request.getParameter(BirtConstants.REPORT_NAME);
		String format = request.getParameter(BirtConstants.REPORT_FORMAT);	
		ServletContext sc = request.getSession().getServletContext();
		
		if (format == null) {
			format = BirtConstants.PDF;
		}

		IReportRunnable runnable = null;
		runnable = birtEngine.openReportDesign(sc.getRealPath(BirtConstants.TEMPLATE_REPOSITORY) + "/" + reportName + ".rptdesign");
		IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(runnable);
		runAndRenderTask.setParameterValues(discoverAndSetParameters(runnable, request));
			
		response.setContentType(birtEngine.getMIMEType(format));
		IRenderOption options = null == this.renderOptions ? new RenderOption() : this.renderOptions;
		
		if (format.equalsIgnoreCase(BirtConstants.HTML)) {
			HTMLRenderOption htmlOptions = new HTMLRenderOption(options);
			htmlOptions.setOutputFormat("html");
			htmlOptions.setOutputStream(response.getOutputStream());
			htmlOptions.setImageHandler(new HTMLServerImageHandler());
			htmlOptions.setBaseImageURL(request.getContextPath() + BirtConstants.IMAGE_REPOSITORY);
			htmlOptions.setImageDirectory(sc.getRealPath(BirtConstants.IMAGE_REPOSITORY));
			runAndRenderTask.setRenderOption(htmlOptions);

		} 
		else if (format.equalsIgnoreCase(BirtConstants.PDF)) {
			PDFRenderOption pdfOptions = new PDFRenderOption(options);
			pdfOptions.setOutputFormat("pdf");
			pdfOptions.setOption(IPDFRenderOption.PAGE_OVERFLOW, IPDFRenderOption.FIT_TO_PAGE_SIZE);
			pdfOptions.setOutputStream(response.getOutputStream());
			runAndRenderTask.setRenderOption(pdfOptions);
		} 
		else if (format.equalsIgnoreCase(BirtConstants.CSV)) {
			CSVRenderOption csvOptions = new CSVRenderOption(options);
			csvOptions.setOutputFormat(CSVRenderOption.OUTPUT_FORMAT_CSV);
			csvOptions.setDelimiter(";");
			csvOptions.setReplaceDelimiterInsideTextWith(",");
			csvOptions.setOutputStream(response.getOutputStream());
			runAndRenderTask.setRenderOption(csvOptions);
		} 
		else if (format.equalsIgnoreCase(BirtConstants.XLS)) {
			EXCELRenderOption  xlsOptions = new EXCELRenderOption(options);
			xlsOptions.setOutputFormat("xls");
			xlsOptions.setOutputStream(response.getOutputStream());
			runAndRenderTask.setRenderOption(xlsOptions);
		}		
		
		runAndRenderTask.getAppContext().put(EngineConstants.APPCONTEXT_BIRT_VIEWER_HTTPSERVET_REQUEST, request);
		runAndRenderTask.run();
		runAndRenderTask.close();

	}

	/**
	 * 
	 * @param report
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected HashMap<String, Object> discoverAndSetParameters(IReportRunnable report, HttpServletRequest request) throws Exception {

		HashMap<String, Object> parms = new HashMap<String, Object>();
		IGetParameterDefinitionTask task = birtEngine.createGetParameterDefinitionTask(report);

		@SuppressWarnings("unchecked")
		Collection<IParameterDefnBase> params = task.getParameterDefns(true);
		Iterator<IParameterDefnBase> iter = params.iterator();
		while (iter.hasNext()) {
			IParameterDefnBase param = (IParameterDefnBase) iter.next();
			IScalarParameterDefn scalar = (IScalarParameterDefn) param;
			if (request.getParameter(param.getName()) != null) {
				parms.put(param.getName(), getParamValueObject(request, scalar));
			}
		}
		task.close();
		return parms;
	}

	/**
	 * 
	 * @param request
	 * @param parameterObj
	 * @return
	 * @throws Exception
	 */
	protected Object getParamValueObject(HttpServletRequest request, IScalarParameterDefn parameterObj) throws Exception {
		
		String paramName = parameterObj.getName();
		String format = parameterObj.getDisplayFormat();
		if (doesReportParameterExist(request, paramName)) {
			ReportParameterConverter converter = new ReportParameterConverter(format, request.getLocale());
			// Get value from http request
			String paramValue = getReportParameter(request, paramName, null);
			return converter.parse(paramValue, parameterObj.getDataType());
		}
		return null;
	}

	/**
	 * 
	 * @param request
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static String getReportParameter(HttpServletRequest request, String name, String defaultValue) {
		
		assert request != null && name != null;

		String value = getParameter(request, name);
		if (value == null || value.length() <= 0) // Treat it as blank value.
			value = "";

		Map<String,String[]> paramMap = request.getParameterMap();
		if (paramMap == null || !paramMap.containsKey(name)) {
			value = defaultValue;
		}

		Set<String> nullParams = getParameterValues(request, BirtConstants.PARAM_ISNULL);
		if (nullParams != null && nullParams.contains(name)) {
			value = null;
		}

		return value;
	}

	/**
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static boolean doesReportParameterExist(HttpServletRequest request, String name) {		
		
		assert request != null && name != null;
		boolean isExist = false;

		Map<String,String[]> paramMap = request.getParameterMap();
		if (paramMap != null) {
			isExist = (paramMap.containsKey(name));
		}
		Set<String> nullParams = getParameterValues(request, BirtConstants.PARAM_ISNULL);
		if (nullParams != null && nullParams.contains(name)) {
			isExist = true;
		}

		return isExist;
	}

	/**
	 * 
	 * @param request
	 * @param parameterName
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String parameterName) {

		if (request.getCharacterEncoding() == null) {
			try {
				request.setCharacterEncoding(BirtConstants.UTF_8_ENCODE);
			} catch (UnsupportedEncodingException e) {
			}
		}
		return request.getParameter(parameterName);
	}

	/**
	 * allows setting parameter values to null using __isnull
	 * @param request
	 * @param parameterName
	 * @return
	 */
	public static Set<String> getParameterValues(HttpServletRequest request, String parameterName) {
		
		Set<String> parameterValues = null;
		String[] parameterValuesArray = request.getParameterValues(parameterName);
		if (parameterValuesArray != null) {
			parameterValues = new LinkedHashSet<String>();
			for (int i = 0; i < parameterValuesArray.length; i++) {
				parameterValues.add(parameterValuesArray[i]);
			}
		}
		return parameterValues;
	}

	/**
	 * Setter for bean injection
	 * @param birtEngine
	 */
	public void setBirtEngine(IReportEngine birtEngine) {
		this.birtEngine = birtEngine;
	}
	
	/**
	 * 
	 * @param ro
	 */
	public void setRenderOptions(IRenderOption ro) {
		this.renderOptions = ro;
	}

}
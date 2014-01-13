package org.imogene.web.server.birt;

import java.util.logging.Logger;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Factory bean for the instance of the {@link IReportEngine report engine}.
 */
public class BirtEngineFactory implements FactoryBean<IReportEngine>, ApplicationContextAware, DisposableBean {
	
	
	private static final Logger logger = Logger.getLogger(BirtEngineFactory.class.getName());

	private ApplicationContext context;
	private IReportEngine birtEngine;


	@SuppressWarnings("unchecked")
	public IReportEngine getObject() {

		EngineConfig config = new EngineConfig();

		// inject the Spring Context into the BIRT Context
		config.getAppContext().put("spring", this.context);
		config.setLogger(logger);
		try {
			Platform.startup(config);
		} catch (BirtException e) {
			throw new RuntimeException("Could not start the Birt engine!", e);
		}

		IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
		IReportEngine be = factory.createReportEngine(config);
		this.birtEngine = be;

		return be;
	}
	
	public void destroy() throws Exception {
		birtEngine.destroy();
		Platform.shutdown();
	}
	
	public boolean isSingleton() {
		return true;
	}
	
	public void setApplicationContext(ApplicationContext ctx) {
		this.context = ctx;
	}

	@Override
	public Class<?> getObjectType() {
		return IReportEngine.class;
	}
	

}

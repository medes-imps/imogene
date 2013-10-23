package org.imogene.studio.contrib.ui.navigator;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.imogene.studio.contrib.ImogeneStudioPlugin;


/**
 * Section that groups all generated 
 * projects for a Medoo model.
 * @author Medes-IMPS
 */
public class GeneratedShadow extends AbstractShadow {	

	public static final String TYPE="generated"; //$NON-NLS-1$
	
	private Map<String, Object> objectCache = new HashMap<String, Object>();
	
	public GeneratedShadow(IProject parent){
		super(parent, TYPE);
		setLabel("Generated projects"); //$NON-NLS-1$
		setIcon(ImogeneStudioPlugin.getImageDescriptor(
			"icons/launch_web_16.gif").createImage()); //$NON-NLS-1$
	}
	
	@Override
	public Object[] getChildren() {
		IShadow[] pp = new IShadow[10];
		
		IShadow init = (IShadow)objectCache.get(InitializerShadow.TYPE);
		if(init==null){
			init = new InitializerShadow(parent);
			objectCache.put(InitializerShadow.TYPE, init);
		}
		pp[0] = init;
		
		IShadow web = (IShadow)objectCache.get(WebShadow.TYPE);
		if(web==null){
			web = new WebShadow(parent);
			objectCache.put(WebShadow.TYPE, web);
		}
		pp[1] = web;
		
		IShadow sync = (IShadow)objectCache.get(SynchroShadow.TYPE);
		if(sync==null){
			sync = new SynchroShadow(parent);
			objectCache.put(SynchroShadow.TYPE, sync);
		}
		pp[2] = sync;
		
		IShadow rcp = (IShadow)objectCache.get(RcpShadow.TYPE);
		if(rcp==null){
			rcp = new RcpShadow(parent);
			objectCache.put(RcpShadow.TYPE, rcp);
		}
		pp[3] = rcp;
		
		IShadow droid = (IShadow)objectCache.get(AndroidShadow.TYPE);
		if(droid==null){
			droid = new AndroidShadow(parent);
			objectCache.put(AndroidShadow.TYPE, droid);
		}
		pp[4] = droid;
		
		IShadow admin = (IShadow)objectCache.get(AdminShadow.TYPE);
		if(admin==null){
			admin = new AdminShadow(parent);
			objectCache.put(AdminShadow.TYPE, admin);
		}
		pp[5] = admin;
		
		IShadow notif = (IShadow)objectCache.get(NotifierShadow.TYPE);
		if(notif==null){
			notif = new NotifierShadow(parent);
			objectCache.put(NotifierShadow.TYPE, notif);
		}
		pp[6] = notif;
		
		IShadow serv = (IShadow)objectCache.get(WebServiceShadow.TYPE);
		if(serv==null){
			serv = new WebServiceShadow(parent);
			objectCache.put(WebServiceShadow.TYPE, serv);
		}
		pp[7] = serv;
		
		IShadow serv_soap = (IShadow)objectCache.get(WebServiceSoapShadow.TYPE);
		if(serv_soap==null){
			serv_soap = new WebServiceSoapShadow(parent);
			objectCache.put(WebServiceSoapShadow.TYPE, serv_soap);
		}
		pp[8] = serv_soap;
		
		IShadow dao = (IShadow) objectCache.get(DaoShadow.TYPE);
		if (dao == null) {
			dao = new DaoShadow(parent);
			objectCache.put(DaoShadow.TYPE, dao);
		}
		pp[9] = dao;
		
		return pp;
	}

	@Override
	public boolean hasChildren() {
		return true;
	}

}

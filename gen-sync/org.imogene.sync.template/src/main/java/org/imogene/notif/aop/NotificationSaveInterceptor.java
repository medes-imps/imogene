package org.imogene.notif.aop;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.imogene.lib.common.constants.UserActionConstants;
import org.imogene.lib.common.dao.GenericDao;
import org.imogene.lib.common.entity.CloneFactory;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.entity.ImogBean;
import org.imogene.lib.common.useraction.UserAction;
import org.imogene.lib.sync.SyncConstants;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author MEDES/IMPS
 */
public class NotificationSaveInterceptor implements AfterReturningAdvice {

	private static final Logger logger = Logger.getLogger(NotificationSaveInterceptor.class);

	private String notifierHost = "http://localhost:8080/DiabsatNotif";

	private GenericDao genericDao;

	private CloneFactory cloneFactory;

	/**
	 * Notify about the entity modification/creation
	 */
	@Override
	public void afterReturning(Object result, Method method, Object[] args, Object target) throws Throwable {
		handleAction(method, args, result);
	}

	/**
	 * 
	 * @param method
	 * @param args
	 * @param result
	 */
	@Transactional
	private void handleAction(Method method, Object[] args, Object result) {

		if (method.getName().equals("saveOrUpdate") || method.getName().equals("merge")) {
			handleSaveAction(args);
		} else if (method.getName().equals("load") && args != null && args.length == 1 && args[0] instanceof String) {
			handleViewAction(result);
		} else if (method.getName().equals("delete")) {
			handleDeleteAction(args);
		}
	}

	/**
	 * Handle the saveOrUpdate method
	 * 
	 * @param args arguments of the method
	 */
	private void handleSaveAction(Object[] args) {

		ImogBean bean = (ImogBean) args[0];
		boolean isNew = (Boolean) args[1];

		String idFormulaire = bean.getId();
		String className = bean.getClass().getName();
		String typeFormulaire = bean.getClass().getSimpleName();

		String typeAction = null;
		if (isNew)
			typeAction = UserActionConstants.USERACTION_TYPE_CREATE;
		else
			typeAction = UserActionConstants.USERACTION_TYPE_UPDATE;

		if (!SyncConstants.SYNC_ID_SYS.equals(bean.getCreatedBy())) {
			saveAction(typeAction, typeFormulaire, idFormulaire, getActor(bean.getCreatedBy()));
		}

		try {
			cloneEntity(bean);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		sendToNotifier(className, typeAction, idFormulaire);
	}

	/**
	 * 
	 * @param args
	 * @param result
	 */
	private void handleViewAction(Object result) {

		if (result != null) {
			ImogBean bean = (ImogBean) result;
			String idFormulaire = bean.getId();
			String typeFormulaire = bean.getClass().getSimpleName();
			String typeAction = UserActionConstants.USERACTION_TYPE_READ;

			if (!SyncConstants.SYNC_ID_SYS.equals(bean.getCreatedBy())) {
				saveAction(typeAction, typeFormulaire, idFormulaire, getActor(bean.getCreatedBy()));
			}
		}
	}

	/**
	 * 
	 * @param args
	 */
	private void handleDeleteAction(Object[] args) {

		ImogBean bean = (ImogBean) args[0];
		String idFormulaire = bean.getId();
		String typeFormulaire = bean.getClass().getSimpleName();
		String typeAction = UserActionConstants.USERACTION_TYPE_DELETE;

		if (!SyncConstants.SYNC_ID_SYS.equals(bean.getCreatedBy())) {
			saveAction(typeAction, typeFormulaire, idFormulaire, getActor(bean.getCreatedBy()));
		}
	}

	private void cloneEntity(Object source) {
		if (source != null) {
			Object clone = cloneFactory.clone(source);
			if (clone != null)
				genericDao.saveOrUpdate(clone);
		}
	}

	/**
	 * Notify the notifier by http.
	 * 
	 * @param type the card type
	 * @param operation the operation on the card
	 * @param id the card id
	 */
	private void sendToNotifier(final String type, final String operation, final String id) {

		/* thread the notification process */
		new Thread() {
			@Override
			public void run() {

				HttpClient client = new HttpClient();
				String uri = notifierHost + "?" + "type=" + type + "&op=" + operation + "&id=" + id;
				logger.debug("Notifier URI : " + uri);
				GetMethod method = new GetMethod(uri);
				try {
					client.executeMethod(method);
				} catch (Exception ex) {
					logger.error(ex.getMessage());
				}

			}
		}.start();
	}

	/**
	 * 
	 * @param typeAction
	 * @param typeFormulaire
	 * @param idFormulaire
	 */
	private void saveAction(String actionType, String form, String formId, ImogActor actor) {

		UserAction action = new UserAction();
		action.setId(UUID.randomUUID().toString());
		action.setActionDate(new Date(System.currentTimeMillis()));
		if (actor != null)
			action.setUserId(actor.getId());
		action.setActionType(actionType);
		action.setFormType(form);
		action.setFormId(formId);

		genericDao.saveOrUpdate(action);
	}

	/**
	 * 
	 * @param login
	 * @return
	 */
	private ImogActor getActor(String login) {
		return genericDao.loadFromLogin(login);
	}

	/**
	 * For bean injection
	 * 
	 * @param notifier the notifier URL
	 */
	public void setNotifierUrl(String notifier) {
		notifierHost = notifier;
	}

	/**
	 * For bean injection
	 * 
	 * @param dao
	 */
	public void setDao(GenericDao dao) {
		genericDao = dao;
	}

	/**
	 * For bean injection
	 * 
	 * @param cloneFactory
	 */
	public void setCloneFactory(CloneFactory cloneFactory) {
		this.cloneFactory = cloneFactory;
	}

}

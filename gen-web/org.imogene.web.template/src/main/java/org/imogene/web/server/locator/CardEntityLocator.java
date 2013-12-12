package org.imogene.web.server.locator;

import javax.servlet.http.HttpServletRequest;

import org.imogene.lib.common.model.CardEntity;
import org.imogene.web.server.handler.CardEntityHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * A Locator to locate SynchronizableEntity beans 
 * @author Medes-IMPS
 */
public class CardEntityLocator
		extends
			Locator<CardEntity, String> {

	private CardEntityHandler handler;

	public CardEntityLocator() {

	}

	@Override
	public CardEntity create(
			Class<? extends CardEntity> clazz) {
		return new CardEntity();
	}

	@Override
	public CardEntity find(
			Class<? extends CardEntity> clazz, String id) {
		if (handler == null)
			initHandler();
		return handler.findById(id);
	}

	@Override
	public Class<CardEntity> getDomainType() {
		return CardEntity.class;
	}

	@Override
	public String getId(CardEntity domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<String> getIdType() {
		return String.class;
	}

	@Override
	public Object getVersion(CardEntity domainObject) {
		return domainObject.getVersion();
	}

	private void initHandler() {
		HttpServletRequest request = RequestFactoryServlet
				.getThreadLocalRequest();
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(request.getSession()
						.getServletContext());
		handler = (CardEntityHandler) context
				.getBean("cardEntityHandler");
	}
}

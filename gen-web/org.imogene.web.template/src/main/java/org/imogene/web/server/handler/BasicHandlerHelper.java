package org.imogene.web.server.handler;

import java.util.Date;

import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.lib.common.criteria.BasicCriteria;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.entity.ImogBean;
import org.imogene.lib.common.model.CardEntity;
import org.imogene.lib.common.model.FieldGroup;
import org.imogene.lib.common.profile.EntityProfile;
import org.imogene.lib.common.profile.FieldGroupProfile;
import org.imogene.lib.common.profile.Profile;
import org.imogene.lib.common.util.SystemUtil;
import org.imogene.web.server.util.HttpSessionUtil;

public class BasicHandlerHelper implements HandlerHelper {

	private ProfileHandler profileHandler;
	private EntityProfileHandler entityProfileHandler;
	private FieldGroupProfileHandler fieldGroupProfileHandler;
	private CardEntityHandler cardEntityHandler;
	private FieldGroupHandler fieldGroupHandler;

	private SystemUtil systemUtil;

	@Override
	public void delete(ImogBean bean) {
		if (bean instanceof Profile) {
			profileHandler.delete((Profile) bean);
		} else if (bean instanceof EntityProfile) {
			entityProfileHandler.delete((EntityProfile) bean);
		} else if (bean instanceof FieldGroupProfile) {
			fieldGroupProfileHandler.delete((FieldGroupProfile) bean);
		} else if (bean instanceof CardEntity) {
			cardEntityHandler.delete((CardEntity) bean);
		} else if (bean instanceof FieldGroup) {
			fieldGroupHandler.delete((FieldGroup) bean);
		}
	}

	@Override
	public void save(ImogBean bean, boolean isNew) {
		if (bean instanceof Profile) {
			profileHandler.save((Profile) bean, isNew);
		} else if (bean instanceof EntityProfile) {
			entityProfileHandler.save((EntityProfile) bean, isNew);
		} else if (bean instanceof FieldGroupProfile) {
			fieldGroupProfileHandler.save((FieldGroupProfile) bean, isNew);
		} else if (bean instanceof CardEntity) {
			cardEntityHandler.save((CardEntity) bean, isNew);
		} else if (bean instanceof FieldGroup) {
			fieldGroupHandler.save((FieldGroup) bean, isNew);
		}
	}

	@Override
	public void prepare(ImogBean bean) {
		ImogActor actor = HttpSessionUtil.getCurrentUser();
		bean.setModified(new Date(systemUtil.getCurrentTimeMillis()));
		bean.setModifiedBy(actor.getLogin());
		bean.setModifiedFrom(systemUtil.getTerminal());
		if (bean.getCreatedBy() == null) {
			bean.setCreatedBy(actor.getLogin());
		}
		if (bean.getCreated() == null) {
			bean.setCreated(new Date(systemUtil.getCurrentTimeMillis()));
		}
	}

	@Override
	public void prepareForDelete(ImogBean bean) {
		bean.setDeleted(new Date(systemUtil.getCurrentTimeMillis()));
		prepare(bean);
	}

	/**
	 * Setter for bean injection
	 * @param handler the Profile Handler
	 */
	public void setProfileHandler(ProfileHandler handler) {
		this.profileHandler = handler;
	}

	/**
	 * Setter for bean injection
	 * @param handler the EntityProfile Handler
	 */
	public void setEntityProfileHandler(EntityProfileHandler handler) {
		this.entityProfileHandler = handler;
	}

	/**
	 * Setter for bean injection
	 * @param handler the FieldGroupProfile Handler
	 */
	public void setFieldGroupProfileHandler(FieldGroupProfileHandler handler) {
		this.fieldGroupProfileHandler = handler;
	}

	/**
	 * Setter for bean injection
	 * @param handler the CardEntity Handler
	 */
	public void setCardEntityHandler(CardEntityHandler handler) {
		this.cardEntityHandler = handler;
	}

	/**
	 * Setter for bean injection
	 * @param handler the FieldGroup Handler
	 */
	public void setFieldGroupHandler(FieldGroupHandler handler) {
		this.fieldGroupHandler = handler;
	}

	/**
	 * Setter for bean injection.
	 * @param systemUtil
	 */
	public void setSystemUtil(SystemUtil systemUtil) {
		this.systemUtil = systemUtil;
	}

	@Override
	public Date getCurrentTimeMillis() {
		return new Date(systemUtil.getCurrentTimeMillis());
	}

	@Override
	public BasicCriteria getNotDeletedFilterCriteria() {
		BasicCriteria criteria = new BasicCriteria();
		criteria.setOperation(CriteriaConstants.OPERATOR_ISNULL);
		criteria.setField("deleted");
		criteria.setValue(null);
		return criteria;
	}

}

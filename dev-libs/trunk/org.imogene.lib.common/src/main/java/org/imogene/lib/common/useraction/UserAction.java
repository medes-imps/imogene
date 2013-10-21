package org.imogene.lib.common.useraction;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.imogene.lib.common.entity.ImogBeanImpl;

/**
 * ImogBean implementation for the entity UserAction
 * @author MEDES-IMPS
 */
@Entity
@Table(name = "imog_useraction")
public class UserAction extends ImogBeanImpl {

	private static final long serialVersionUID = -6164374875227887060L;

	/* Description group fields */

	@Temporal(TemporalType.TIMESTAMP)
	private Date actionDate;

	private String userId;

	private String actionType;

	private String formType;

	private String formId;

	/**
	 * Constructor
	 */
	public UserAction() {
	}

	/* Getters and Setters for Description group fields */

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date value) {
		actionDate = value;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String value) {
		userId = value;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String value) {
		actionType = value;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String value) {
		formType = value;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String value) {
		formId = value;
	}

}

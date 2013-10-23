package org.imogene.lib.common.filter;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.imogene.lib.common.entity.ImogBeanImpl;

/**
 * ClientFilter Bean Implementation
 * @author MEDES-IMPS
 */
@Entity
@Table(name="client_filter")
public class ClientFilter extends ImogBeanImpl {

	private static final long serialVersionUID = 6252100029922768147L;

	private String userId;
	private String terminalId;
	private String cardEntity;
	private String entityField;
	private String operator;
	private String fieldValue;
	private Boolean isNew;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String param) {
		userId = param;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String param) {
		terminalId = param;
	}

	public String getCardEntity() {
		return cardEntity;
	}

	public void setCardEntity(String param) {
		cardEntity = param;
	}

	public String getEntityField() {
		return entityField;
	}

	public void setEntityField(String param) {
		entityField = param;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String param) {
		operator = param;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String param) {
		fieldValue = param;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean param) {
		isNew = param;
	}

	public void setEntity(ClientFilter entity) {
		this.setId(entity.getId());
		this.setModified(entity.getModified());
		this.setModifiedBy(entity.getModifiedBy());
		this.setCreated(entity.getCreated());
		this.setCreatedBy(entity.getCreatedBy());
		this.setUserId(entity.getUserId());
		this.setTerminalId(entity.getTerminalId());
		this.setCardEntity(entity.getCardEntity());
		this.setEntityField(entity.getEntityField());
		this.setOperator(entity.getOperator());
		this.setFieldValue(entity.getFieldValue());
		this.setIsNew(entity.getIsNew());
	}

}

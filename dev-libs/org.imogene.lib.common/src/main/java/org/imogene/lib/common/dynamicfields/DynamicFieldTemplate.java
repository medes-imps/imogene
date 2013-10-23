package org.imogene.lib.common.dynamicfields;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.imogene.lib.common.entity.ImogActorImpl;
import org.imogene.lib.common.entity.ImogBeanImpl;

/**
 * ImogBean implementation for the entity DynamicField_Template
 * @author MEDES-IMPS
 */
@Entity
@Table(name = "dynamicfield_template")
public class DynamicFieldTemplate extends ImogBeanImpl {

	private static final long serialVersionUID = 95662106198955441L;

	/* Identification group fields */
	private String fieldName;
	private String fieldType;
	private String parameters;
	private String formType;
	@ManyToOne
	private ImogActorImpl templateCreator;
	private String description;
	private String reason;
	private Boolean isDefault;

	/* Properties group fields */
	private Boolean requiredValue;
	private Integer fieldPosition;
	private String minimumValue;
	private String maximumValue;
	private String defaultValue;

	/* Administration group fields */
	private Boolean allUsers;
	private Boolean isActivated;

	/* Getters and Setters for Identification group fields */

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String value) {
		fieldName = value;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String value) {
		fieldType = value;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String value) {
		parameters = value;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String value) {
		formType = value;
	}

	public ImogActorImpl getTemplateCreator() {
		return templateCreator;
	}

	public void setTemplateCreator(ImogActorImpl value) {
		templateCreator = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String value) {
		description = value;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String value) {
		reason = value;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean value) {
		isDefault = value;
	}

	/* Getters and Setters for Properties group fields */

	public Boolean getRequiredValue() {
		return requiredValue;
	}

	public void setRequiredValue(Boolean value) {
		requiredValue = value;
	}

	public Integer getFieldPosition() {
		return fieldPosition;
	}

	public void setFieldPosition(Integer value) {
		fieldPosition = value;
	}

	public String getMinimumValue() {
		return minimumValue;
	}

	public void setMinimumValue(String value) {
		minimumValue = value;
	}

	public String getMaximumValue() {
		return maximumValue;
	}

	public void setMaximumValue(String value) {
		maximumValue = value;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String value) {
		defaultValue = value;
	}

	/* Getters and Setters for Administration group fields */

	public Boolean getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(Boolean value) {
		allUsers = value;
	}

	public Boolean getIsActivated() {
		return isActivated;
	}

	public void setIsActivated(Boolean value) {
		isActivated = value;
	}

}

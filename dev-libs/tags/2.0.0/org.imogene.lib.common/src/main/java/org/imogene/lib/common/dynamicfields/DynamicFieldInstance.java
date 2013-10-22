package org.imogene.lib.common.dynamicfields;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.imogene.lib.common.entity.ImogBeanImpl;

/**
 * ImogBean implementation for the entity DynamicField_Instance
 * @author MEDES-IMPS
 */
@Entity
@Table(name = "dynamicfield_instance")
public class DynamicFieldInstance extends ImogBeanImpl {

	private static final long serialVersionUID = -7103436618299607491L;

	/* Identification group fields */

	@ManyToOne
	private DynamicFieldTemplate fieldTemplate;

	private String fieldValue;

	/* Getters and Setters for Identification group fields */

	public DynamicFieldTemplate getFieldTemplate() {
		return fieldTemplate;
	}

	public void setFieldTemplate(DynamicFieldTemplate value) {
		fieldTemplate = value;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String value) {
		fieldValue = value;
	}

}

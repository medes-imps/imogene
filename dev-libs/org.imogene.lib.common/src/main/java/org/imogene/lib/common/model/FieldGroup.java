package org.imogene.lib.common.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.imogene.lib.common.entity.ImogBeanImpl;

/**
 * ImogBean implementation for the entity FieldGroup
 * 
 * @author MEDES-IMPS
 */
@Entity
@Table(name = "imog_fieldgroup")
public class FieldGroup extends ImogBeanImpl {

	private static final long serialVersionUID = 599981452943132931L;

	/* Description group fields */

	private String name;

	@ManyToOne
	private CardEntity entity;

	/**
	 * Constructor
	 */
	public FieldGroup() {
	}

	/* Getters and Setters for Description group fields */

	public String getName() {
		return name;
	}

	public void setName(String value) {
		name = value;
	}

	public CardEntity getEntity() {
		return entity;
	}

	public void setEntity(CardEntity value) {
		entity = value;
	}

}

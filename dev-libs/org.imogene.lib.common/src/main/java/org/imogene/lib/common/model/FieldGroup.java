package org.imogene.lib.common.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.imogene.lib.common.entity.ImogEntityImpl;

/**
 * ImogBean implementation for the entity FieldGroup
 * 
 * @author MEDES-IMPS
 */
@Entity
@Table(name = "imog_fieldgroup")
public class FieldGroup extends ImogEntityImpl {

	private static final long serialVersionUID = 599981452943132931L;

	/* Description group fields */

	private String name;

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

}

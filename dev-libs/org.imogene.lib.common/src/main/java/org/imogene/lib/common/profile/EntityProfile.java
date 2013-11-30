package org.imogene.lib.common.profile;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.imogene.lib.common.entity.ImogEntityImpl;
import org.imogene.lib.common.model.CardEntity;

/**
 * ImogBean implementation for the entity EntityProfile
 * 
 * @author MEDES-IMPS
 */
@Entity
@Table(name = "imog_entityprofile")
public class EntityProfile extends ImogEntityImpl {

	private static final long serialVersionUID = 7405736400479451159L;

	/* Description group fields */

	@ManyToOne
	@JoinColumn(name = "entityProfilesProfile_id")
	private Profile profile;

	@ManyToOne
	private CardEntity entity;

	private Boolean create;

	private Boolean directAccess;

	private Boolean delete;

	private Boolean export;

	/**
	 * Constructor
	 */
	public EntityProfile() {
	}

	/* Getters and Setters for Description group fields */

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile value) {
		profile = value;
	}

	public CardEntity getEntity() {
		return entity;
	}

	public void setEntity(CardEntity value) {
		entity = value;
	}

	public Boolean getCreate() {
		return create;
	}

	public void setCreate(Boolean value) {
		create = value;
	}

	public Boolean getDirectAccess() {
		return directAccess;
	}

	public void setDirectAccess(Boolean value) {
		directAccess = value;
	}

	public Boolean getDelete() {
		return delete;
	}

	public void setDelete(Boolean value) {
		delete = value;
	}

	public Boolean getExport() {
		return export;
	}

	public void setExport(Boolean value) {
		export = value;
	}

}

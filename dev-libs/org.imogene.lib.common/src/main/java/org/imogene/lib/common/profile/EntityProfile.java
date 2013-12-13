package org.imogene.lib.common.profile;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.imogene.lib.common.entity.ImogBeanImpl;
import org.imogene.lib.common.model.CardEntity;

/**
 * ImogBean implementation for the entity EntityProfile
 * 
 * @author MEDES-IMPS
 */
@Entity
@Table(name = "imog_entityprofile")
public class EntityProfile extends ImogBeanImpl {

	private static final long serialVersionUID = 7405736400479451159L;

	/* Description group fields */

	@ManyToOne
	@JoinColumn(name = "entityProfilesProfile_id")
	private Profile profile;

	@ManyToOne
	private CardEntity entity;

	private Boolean canCreate;

	private Boolean canDirectAccess;

	private Boolean canDelete;

	private Boolean canExport;

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
		return canCreate;
	}

	public void setCreate(Boolean value) {
		canCreate = value;
	}

	public Boolean getDirectAccess() {
		return canDirectAccess;
	}

	public void setDirectAccess(Boolean value) {
		canDirectAccess = value;
	}

	public Boolean getDelete() {
		return canDelete;
	}

	public void setDelete(Boolean value) {
		canDelete = value;
	}

	public Boolean getExport() {
		return canExport;
	}

	public void setExport(Boolean value) {
		canExport = value;
	}

}

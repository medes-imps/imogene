package org.imogene.lib.common.profile;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.imogene.lib.common.entity.ImogEntityImpl;

/**
 * ImogBean implementation for the entity Profile
 * 
 * @author MEDES-IMPS
 */
@Entity
@Table(name = "imog_profile")
public class Profile extends ImogEntityImpl {

	private static final long serialVersionUID = -1146782351391973857L;

	/* Description group fields */

	private String name;

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = false)
	@JoinColumn(name = "entityProfilesProfile_id")
	private List<EntityProfile> entityProfiles;

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = false)
	@JoinColumn(name = "fieldGroupProfilesProfile_id")
	private List<FieldGroupProfile> fieldGroupProfiles;

	/**
	 * Constructor
	 */
	public Profile() {
		entityProfiles = new ArrayList<EntityProfile>();
		fieldGroupProfiles = new ArrayList<FieldGroupProfile>();
	}

	/* Getters and Setters for Description group fields */

	public String getName() {
		return name;
	}

	public void setName(String value) {
		name = value;
	}

	public List<EntityProfile> getEntityProfiles() {
		return entityProfiles;
	}

	public void setEntityProfiles(List<EntityProfile> value) {
		entityProfiles = value;
	}

	/**
	 * @param param the EntityProfile to add to the entityProfiles collection
	 */
	public void addToentityProfiles(EntityProfile param) {
		param.setProfile(this);
		entityProfiles.add(param);
	}

	/**
	 * @param param the EntityProfile to remove from the entityProfiles collection
	 */
	public void removeFromentityProfiles(EntityProfile param) {
		param.setProfile(null);
		entityProfiles.remove(param);
	}

	public List<FieldGroupProfile> getFieldGroupProfiles() {
		return fieldGroupProfiles;
	}

	public void setFieldGroupProfiles(List<FieldGroupProfile> value) {
		fieldGroupProfiles = value;
	}

	/**
	 * @param param the FieldGroupProfile to add to the fieldGroupProfiles collection
	 */
	public void addTofieldGroupProfiles(FieldGroupProfile param) {
		param.setProfile(this);
		fieldGroupProfiles.add(param);
	}

	/**
	 * @param param the FieldGroupProfile to remove from the fieldGroupProfiles collection
	 */
	public void removeFromfieldGroupProfiles(FieldGroupProfile param) {
		param.setProfile(null);
		fieldGroupProfiles.remove(param);
	}

}

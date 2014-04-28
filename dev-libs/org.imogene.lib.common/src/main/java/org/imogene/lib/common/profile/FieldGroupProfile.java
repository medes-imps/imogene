package org.imogene.lib.common.profile;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.imogene.lib.common.entity.ImogBeanImpl;
import org.imogene.lib.common.model.CardEntity;
import org.imogene.lib.common.model.FieldGroup;

/**
 * ImogBean implementation for the entity FieldGroupProfile
 * 
 * @author MEDES-IMPS
 */
@Entity
@Table(name = "imog_fieldgroupprofile")
public class FieldGroupProfile extends ImogBeanImpl {

	private static final long serialVersionUID = 6409432097510291085L;

	/* Description group fields */

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "fieldGroupProfilesProfile_id")
	private Profile profile;

	@ManyToOne
	private CardEntity cardEntity;

	@ManyToOne(cascade = CascadeType.DETACH)
	private FieldGroup fieldGroup;

	@Column(name = "imogread")
	private Boolean read;

	@Column(name = "imogwrite")
	private Boolean write;

	@Column(name = "imogexport")
	private Boolean export;

	/**
	 * Constructor
	 */
	public FieldGroupProfile() {
	}

	/* Getters and Setters for Description group fields */

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile value) {
		profile = value;
	}

	public CardEntity getCardEntity() {
		return cardEntity;
	}

	public void setCardEntity(CardEntity cardEntity) {
		this.cardEntity = cardEntity;
	}

	public FieldGroup getFieldGroup() {
		return fieldGroup;
	}

	public void setFieldGroup(FieldGroup value) {
		fieldGroup = value;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean value) {
		read = value;
	}

	public Boolean getWrite() {
		return write;
	}

	public void setWrite(Boolean value) {
		write = value;
	}

	public Boolean getExport() {
		return export;
	}

	public void setExport(Boolean value) {
		export = value;
	}

}

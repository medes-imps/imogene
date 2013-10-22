package org.imogene.lib.common.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.imogene.lib.common.dynamicfields.DynamicFieldInstance;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ImogEntityImpl extends ImogBeanImpl implements ImogEntity {

	private static final long serialVersionUID = -6376685503245500817L;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "forminstance_id")
	private List<DynamicFieldInstance> dynamicFieldValues;

	@Override
	public List<DynamicFieldInstance> getDynamicFieldValues() {
		return dynamicFieldValues;
	}

	@Override
	public void setDynamicFieldValues(List<DynamicFieldInstance> value) {
		dynamicFieldValues = value;
	}

}

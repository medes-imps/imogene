package org.imogene.lib.common.role;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.imogene.lib.common.entity.ImogBeanImpl;

/**
 * Role that can be assigned to an actor and that defines its user privileges
 * @author Medes-IMPS
 */
@Entity
@Table(name = "imog_role")
public class ImogRole extends ImogBeanImpl {

	private static final long serialVersionUID = -4397277804807687834L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String value) {
		name = value;
	}

}

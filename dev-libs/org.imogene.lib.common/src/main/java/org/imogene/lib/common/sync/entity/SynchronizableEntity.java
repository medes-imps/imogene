package org.imogene.lib.common.sync.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.imogene.lib.common.entity.ImogBeanImpl;

/**
 * Synchronizable Entity that can be assigned to an actor. A Synchronizable
 * Entity is an entity that can be synchronized with remote applications
 * @author Medes-IMPS
 */
@Entity
@Table(name = "sync_entity")
public class SynchronizableEntity extends ImogBeanImpl {

	private static final long serialVersionUID = 6937807544902835211L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String value) {
		name = value;
	}

}

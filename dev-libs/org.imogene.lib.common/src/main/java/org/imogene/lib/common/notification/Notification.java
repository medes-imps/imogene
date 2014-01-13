package org.imogene.lib.common.notification;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.imogene.lib.common.entity.ImogActorImpl;
import org.imogene.lib.common.entity.ImogBeanImpl;

/**
 * ImogBean implementation for the entity Notification
 * 
 * @author MEDES-IMPS
 */
@Entity
public class Notification extends ImogBeanImpl {

	private static final long serialVersionUID = 8575779927881293752L;

	/* Description group fields */

	private String name;

	private String method;

	private String title;

	private String formType;

	private String operation;

	private String message;

	/* Recipients group fields */

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "Notification_Actors", joinColumns = @JoinColumn(name = "notification_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "id"))
	private List<ImogActorImpl> actorRecipients;

	/* Getters and Setters for Description group fields */

	public String getName() {
		return name;
	}

	public void setName(String value) {
		name = value;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String value) {
		method = value;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String value) {
		title = value;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String value) {
		formType = value;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String value) {
		operation = value;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String value) {
		message = value;
	}

	/* Getters and Setters for Recipients group fields */

	public List<ImogActorImpl> getActorRecipients() {
		return actorRecipients;
	}

	public void setActorRecipients(List<ImogActorImpl> value) {
		actorRecipients = value;
	}

	/**
	 * @param param the AppliUser to add to the actorRecipients collection
	 */
	public void addToactorRecipients(ImogActorImpl param) {
		actorRecipients.add(param);
	}

	/**
	 * @param param the AppliUser to remove from the actorRecipients collection
	 */
	public void removeFromactorRecipients(ImogActorImpl param) {
		actorRecipients.remove(param);
	}

}

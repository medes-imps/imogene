package org.imogene.model.validation.constraints;

import org.eclipse.emf.validation.AbstractModelConstraint;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FieldGroup;
import org.imogene.model.core.Project;

/**
 * 
 * @author MEDES-IMPS
 * 
 */
public abstract class AbstractMedanyModelConstraint extends AbstractModelConstraint {

	/**
	 * Format message with target property
	 * 
	 * @param message The message template
	 * @param entity The target entity
	 * @return The formated message
	 */
	public String formatMessage(String message, FieldEntity entity) {
		String newMessage = message;
		newMessage = newMessage.replace("%FIELD_NAME%", entity.getName());
		return newMessage;
	}

	/**
	 * Format message with target property
	 * 
	 * @param message The message template
	 * @param group The target group
	 * @return The formated message
	 */
	public String formatMessage(String message, FieldGroup group) {
		String newMessage = message;
		newMessage = newMessage.replace("%GROUP_NAME%", group.getName());
		return newMessage;
	}

	public String formatMessage(String message, CardEntity card, FieldEntity field) {
		String newMessage = message;
		newMessage = newMessage.replace("%FIELD_NAME%", field.getName());
		newMessage = newMessage.replace("%ENTITY_NAME%", card.getName());
		return newMessage;
	}

	public String formatMessage(String message, CardEntity card, FieldGroup group) {
		String newMessage = message;
		newMessage = newMessage.replace("%GROUP_NAME%", group.getName());
		newMessage = newMessage.replace("%ENTITY_NAME%", card.getName());
		return newMessage;
	}

	/**
	 * Format message with target property
	 * 
	 * @param message The message template
	 * @param card The target CardEntity
	 * @return The formated message
	 */
	public String formatMessage(String message, CardEntity card) {
		String newMessage = message;
		newMessage = newMessage.replace("%ENTITY_NAME%", card.getName());
		return newMessage;
	}

	/**
	 * Format message with target property
	 * 
	 * @param message The message template
	 * @param card The target Project
	 * @return The formated message
	 */
	public String formatMessage(String message, Project project) {
		String newMessage = message;
		newMessage = newMessage.replace("%PROJECT_NAME%", project.getName());
		return newMessage;
	}
}

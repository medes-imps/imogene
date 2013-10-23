package org.imogene.notifier.server.services;

import java.util.List;
import java.util.Vector;

import org.imogene.lib.common.constants.NotificationConstants;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.notifier.server.NotificationInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Notification by email.
 * 
 * @author Medes-IMPS
 */
public class EmailNotification implements NotificationService {

	private static String FROM = "contact@imogene.org";

	@Autowired
	@Qualifier(value = "mailSender")
	private JavaMailSender mailSender;

	@Override
	public void sendNotification(NotificationInstance notification) {

		List<SimpleMailMessage> messages = new Vector<SimpleMailMessage>();
		for (ImogActor actor : notification.getUserRecipients()) {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(FROM);
			message.setSubject(notification.getTitle());
			message.setText(notification.getMessage());
			message.setTo(actor.getNotificationData(NotificationConstants.EMAIL_NOTIF));
			messages.add(message);
		}
		mailSender.send(messages.toArray(new SimpleMailMessage[messages.size()]));
	}

	/**
	 * Set the mail sender to use
	 * 
	 * @param mailSender the mailSender to set
	 */
	public void setMailSender(JavaMailSender pMailSender) {
		mailSender = pMailSender;
	}

}

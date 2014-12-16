package org.imogene.web.server.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.smtp.SMTPTransport;

public class SMTPSender {

	private static Logger logger = LoggerFactory.getLogger(SMTPSender.class);

	private String mailHost = "dumbo.medes.fr";

	private String fromAddress = "educadom@medes.fr";

	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public void sendMessage(String subject, String message, String[] addresses) throws Exception {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", mailHost);
		Session session = Session.getInstance(props, null);

		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(fromAddress));

		for (String address : addresses) {
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
		}

		msg.setSubject(subject);
		msg.setText(message);
		// msg.setHeader("X-Mailer", "Tov Are's program");
		msg.setSentDate(new Date());
		SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
		t.connect();
		t.sendMessage(msg, msg.getAllRecipients());
		logger.debug("Response: " + t.getLastServerResponse());
		t.close();
	}
}

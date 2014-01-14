package org.imogene.client.bugreport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.mail.Authenticator;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.preference.IPreferenceStore;
import org.imogene.client.Activator;
import org.imogene.client.i18n.Messages;

public class BugReportJob extends Job {

	private IPreferenceStore mPreferences;
	private String mMessageBody;

	public BugReportJob(String body) {
		super(Messages.bugreport_send_dialog_title);
		mMessageBody = body;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		monitor.beginTask(Messages.bugreport_send_dialog_sending, IProgressMonitor.UNKNOWN);
		mPreferences = Activator.getDefault().getPreferenceStore();

		File logs = null;

		try {
			MimeMessage message = new MimeMessage(getSession());
			message.setFrom();
			message.addRecipient(RecipientType.TO,
					new InternetAddress(mPreferences.getString(IBugReportConstants.SMTP_DESTINATION)));
			message.setSubject(Messages.bugreport_send_dialog_title);

			Multipart multipart = new MimeMultipart();

			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(mMessageBody);
			multipart.addBodyPart(messageBodyPart);

			logs = getLogs();
			if (logs != null && logs.exists()) {
				MimeBodyPart attachedBodyPart = new MimeBodyPart();
				attachedBodyPart.setDisposition(Part.ATTACHMENT);
				attachedBodyPart.attachFile(logs);
				attachedBodyPart.setFileName("logs.zip"); //$NON-NLS-1$
				attachedBodyPart.setHeader("Content-Type", "application/zip"); //$NON-NLS-1$ //$NON-NLS-2$
				multipart.addBodyPart(attachedBodyPart);
			}

			message.setContent(multipart);

			Transport.send(message);
			return Status.OK_STATUS;
		} catch (Exception e) {
			return new Status(Status.ERROR, Activator.PLUGIN_ID, Messages.bugreport_send_error, e);
		} finally {
			if (logs != null) {
				logs.delete();
			}
			monitor.done();
		}
	}

	private File getLogs() {
		boolean logDatabase = mPreferences.getBoolean(IBugReportConstants.LOG_DATABASE);
		boolean logRuntime = mPreferences.getBoolean(IBugReportConstants.LOG_RUNTIME);
		boolean logServer = mPreferences.getBoolean(IBugReportConstants.LOG_SERVER);
		boolean logSync = mPreferences.getBoolean(IBugReportConstants.LOG_SYNC);
		boolean logWebApp = mPreferences.getBoolean(IBugReportConstants.LOG_WEBAPP);

		if (!(logDatabase || logRuntime || logServer || logSync || logWebApp)) {
			return null;
		}

		try {
			File zipFile = File.createTempFile("log", ".zip"); //$NON-NLS-1$ //$NON-NLS-2$
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));

			File log = null;
			if (logDatabase) {
				log = new File(System.getProperty("derby.system.home"), "/derby.log"); //$NON-NLS-1$ //$NON-NLS-2$
				addEntry(zos, "myderby.log", log); //$NON-NLS-1$
			}

			if (logServer) {
				log = new File(System.getProperty("jetty.home") + "/logs", "jetty.log"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				addEntry(zos, "myjetty.log", log); //$NON-NLS-1$
			}

			if (logWebApp) {
				log = new File(System.getProperty("jetty.home") + "/logs", "TBLabPom.log"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				addEntry(zos, "mywebapp.log", log); //$NON-NLS-1$
			}

			if (logSync) {
				log = new File(System.getProperty("sync.home") + "/logs", "ExtbSync.log"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				addEntry(zos, "mysync.log", log); //$NON-NLS-1$
			}

			if (logRuntime) {
				log = Platform.getLogFileLocation().toFile();
				addEntry(zos, "myruntime.log", log); //$NON-NLS-1$
			}

			zos.close();

			return zipFile;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Session getSession() {
		boolean auth = mPreferences.getBoolean(IBugReportConstants.SMTP_AUTH);

		Properties properties = new Properties();
		properties.put("mail.smtp.connectiontimeout", "60000"); //$NON-NLS-1$ //$NON-NLS-2$
		properties.put("mail.smtp.timeout", "60000"); //$NON-NLS-1$ //$NON-NLS-2$
		properties.put("mail.smtp.host", mPreferences.getString(IBugReportConstants.SMTP_HOST)); //$NON-NLS-1$
		properties.put("mail.smtp.port", Integer.toString(mPreferences.getInt(IBugReportConstants.SMTP_PORT))); //$NON-NLS-1$
		properties.put("mail.smtp.auth", Boolean.toString(auth)); //$NON-NLS-1$
		properties.put("mail.smtp.starttls.enable", Boolean.toString(mPreferences.getBoolean(IBugReportConstants.SMTP_TLS))); //$NON-NLS-1$

		Authenticator authenticator = null;

		if (auth) {
			authenticator = new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					String user = mPreferences.getString(IBugReportConstants.SMTP_USER);
					String password = mPreferences.getString(IBugReportConstants.SMTP_PASSWORD);
					return new PasswordAuthentication(user, password);
				}
			};
		}

		return Session.getDefaultInstance(properties, authenticator);
	}

	private static void addEntry(ZipOutputStream zos, String name, File file) {
		if (file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(file);

				zos.putNextEntry(new ZipEntry(name));

				int length;
				byte[] buffer = new byte[1024];
				while ((length = fis.read(buffer)) > 0) {
					zos.write(buffer, 0, length);
				}

				fis.close();
				zos.closeEntry();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

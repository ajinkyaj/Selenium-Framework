package com.utilfactory;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailHelpers {
	private static void send(final String from, final String password, String to, String sub, String msg) {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(sub);

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(msg);

			Multipart multipart = new MimeMultipart();

			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			String filename = System.getProperty("user.dir") + "\\reports\\AutomationReport.html";

			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			Transport.send(message);
			System.out.println("Mail Sent Successfully!");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public static void emailReport() {
		String from = "upadhyayparitosh9049320141@gmail.com";
		String password = "32579789";
		String to = "pupadhyay@spiderlogic.com";
		String subject = "ALDI DFFR Test Automation Report - " + SeleniumHelpers.getCurrentTimeStamp();
		String body = "To view the report, please download the attached file and open it in any web browser.";
		
		send(from, password, to, subject, body);
	}
}

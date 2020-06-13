package Server;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

	public enum MessageType {
		NewTest, NewTimeExtensionRequest, TimeExtensionRequestResult, TestFinished
	}

	private Properties props;
	private Session session;
	private MimeMessage message;

	private final String HSTS_EmailAddressName = "highschooltestsystem@gmail.com";
	private final String HSTS_EmailAddressPassword = "HSTS147852";

	private final String NewTest_subject = "New test has been scheduled";
	private final String TestFinished_subject = "Test has been finished";
	private final String NewTimeExtensionRequest_subject = "New time extension request";
	private final String TimeExtensionRequestResult_subject = "Time extension request answered";

	private final String NewTest_body = "You Have test, study hard and don't give up. remember to check the time to see when is your exam";
	private final String TestFinished_body = "You finished the test, Hope it turn out hiw you wanted";
	private final String NewTimeExtensionRequest_body = " An extension request has been sent";
	private final String TimeExtensionRequestResult_body = "The result for the time extention has arrived";

	private final String Signature = "\n\nThis message was sent to you automatically. All rights reserved. HTST 2020  \u00a9";

	public SendEmail() {
		// Get properties object
		props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		// get Session
		session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(HSTS_EmailAddressName, HSTS_EmailAddressPassword);
			}
		});

		// Initialize Message object
		message = new MimeMessage(session);

		//System.out.println("Mail API: initialized\n");
	}

	public void sendMessage(String to, MessageType type) {
		String sub, msg;

		// Debug mode
		return;

//		try {
//			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//			
//			switch (type) {
//			case NewTest:
//				sub = NewTest_subject;
//				msg = NewTest_body;
//			case NewTimeExtensionRequest:
//				sub = NewTimeExtensionRequest_subject;
//				msg = NewTimeExtensionRequest_body;
//				break;
//			case TestFinished:
//				sub = TestFinished_subject;
//				msg = TestFinished_body;
//				break;
//			case TimeExtensionRequestResult:
//				sub = TimeExtensionRequestResult_subject;
//				msg = TimeExtensionRequestResult_body;
//				break;
//			default:
//				return;
//			}
//
//			// build message
//			message.setSubject(sub);
//			message.setText(msg + Signature);
//
//			// send message
//			Transport.send(message);
//
//			// System.out.println("message sent successfully");
//		} catch (MessagingException e) {
//			throw new RuntimeException(e);
//		}
	}
}

package com.github.signed.sandbox.mail;

import j2html.TagCreator;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.junit.Test;

import static j2html.TagCreator.document;
import static j2html.TagCreator.html;

public class SamplesTest {

	@Test
	public void htmlDocumentBuilder() {
		String htmlAsString = htmlMessageBody();

		System.out.println(htmlAsString);
	}

	@Test
	public void sendIt() throws EmailException {
		String from = "from@example.org";
		String toEmail = "to@example.org";

		SmtpConfiguration smtpConfiguration = SmtpConfiguration.gmail();
		HtmlEmail email = new HtmlEmail();
		email.setHostName(smtpConfiguration.hostName);
		email.setSmtpPort(smtpConfiguration.port);
		email.setSSLOnConnect(true);
		email.setStartTLSEnabled(true);
		email.setStartTLSRequired(true);
		email.setAuthenticator(new DefaultAuthenticator("login", "password"));
		email.setFrom(from, "Source");
		email.addTo(toEmail, "Destination");
		email.setSubject("commons-email");
		email.setHtmlMsg(htmlMessageBody());
		email.setTextMsg("Your email client does not support HTML messages");

		email.send();
	}

	private String htmlMessageBody() {
		return document().render() + "\n" +
				html(TagCreator.body(
						TagCreator.text("Hello link http:// example.org"),
						TagCreator.text("Hello link http://<span/>example.org")
				)).renderFormatted();
	}

}

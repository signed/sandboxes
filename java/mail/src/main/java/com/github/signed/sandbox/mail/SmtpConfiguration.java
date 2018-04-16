package com.github.signed.sandbox.mail;

class SmtpConfiguration {

	static SmtpConfiguration webde() {
		return new SmtpConfiguration("smtp.web.de", 587);
	}

	static SmtpConfiguration gmail() {
		return new SmtpConfiguration("smtp.gmail.com", 465);
	}

	final String hostName;
	final int port;

	private SmtpConfiguration(String hostName, int port) {
		this.hostName = hostName;
		this.port = port;
	}
}

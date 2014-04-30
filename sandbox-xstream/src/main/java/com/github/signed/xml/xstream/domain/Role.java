package com.github.signed.xml.xstream.domain;

import static java.text.MessageFormat.format;

public enum Role {
	ReserveProvider("A04"), TransmissionSystemOperator("A27");
	
	private final String xmlRepresentation;

	private Role(String xmlRepresentation) {
		this.xmlRepresentation = xmlRepresentation;
	}
	
	public String xmlRepresentation() {
		return this.xmlRepresentation;
	}

	public static Role valueOfXmlRepresentation(String attribute) {
		for(Role role : values()) {
			if(role.xmlRepresentation().equals(attribute)) {
				return role;
			}
		}
		throw new RuntimeException(format("Role known for ''{0}''", attribute));
	}
}
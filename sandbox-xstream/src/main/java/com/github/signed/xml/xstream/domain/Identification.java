package com.github.signed.xml.xstream.domain;

public class Identification {

	private final EicCode code;
	private final String codeScheme = "A01";

	public Identification(EicCode code) {
		this.code = code;
	}
	
	public EicCode code() {
		return code;
	}
	
	public String codingScheme() {
		return codeScheme;
	}
}
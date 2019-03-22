package com.github.signed.xml.xstream.domain;


import com.github.signed.xml.xstream.domain.EicCode;
import com.thoughtworks.xstream.converters.SingleValueConverter;

public class EicCodeConverter implements SingleValueConverter {
	@Override
	public boolean canConvert(Class type) {
		return EicCode.class.equals(type);
	}

	@Override
	public String toString(Object obj) {
		return ((EicCode)obj).asString();
	}

	@Override
	public Object fromString(String str) {
		return new EicCode(str);
	}
}
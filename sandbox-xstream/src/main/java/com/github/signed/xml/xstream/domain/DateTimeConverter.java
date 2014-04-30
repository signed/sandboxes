package com.github.signed.xml.xstream.domain;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.github.signed.xml.xstream.converter.SingleAttributeConverter;


public class DateTimeConverter implements SingleAttributeConverter<DateTime> {
	static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(DateTimeZone.UTC);

	private final String attributeName;

	public DateTimeConverter(String attributeName) {
		this.attributeName = attributeName;
	}

	@Override
	public String attributeName() {
		return attributeName;
	}

	@Override
	public String toString(DateTime time) {
		return time.toString(DateTimeConverter.formatter);
	}

	@Override
	public DateTime fromString(String str) {
		return formatter.parseDateTime(str);
	}

	@Override
	public boolean canConvert(Class<?> type) {
		return DateTime.class.equals(type);
	}
}
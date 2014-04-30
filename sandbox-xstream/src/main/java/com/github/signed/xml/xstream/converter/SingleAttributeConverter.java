package com.github.signed.xml.xstream.converter;

public interface SingleAttributeConverter<T> {

	String attributeName();

	String toString(T obj);

	T fromString(String attributeValue);

	boolean canConvert(Class<?> type);
}
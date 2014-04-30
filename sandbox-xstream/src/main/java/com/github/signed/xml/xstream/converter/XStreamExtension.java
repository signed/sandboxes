package com.github.signed.xml.xstream.converter;

import com.github.signed.xml.xstream.domain.AcknowledgementDocument;
import com.github.signed.xml.xstream.domain.DateTimeConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;

public class XStreamExtension extends XStream {
    public void registerConverter(SingleAttributeConverter<?> converter) {
        SingleAttributeConverterWrapper singleAttribute = new SingleAttributeConverterWrapper(converter);
        this.registerConverter((Converter) singleAttribute);
    }

    public void registerLocalConverter(Class<AcknowledgementDocument> definedIn, String fieldName, DateTimeConverter converter) {
        SingleAttributeConverterWrapper singleAttribute = new SingleAttributeConverterWrapper(converter);
        this.registerLocalConverter(definedIn, fieldName, singleAttribute);
    }
}
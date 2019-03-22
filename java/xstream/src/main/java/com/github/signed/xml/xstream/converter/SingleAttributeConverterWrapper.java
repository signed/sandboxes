package com.github.signed.xml.xstream.converter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ErrorReporter;
import com.thoughtworks.xstream.converters.ErrorWriter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

final class SingleAttributeConverterWrapper<T> implements Converter, SingleAttributeConverter<T>, ErrorReporter {
    public final SingleAttributeConverter<T> wrapped;

    public SingleAttributeConverterWrapper(SingleAttributeConverter<T> wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
        return wrapped.canConvert(type);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        String value = wrapped.toString((T) source);
        writer.addAttribute(wrapped.attributeName(), value);
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        String attribute = reader.getAttribute(wrapped.attributeName());
        return wrapped.fromString(attribute);
    }

    public void appendErrors(ErrorWriter errorWriter) {
        errorWriter.add("wrapped-converter", wrapped.getClass().getName());
        if (wrapped instanceof ErrorReporter) {
            ((ErrorReporter) wrapped).appendErrors(errorWriter);
        }
    }

    @Override
    public String attributeName() {
        return wrapped.attributeName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public String toString(Object obj) {
        return wrapped.toString((T) obj);
    }

    @Override
    public T fromString(String str) {
        return wrapped.fromString(str);
    }
}
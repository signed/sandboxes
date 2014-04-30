package com.github.signed.xml.xstream.converter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class FromSingleValueToAttributeConverter implements Converter{
    
    private final SingleValueConverter singleValueConverter;

    public FromSingleValueToAttributeConverter(SingleValueConverter singleValueConverter) {
        this.singleValueConverter = singleValueConverter;
        
    }

    @Override
    public boolean canConvert(Class type) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        // TODO Auto-generated method stub
        return null;
    }

}

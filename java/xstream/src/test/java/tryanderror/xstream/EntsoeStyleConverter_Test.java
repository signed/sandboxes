package tryanderror.xstream;

import org.junit.Test;

import com.github.signed.xml.xstream.converter.ToAttributedValueConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.mapper.Mapper;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class EntsoeStyleConverter_Test {
    
    public static class AClass{
        public String value = "This should be in an attribute";
        public CompositeClass  anotherValue = new CompositeClass();
    }
    
    public static class CompositeClass {
        public Integer value = 42;
    }
    
    public static class ToSingleValue implements SingleValueConverter{

        @Override
        public boolean canConvert(Class type) {
            return CompositeClass.class == type;
        }

        @Override
        public String toString(Object obj) {
            CompositeClass cCompositeClass = (CompositeClass)obj;
            return cCompositeClass.value.toString();
        }

        @Override
        public Object fromString(String str) {
            return new CompositeClass();
        }
        
    }
    
    @Test
    public void testName() throws Exception {
        XStream xStream = new XStream();
        xStream.registerConverter(new ToSingleValue());
        
        Mapper mapper = xStream.getMapper();
        ReflectionProvider reflectionProvider = xStream.getReflectionProvider();
        ConverterLookup converterLookup = xStream.getConverterLookup();
        String valueField = null;
        ToAttributedValueConverter converter = new ToAttributedValueConverter(AClass.class, mapper, reflectionProvider, converterLookup, valueField );
        xStream.registerConverter(converter);
        System.out.println(xStream.toXML(new AClass()));
    }
    
    protected MapperWrapper wrapMapper(MapperWrapper next) {
        return next;
    }
}
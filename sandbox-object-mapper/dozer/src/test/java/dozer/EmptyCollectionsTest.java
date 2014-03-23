package dozer;

import beans.ClassWithCollections;
import beans.IntegerBean;
import beans.StringBean;
import org.dozer.CustomFieldMapper;
import org.dozer.DozerBeanMapper;
import org.dozer.classmap.ClassMap;
import org.dozer.fieldmap.FieldMap;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EmptyCollectionsTest {

    @Test
    public void testName() throws Exception {
        ClassWithCollections classWithCollections = new ClassWithCollections();
        ArrayList<StringBean> stringBeans = new ArrayList<StringBean>();
        stringBeans.add(stringBean("one"));
        stringBeans.add(stringBean("two"));
        stringBeans.add(stringBean("tree"));
        classWithCollections.setStringBeans(stringBeans);
        IntegerBean integerBean = new IntegerBean();
        integerBean.setInteger(45);
        classWithCollections.setIntegerBean(integerBean);

        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.setCustomFieldMapper(new MyFieldMapper());

        ClassWithCollections mapped = mapper.map(classWithCollections, ClassWithCollections.class);

        assertThat(mapped.getStringBeans().size(), is(0));
        assertThat(mapped.getIntegerBean().getInteger(), is( 45));
        assertThat(mapped.getStringBeansSet().size(), is(0));
    }

    public static class MyFieldMapper implements CustomFieldMapper {

        @Override
        public boolean mapField(Object source, Object destination, Object sourceFieldValue, ClassMap classMap, FieldMap fieldMapping) {
            Class<?> sourceFieldType = fieldMapping.getSrcFieldType(source.getClass());
            if (!Collection.class.isAssignableFrom(sourceFieldType)) {
                return false;
            }

            Class<?> destinationFieldType = fieldMapping.getDestFieldType(destination.getClass());

            if (List.class.isAssignableFrom(destinationFieldType)) {
                writeInto(destination, new ArrayList(), fieldMapping);
                return true;
            }

            if (Set.class.isAssignableFrom(destinationFieldType)) {
                writeInto(destination, new HashSet(), fieldMapping);
                return true;
            }

            if(Map.class.isAssignableFrom(destinationFieldType)) {
                writeInto(destination, new HashMap(), fieldMapping);
                return true;
            }


            throw new RuntimeException("unknown collection");
        }

        private void writeInto(Object destination, Object object, FieldMap fieldMapping) {
            fieldMapping.writeDestValue(destination, object);
        }
    }

    private StringBean stringBean(String one) {
        StringBean stringBean = new StringBean();
        stringBean.setString(one);
        return stringBean;
    }
}

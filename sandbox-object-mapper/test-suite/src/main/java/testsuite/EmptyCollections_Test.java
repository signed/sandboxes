package testsuite;

import beans.ClassWithCollections;
import beans.IntegerBean;
import beans.StringBean;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class EmptyCollections_Test {
    ClassWithCollections classWithCollections = new ClassWithCollections();

    @Before
    public void initializeClassWithCollections() {
        ArrayList<StringBean> stringBeans = new ArrayList<StringBean>();
        stringBeans.add(stringBean("one"));
        stringBeans.add(stringBean("two"));
        stringBeans.add(stringBean("tree"));
        classWithCollections.setStringBeans(stringBeans);
        IntegerBean integerBean = new IntegerBean();
        integerBean.setInteger(45);
        classWithCollections.setIntegerBean(integerBean);
    }

    @Test
    public void stripCollectionsOfContent() throws Exception {
        ClassWithCollections mapped = emptyCollectionsWhileMapping(classWithCollections);

        assertThat(mapped.getStringBeans().size(), is(0));
    }

    @Test
    public void mapTheNonCollectionProperties() throws Exception {
        ClassWithCollections mapped = emptyCollectionsWhileMapping(classWithCollections);

        assertThat(mapped.getIntegerBean().getInteger(), is(45));
    }

    @Test
    public void replaceNullReferencesWithEmptyCollection(){
        ClassWithCollections mapped = emptyCollectionsWhileMapping(classWithCollections);

        assertThat(mapped.getStringBeansSet().size(), is(0));
    }

    private StringBean stringBean(String one) {
        StringBean stringBean = new StringBean();
        stringBean.setString(one);
        return stringBean;
    }

    protected abstract ClassWithCollections emptyCollectionsWhileMapping(ClassWithCollections classWithCollections);
}

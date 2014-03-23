package testsuite;

import beans.ClassWithCollections;
import beans.IntegerBean;
import beans.StringBean;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class EmptyCollections_Test {

    @Test
    public void stripCollectionsOfContent() throws Exception {
        ClassWithCollections classWithCollections = new ClassWithCollections();
        ArrayList<StringBean> stringBeans = new ArrayList<StringBean>();
        stringBeans.add(stringBean("one"));
        stringBeans.add(stringBean("two"));
        stringBeans.add(stringBean("tree"));
        classWithCollections.setStringBeans(stringBeans);
        IntegerBean integerBean = new IntegerBean();
        integerBean.setInteger(45);
        classWithCollections.setIntegerBean(integerBean);

        ClassWithCollections mapped = emptyCollectionsWhileMapping(classWithCollections);

        assertThat(mapped.getStringBeans().size(), is(0));
        assertThat(mapped.getIntegerBean().getInteger(), is(45));
        assertThat(mapped.getStringBeansSet().size(), is(0));
    }

    private StringBean stringBean(String one) {
        StringBean stringBean = new StringBean();
        stringBean.setString(one);
        return stringBean;
    }

    protected abstract ClassWithCollections emptyCollectionsWhileMapping(ClassWithCollections classWithCollections);
}

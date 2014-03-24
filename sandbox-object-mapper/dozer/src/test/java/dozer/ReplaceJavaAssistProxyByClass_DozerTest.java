package dozer;

import beans.StringBean;
import javassist.JavasisstHelper;
import org.junit.Test;
import testsuite.ReplaceJavaAssistProxyByClass;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReplaceJavaAssistProxyByClass_DozerTest extends ReplaceJavaAssistProxyByClass {

    @Override
    protected ReplaceJavaAssistProxyByClass.BeanWithJavaAssistProxy mapped(ReplaceJavaAssistProxyByClass.BeanWithJavaAssistProxy beanWithJavaAssistProxy) {
        return beanWithJavaAssistProxy;
    }

    @Test
    public void initialValueIsNull() throws Exception {
        StringBean stringBean = JavasisstHelper.create(StringBean.class);
        assertThat(stringBean.getString(), nullValue());
    }

    @Test
    public void returnASetValue() throws Exception {
        StringBean stringBean = JavasisstHelper.create(StringBean.class);
        stringBean.setString("one");
        assertThat(stringBean.getString(), is("one"));
    }

    @Test
    public void play() throws Exception {
        StringBean stringBean = JavasisstHelper.create(StringBean.class);
        stringBean.setString("one");
        assertThat(stringBean.getString(), is("one"));
    }
}

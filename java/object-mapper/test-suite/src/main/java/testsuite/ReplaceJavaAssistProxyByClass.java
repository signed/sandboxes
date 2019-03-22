package testsuite;

import beans.StringBean;
import javassist.JavasisstHelper;
import org.junit.Test;

import static javassist.matchers.JavaAssistMatchers.proxy;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class ReplaceJavaAssistProxyByClass {

    public static class BeanWithJavaAssistProxy {

        private StringBean stringBean;

        public StringBean getStringBean() {
            return stringBean;
        }

        public void setStringBean(StringBean stringBean) {
            this.stringBean = stringBean;
        }
    }

    @Test
    public void mapToConcreteTypeInsteadOfProxy() throws Exception {
        BeanWithJavaAssistProxy beanWithJavaAssistProxy = new BeanWithJavaAssistProxy();
        StringBean stringBean = JavasisstHelper.create(StringBean.class);
        beanWithJavaAssistProxy.setStringBean(stringBean);

        assertThat(mapped(beanWithJavaAssistProxy).getStringBean(), not(proxy()));
    }

    protected abstract BeanWithJavaAssistProxy mapped(BeanWithJavaAssistProxy beanWithJavaAssistProxy);

}

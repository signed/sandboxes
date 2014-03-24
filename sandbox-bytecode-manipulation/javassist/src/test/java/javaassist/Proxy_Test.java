package javaassist;

import javassist.StringBean;
import org.junit.Test;

import static javaassist.JavassistHelper.create;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class Proxy_Test {

    @Test
    public void initialValueIsNull() throws Exception {
        StringBean stringBean = create(StringBean.class);
        assertThat(stringBean.getString(), nullValue());
    }

    @Test
    public void returnASetValue() throws Exception {
        StringBean stringBean = create(StringBean.class);
        stringBean.setString("one");
        assertThat(stringBean.getString(), is("one"));
    }

    @Test
    public void play() throws Exception {
        StringBean stringBean = create(StringBean.class);
        stringBean.setString("one");
        assertThat(stringBean.getString(), is("one"));
    }
}

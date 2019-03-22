package javassist.matchers;

import javassist.util.proxy.ProxyFactory;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class JavaAssistMatchers {
    public static Matcher<Object> proxy() {
        return new TypeSafeDiagnosingMatcher<Object>() {
            @Override
            protected boolean matchesSafely(Object item, Description mismatchDescription) {
                boolean isProxy = ProxyFactory.isProxyClass(item.getClass());
                if(!isProxy) {
                    mismatchDescription.appendText("was instance of ").appendValue(item.getClass());
                }
                return isProxy;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("instance of a proxy class");
            }
        };
    }
}

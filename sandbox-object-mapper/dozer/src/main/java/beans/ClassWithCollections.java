package beans;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassWithCollections {

    private List<StringBean> stringBeans;
    private Set<StringBean> stringBeansSet;
    private Map<String, IntegerBean> integerBeansMap;
    private IntegerBean integerBean;

    public List<StringBean> getStringBeans() {
        return stringBeans;
    }

    public void setStringBeans(List<StringBean> stringBeans) {
        this.stringBeans = stringBeans;
    }

    public IntegerBean getIntegerBean() {
        return integerBean;
    }

    public void setIntegerBean(IntegerBean integerBean) {
        this.integerBean = integerBean;
    }

    public Set<StringBean> getStringBeansSet() {
        return stringBeansSet;
    }

    public void setStringBeansSet(Set<StringBean> stringBeansSet) {
        this.stringBeansSet = stringBeansSet;
    }
}

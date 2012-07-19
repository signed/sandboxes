package construction

import org.junit.Test
import org.hamcrest.CoreMatchers

import static org.hamcrest.MatcherAssert.assertThat

class Constructor {


    private static class ExplicitDefaultConstructor{
        String stringProperty
        Integer integerProperty;

        ExplicitDefaultConstructor() {
        }

        ExplicitDefaultConstructor(String stringProperty) {
            this.stringProperty = stringProperty
        }
    }


    @Test
    public void comeToMe(){
        new ExplicitDefaultConstructor(stringProperty: "value", integerProperty: 7)
    }

    private static class SimpleClass{
        String stringProperty
        Integer integerProperty
        String derived


        SimpleClass(def map) {
            def that = this;
            map.each {key, value ->
                that.setProperty(key, value)
            }
            derived = stringProperty+integerProperty
        }
    }

    @Test
    public void setIntegerProperty(){
        assertThat(new SimpleClass(integerProperty: 7, stringProperty: 'hello').integerProperty, CoreMatchers.is(7))
    }

    @Test
    public void setStringProperty(){
        assertThat(new SimpleClass(integerProperty: 7, stringProperty: 'hello').stringProperty, CoreMatchers.is('hello'))
    }

    @Test
    public void setDerivedProperty(){
        assertThat(new SimpleClass(integerProperty: 7, stringProperty: 'hello').derived, CoreMatchers.is('hello7'))
    }
}

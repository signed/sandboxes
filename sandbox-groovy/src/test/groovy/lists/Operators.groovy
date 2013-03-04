package lists
import org.hamcrest.CoreMatchers
import org.junit.Test

import static org.hamcrest.MatcherAssert.assertThat

class Operators {

    private static class AClass{
        String attribute
    }

    private static class AnotherClass{
    }


    @Test
    public void extractAPropertyFromAllElementsOfTheListAndReturnIt() throws Exception {
        def trimmed = [new AClass(attribute: 'one'), new AClass(attribute: 'two')]*.attribute
        assertThat(trimmed, CoreMatchers.is(['one', 'two']))
    }

    @Test(expected = MissingFieldException.class)
    public void ifAnElementDoesNotHaveTheRequiredField() throws Exception {
        def trimmed = [new AClass(attribute: 'one'), new AnotherClass()]*.attribute
        assertThat(trimmed, CoreMatchers.is(['one']))
    }
}
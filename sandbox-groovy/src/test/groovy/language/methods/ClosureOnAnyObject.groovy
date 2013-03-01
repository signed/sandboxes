package language.methods

import org.junit.Test

class ClosureOnAnyObject {


    private static class ClassToCallOn{
        String property;

        public void method(){

        }
    }

    @Test
    public void testName() throws Exception {
        def instance = new ClassToCallOn();
        instance{
            property: 'nice'
        }


    }
}

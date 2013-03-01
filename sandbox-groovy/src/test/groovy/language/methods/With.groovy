package language.methods

import org.hamcrest.CoreMatchers
import org.junit.Test


import static org.junit.Assert.assertThat

class With {

    private static class Person {
        String theName
        int age
    }


    @Test
    public void testName() throws Exception {
        def person = new Person().with {
            theName = 'Bond'
            age = 7
            return it
        }

        assertThat(person.theName, CoreMatchers.is('Bond'))
        assertThat(person.age, CoreMatchers.is(7))
    }

    @Test
    public void createAPerson() throws Exception {
        def person = new Person( theName: 'Bond');

        assertThat(person.theName, CoreMatchers.is('Bond'))
    }
}
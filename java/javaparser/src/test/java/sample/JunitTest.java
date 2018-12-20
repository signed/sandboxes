package sample;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JunitTest {

    @BeforeClass
    public static void classSetUp() { }

    @AfterClass
    public static void classTearDown(){ }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void name() {

    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptions(){
        throw new IllegalArgumentException();
    }

}

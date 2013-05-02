package validate;

import org.junit.Test;

public class SimpleValidate {
    @Test
    public void testName() throws Exception {
        blankLine();
        String artifact = System.getProperties().getProperty("artifact", "not present");
        System.out.println("validate up an running: "+artifact);
        blankLine();

    }

    private void blankLine() {
        System.out.println();
    }
}

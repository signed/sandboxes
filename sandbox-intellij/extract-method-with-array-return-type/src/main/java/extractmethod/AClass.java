package extractmethod;

import java.io.File;

public class AClass {
    private File file = new File("path");


    public void methodOne(){
        int someBogusNumber = file.listFiles().length + 3;
    }

    public void methodTwo(){
        int anotherBogusNumber = file.listFiles().length %6;
    }
}

package extractmethod;

import inerhitedjavadoc.ClassWithStaticMethod;

public class AClass {



    public void methodOne(){
        String local = ClassWithStaticMethod.createAString();
    }

    public void methodTwo() {
        ClassWithStaticMethod.createAString().compareTo("48");
    }
}

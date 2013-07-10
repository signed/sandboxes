package mnemonic;

public class AllOccurrences {

    private final String field = "not important";

    public void callerMethod(){
        calledMethod();
    }

    private void calledMethod(){
        System.out.println("first: "+ field + " second: "+ field);
    }
}
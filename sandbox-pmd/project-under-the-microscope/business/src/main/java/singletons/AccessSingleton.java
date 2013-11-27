package singletons;

import static singletons.CentralData.instance;

public class AccessSingleton {
    private CentralData centralData;
    private CentralData staticImport;
    private CentralData constructorCall;

    public AccessSingleton() {
        this.centralData = CentralData.instance();
        this.staticImport = instance();
        this.constructorCall = new CentralData();
    }

    public String someOtherStuff(){
        centralData.doStuff();
        return "a string";
    }
}

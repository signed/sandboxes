package guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
    public static void main(String []args){
        AbstractModule module = new AbstractModule() {

            @Override
            protected void configure() {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };

        Injector injector = Guice.createInjector(module);
        PostOffice instance = injector.getInstance(PostOffice.class);
        instance.deliver();
    }
}


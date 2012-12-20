package jersey.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import jersey.ResourceModule;

public class GuiceConfig extends GuiceServletContextListener {
    private String message = "";

    public void swapLogic() {
        message = "decorated ";
    }

    @Override
    protected Injector getInjector() {
        System.out.println("\ncreate the " + message + "injector\n");
        return Guice.createInjector(new ResourceModule());
    }
}
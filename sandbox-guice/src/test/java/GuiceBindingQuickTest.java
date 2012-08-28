import application.ApplicationModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

public class GuiceBindingQuickTest {
    private final Injector injector = Guice.createInjector(new ApplicationModule());

    @Test
    public void testName() throws Exception {
        ApplicationModule model = injector.getInstance(ApplicationModule.class);
        ApplicationModule model2 = injector.getInstance(ApplicationModule.class);
        System.out.println(model==model2);

    }
}

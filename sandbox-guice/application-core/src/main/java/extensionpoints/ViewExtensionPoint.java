package extensionpoints;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;

public class ViewExtensionPoint {
    private final Multibinder<ViewContribution> binder;

    public static ViewExtensionPoint contribute(Binder binder) {
        return new ViewExtensionPoint(Multibinder.newSetBinder(binder, ViewContribution.class));
    }

    public ViewExtensionPoint(Multibinder<ViewContribution> binder) {
        this.binder = binder;
    }

    public void aView(Class<?extends ViewContribution> implementer) {
        binder.addBinding().to(implementer);
    }
}

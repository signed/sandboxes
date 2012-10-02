package application.extensionpoints;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;

public class ToolBarExtensionPoint {
    private final Multibinder<ToolBarContribution> binder;

    public static ToolBarExtensionPoint contribute(Binder binder) {
        return new ToolBarExtensionPoint(Multibinder.newSetBinder(binder, ToolBarContribution.class));
    }

    public ToolBarExtensionPoint(Multibinder<ToolBarContribution> binder) {
        this.binder = binder;
    }

    public void aToolbarItem(Class<? extends ToolBarContribution> implementer) {
        binder.addBinding().to(implementer);
    }
}

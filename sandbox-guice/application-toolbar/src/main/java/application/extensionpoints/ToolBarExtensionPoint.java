package application.extensionpoints;

import com.github.signed.microplugin.core.ExtensionPoint;
import com.google.inject.Binder;

public class ToolBarExtensionPoint extends ExtensionPoint<ToolBarContribution> {

    public static ToolBarExtensionPoint contributeTo(Binder binder) {
        return new ToolBarExtensionPoint(binder);
    }

    public ToolBarExtensionPoint(Binder binder) {
        super(binder, ToolBarContribution.class);
    }
}

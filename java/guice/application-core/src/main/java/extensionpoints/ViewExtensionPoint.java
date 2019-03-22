package extensionpoints;

import com.github.signed.microplugin.core.ExtensionPoint;
import com.google.inject.Binder;

public class ViewExtensionPoint extends ExtensionPoint<ViewContribution>{

    public static ViewExtensionPoint contributeTo(Binder binder) {
        return new ViewExtensionPoint(binder);
    }

    public ViewExtensionPoint(Binder binder) {
        super(binder, ViewContribution.class);
    }
}

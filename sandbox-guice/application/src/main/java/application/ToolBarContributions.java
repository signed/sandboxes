package application;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.Set;

public class ToolBarContributions implements Iterable<ToolBarContribution>{
    public Set<ToolBarContribution> contributions;

    @Inject
    public ToolBarContributions(Set<ToolBarContribution> contributions) {
        this.contributions = contributions;
    }

    @Override
    public Iterator<ToolBarContribution> iterator() {
        return contributions.iterator();
    }
}

package styling;

import com.sun.javafx.css.CssError;
import javafx.collections.ListChangeListener;

import java.util.List;

public class StyleCritic implements ListChangeListener<CssError> {
    private final Forum forum;

    public StyleCritic(Forum forum) {
        this.forum = forum;
    }

    @Override
    public void onChanged(Change<? extends CssError> change) {
        forum.clear();
        while (change.next()) {
            List<? extends CssError> added = change.getAddedSubList();
            for (CssError cssError : added) {
                forum.entertainError(cssError);
            }
        }
    }
}

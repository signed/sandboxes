package contributionTwo;

import application.ApplicationModel;
import lang.Closure;

public class TwoPresenter {

    public TwoPresenter(TwoView view, final ApplicationModel model) {
        view.onAction(new Closure() {
            @Override
            public void execute() {
                model.message("Gruß von contribution two");
            }
        });
    }
}

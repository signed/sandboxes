package contributionOne;

import application.ApplicationModel;
import lang.Closure;

public class OnePresenter {

    public OnePresenter(OneView view, final ApplicationModel model) {
        view.onAction(new Closure() {
            @Override
            public void execute() {
                System.out.println("pressed button one: " +model.message());
            }
        });
    }
}

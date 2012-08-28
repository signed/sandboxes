package contributionOne;

import application.ApplicationModel;
import application.ModularJFx;

public class OnePresenter {

    public OnePresenter(OneView view, final ApplicationModel model) {
        view.onAction(new ModularJFx.Closure() {
            @Override
            public void execute() {
                System.out.println("pressed button one: " +model.message());
            }
        });
    }
}

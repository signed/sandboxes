package application.input;

import application.ApplicationModel;
import lang.ArgumentClosure;
import lang.Closure;

import javax.inject.Inject;

public class InputPresenter {
    @Inject
    public InputPresenter(final InputView view, final ApplicationModel model) {
        view.onChange(new ArgumentClosure<String>() {
            @Override
            public void excecute(String argument) {
                model.message(argument);
            }
        });
        model.onChange(new Closure() {
            @Override
            public void execute() {
                view.display(model.message());
            }
        });
    }
}

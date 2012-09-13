package application.input;

import application.ApplicationModel;
import lang.ArgumentClosure;
import lang.Closure;

import javax.inject.Inject;

public class InputPresenter {
    private final InputView view;
    private final ApplicationModel model;

    @Inject
    public InputPresenter(final InputView view, final ApplicationModel model) {
        this.view = view;
        this.model = model;
    }


    public void wireModelAndView(){
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

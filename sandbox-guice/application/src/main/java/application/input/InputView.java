package application.input;

import lang.ArgumentClosure;

public interface InputView extends SubordinatingView {

    void onChange(ArgumentClosure<String> closure);

    void display(String message);

}

package application.recordings;

import application.recordings.view.RecordingsView;
import domain.Recording;
import lang.ArgumentClosure;
import view.Presenter;

import java.util.List;

public class RecordingsPresenter implements Presenter {


    private RecordingsModel model;
    private RecordingsView view;

    public RecordingsPresenter(RecordingsModel model, RecordingsView view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void wireModelAndView() {
        model.provideTo(new ArgumentClosure<List<Recording>>() {
            @Override
            public void excecute(List<Recording> argument) {
                view.display(argument);
            }
        });
    }
}

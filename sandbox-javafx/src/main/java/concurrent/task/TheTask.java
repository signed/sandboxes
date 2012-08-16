package concurrent.task;

import javafx.concurrent.Task;

public class TheTask extends Task<Void> {

    @Override
    protected Void call() throws Exception {
        print("called");
        return null;
    }

    @Override
    protected void scheduled() {
        print("scheduled");
    }

    @Override
    protected void cancelled() {
        print("canceled");
    }

    @Override
    protected void succeeded() {
        print("succeeded");
    }

    @Override
    protected void done() {
        print("done");
    }

    @Override
    protected void running() {
        print("running");
    }

    @Override
    protected void failed() {
        print("failed");
    }

    private void print(String action) {
        System.out.println("the task is " + action);
    }
}

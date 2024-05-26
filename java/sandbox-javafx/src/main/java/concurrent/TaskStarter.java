package concurrent;

import javafx.concurrent.Task;

import java.util.concurrent.ExecutorService;

public class TaskStarter implements Starter {
    private final ExecutorService executorService;
    private Task<?> task;

    public TaskStarter(Task<?> task, ExecutorService executorService) {
        this.task = task;
        this.executorService = executorService;
    }

    @Override
    public void start() {
        executorService.submit(task);
    }
}

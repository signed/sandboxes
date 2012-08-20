package progress;

import domain.Announcer;
import javafx.util.Callback;

class LongRunningAction {

    public static interface VoidCallback extends Callback<Void, Void>{

    }

    private final Announcer<VoidCallback> completionListeners = new Announcer<>(VoidCallback.class);
    private final Announcer<VoidCallback> startListeners = new Announcer<>(VoidCallback.class);


    public void onCompletion(VoidCallback callback) {
        completionListeners.addListener(callback);
    }

    public void onStart(VoidCallback callback){
        startListeners.addListener(callback);
    }

    public void start() {
        startListeners.announce().call(null);
    }

    public void completed() {
        completionListeners.announce().call(null);
    }
}

package hazelcast;

public class JVMShutdownHook extends Thread {
    private Watchlist watchlist;

    public JVMShutdownHook(Watchlist watchlist) {
        this.watchlist = watchlist;
    }

    public void run() {
        System.out.println("shutting down");
        watchlist.shutdown();
        System.out.println("shut down");
    }
}

package networking;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Ping {

    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        final InetAddress adress = InetAddress.getByName("192.168.22.7");
        executor.scheduleWithFixedDelay(new IsReachable(adress), 0, 2, TimeUnit.SECONDS);
    }

    private static class IsReachable implements Runnable {
        private final InetAddress adress;

        public IsReachable(InetAddress address) {
            this.adress = address;
        }

        @Override
        public void run() {
            try {
                if (adress.isReachable(2000)) {
                    System.out.println("reachable");
                } else {
                    System.out.println("not reachable");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

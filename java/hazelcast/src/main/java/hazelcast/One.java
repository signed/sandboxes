package hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;

import java.util.Map;
import java.util.Queue;

public class One {

    public static void main(String[] args) {
        Config cfg = new Config();
        cfg.setProperty("hazelcast.logging.type", "slf4j");
        GroupConfig groupConfig = cfg.getGroupConfig();
        groupConfig.setName("development");


        Watchlist watchlist = new Watchlist(cfg);
        JVMShutdownHook jvmShutdownHook = new JVMShutdownHook(watchlist);
        Runtime.getRuntime().addShutdownHook(jvmShutdownHook);

        Map<Integer, String> mapCustomers = watchlist.map();
        mapCustomers.put(1, "Joe");
        mapCustomers.put(2, "Ali");
        mapCustomers.put(3, "Avi");

        Queue<String> queueCustomers = watchlist.queue();
        queueCustomers.offer("Tom");
        queueCustomers.offer("Mary");
        queueCustomers.offer("Jane");
        System.out.println("First customer: " + queueCustomers.poll());
        System.out.println("Second customer: "+ queueCustomers.peek());
        System.out.println("Queue size: " + queueCustomers.size());
    }

}

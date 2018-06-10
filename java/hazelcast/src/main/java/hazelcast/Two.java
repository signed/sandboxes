package hazelcast;

import com.hazelcast.config.Config;

import java.util.Map;

public class Two {

    public static void main(String[] args) {
        Config cfg = new Config();
        //cfg.getGroupConfig().setName("development");
        Map<Integer, String> mapCustomers = new Watchlist(cfg).map();

        System.out.println("Customer with key 1: "+ mapCustomers.get(1));
        System.out.println("Map Size:" + mapCustomers.size());
    }
}

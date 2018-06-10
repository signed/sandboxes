package hazelcast;

import java.util.Map;

public class One {

    public static void main(String[] args) {
        Map<Integer, String> mapCustomers = new Watchlist().map();
        mapCustomers.put(1, "Joe");
        mapCustomers.put(2, "Ali");
        mapCustomers.put(3, "Avi");
    }

}

package hazelcast;

import java.util.Map;

public class Two {

    public static void main(String[] args) {
        Map<Integer, String> mapCustomers = new Watchlist().map();
        mapCustomers.put(1, "Joe");
        mapCustomers.put(2, "Ali");
        mapCustomers.put(3, "Avi");

        System.out.println("Customer with key 1: "+ mapCustomers.get(1));
        System.out.println("Map Size:" + mapCustomers.size());
    }
}

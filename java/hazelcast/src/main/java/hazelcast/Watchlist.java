package hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IQueue;

public class Watchlist {
    private final HazelcastInstance instance;

    public Watchlist(Config cfg) {
        instance = Hazelcast.newHazelcastInstance(cfg);
    }

    public <Key, Value> IMap<Key, Value> customersMap() {
        String mapName = "customers";
        return map(mapName);
    }

    public <Key, Value> IMap<Key, Value> map(String mapName) {
        return instance.getMap(mapName);
    }

    public <T> IQueue<T> queue() {
        return instance.getQueue("customers");
    }

    public void shutdown(){
        instance.shutdown();
    }

}

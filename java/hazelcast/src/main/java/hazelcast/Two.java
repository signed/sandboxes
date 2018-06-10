package hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.IMap;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializableFactory;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;

import java.io.IOException;
import java.util.Map;

public class Two {

    public static class StorageObjectDataSerializableFactory implements DataSerializableFactory{

        public static final int FACTORY_ID = 1;
        public static final int STORAGE_OBJECT_TYPE_ID = 1;


        @Override
        public IdentifiedDataSerializable create(int typeId) {
            if (STORAGE_OBJECT_TYPE_ID == typeId) {
                return new StorageObject();
            }
            throw new IllegalArgumentException();
        }
    }

    public static class StorageObject implements IdentifiedDataSerializable {
        public String hello;
        public Long world;

        @Override
        public void writeData(ObjectDataOutput out) throws IOException {
            out.writeUTF(hello);
            out.writeLong(world);
        }

        @Override
        public void readData(ObjectDataInput in) throws IOException {
            hello = in.readUTF();
            world = in.readLong();
        }

        @Override
        public int getFactoryId() {
            return StorageObjectDataSerializableFactory.FACTORY_ID;
        }

        @Override
        public int getId() {
            return StorageObjectDataSerializableFactory.STORAGE_OBJECT_TYPE_ID;
        }
    }

    public static void main(String[] args) {
        Config cfg = new Config();
        cfg.setProperty("hazelcast.logging.type", "slf4j");
        cfg.getGroupConfig().setName("development");
        cfg.getSerializationConfig().addDataSerializableFactory(StorageObjectDataSerializableFactory.FACTORY_ID, new StorageObjectDataSerializableFactory());

        Watchlist watchlist = new Watchlist(cfg);
        Map<Integer, String> mapCustomers = watchlist.customersMap();
        System.out.println("Customer with key 1: "+ mapCustomers.get(1));
        System.out.println("Map Size:" + mapCustomers.size());


        IMap<Long, StorageObject> map = watchlist.map("custom-serializer");
        StorageObject storage = new StorageObject();
        storage.hello = "world";
        storage.world = 43L;
        //map.put(43L, storage);

        System.out.println(map.size());
        StorageObject storageObject = map.get(43L);
        System.out.println(storageObject.hello);
        System.out.println(storage.world);
    }
}

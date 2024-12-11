import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RedisStorage {
    private Map<String, String> storage;

    public RedisStorage() {
        storage = new ConcurrentHashMap<>();
    }

    public String get(String key) {
        return storage.get(key);
    }

    public void set(String key, String value) {
        storage.put(key, value);
    }

    public boolean exists(String key) {
        return storage.containsKey(key);
    }
}

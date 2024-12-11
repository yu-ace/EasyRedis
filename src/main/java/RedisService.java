public class RedisService {
    private final RedisStorage storage;

    public RedisService() {
        storage = new RedisStorage();
    }

    public String get(String key) {
        if (key == null || key.isEmpty()) {
            return "Error: key cannot be empty";
        }
        String value = storage.get(key);
        return value != null ? value : "null";
    }

    public String set(String key, String value) {
        if (key == null || key.isEmpty()) {
            return "Error: key cannot be empty";
        }
        if (value == null) {
            return "Error: value cannot be null";
        }
        storage.set(key, value);
        return "OK";
    }
}
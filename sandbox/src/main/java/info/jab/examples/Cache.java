package info.jab.examples;

import java.util.Optional;

public interface Cache {
    void put(String key, Object value);

    Optional<Object> get(String key);
}

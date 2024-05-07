package org.example.save;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SubMap{

    private final String prefix;
    private final Map<String, String> parent;

    public SubMap(String prefix, Map<String, String> parent) {
        this.prefix = prefix;
        this.parent = parent;
    }

    public void put(String key, String value) {
        parent.put(prefix + '.' + key, value);
    }

    public String get(String key) {
        return parent.get(key);
    }

    public Set<String> keySet() {
        return parent.keySet().stream()
                    .filter(key -> key.startsWith(prefix))
                    .collect(Collectors.toSet());
    }
}

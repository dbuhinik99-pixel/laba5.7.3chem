package org.example;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private final Map<String, AtomicLong> sequences = new ConcurrentHashMap<>();

    public long nextId(String entityName) {
        return sequences.computeIfAbsent(entityName, k -> new AtomicLong(1))
                .getAndIncrement();
    }

    public void setInitialId(String entityName, long value) {
        sequences.put(entityName, new AtomicLong(value));
    }

    public long getCurrentId(String entityName) {
        AtomicLong seq = sequences.get(entityName);
        return seq != null ? seq.get() - 1 : 0;
    }

    public void reset(String entityName) {
        sequences.remove(entityName);
    }
}

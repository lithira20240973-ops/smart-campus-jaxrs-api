package com.smartcampus.repository;

import com.smartcampus.model.Sensor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SensorRepository {
    // In-memory static store for sensors
    private static final Map<String, Sensor> SENSORS = new ConcurrentHashMap<>();

    public static List<Sensor> findAll() {
        return new ArrayList<>(SENSORS.values());
    }

    public static List<Sensor> findByType(String type) {
        if (type == null || type.trim().isEmpty()) {
            return findAll();
        }
        return SENSORS.values().stream()
                .filter(s -> type.equalsIgnoreCase(s.getType()))
                .collect(Collectors.toList());
    }

    public static Sensor findById(String id) {
        return SENSORS.get(id);
    }

    public static boolean save(Sensor sensor) {
        if (SENSORS.containsKey(sensor.getId())) {
            return false; // Already exists
        }
        SENSORS.put(sensor.getId(), sensor);
        return true;
    }

    public static boolean delete(String id) {
        return SENSORS.remove(id) != null;
    }
}

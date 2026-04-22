package com.smartcampus.repository;

import com.smartcampus.model.SensorReading;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class SensorReadingRepository {
    // In-memory static store for readings per sensor
    private static final Map<String, List<SensorReading>> READINGS = new ConcurrentHashMap<>();

    public static List<SensorReading> getReadings(String sensorId) {
        return new ArrayList<>(READINGS.getOrDefault(sensorId, new CopyOnWriteArrayList<>()));
    }

    public static void addReading(String sensorId, SensorReading reading) {
        READINGS.computeIfAbsent(sensorId, k -> new CopyOnWriteArrayList<>()).add(reading);
    }
}

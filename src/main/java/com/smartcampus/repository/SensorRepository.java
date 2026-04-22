package com.smartcampus.repository;

import com.smartcampus.model.Sensor;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory repository, as no database is allowed.
 */
public class SensorRepository {
    private final List<Sensor> sensors = new ArrayList<>();

    public List<Sensor> findAll() {
        return sensors;
    }
    
    // Add logic to save, delete, update, etc.
}

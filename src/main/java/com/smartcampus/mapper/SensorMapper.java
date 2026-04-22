package com.smartcampus.mapper;

import com.smartcampus.model.Sensor;

/**
 * Mapper for converting between Entities and Data Transfer Objects (DTOs)
 */
public class SensorMapper {
    
    public Sensor toEntity(Object dto) {
        // Implementation for mapping
        return new Sensor();
    }
}

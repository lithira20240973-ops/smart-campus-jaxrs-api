package com.smartcampus.repository;

import com.smartcampus.model.Room;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RoomRepository {
    // In-memory static store as requested
    private static final Map<String, Room> ROOMS = new ConcurrentHashMap<>();

    public static List<Room> findAll() {
        return new ArrayList<>(ROOMS.values());
    }

    public static Room findById(String id) {
        return ROOMS.get(id);
    }

    public static boolean save(Room room) {
        if (ROOMS.containsKey(room.getId())) {
            return false; // Already exists
        }
        ROOMS.put(room.getId(), room);
        return true;
    }

    public static boolean delete(String id) {
        return ROOMS.remove(id) != null;
    }
}

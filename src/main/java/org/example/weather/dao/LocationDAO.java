package org.example.weather.dao;

import org.example.weather.model.Location;

import java.util.List;

public interface LocationDAO {
    void save(Location location);
    List<Location> findByUserId(int userId);
    Location findById(int id);
    void delete(int locationId);
}

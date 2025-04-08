package org.example.weather.service;

import org.example.weather.model.Location;

import java.util.List;

public interface LocationService {
    void addLocation(Location location);
    List<Location> getLocationsForUser(int userId);
    Location getLocationById(int id);
    void removeLocation(int locationId);
}

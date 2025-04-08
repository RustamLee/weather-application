package org.example.weather.service;

import org.example.weather.dao.LocationDAO;
import org.example.weather.model.Location;
import org.example.weather.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationDAO locationDAO;

    @Autowired
    public LocationServiceImpl(LocationDAO locationDAO) {
        this.locationDAO = locationDAO;
    }

    @Override
    public void addLocation(Location location) {
        locationDAO.save(location);
    }

    @Override
    public List<Location> getLocationsForUser(int userId) {
        return locationDAO.findByUserId(userId);
    }

    @Override
    public Location getLocationById(int id) {
        return locationDAO.findById(id);
    }

    @Override
    public void removeLocation(int locationId) {
        locationDAO.delete(locationId);
    }
}

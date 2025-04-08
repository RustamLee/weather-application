package org.example.weather.dao;

import org.example.weather.dao.LocationDAO;
import org.example.weather.model.Location;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LocationDAOImpl implements LocationDAO {

    private final SessionFactory sessionFactory;

    public LocationDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Location location) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(location);
            tx.commit();
        }
    }

    @Override
    public List<Location> findByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Location WHERE userId = :userId";
            Query<Location> query = session.createQuery(hql, Location.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        }
    }

    @Override
    public Location findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Location.class, id);
        }
    }

    @Override
    public void delete(int locationId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Location location = session.get(Location.class, locationId);
            if (location != null) {
                session.remove(location);
            }
            tx.commit();
        }
    }
}

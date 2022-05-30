package com.parkingSpot.app.repositories;

import com.parkingSpot.app.models.UsernamePasswordAuthenticationRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository
public class DefaultRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertNewUser(UsernamePasswordAuthenticationRequest user) {
        entityManager.createNativeQuery("INSERT INTO user (username, password, role_id) VALUES (?,?,2)")
                .setParameter(1, user.getUsername())
                .setParameter(2, user.getPassword())
                .executeUpdate();
    }

    @Transactional
    public void spotsAvailabilityShifter(Long spotId, boolean bool) {
        entityManager.createNativeQuery("UPDATE spot SET is_available = ? WHERE id = ?")
                .setParameter(1, bool)
                .setParameter(2, spotId)
                .executeUpdate();
    }

    @Transactional
    public void newParking(Map parkingMap) {
        entityManager.createNativeQuery("INSERT INTO history (entry_date, departure_date, user_id, spot_id, is_finished) VALUES (?,?,?,?, false)")
                .setParameter(1, parkingMap.get("entryDate"))
                .setParameter(2, parkingMap.get("departureDate"))
                .setParameter(3, parkingMap.get("userId"))
                .setParameter(4, parkingMap.get("spotId"))
                .executeUpdate();
    }
}

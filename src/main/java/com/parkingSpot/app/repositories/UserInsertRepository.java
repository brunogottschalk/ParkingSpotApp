package com.parkingSpot.app.repositories;

import com.parkingSpot.app.models.UsernamePasswordAuthenticationRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserInsertRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertNewUser(UsernamePasswordAuthenticationRequest user) {
        entityManager.createNativeQuery("INSERT INTO user (username, password, role_id) VALUES (?,?,2)")
                .setParameter(1, user.getUsername())
                .setParameter(2, user.getPassword())
                .executeUpdate();
    }
}

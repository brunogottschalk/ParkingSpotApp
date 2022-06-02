package com.parkingSpot.app.repositories;

import com.parkingSpot.app.models.UserModel;
import com.parkingSpot.app.models.UsernamePasswordAuthenticationRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Long> {

    Optional<UserModel> findUserByUsername(String username);

}

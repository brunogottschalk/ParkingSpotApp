package com.parkingSpot.app.repositories;

import com.parkingSpot.app.models.SpotsModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpotsRepository extends CrudRepository<SpotsModel, Long> {
    public List<SpotsModel> findAll();
}
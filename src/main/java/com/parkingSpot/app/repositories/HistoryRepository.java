package com.parkingSpot.app.repositories;

import com.parkingSpot.app.models.HistoryModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryRepository extends CrudRepository<HistoryModel, Long> {
    List<HistoryModel> findAll();

    List<HistoryModel> findHistoryByUserId(Long id);

    Optional<HistoryModel> findByUserIdAndIsFinished(Long userId, boolean isFinished);
}

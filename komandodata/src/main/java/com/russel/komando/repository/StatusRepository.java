package com.russel.komando.repository;

import com.russel.komando.entity.StatusData;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StatusRepository extends CrudRepository<StatusData, Integer> {
    Optional<StatusData> findByStatusTitleIgnoreCase(String statusTitle);

}

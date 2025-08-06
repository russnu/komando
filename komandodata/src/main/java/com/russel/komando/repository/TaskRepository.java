package com.russel.komando.repository;

import com.russel.komando.entity.TaskData;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<TaskData, Integer> {
}

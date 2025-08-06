package com.russel.komando.repository;

import com.russel.komando.entity.EmployeeData;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<EmployeeData, Integer> {
}

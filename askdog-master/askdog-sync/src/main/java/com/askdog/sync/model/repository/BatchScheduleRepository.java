package com.askdog.sync.model.repository;

import com.askdog.sync.model.entity.BatchSchedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchScheduleRepository extends CrudRepository<BatchSchedule, String> {
}
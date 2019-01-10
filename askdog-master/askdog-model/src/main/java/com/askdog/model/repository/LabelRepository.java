package com.askdog.model.repository;

import com.askdog.model.entity.Label;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface LabelRepository extends CrudRepository<Label, String> {
    Optional<Label> findByName(String name);
    List<Label> findByNameIn(Collection<String> names);
}

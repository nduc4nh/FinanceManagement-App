package com.bookstore.forreal.Model.Repository;
import com.bookstore.forreal.Model.Entities.OptionalEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionalRepository extends CrudRepository<OptionalEntity,Integer> {
}

package com.bookstore.forreal.Model.Repository;

import com.bookstore.forreal.Model.Entities.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends CrudRepository<Genre,Integer> {
}

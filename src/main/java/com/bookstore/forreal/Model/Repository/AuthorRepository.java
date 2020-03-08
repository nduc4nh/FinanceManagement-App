package com.bookstore.forreal.Model.Repository;

import com.bookstore.forreal.Model.Entities.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author,Integer> {
}

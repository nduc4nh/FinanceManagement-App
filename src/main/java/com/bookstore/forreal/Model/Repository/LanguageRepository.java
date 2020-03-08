package com.bookstore.forreal.Model.Repository;

import com.bookstore.forreal.Model.Entities.Language;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends CrudRepository<Language,Integer> {
}

package com.bookstore.forreal.Model.Repository;

import com.bookstore.forreal.Model.Entities.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image,Integer> {
}

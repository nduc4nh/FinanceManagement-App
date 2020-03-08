package com.bookstore.forreal.Model.Services;
import com.bookstore.forreal.Model.Entities.Genre;
import com.bookstore.forreal.Model.Repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService implements Services<Genre> {
    @Autowired
    GenreRepository repository;
    /*
    @Override
    public Genre findByName(String name) {
        return null;
    }*/
    @Transactional
    @Override
    public List<Genre> findAll() {
        return (List<Genre>) repository.findAll();
    }
    @Transactional
    @Override
    public List<Genre> findAllById(List<Integer> Ids) {
        return (List<Genre>) repository.findAllById(Ids);
    }
    @Transactional
    @Override
    public Optional<Genre> findById(Integer Id) {
        return repository.findById(Id);
    }
    @Transactional
    @Override
    public void deleteById(Integer Id) {
        repository.deleteById(Id);
    }
    @Transactional
    @Override
    public void deleteAllById(List<Integer> Ids) {
        //repository.findAllById(Ids);
    }
    @Transactional
    @Override
    public void Save(Genre draft) {
        repository.save(draft);
    }
    @Transactional
    @Override
    public void saveAll(List<Genre> Books) {
    }

    @Override
    public boolean existById(int Id) {
        return repository.existsById(Id);
    }
}

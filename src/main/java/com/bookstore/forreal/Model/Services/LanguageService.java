package com.bookstore.forreal.Model.Services;
import com.bookstore.forreal.Model.Entities.Language;
import com.bookstore.forreal.Model.Repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService implements Services<Language>{
    @Autowired
    LanguageRepository repository;
    /*
    @Override
    public Language findByName(String name) {
        return null;
    }
    */
    @Transactional
    @Override
    public List<Language> findAll() {
        return (List<Language>) repository.findAll();
    }
    @Transactional
    @Override
    public List<Language> findAllById(List<Integer> Ids) {
        return (List<Language>) repository.findAllById(Ids);
    }
    @Transactional
    @Override
    public Optional<Language> findById(Integer Id) {
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

    }
    @Transactional
    @Override
    public void Save(Language draft) {
        repository.save(draft);
    }
    @Transactional
    @Override
    public void saveAll(List<Language> drafts) {
        repository.saveAll(drafts);
    }

    @Override
    public boolean existById(int Id) {
        return repository.existsById(Id);
    }
}

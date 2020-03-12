package com.bookstore.forreal.Model.Services;

import com.bookstore.forreal.Model.Entities.Book;
import com.bookstore.forreal.Model.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService implements Services<Book> {
    @Autowired
    BookRepository repository;
    
    @Override
    public Book findByName(String name) {
        // TODO Auto-generated method stub
        List<Book> authors = (List<Book>) repository.findAll();
        for (Book ele:authors)
        {
            if (ele.getName().strip().toLowerCase().equals(name.strip().toLowerCase())) return ele;
        }
        return null;
    }
    
    @Override
    public List<Book>  findAllByName(String[] names){
        // TODO Auto-generated method stub
        List<Book> books = (List<Book>) repository.findAll();
        ArrayList <Book> re = new ArrayList<Book>();
        for (String name:names)
        {
            re.add(findByName(name)); 
        }
        return re;
    }
    @Transactional
    @Override
    public List<Book> findAll() {
        return (List<Book>) repository.findAll();
    }
    @Transactional
    @Override
    public List<Book> findAllById(List<Integer> Ids) {
        return (List<Book>) repository.findAllById(Ids);
    }
    @Transactional
    @Override
    public Optional<Book> findById(Integer Id) {
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
    public void Save(Book draft) {
        repository.save(draft);
    }
    @Transactional
    @Override
    public void saveAll(List<Book> drafts) {
        repository.saveAll(drafts);
    }

    @Override
    public boolean existById(int Id) {
        return repository.existsById(Id);
    }
}

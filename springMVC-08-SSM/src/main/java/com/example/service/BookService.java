package com.example.service;

import com.example.domain.Book;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BookService {

    public Boolean save(Book book);

    public Boolean update(Book book);

    public Boolean delete(Integer id);

    public Book getById(Integer id);

    public List<Book> getAll();
}

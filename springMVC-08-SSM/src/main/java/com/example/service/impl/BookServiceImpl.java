package com.example.service.impl;

import com.example.controller.Code;
import com.example.dao.BookDao;
import com.example.domain.Book;
import com.example.exception.BusinessException;
import com.example.exception.SystemException;
import com.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public Boolean save(Book book) {
        bookDao.save(book);
        return true;
    }

    @Override
    public Boolean update(Book book) {
        bookDao.update(book);
        return true;
    }

    @Override
    public Boolean delete(Integer id) {
        bookDao.delete(id);
        return true;
    }

    @Override
    public Book getById(Integer id) {
        if(id == 1) {
            throw new BusinessException(Code.BUSINESS_ERR,"请稍后再试！");
        }
        try {
            int i = 1/0;
        } catch(Exception e) {
            throw new SystemException(Code.SYSTEM_ERR,"服务器访问超时，请重试！",e);
        }

        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }
}

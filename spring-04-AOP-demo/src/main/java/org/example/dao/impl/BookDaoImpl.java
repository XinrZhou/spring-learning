package org.example.dao.impl;

import org.example.dao.BookDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImpl implements BookDao {

    public void save() {
        System.out.println("bookDao save...");
    }

    public void update() {
        System.out.println("bookDao update...");
    }

    public String findName(int id) {
        System.out.println("id:" + id);
        return "test";
    }
}


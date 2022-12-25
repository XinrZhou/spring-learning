package org.example.dao.impl;

import org.example.dao.BookDao;

public class BookDaoImpl implements BookDao {

    private int connectNum;
    private String databseName;

    public void setConnectNum(int connectNum) {
        this.connectNum = connectNum;
    }

    public void setDatabseName(String databseName) {
        this.databseName = databseName;
    }


    public void save() {
        System.out.println("book dao save..."+databseName+","+connectNum);
    }

    public void init() {
        System.out.println("init...");
    }

    public void destroy() {
        System.out.println("destroy...");
    }
}

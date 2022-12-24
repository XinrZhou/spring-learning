package org.example;

import org.example.service.BookService;
import org.example.service.impl.BookServiceImpl;


public class App 
{
    public static void main( String[] args )
    {
        BookService bookService = new BookServiceImpl();
        bookService.save();
    }
}

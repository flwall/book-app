package com.waflo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Author {
    @Id
    @GeneratedValue
    public int id;


    //@ManyToMany(mappedBy = "books", targetEntity = Book.class)
    //List<Book> books = new LinkedList<>();

    @Column(name = "name")
    public String name;


}

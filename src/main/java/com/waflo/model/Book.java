package com.waflo.model;


import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue
    public int id;

    @Column(name = "title", nullable = false)
    public String title;

    @Column
    public String description;

    //@Column
    //List<String> tags = new LinkedList<>();

    @ManyToMany(targetEntity = Author.class)
    public List<Author> authors = new LinkedList<>();

    @Column(name = "path")
    public String path_to_book;

}


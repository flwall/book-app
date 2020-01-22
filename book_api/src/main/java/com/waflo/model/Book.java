package com.waflo.model;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "title", nullable = false, unique = true)
    @NotEmpty(message = "Title must not be empty")
    private String title;

    @Column(length = 2048)
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private Set<String> tags = new HashSet<>();

    //  @ElementCollection(fetch = FetchType.EAGER)
    //@Fetch(FetchMode.SELECT)
    private String format;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull(message = "Author must not be null")
    private Author author;

    @Column(name = "path")
    private String path_to_book;

    @javax.validation.constraints.Min(value = -1, message = "Rating cannot be < 0")
    @javax.validation.constraints.Max(value = 5, message = "Rating cannot be > 5")
    private int rating = -1;        //rating between 0 and 5 stars  (-1 for unrated)

    @Transient
    public String timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getPath_to_book() {
        return path_to_book;
    }

    public void setPath_to_book(String path_to_book) {
        this.path_to_book = path_to_book;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


}


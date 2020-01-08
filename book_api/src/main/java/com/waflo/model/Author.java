package com.waflo.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    @NotEmpty
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @OneToMany(mappedBy = "author")
    public List<Book> books;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Author && ((Author) obj).name.equals(this.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + Objects.hashCode(id);
    }
}

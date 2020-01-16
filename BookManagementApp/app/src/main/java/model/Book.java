package model;

import com.google.gson.annotations.SerializedName;

public class Book {
    @SerializedName("id")
    private int bookId;
    @SerializedName("title")
    private String title;
    @SerializedName("author")
    private Author author;
    @SerializedName("description")
    private String description;
    @SerializedName("rating")
    private String rating;
    @SerializedName("formats")
    private String[] formats;
    @SerializedName("tags")
    private String[] tags;
    @SerializedName("path_to_book")
    private String path_to_book;

    public Book(int bookId, String title, Author author, String description, String rating, String[] formats, String[] tags, String path_to_book) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.description = description;
        this.rating = rating;
        this.formats = formats;
        this.tags = tags;
        this.path_to_book = path_to_book;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String[] getFormats() {
        return formats;
    }

    public void setFormats(String[] formats) {
        this.formats = formats;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getPath_to_book() {
        return path_to_book;
    }

    public void setPath_to_book(String path_to_book) {
        this.path_to_book = path_to_book;
    }
}

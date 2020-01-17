package model;

import com.google.gson.annotations.SerializedName;

public class Author {
    @SerializedName("id")
    private int authorId;
    @SerializedName("name")
    private String name;

    public Author(int authorId, String name) {
        this.authorId = authorId;
        this.name = name;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

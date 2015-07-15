package com.example.ben.dejsontest;

/**
 * Created by Ben on 7/15/2015.
 */
public class Book {
   private String imageURL;
    private String title;
    private String author;

    public Book(String imageURL, String title, String author) {
        this.imageURL = imageURL;
        this.title = title;
        this.author = author;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

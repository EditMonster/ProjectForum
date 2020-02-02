package com.example.coordinator;

public class Post {
    private String text;
    private String tag;

    public Post() {
        //for firestore
    }

    public Post(String name, String tag) {
        this.text = name;
        this.tag = tag;
    }

    public String getText() {
        return text;
    }

    public String getTag() {
        return tag;
    }
}

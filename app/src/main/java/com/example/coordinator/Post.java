package com.example.coordinator;

import java.text.SimpleDateFormat;

public class Post {
    private String text;
    private String tag;
    private String date;

    public Post() {
        //for firestore
    }

    public Post(String name, String tag, String date) {
        this.text = name;
        this.tag = tag;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public String getTag() {
        return tag;
    }

    public String getDate() {
        return date;
    }
}

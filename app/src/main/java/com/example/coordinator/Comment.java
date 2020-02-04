package com.example.coordinator;

public class Comment {
    private String text;
    private String date;

    public Comment() {
        //For firestore
    }

    public Comment(String text, String date) {
        this.text = text;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }
}

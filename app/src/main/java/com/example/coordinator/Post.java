package com.example.coordinator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Post {
    private String text;
    private String tag;
    private String date;
    ArrayList<Comment> comments;

    public Post() {
        //for firestore
    }

    public Post(String text, String tag, String date, ArrayList<Comment> comments) {
        this.text = text;
        this.tag = tag;
        this.date = date;
        this.comments = comments;
    }

    public Post(ArrayList<Comment> comments) {
        this.comments = comments;
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

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public boolean isComment() {
        return comments != null;
    }
}

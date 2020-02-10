package com.example.coordinator;

import java.util.ArrayList;

public class Post {
    private String text;
    private String tag;
    private String date;
    private ArrayList<Comment> comments;

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

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public int commentCount() {
        return comments.size();
    }
}

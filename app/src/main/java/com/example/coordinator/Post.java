package com.example.coordinator;

public class Post {
    private String text;
    private String tag;
    private String date;
    private int commentCount;

    public Post() {
        //for firestore
    }

    public Post(String text, String tag, String date) {
        this.text = text;
        this.tag = tag;
        this.date = date;
        this.commentCount = 0;
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


    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}

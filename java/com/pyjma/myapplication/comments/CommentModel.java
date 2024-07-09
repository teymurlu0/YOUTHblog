package com.pyjma.myapplication.comments;

public class CommentModel {
    private String blogId;
    private String comment;
    private String timeStamp;
    private String name;
    private String image;

    public CommentModel() {
    }

    public CommentModel(int commentId, String blogId, String comment, String timeStamp, String name, String image) {
        this.blogId = blogId;
        this.comment = comment;
        this.timeStamp = timeStamp;
        this.name = name;
        this.image = image;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

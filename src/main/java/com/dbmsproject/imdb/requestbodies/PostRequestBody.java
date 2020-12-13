package com.dbmsproject.imdb.requestbodies;

public class PostRequestBody {
    private String userId;
    private String movieId;
    private String comment;
    private String rating;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public PostRequestBody(String userId, String movieId, String comment, String rating, String date) {
        this.userId = userId;
        this.movieId = movieId;
        this.comment = comment;
        this.rating = rating;
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public PostRequestBody() {
    }

    public PostRequestBody(String userId, String movieId, String comment, String rating) {
        this.userId = userId;
        this.movieId = movieId;
        this.comment = comment;
        this.rating = rating;
    }
}

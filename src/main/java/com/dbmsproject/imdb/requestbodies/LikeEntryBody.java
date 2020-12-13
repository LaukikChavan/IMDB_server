package com.dbmsproject.imdb.requestbodies;

public class LikeEntryBody {
    private String userId;
    private String movieId;

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

    public LikeEntryBody() {
    }

    public LikeEntryBody(String userId, String movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }
}

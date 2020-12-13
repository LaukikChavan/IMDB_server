package com.dbmsproject.imdb.model;

import com.dbmsproject.imdb.requestbodies.PostRequestBody;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@Document(collection = "reviews")
public class Review {

    @Id
    private String _id;
    private String userId;
    private String comment;
    private String movieId;
    private String rating;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Review(String _id, String userId, String comment, String movieId, String rating, String date) {
        this._id = _id;
        this.userId = userId;
        this.comment = comment;
        this.movieId = movieId;
        this.rating = rating;
        this.date = date;
    }

    public Review(PostRequestBody postRequestBody) {
        this.userId = postRequestBody.getUserId();
        this.movieId = postRequestBody.getMovieId();
        this.comment = postRequestBody.getComment();
        this.rating = postRequestBody.getRating();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @JsonIgnore
    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public Review() {
    }

    public Review(String _id, String comment, String movieId) {
        this._id = _id;
        this.comment = comment;
        this.movieId = movieId;
    }
}

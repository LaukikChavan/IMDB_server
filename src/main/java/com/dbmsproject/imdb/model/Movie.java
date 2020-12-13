package com.dbmsproject.imdb.model;

import com.dbmsproject.imdb.requestbodies.EntryBody;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;

@Data
@Getter
@Setter
@Document(collection = "movies")
public class Movie {
    @Id
    private String _id;
    private String title;
    private String year;
    private String rated;
    private String released;
    private String runtime;
    private String genre;
    private String director;
    private String writer;
    private ArrayList<String> actors;
    private ArrayList<String> reviews;
    private String plot;
    private String language;
    private String country;
    private String awards;
    private String poster;
    private String rating;
    private String production;
    private int likes;
    private String trailer;

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public Movie(String _id, String title, String year, String rated, String released, String runtime, String genre, String director, String writer, ArrayList<String> actors, String plot, String language, String country, String awards, String poster, String rating, String production, int likes, String trailer, ArrayList<String> reviews) {
        this._id = _id;
        this.title = title;
        this.year = year;
        this.rated = rated;
        this.released = released;
        this.runtime = runtime;
        this.genre = genre;
        this.director = director;
        this.writer = writer;
        this.actors = actors;
        this.plot = plot;
        this.language = language;
        this.country = country;
        this.awards = awards;
        this.poster = poster;
        this.rating = rating;
        this.production = production;
        this.likes = likes;
        this.trailer = trailer;
        this.reviews = reviews;
    }

    @JsonIgnore
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<String> reviews) {
        this.reviews = reviews;
    }

    public Movie() {
    }

    public Movie(EntryBody entryBody) {
        this.title = entryBody.getTitle();
        this.year = entryBody.getYear();
        this.rated = entryBody.getRated();
        this.released = entryBody.getReleased();
        this.runtime = entryBody.getRuntime();
        this.genre = entryBody.getGenre();
        this.director = entryBody.getDirector();
        this.writer = entryBody.getWriter();
        this.plot = entryBody.getPlot();
        this.language = entryBody.getLanguage();
        this.country = entryBody.getCountry();
        this.awards = entryBody.getAwards();
        this.poster = entryBody.getPoster();
        this.rating = entryBody.getRating();
        this.production = entryBody.getProduction();
        this.likes = entryBody.getLikes();
        this.trailer = entryBody.getTrailer();
    }
}

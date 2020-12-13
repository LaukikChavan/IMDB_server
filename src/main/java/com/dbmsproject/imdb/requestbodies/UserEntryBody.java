package com.dbmsproject.imdb.requestbodies;

import java.util.ArrayList;

public class UserEntryBody {

    private String name;
    private ArrayList<String> likedMovies;
    private String password;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserEntryBody(String name, ArrayList<String> likedMovies, String password, String email) {
        this.name = name;
        this.likedMovies = likedMovies;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(ArrayList<String> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserEntryBody() {
    }

    public UserEntryBody(String name, ArrayList<String> likedMovies, String password) {
        this.name = name;
        this.likedMovies = likedMovies;
        this.password = password;
    }
}

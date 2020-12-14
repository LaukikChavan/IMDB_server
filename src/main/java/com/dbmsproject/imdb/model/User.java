package com.dbmsproject.imdb.model;

import com.dbmsproject.imdb.enums.CollectionsName;
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
@Document(collection = CollectionsName.users)
public class User {

    @Id
    private String _id;
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

    public User(String id, String name, ArrayList<String> likedMovies, String password, String email) {
        _id = id;
        this.name = name;
        this.likedMovies = likedMovies;
        this.password = password;
        this.email = email;
    }

    public User(String name, ArrayList<String> likedMovies, String password, String email) {
        this.name = name;
        this.likedMovies = likedMovies;
        this.password = password;
        this.email = email;
    }

    public ArrayList<String> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(ArrayList<String> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {
    }
}

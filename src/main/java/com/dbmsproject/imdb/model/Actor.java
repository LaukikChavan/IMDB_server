package com.dbmsproject.imdb.model;

import com.dbmsproject.imdb.enums.CollectionsName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@Document(collection = CollectionsName.actors)
public class Actor {

    @Id
    private String _id;
    private String name;
    private String image;
    private String dob;

    public Actor() {
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Actor(String _id, String name, String image, String dob) {
        this._id = _id;
        this.name = name;
        this.image = image;
        this.dob = dob;
    }

    @JsonIgnore
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}

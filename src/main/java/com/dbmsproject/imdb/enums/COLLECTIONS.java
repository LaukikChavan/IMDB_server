package com.dbmsproject.imdb.enums;

public enum COLLECTIONS {
    USERS(CollectionsName.users),
    MOVIES(CollectionsName.movies),
    ACTORS(CollectionsName.actors),
    REVIEWS(CollectionsName.reviews);

    private final String value;

    COLLECTIONS(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }
}

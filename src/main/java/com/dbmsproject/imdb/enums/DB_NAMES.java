package com.dbmsproject.imdb.enums;

public enum DB_NAMES {
    USERS("users"),
        ACTORS("actors"),
            REVIEWS("review"),
                MOVIES("movies");

    private final String users;

    DB_NAMES(String users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return users;
    }
}

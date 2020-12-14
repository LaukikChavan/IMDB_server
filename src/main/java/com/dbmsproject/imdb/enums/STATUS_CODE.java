package com.dbmsproject.imdb.enums;

public enum STATUS_CODE {
    ALREADY_FOUND(105),
    CREATED(200),
    INSERTED(201),
    UPDATED(204),
    DELETED(204),
    NOT_FOUND(404),
    WRONG(406),
    ERROR(400);

    private final int i;

    STATUS_CODE(int i) {
        this.i = i;
    }

    @Override
    public String toString() {
        return String.valueOf(i);
    }

    public int getI() {
        return i;
    }
}

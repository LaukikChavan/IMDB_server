package com.dbmsproject.imdb.responsebody;

public class MiniMovieBody {

    private String title;
    private String poster;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public MiniMovieBody() {
    }

    public MiniMovieBody(String title, String poster) {
        this.title = title;
        this.poster = poster;
    }
}

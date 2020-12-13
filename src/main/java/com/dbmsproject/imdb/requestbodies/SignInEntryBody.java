package com.dbmsproject.imdb.requestbodies;

public class SignInEntryBody {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SignInEntryBody() {
    }

    public SignInEntryBody(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

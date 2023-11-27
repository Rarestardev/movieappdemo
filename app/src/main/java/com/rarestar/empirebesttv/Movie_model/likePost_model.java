package com.rarestar.empirebesttv.Movie_model;

public class likePost_model {
    int id;
    String username,movie_name,date;

    String message;

    public likePost_model(int id, String username, String movie_name, String date,String message) {
        this.id = id;
        this.username = username;
        this.movie_name = movie_name;
        this.date = date;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

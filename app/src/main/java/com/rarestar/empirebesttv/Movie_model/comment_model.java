package com.rarestar.empirebesttv.Movie_model;

public class comment_model {

    int id;
    String username,movie_name,text,admin,admin_text,date,message;

    public comment_model(int id, String username, String movie_name, String text, String admin,
                         String admin_text, String date,String message) {
        this.id = id;
        this.username = username;
        this.movie_name = movie_name;
        this.text = text;
        this.admin = admin;
        this.admin_text = admin_text;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getAdmin_text() {
        return admin_text;
    }

    public void setAdmin_text(String admin_text) {
        this.admin_text = admin_text;
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

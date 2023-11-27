package com.rarestar.empirebesttv.Movie_model;

public class user_model {
    String email;
    String username,pass;
    String message;
    String movie_name,desc,category,genre ,director, rate_imdb,rate_user,poster_link,
            age,year,status,country,duration;

    public user_model(String email, String username, String pass,
                      String message, String movie_name, String desc, String category,
                      String genre, String director, String rate_imdb,
                      String rate_user, String poster_link, String age,
                      String year, String status, String country, String duration) {
        this.email = email;
        this.username = username;
        this.pass = pass;
        this.message = message;
        this.movie_name = movie_name;
        this.desc = desc;
        this.category = category;
        this.genre = genre;
        this.director = director;
        this.rate_imdb = rate_imdb;
        this.rate_user = rate_user;
        this.poster_link = poster_link;
        this.age = age;
        this.year = year;
        this.status = status;
        this.country = country;
        this.duration = duration;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getRate_imdb() {
        return rate_imdb;
    }

    public void setRate_imdb(String rate_imdb) {
        this.rate_imdb = rate_imdb;
    }

    public String getRate_user() {
        return rate_user;
    }

    public void setRate_user(String rate_user) {
        this.rate_user = rate_user;
    }

    public String getPoster_link() {
        return poster_link;
    }

    public void setPoster_link(String poster_link) {
        this.poster_link = poster_link;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}

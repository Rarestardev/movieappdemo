package com.rarestar.empirebesttv.Movie_model;

public class movie_model {
    int id;
    String movie_name,desc,category,genre ,director,
            rate_imdb,rate_user,
            age,year,status,country,duration;
    String poster_link,trailer_link;

    public movie_model(int id, String movie_name,
                       String desc, String category,
                       String genre, String director,
                       String rate_imdb, String rate_user, String age, String year,
                       String status, String country, String duration,
                       String poster_link,String trailer_link) {
        this.id = id;
        this.movie_name = movie_name;
        this.desc = desc;
        this.category = category;
        this.genre = genre;
        this.director = director;
        this.rate_imdb = rate_imdb;
        this.rate_user = rate_user;
        this.age = age;
        this.year = year;
        this.status = status;
        this.country = country;
        this.duration = duration;
        this.poster_link = poster_link;
        this.trailer_link = trailer_link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTrailer_link() {
        return trailer_link;
    }

    public void setTrailer_link(String trailer_link) {
        this.trailer_link = trailer_link;
    }
}

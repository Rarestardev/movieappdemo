package com.rarestar.empirebesttv.Movie_model;

public class superstars_model {

    int id;
    String movie_name,superstar_name,superstar_imageLink,movie_image,year;

    public superstars_model(int id, String movie_name, String superstar_name, String superstar_imageLink,String movie_image,String year) {
        this.id = id;
        this.movie_name = movie_name;
        this.superstar_name = superstar_name;
        this.superstar_imageLink = superstar_imageLink;
        this.movie_image = movie_image;
        this.year = year;
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

    public String getSuperstar_name() {
        return superstar_name;
    }

    public void setSuperstar_name(String superstar_name) {
        this.superstar_name = superstar_name;
    }

    public String getSuperstar_imageLink() {
        return superstar_imageLink;
    }

    public void setSuperstar_imageLink(String superstar_imageLink) {
        this.superstar_imageLink = superstar_imageLink;
    }

    public String getMovie_image() {
        return movie_image;
    }

    public void setMovie_image(String movie_image) {
        this.movie_image = movie_image;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}

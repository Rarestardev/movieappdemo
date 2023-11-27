package com.rarestar.empirebesttv.Movie_model;

public class MovieDownloadAndPlay_model {

    int id;
    String movie_name,quality,link;
    String vol;

    public MovieDownloadAndPlay_model(int id, String movie_name, String quality, String link,String vol) {
        this.id = id;
        this.movie_name = movie_name;
        this.quality = quality;
        this.link = link;
        this.vol = vol;
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

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }
}

package com.rarestar.empirebesttv.Movie_model;

public class serial_model {

    int id;
    String serial_name,season,episode,quality,link,subtitle_link,date,vol;

    public serial_model(int id, String serial_name, String season, String episode, String quality, String link, String subtitle_link, String date, String vol) {
        this.id = id;
        this.serial_name = serial_name;
        this.season = season;
        this.episode = episode;
        this.quality = quality;
        this.link = link;
        this.subtitle_link = subtitle_link;
        this.date = date;
        this.vol = vol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerial_name() {
        return serial_name;
    }

    public void setSerial_name(String serial_name) {
        this.serial_name = serial_name;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
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

    public String getSubtitle_link() {
        return subtitle_link;
    }

    public void setSubtitle_link(String subtitle_link) {
        this.subtitle_link = subtitle_link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }
}

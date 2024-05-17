package com.example.movieapp.Model;

import java.io.Serializable;

public class Film implements Serializable {
    private String Title;
    private String Description;
    private String Poster;
    private String Time;
    private String Trailer;
    private int Imdb;
    private int Year;
    private String Genre;
    private String Casts;

    public Film() {
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTrailer() {
        return Trailer;
    }

    public void setTrailer(String trailer) {
        Trailer = trailer;
    }

    public int getImdb() {
        return Imdb;
    }

    public void setImdb(int imdb) {
        Imdb = imdb;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getCasts() {
        return Casts;
    }

    public void setCasts(String casts) {
        Casts = casts;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }
}

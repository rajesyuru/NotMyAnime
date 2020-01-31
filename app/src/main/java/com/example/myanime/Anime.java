package com.example.myanime;

public class Anime {
    int animeId;
    String title;
    String cover;
    float rating;

    public Anime(int animeId, String title, String cover, float rating) {
        this.animeId = animeId;
        this.title = title;
        this.cover = cover;
        this.rating = rating;
    }

    public int getAnimeId() {
        return animeId;
    }

    public void setAnimeId(int animeId) {
        this.animeId = animeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}

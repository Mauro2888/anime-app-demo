package com.app.home.home.Model;

import java.io.Serializable;

public class AnimeModel implements Serializable {
    String title;
    String imageUrl;
    String description;
    String episodes;
    String score;
    int pos;
    String volumes;



    public AnimeModel(String title, String imageUrl, String description, String episodes, String score) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.episodes = episodes;
        this.score = score;
    }

    public AnimeModel(String title, String imageUrl, String description, String episodes, String score, int pos) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.episodes = episodes;
        this.score = score;
        this.pos = pos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEpisodes() {
        return episodes;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getVolumes() {
        return volumes;
    }

    public void setVolumes(String volumes) {
        this.volumes = volumes;
    }
}

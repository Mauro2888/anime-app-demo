package com.app.home.home.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DAOAnime {
    @SerializedName("request_hash")
    @Expose
    private String requestHash;
    @SerializedName("request_cached")
    @Expose
    private Boolean requestCached;
    @SerializedName("request_cache_expiry")
    @Expose
    private Integer requestCacheExpiry;
    @SerializedName("result")
    @Expose
    private List<Result> result;
    @SerializedName("result_last_page")
    @Expose
    private Integer resultLastPage;

    public String getRequestHash() {
        return requestHash;
    }

    public void setRequestHash(String requestHash) {
        this.requestHash = requestHash;
    }

    public Boolean getRequestCached() {
        return requestCached;
    }

    public void setRequestCached(Boolean requestCached) {
        this.requestCached = requestCached;
    }

    public Integer getRequestCacheExpiry() {
        return requestCacheExpiry;
    }

    public void setRequestCacheExpiry(Integer requestCacheExpiry) {
        this.requestCacheExpiry = requestCacheExpiry;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public Integer getResultLastPage() {
        return resultLastPage;
    }

    public void setResultLastPage(Integer resultLastPage) {
        this.resultLastPage = resultLastPage;
    }

    public class Result {
        @SerializedName("mal_id")
        @Expose
        private Integer malId;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("image_url")
        @Expose
        private String imageUrl;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("episodes")
        @Expose
        private Integer episodes;
        @SerializedName("score")
        @Expose
        private Double score;
        @SerializedName("members")
        @Expose
        private Integer members;

        public Integer getMalId() {
            return malId;
        }

        public void setMalId(Integer malId) {
            this.malId = malId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getEpisodes() {
            return episodes;
        }

        public void setEpisodes(Integer episodes) {
            this.episodes = episodes;
        }

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }

        public Integer getMembers() {
            return members;
        }

        public void setMembers(Integer members) {
            this.members = members;
        }

    }
}

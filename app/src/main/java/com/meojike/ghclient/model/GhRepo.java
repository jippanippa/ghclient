package com.meojike.ghclient.model;

import com.google.gson.annotations.SerializedName;

public class GhRepo {
    @SerializedName("name")
    private String repoName;

    @SerializedName("description")
    private String description;

    @SerializedName("owner")
    private GhUser owner;

    @SerializedName("forks_count")
    private int forksCount;

    @SerializedName("watchers")
    private int watchers;

    public String getRepoName() {
        return repoName;
    }

    public String getDescription() {
        return description;
    }

    public GhUser getOwner() {
        return owner;
    }

    public int getForksCount() {
        return forksCount;
    }

    public int getWatchers() {
        return watchers;
    }
}

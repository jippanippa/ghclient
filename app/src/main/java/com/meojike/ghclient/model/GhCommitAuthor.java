package com.meojike.ghclient.model;

import com.google.gson.annotations.SerializedName;

public class GhCommitAuthor {
    @SerializedName("name")
    private String name;

    @SerializedName("date")
    private String date;

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}

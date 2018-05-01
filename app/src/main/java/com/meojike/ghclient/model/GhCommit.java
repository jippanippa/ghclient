package com.meojike.ghclient.model;

import com.google.gson.annotations.SerializedName;

public class GhCommit {
    @SerializedName("message")
    private String message;

    @SerializedName("author")
    private GhCommitAuthor author;

    public String getMessage() {
        return message;
    }

    public GhCommitAuthor getAuthor() {
        return author;
    }
}

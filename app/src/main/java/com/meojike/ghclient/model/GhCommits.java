package com.meojike.ghclient.model;

import com.google.gson.annotations.SerializedName;

public class GhCommits {
    @SerializedName("sha")
    private String sha;

    @SerializedName("commit")
    private GhCommit commit;

    public String getSha() {
        return sha;
    }

    public GhCommit getCommit() {
        return commit;
    }
}

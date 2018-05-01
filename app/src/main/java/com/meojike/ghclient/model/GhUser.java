package com.meojike.ghclient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GhUser {
    @SerializedName("login")
    String login;

    @SerializedName("avatar_url")
    String avatarUrl;

    @SerializedName("repos_url")
    String reposUrl;

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getReposUrl() { return reposUrl; }
}

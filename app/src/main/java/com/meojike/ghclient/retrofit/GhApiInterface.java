package com.meojike.ghclient.retrofit;

import com.meojike.ghclient.model.GhAccessToken;
import com.meojike.ghclient.model.GhCommits;
import com.meojike.ghclient.model.GhRepo;
import com.meojike.ghclient.model.GhUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GhApiInterface {

    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    Call<GhAccessToken> getAccessToken(
        @Field("client_id") String clientId,
        @Field("client_secret") String clientSecret,
        @Field("code") String code
    );

    @GET("/user/repos")
    Call<List<GhRepo>> getReposByAccessToken(@Query("access_token") String accessToken);

    @GET("/user")
    Call<GhUser> getUserByAccessToken(@Query("access_token") String accessToken);

    @GET("/repos/{user}/{repo}/commits")
    Call<List<GhCommits>> getCommits(@Path("user") String user, @Path("repo") String repo);
}

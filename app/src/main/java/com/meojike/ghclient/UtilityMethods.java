package com.meojike.ghclient;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.meojike.ghclient.retrofit.GhApiInterface;
import com.meojike.ghclient.ui.ConnectionProblemsFragment;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UtilityMethods {

    private static GhApiInterface authClient;
    private static GhApiInterface client;

    public static GhApiInterface getAuthGhApiInterfaceClient() {
        if(authClient == null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://github.com")
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            authClient = retrofit.create(GhApiInterface.class);
        }

        return authClient;
    }

    public static GhApiInterface getGhApiInterfaceClient() {
        if(client == null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            client = retrofit.create(GhApiInterface.class);
        }

        return client;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;

        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }

    public static void alertUserAboutInternetConnectionError(Context context) {
        ConnectionProblemsFragment dialog = new ConnectionProblemsFragment();
        dialog.show(((Activity)context).getFragmentManager(), "error_dialog");
    }
}

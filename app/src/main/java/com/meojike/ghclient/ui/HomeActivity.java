package com.meojike.ghclient.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.meojike.ghclient.UtilityMethods;
import com.meojike.ghclient.R;
import com.meojike.ghclient.adapter.ReposAdapter;
import com.meojike.ghclient.model.GhAccessToken;
import com.meojike.ghclient.model.GhRepo;
import com.meojike.ghclient.model.GhUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.meojike.ghclient.UtilityMethods.alertUserAboutInternetConnectionError;
import static com.meojike.ghclient.UtilityMethods.isNetworkAvailable;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private Context mContext;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private String clientId = "3a4259a38480a9e88ad4";
    private String clientSecret = "5030a819b65061cf769a84ce467c6ef302c5b68d";
    private String redirectUri = "ghclient://callback";

    public static String accessToken = "";

    private GridView mReposGridView;
    private List<GhRepo> mGhRepos;
    private GhUser mGhUser;
    private ProgressBar mProgressBar;
    private TextView mDefaultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = HomeActivity.this;

        if(!isNetworkAvailable(mContext)) {
            alertUserAboutInternetConnectionError(mContext);
        }

        mSharedPreferences = mContext.getSharedPreferences("ghclient_prefs", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mReposGridView = findViewById(R.id.gridView);
        mProgressBar = findViewById(R.id.homeActivityProgressBar);
        mDefaultText = findViewById(R.id.defaultReposTextView);
        mDefaultText.setVisibility(View.GONE);


        accessToken = mSharedPreferences.getString("accessToken", "");

        if(accessToken.isEmpty()) {
            if (getIntent().getData() == null) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/login/oauth/authorize?client_id="
                        + clientId + "&scope=repo&redirect_uri=" + redirectUri));
                startActivity(intent);
            } else {
                passAuthorization();
            }
        } else {
            showGhRepos();
        }
    }

    private void showGhRepos() {
        Call<List<GhRepo>> call = UtilityMethods.getGhApiInterfaceClient()
        .getReposByAccessToken(accessToken);
        call.enqueue(new Callback<List<GhRepo>>() {
            @Override
            public void onResponse(Call<List<GhRepo>> call, Response<List<GhRepo>> response) {
                mGhRepos = response.body();
                mGhUser = mGhRepos.get(0).getOwner();
                setTitle("Репозитории " + mGhUser.getLogin());

                if(mGhRepos.size() == 0) {
                    mDefaultText.setVisibility(View.VISIBLE);
                } else {
                    ReposAdapter mReposAdapter = new ReposAdapter(mContext, R.layout.layout_repository_item, mGhRepos);
                    mReposGridView.setAdapter(mReposAdapter);
                    mReposGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(view.getContext(), CommitsActivity.class);
                            intent.putExtra("username", mGhUser.getLogin());
                            intent.putExtra("reponame", mGhRepos.get(i).getRepoName());
                            startActivity(intent);
                        }
                    });
                }
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<GhRepo>> call, Throwable t) {
            }
        });
    }

    private void passAuthorization() {
        Uri uri = getIntent().getData();

        if(uri != null && uri.toString().startsWith(redirectUri)) {
            String code = uri.getQueryParameter("code");

            Call<GhAccessToken> accessTokenCall = UtilityMethods.getAuthGhApiInterfaceClient()
                    .getAccessToken(clientId, clientSecret, code);

            accessTokenCall.enqueue(new Callback<GhAccessToken>() {
                @Override
                public void onResponse(Call<GhAccessToken> call, Response<GhAccessToken> response) {
                    accessToken = response.body().getAccessToken();
                    mEditor.putString("accessToken", accessToken);
                    mEditor.commit();
                    showGhRepos();
                }

                @Override
                public void onFailure(Call<GhAccessToken> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.toString());
                }
            });
        }
    }
}

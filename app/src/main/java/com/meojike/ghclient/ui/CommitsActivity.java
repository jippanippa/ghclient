package com.meojike.ghclient.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.meojike.ghclient.UtilityMethods;
import com.meojike.ghclient.R;
import com.meojike.ghclient.adapter.CommitsAdapter;
import com.meojike.ghclient.model.GhCommits;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.meojike.ghclient.UtilityMethods.alertUserAboutInternetConnectionError;
import static com.meojike.ghclient.UtilityMethods.isNetworkAvailable;

public class CommitsActivity extends AppCompatActivity {
    private static final String TAG = "CommitsActivity";

    private Context mContext;
    private List<GhCommits> mGhCommits;

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private TextView mDefaultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commits);
        mContext = CommitsActivity.this;

        String username = getIntent().getStringExtra("username");
        String reponame = getIntent().getStringExtra("reponame");
        setTitle("Коммиты " + reponame);

        if(!isNetworkAvailable(mContext)) {
            alertUserAboutInternetConnectionError(mContext);
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mProgressBar = findViewById(R.id.homeActivityProgressBar);
        mDefaultText = findViewById(R.id.defaultCommitsTextView);
        mDefaultText.setVisibility(View.GONE);

        getCommitsData(username, reponame);
    }

    private void getCommitsData(String username, String reponame) {

        Call<List<GhCommits>> call = UtilityMethods.getGhApiInterfaceClient()
                .getCommits(username, reponame);
        call.enqueue(new Callback<List<GhCommits>>() {
            @Override
            public void onResponse(Call<List<GhCommits>> call, Response<List<GhCommits>> response) {
                mProgressBar.setVisibility(View.GONE);

                mGhCommits = response.body();

                if(mGhCommits == null || mGhCommits.size() == 0) {
                    mDefaultText.setVisibility(View.VISIBLE);
                } else {
                    CommitsAdapter commitsAdapter = new CommitsAdapter(mGhCommits);
                    mRecyclerView.setAdapter(commitsAdapter);

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                    mRecyclerView.setLayoutManager(layoutManager);
                }
            }

            @Override
            public void onFailure(Call<List<GhCommits>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}

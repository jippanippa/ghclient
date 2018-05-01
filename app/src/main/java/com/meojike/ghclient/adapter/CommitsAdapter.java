package com.meojike.ghclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meojike.ghclient.R;
import com.meojike.ghclient.model.GhCommits;

import java.util.List;

public class CommitsAdapter extends RecyclerView.Adapter<CommitsAdapter.CommitsViewHolder> {

    List<GhCommits> mGhCommits;

    public CommitsAdapter(List<GhCommits> ghCommits) {
        mGhCommits = ghCommits;
    }

    @Override
    public CommitsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_commit_item, parent, false);
        CommitsViewHolder commitsViewHolder = new CommitsViewHolder(view);
        return commitsViewHolder;
    }

    @Override
    public void onBindViewHolder(CommitsViewHolder holder, int position) {
        holder.bindCommit(mGhCommits.get(position));
    }

    @Override
    public int getItemCount() {
        return mGhCommits.size();
    }

    public class CommitsViewHolder extends RecyclerView.ViewHolder {
        public TextView mSha;
        public TextView mCommitMessage;
        public TextView mCommitAuthor;
        public TextView mDate;

        public CommitsViewHolder(View itemView) {
            super(itemView);

            mSha = itemView.findViewById(R.id.sha);
            mCommitMessage = itemView.findViewById(R.id.commitMessage);
            mCommitAuthor = itemView.findViewById(R.id.commitAuthor);
            mDate = itemView.findViewById(R.id.date);
        }

        public void bindCommit(GhCommits ghCommits) {
            mSha.setText(ghCommits.getSha());
            mCommitMessage.setText(ghCommits.getCommit().getMessage());
            mCommitAuthor.setText(ghCommits.getCommit().getAuthor().getName());
            mDate.setText(ghCommits.getCommit().getAuthor().getDate());
        }

    }
}

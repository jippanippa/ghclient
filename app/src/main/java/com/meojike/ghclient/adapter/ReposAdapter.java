package com.meojike.ghclient.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meojike.ghclient.R;
import com.meojike.ghclient.model.GhRepo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import java.util.List;

public class ReposAdapter extends ArrayAdapter<GhRepo> {
    private Context mContext;
    private LayoutInflater mInflater;
    private int layoutResource;
    private List<GhRepo> mGhRepos;
    private ImageLoader imageLoader;

    public ReposAdapter(Context context, int layoutResource, List<GhRepo> ghRepos) {
        super(context, layoutResource, ghRepos);
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutResource = layoutResource;
        this.mGhRepos = ghRepos;
        setImageLoader();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ReposViewHolder reposViewHolder;

        if(convertView == null) {
            convertView = mInflater.inflate(layoutResource, parent, false);
            reposViewHolder = new ReposViewHolder();
            reposViewHolder.mRepoName = convertView.findViewById(R.id.repoItemName);
            reposViewHolder.mRepoDescription = convertView.findViewById(R.id.repoItemDescription);
            reposViewHolder.mRepoAuthor = convertView.findViewById(R.id.repoAuthorName);
            reposViewHolder.mForksNumber = convertView.findViewById(R.id.forksNumber);
            reposViewHolder.mWatchersNumber = convertView.findViewById(R.id.watchersNumber);
            reposViewHolder.mRepoItem = convertView.findViewById(R.id.repoItem);

            convertView.setTag(reposViewHolder);
        } else {
            reposViewHolder = (ReposViewHolder) convertView.getTag();
        }

        reposViewHolder.mRepoName.setText(mGhRepos.get(position).getRepoName());
        reposViewHolder.mRepoAuthor.setText(mGhRepos.get(position).getOwner().getLogin());
        reposViewHolder.mForksNumber.setText(String.valueOf(mGhRepos.get(position).getForksCount()));
        reposViewHolder.mWatchersNumber.setText(String.valueOf(mGhRepos.get(position).getWatchers()));
        if(mGhRepos.get(position).getDescription() != null) {
            reposViewHolder.mRepoDescription.setText(mGhRepos.get(position).getDescription());
        }

        String avatarUrl = mGhRepos.get(position).getOwner().getAvatarUrl();
        List<Bitmap> bitmaps = MemoryCacheUtils.
                findCachedBitmapsForImageUri(avatarUrl, ImageLoader.getInstance().getMemoryCache());

        if(bitmaps.isEmpty()) {
            imageLoader.loadImage(avatarUrl, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    reposViewHolder.mRepoItem.setBackground(new BitmapDrawable(mContext.getResources(), loadedImage));
                }
            });
        } else {
            reposViewHolder.mRepoItem.setBackground(new BitmapDrawable(mContext.getResources(),
                    bitmaps.get(bitmaps.size() - 1)));
        }


        return convertView;
    }

    private static class ReposViewHolder {
        public TextView mRepoName;
        public TextView mRepoDescription;
        public TextView mRepoAuthor;
        public TextView mForksNumber;
        public TextView mWatchersNumber;
        public RelativeLayout mRepoItem;
    }

    private void setImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }
}

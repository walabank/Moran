package com.geekband.snap.moran.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekband.snap.moran.R;
import com.geekband.snap.moran.model.ImageItem;

import java.util.List;

public class ImageItemAdapter extends RecyclerView.Adapter<ImageItemAdapter.ViewHolder> {

    private List<ImageItem> mImageItems;

    public ImageItemAdapter(List<ImageItem> imageItems) {
        mImageItems = imageItems;
    }


    @Override
    public ImageItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent,
                false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ImageItemAdapter.ViewHolder holder, int position) {
        holder.mPhoto.setImageResource(mImageItems.get(position).getImageId());
        holder.mComment.setText(mImageItems.get(position).getComment());

    }


    @Override
    public int getItemCount() {
        return mImageItems == null ? 0 : mImageItems.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mPhoto;
        public TextView mComment;

        public ViewHolder(View itemView) {
            super(itemView);
            mPhoto = (ImageView) itemView.findViewById(R.id.photo);
            mComment = (TextView) itemView.findViewById(R.id.comment);
        }
    }
}
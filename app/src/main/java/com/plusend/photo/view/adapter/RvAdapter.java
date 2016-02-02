package com.plusend.photo.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.plusend.photo.R;
import com.plusend.photo.db.PhotoDAO;
import com.plusend.photo.db.handler.PhotoCursorHandler;
import com.plusend.photo.model.Photo;

import java.util.List;

/**
 * Created by plusend on 16/2/2.
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvHolder> {

    private final LayoutInflater mLayoutInflater;
    private List<Photo> photos;

    public RvAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        PhotoDAO photoDAO = new PhotoDAO(context);
        photos = photoDAO.query(new String[]{"pic", "level", "note", "date"}, null, null, new PhotoCursorHandler());
    }

    @Override
    public RvAdapter.RvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RvHolder(mLayoutInflater.inflate(R.layout.recycle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RvAdapter.RvHolder holder, int position) {
        holder.imageView.setImageBitmap(photos.get(position).getPic());
    }

    @Override
    public int getItemCount() {
        return photos == null ? 0 : photos.size();
    }

    public static class RvHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public RvHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}

package com.plusend.photo.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.plusend.photo.R;
import com.plusend.photo.model.Photo;
import com.plusend.photo.utils.log.Logger;

import java.util.Calendar;
import java.util.List;

/**
 * Created by plusend on 16/2/2.
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvHolder> {

    private static final String TAG = "RvAdapter";
    private List<Photo> photos;

    public RvAdapter(List<Photo> list) {
        photos = list;
    }

    private OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    @Override
    public RvAdapter.RvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RvAdapter.RvHolder holder, int position) {
        Logger.d(TAG, "position:" + position);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(photos.get(position).getDate());
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            holder.textView.setText(calendar.get(Calendar.YEAR) + "." + (calendar.get(Calendar.MONTH) + 1)
                    + "." + calendar.get(Calendar.DATE));
            holder.textView.setVisibility(View.VISIBLE);
        }else{
            holder.textView.setVisibility(View.INVISIBLE);
        }
        holder.imageView.setImageBitmap(photos.get(position).getPic());
    }

    @Override
    public int getItemCount() {
        return photos == null ? 0 : photos.size();
    }

    public class RvHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView textView;

        public RvHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.date);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(v, getPosition());
            }
        }
    }

}

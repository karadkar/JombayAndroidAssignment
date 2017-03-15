package io.github.karadkar.jombaytest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rnztx on 15/3/17.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>{

    private List<Photo> mPhotos;
    private Context mContext;

    public PhotoAdapter(Context context) {
        this.mPhotos = new ArrayList<>();
        mContext = context;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Photo photo = mPhotos.get(position);
        holder.titleTextView.setText(photo.getTitle());
        /*Picasso.with(mContext)
                .load("http://placehold.it/150/a5a748")
                .into(holder.imageView);*/
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item,parent,false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.photo_item_imageView);
            titleTextView = (TextView)itemView.findViewById(R.id.photo_item_title_textView);
        }
    }

    public void addPhotos(List<Photo> photos){
        mPhotos.addAll(photos);
        notifyDataSetChanged();
    }
}

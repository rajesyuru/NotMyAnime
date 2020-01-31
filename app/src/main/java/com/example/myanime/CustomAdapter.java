package com.example.myanime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Anime> {

    private Context mContext;
    private int mResource;
    private ArrayList<Anime> mAnime;

    public static class ViewHolder {
        ImageView cover;
    }

    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Anime> anime) {
        super(context, resource, anime);

        mContext = context;
        mResource = resource;
        mAnime = anime;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = layoutInflater.inflate(mResource, parent, false);
            viewHolder.cover = convertView.findViewById(R.id.ivCover);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Anime anime = mAnime.get(position);

        Picasso.with(mContext).load(anime.getCover()).into(viewHolder.cover);

        return convertView;
    }
}

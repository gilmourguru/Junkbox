package com.junkheadaictribute.tools.junkbox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Gary on 8/28/2016.
 */

public class SetlistAdapter extends RecyclerView.Adapter<SetlistAdapter.MyViewHolder> {

    private LayoutInflater inflater;

    List<SetlistSong> setlistData = Collections.emptyList();

    public SetlistAdapter(Context context, List<SetlistSong> setlistData) {
        inflater = LayoutInflater.from(context);
        this.setlistData = setlistData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.setlist_song_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

     SetlistSong current = setlistData.get(position);
     holder.songTitle.setText(current.songTitle);
        holder.fromAlbum.setText(current.album);
        holder.setlistAlbumIcon.setImageResource(current.albumArt);
        holder.songBpm.setText(current.tempo);
        holder.songDuration.setText(current.time);

    }

    @Override
    public int getItemCount() {
        return setlistData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView songTitle;
        TextView songDuration;
        TextView fromAlbum;
        ImageView setlistAlbumIcon;
        TextView songBpm;

        public MyViewHolder(View itemView) {
            super(itemView);
            songTitle = (TextView) itemView.findViewById(R.id.songTitle);
            songDuration = (TextView) itemView.findViewById(R.id.songDuration);
            fromAlbum = (TextView) itemView.findViewById(R.id.fromAlbum);
            setlistAlbumIcon = (ImageView) itemView.findViewById(R.id.setlistAlbumIcon);
            songBpm = (TextView) itemView.findViewById(R.id.songBpm);

        }

    }
}

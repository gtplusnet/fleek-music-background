package com.video.videomusic;

import android.app.Dialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.video.videomusic.MainActivity.isSelectedMusic;
import static com.video.videomusic.MainActivity.musicName;
import static com.video.videomusic.MusicGallery.MGselectedMusic;
import static com.video.videomusic.MusicGallery.allMediaPlayer;
import static com.video.videomusic.MusicGallery.checkedImageView;

public class CustomAdapter extends BaseAdapter{
    private Context context;
    private Dialog dialog;
    private final JSONArray musicData;
    private int layoutToPut;
    private String selectedMusic;
    private MediaPlayer mediaPlayer;

    CustomAdapter(Context context, JSONArray musicData, int layoutToPut) {
        this.context = context;
        this.musicData = musicData;
        this.layoutToPut = layoutToPut;
        dialog = new Dialog(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView = null;

        if (convertView == null) {
            try {
                gridView = new View(context);

                // get layout from mobile.xml
                JSONObject data = musicData.getJSONObject(position).getJSONObject("data");
                String id = musicData.getJSONObject(position).getString("id");

                gridView = inflater.inflate(this.layoutToPut, null);

                ImageView imageView = gridView.findViewById(R.id.music_thumbnail);
                ImageView check = gridView.findViewById(R.id.selected_check);
                Glide.with(context)
                        .load(data.getString("thumbnail_link"))
                        .into(imageView);

                // set value into textview
                TextView musicTitle = (TextView) gridView.findViewById(R.id.music_title);
                musicTitle.setTag(id);
                musicTitle.setText(data.getString("audio_name"));

                TextView musicArtist = (TextView) gridView.findViewById(R.id.music_artist);
                musicArtist.setTag(id);
                musicArtist.setText(data.getString("artist"));

                TextView musicDuration = (TextView) gridView.findViewById(R.id.music_duration);
                String duration = data.getString("duration");
                musicDuration.setTag(id);
                musicDuration.setText(duration);

                musicTitle.setOnClickListener(new SelectedMusic(data.getString("audio_link"),data.getString("audio_name"),check));
                imageView.setOnClickListener(new SelectedMusic(data.getString("audio_link"),data.getString("audio_name"),check));
                musicArtist.setOnClickListener(new SelectedMusic(data.getString("audio_link"),data.getString("audio_name"),check));

            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        else{
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return 6;
    } // 6 lang ididisplay

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public class SelectedMusic implements View.OnClickListener {

        String musicLink;
        String name;
        ImageView check;
        SelectedMusic(String link, String name, ImageView check) {
            this.musicLink = link;
            this.name = name;
            this.check = check;
        }
        @Override
        public void onClick(View v) {
            if(allMediaPlayer != null) allMediaPlayer.stop(); allMediaPlayer = null;
            allMediaPlayer = new MediaPlayer();
            allMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                if(checkedImageView != null)  checkedImageView.setVisibility(View.GONE);
                checkedImageView = check;
                check.setVisibility(View.VISIBLE);
                selectedMusic = musicLink;
                MGselectedMusic = selectedMusic;
                allMediaPlayer.setDataSource(musicLink);
                allMediaPlayer.prepare();
                allMediaPlayer.start();
                isSelectedMusic = true;
                musicName = name;
            } catch (Exception e) {
                //
            }
        }
    }


}

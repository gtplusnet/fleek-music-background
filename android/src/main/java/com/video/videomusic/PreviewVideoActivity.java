package com.video.videomusic;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.getcapacitor.JSObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PreviewVideoActivity extends AppCompatActivity {
    String videoPath;
    ImageButton delete;
    ImageButton save;
    Context context = this;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        videoPath = bundle.getString("path");
        setContentView(R.layout.activity_preview_video);
        initVideoPreview();
    }

    public void initVideoPreview()
    {
        final VideoView mVideoView = (VideoView) findViewById(R.id.videoView);
        mVideoView.setVideoPath(videoPath);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.start();
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideoView.start();
            }
        });
        delete = findViewById(R.id.delete);
        save = findViewById(R.id.save);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = initPopup();
                dialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            JSObject res = new JSObject();
                File thumbnail = savebitmap("thumbnail");
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(videoPath);
                res.put("thumbnail",thumbnail.getAbsolutePath());
                res.put("data",jsonArray);
                VideoBackgroundMusic.returnResponse(res,true,context);
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
                finish();
            }
        });
    }

    private Dialog initPopup(){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_delete_video);
        TextView no = (TextView) dialog.findViewById(R.id.no);
        TextView yes = (TextView) dialog.findViewById(R.id.yes);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(videoPath);
                if(file.exists()){
                    file.delete();
                }
                finish();
            }
        });

        return dialog;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dialog = initPopup();
        dialog.show();
    }

    private File savebitmap(String filename) {
        String extStorageDirectory = Environment.getExternalStorageDirectory() + "/Android/data/";
        OutputStream outStream = null;

        File file = new File(extStorageDirectory,filename + ".png");
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, filename + ".png");
        }else
        {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            // make a new bitmap from your file
            Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);

            outStream = new FileOutputStream(file);
            bmThumbnail.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;

    }
}

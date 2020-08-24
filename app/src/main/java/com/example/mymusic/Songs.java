package com.example.mymusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Songs extends AppCompatActivity {

    int songs,imagee;
    ImageView iv;
    TextView textView,t,t2;
    MediaPlayer mp;
    ImageButton ib1,ib2,ib3,ib4,ib5;
    int stopped = 0;
    private SeekBar volumeSeekbar = null,seekBar;
    private AudioManager audioManager = null;
    private Handler mSeekbarUpdateHandler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);
        iv = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView2);
        t=findViewById(R.id.textView4);
        final String s = getIntent().getExtras().getString("song");
        String si = getIntent().getExtras().getString("image");
        String namee = getIntent().getExtras().getString("namesong");
        imagee = Integer.parseInt(si);
        songs = Integer.parseInt(s);
        iv.setImageResource(imagee);
        textView.setText(namee);
        ib4 = findViewById(R.id.imageButton4);
        ib4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s1 = getIntent().getExtras().getString("nextsong");
                String si1 = getIntent().getExtras().getString("nextimage");
                String namee1 = getIntent().getExtras().getString("nextnamesong");

                imagee = Integer.parseInt(si1);
                songs = Integer.parseInt(s1);
                iv.setImageResource(imagee);
                textView.setText(namee1);
            }
        });
        ib5 = findViewById(R.id.imageButton5);
        ib5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s1 = getIntent().getExtras().getString("prevsong");
                String si1 = getIntent().getExtras().getString("previmage");
                String namee1 = getIntent().getExtras().getString("prevnamesong");

                imagee = Integer.parseInt(si1);
                songs = Integer.parseInt(s1);
                iv.setImageResource(imagee);
                textView.setText(namee1);
            }
        });
        initControls();
        mp = new MediaPlayer();
        mp = MediaPlayer.create(getApplicationContext(),songs);
        ib1 = findViewById(R.id.imageButton);
        ib2 = findViewById(R.id.imageButton2);
        if(mp.isPlaying() && mp!=null)
        {
            mp.stop();
            mp.release();
            seekBar.setProgress(0);
        }
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!mp.isPlaying()){
                    mp.start();
                    ib1.setImageResource(R.drawable.ic_media_pause);
                    seekBar.setMax(mp.getDuration());
                    mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);

                }
                else
                {
                    mp.pause();
                    ib1.setImageResource(R.drawable.ic_media_play);
                    mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);

                }
            }
        });
       ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp.isPlaying()){
                    mp.pause();
                    seekBar.setProgress(0);
                    mp.seekTo(0);
                    stopped =1;
                    ib1.setImageResource(R.drawable.ic_media_play);
                }
                else{
                    Toast.makeText(getApplicationContext(),"SONG IS NOT PLAYING!",Toast.LENGTH_SHORT).show();
                }
            }
        });
       ib3 = findViewById(R.id.imageButton3);
        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp.isPlaying() ){
                    mp.pause();
                    mp.seekTo(0);
                    mp.start();
                    seekBar.setProgress(0);
                    ib1.setImageResource(R.drawable.ic_media_pause);
                }
                else{
                    Toast.makeText(getApplicationContext(),"SONG IS NOT PLAYING!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        seekBar = findViewById(R.id.seekBar2);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                int x = (int) Math.ceil(progress / 1000f);

                if ( mp != null && !mp.isPlaying()) {
                    Songs.this.seekBar.setProgress(0);
                }
                if (fromTouch)
                    mp.seekTo(progress);
                t2.setText(String.format("%d:%d", (progress % (1000 * 60 * 60)/ (1000 * 60)),(progress % (1000 * 60 * 60)) % (1000 * 60)/ 1000));
            }



            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


                if (mp!= null && mp.isPlaying()) {
                    mp.seekTo(seekBar.getProgress());
                }
            }
        });
        int currentPosition = mp.getCurrentPosition();
        int total = mp.getDuration();
        t.setText(String.format("%d:%d", (total % (1000 * 60 * 60)/ (1000 * 60)),(total % (1000 * 60 * 60)) % (1000 * 60)/ 1000) );
        t2 = findViewById(R.id.textView3);


        while (mp != null && mp.isPlaying() && currentPosition < total) {
            try {
                Thread.sleep(1000);
                currentPosition = mp.getCurrentPosition();
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                return;
            }

            seekBar.setProgress(currentPosition);

        }

    }
    private void initControls() {
        try {
            volumeSeekbar = (SeekBar) findViewById(R.id.seekBar);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {
        if(mp.isPlaying()==true)
        {
            mp.stop();
        }
        this.finish();
        super.onBackPressed();
    }

    private Runnable mUpdateSeekbar = new Runnable() {
        @Override
        public void run() {
            seekBar.setProgress(mp.getCurrentPosition());
            mSeekbarUpdateHandler.postDelayed(this, 50);
        }
    };

}
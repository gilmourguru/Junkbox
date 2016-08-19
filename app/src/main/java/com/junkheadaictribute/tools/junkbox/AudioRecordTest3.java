package com.junkheadaictribute.tools.junkbox;

import android.media.MediaPlayer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.junkheadaictribute.tools.junkbox.ExtAudioRecorder.*;

public class AudioRecordTest3 extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ImageView recordBtn;
    private ImageView stopRecordBtn;
    private ImageView playBtn;
    private ImageView stopPlaybackBtn;
    private TextView recordingStatus;
    private TextView playbackStatus;
    private RadioButton lossless;
    private RadioButton lossy;
    private boolean rCompressed;
    private ExtAudioRecorder mRecorder;
    private String outputFile = null;
    private MediaPlayer myPlayer;
    private boolean RECORDING_UNCOMPRESSED = true;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_record_test3);
        toolbar = (Toolbar) findViewById(R.id.app_bar_audiotest3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recordBtn = (ImageView) findViewById(R.id.recordBtn);
        recordBtn.setOnClickListener(this);
        stopRecordBtn = (ImageView) findViewById(R.id.stopRecordBtn);
        stopRecordBtn.setOnClickListener(this);
        playBtn = (ImageView) findViewById(R.id.playBtn);
        playBtn.setOnClickListener(this);
        stopPlaybackBtn = (ImageView) findViewById(R.id.stopPlaybackBtn);
        stopPlaybackBtn.setOnClickListener(this);
        recordingStatus = (TextView) findViewById(R.id.recordStatus_textview);
        recordingStatus.setText("Ready...");
        playbackStatus = (TextView) findViewById(R.id.playbackStatus_textview);
        playbackStatus.setText("No Recordings...");
        lossless = (RadioButton) findViewById(R.id.losslessBtn);
        lossless.setChecked(true);
        rCompressed = false;
        lossless.setOnClickListener(this);
        lossy = (RadioButton) findViewById(R.id.lossyBtn);
        lossy.setOnClickListener(this);
        initializeRecorder();




    }

    public void onClick(View v) {
        // do something when the button is clicked
        int id = v.getId();

        if (id == R.id.recordBtn) {
            try{
                recordingStatus.setText("Active");
                playbackStatus.setText("Stop recording first...");
                recordBtn.setImageResource(R.drawable.ic_fiber_smart_record_red_a700_48dp);
                if (mRecorder == null) mRecorder = ExtAudioRecorder.getInstanse(rCompressed);
                mRecorder.prepare();
                mRecorder.start();
                recordBtn.setEnabled(false);
                stopRecordBtn.setEnabled(true);

                Toast.makeText(this,"You pressed Record.",Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (id == R.id.stopRecordBtn) {
            try {
                recordingStatus.setText("Ready...");
                playbackStatus.setText("Ready...");
                recordBtn.setImageResource(R.drawable.ic_fiber_manual_record_red_a700_48dp);
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
                recordBtn.setEnabled(true);
                playBtn.setEnabled(true);
                stopRecordBtn.setEnabled(false);


                Toast.makeText(this, "You pressed 'Stop Record'", Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
            }

            initializeRecorder();

        }
        if (id == R.id.playBtn) {
            try {
                myPlayer = new MediaPlayer();
                myPlayer.setDataSource(outputFile);
                myPlayer.prepare();
                myPlayer.start();
                playBtn.setEnabled(false);
                stopPlaybackBtn.setEnabled(true);
                recordBtn.setEnabled(false);
                recordingStatus.setText("Stop playback first...");
                playbackStatus.setText("Playing...");

                Toast.makeText(this, "You pressed Play.", Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (id == R.id.stopPlaybackBtn) {
           try {
               myPlayer.stop();
               myPlayer.release();
               stopPlaybackBtn.setEnabled(false);
               playBtn.setEnabled(true);
               recordBtn.setEnabled(true);
               recordingStatus.setText("Ready...");
               playbackStatus.setText("Ready...");

               Toast.makeText(this, "You pressed 'Stop Playback'", Toast.LENGTH_LONG).show();
           } catch (Exception e) {
               e.printStackTrace();
           }
        }

        if (id == R.id.losslessBtn) {
            try{
                lossy.setChecked(false);
                rCompressed = false;
                initializeRecorder();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (id == R.id.lossyBtn) {
            try{
                lossless.setChecked(false);
                rCompressed = true;
                initializeRecorder();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initializeRecorder() {
        mRecorder = ExtAudioRecorder.getInstanse(rCompressed);
        Toast.makeText(this, "ExtAudioRecorder InstanSe initiated...",Toast.LENGTH_LONG).show();
        i++;
        System.out.println("Working directory: "+new java.io.File(".").getAbsolutePath());
        outputFile = "/junkboxRecording.wav";
        mRecorder.setOutputFile(outputFile);
        stopRecordBtn.setEnabled(false);
        playBtn.setEnabled(false);
        stopPlaybackBtn.setEnabled(false);
    }


}

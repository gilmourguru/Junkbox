package com.junkheadaictribute.tools.junkbox;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_record_test3);
        toolbar = (Toolbar) findViewById(R.id.toolbar_audio_record_test3);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_audio_record_test3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Why'd you hit that?", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_audio_record_test3);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        // Handle navigation view item clicks here.
                        int id = item.getItemId();

                        if (id == R.id.nav_setlistTool) {
                            openSetListManager();
                            return true;
                        } else if (id == R.id.nav_audioRecord3) {
                            drawer.closeDrawer(GravityCompat.START);
                            Snackbar.make(findViewById(R.id.snackbar_audio_record_test3),"Already viewing Audio Recorder, Dumbass!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                        } else if (id == R.id.nav_bpmTool) {

                            openBpmLedTool();
                            return true;

                        } else if (id == R.id.nav_lyrics) {
                            drawer.closeDrawer(GravityCompat.START);
                            Snackbar.make(findViewById(R.id.snackbar_audio_record_test3),"Lyrics Activity not yet developed!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                        } else if (id == R.id.nav_share) {
                            drawer.closeDrawer(GravityCompat.START);
                            Snackbar.make(findViewById(R.id.snackbar_audio_record_test3),"Share what you doper?", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                        } else if (id == R.id.nav_send) {
                            drawer.closeDrawer(GravityCompat.START);
                            Snackbar.make(findViewById(R.id.snackbar_audio_record_test3),"Nothing to send", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                        }


                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    }
                }
        );

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

    private void openSetListManager() {
        Intent mIntent = new Intent(this, SetlistManager.class);
        drawer.closeDrawer(GravityCompat.START);
        startActivity(mIntent);
    }

    @SuppressWarnings("StatementWithEmptyBody")


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
    public void openBpmLedTool() {
        Intent mIntent = new Intent(this, BpmLedTool.class);
        drawer.closeDrawer(GravityCompat.START);
        startActivity(mIntent);
    }


}

package com.junkheadaictribute.tools.junkbox;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BpmLedTool extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private ActionBar ab;
    private NumberPicker bpmPicker;
    private TextView tv1, tv2, tv3, tv4, tv5;
    private int min2sec = 60;
    private int milliSec = 1000;
    private int finalInterval = 500;
    private double finalIntervalDouble;
    private int bpm;
    private String firebaseBpmValue = null;
    private String firebaseBpmResult = null;
    private String Old = "Old BPM : ";
    private String New = "New BPM : ";

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mBpmRef = mRootRef.child("BPM/curr_bpm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpm_led_tool);
        toolbar = (Toolbar) findViewById(R.id.toolbar_bpm_led_tool);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_bpm);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Why'd you hit that?", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_bpm);
        navigationView.setNavigationItemSelectedListener(this);

        bpmPicker = (NumberPicker) findViewById(R.id.BpmPicker);
        bpmPicker.setMinValue(48);
        bpmPicker.setMaxValue(240);
        tv1 = (TextView) findViewById(R.id.textView2);
        tv2 = (TextView) findViewById(R.id.textView3);
        tv3 = (TextView) findViewById(R.id.textView4);
        tv4 = (TextView) findViewById(R.id.textView5);
        tv5 = (TextView) findViewById(R.id.textView6);


        bpmPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // TODO Auto-generated method stub



                tv1.setText(Old.concat(String.valueOf(oldVal)));
                firebaseBpmValue = (String.valueOf(newVal));
                //tv2.setText(firebaseBpmValue);
                mBpmRef.setValue(firebaseBpmValue);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bpm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_settings:
                Snackbar mSnackbar = Snackbar.make(findViewById(R.id.snackbar_bpm_led_tool), R.string.settings_msg,
                        Snackbar.LENGTH_LONG);
                mSnackbar.show();
                return true;


            case R.id.app_info:
                mSnackbar = Snackbar.make(findViewById(R.id.snackbar_bpm_led_tool), R.string.app_info_msg,
                        Snackbar.LENGTH_LONG);
                mSnackbar.show();
                return true;

        }

        return super.onOptionsItemSelected(item);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_setlistTool) {
            Intent mIntent = new Intent(this, SetlistManager.class);
            drawer.closeDrawer(GravityCompat.START);
            startActivity(mIntent);
            return true;
        } else if (id == R.id.nav_audioRecord3) {
            Intent mIntent = new Intent(this, AudioRecordTest3.class);
            drawer.closeDrawer(GravityCompat.START);
            startActivity(mIntent);
            return true;

        } else if (id == R.id.nav_bpmTool) {
            drawer.closeDrawer(GravityCompat.START);
            Snackbar.make(findViewById(R.id.snackbar_bpm_led_tool),"Already viewing BPM LED Tool, Dumbass!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();


        } else if (id == R.id.nav_lyrics) {
            drawer.closeDrawer(GravityCompat.START);
            Snackbar.make(findViewById(R.id.snackbar_bpm_led_tool),"Lyrics Activity not yet developed!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        } else if (id == R.id.nav_share) {
            drawer.closeDrawer(GravityCompat.START);
            Snackbar.make(findViewById(R.id.snackbar_bpm_led_tool),"Share what you doper?", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        } else if (id == R.id.nav_send) {
            drawer.closeDrawer(GravityCompat.START);
            Snackbar.make(findViewById(R.id.snackbar_bpm_led_tool),"Nothing to send", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        mBpmRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 firebaseBpmResult = dataSnapshot.getValue(String.class);
                tv4.setText(firebaseBpmResult);
                bpm = Integer.parseInt(firebaseBpmResult);
                calcLedInt();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    private void calcLedInt() {
        double milliDouble = (double) milliSec;
        double bpmDouble = (double) bpm;
        double min2secDouble = (double) min2sec;
        finalIntervalDouble = (milliDouble/(bpmDouble/min2secDouble));
        finalInterval = (int) finalIntervalDouble;
        //Toast.makeText(this, "LED Interval : " + finalInterval, Toast.LENGTH_LONG).show();
        //Notification.Builder setLights (255, ((finalInterval%1)/2), ((finalInterval%1)/2));
        testNotification();

    }

    private void testNotification() {
        int ledOn = finalInterval/4;
        int ledOff = ledOn*3;
        String lOn = "LED On : ";
        String lOff = "LED Off : ";
        String ms = "ms";
        String on = lOn.concat(String.valueOf(ledOn)).concat(ms);
        String off = lOff.concat(String.valueOf(ledOff)).concat(ms);
        tv2.setText(on);
        tv5.setText(off);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_notify_main)
                .setContentTitle(bpm + "bpm")
                .setContentText(on + " | " + off + " | " + "1/8.= " + ledOff + ms)
                .setPriority(Notification.PRIORITY_HIGH)
                .setVisibility(3)
                .setAutoCancel(true);

        builder.setLights(0xff00ff00, ledOn, ledOff);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }

    public void openSubdivisionTool(View view) {

    }


}

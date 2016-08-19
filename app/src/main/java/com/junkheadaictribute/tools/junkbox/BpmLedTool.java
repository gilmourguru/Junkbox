package com.junkheadaictribute.tools.junkbox;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.widget.DrawerLayout;
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

public class BpmLedTool extends AppCompatActivity {

    private Toolbar toolbar;
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
        toolbar = (Toolbar) findViewById(R.id.app_bar_bpm);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.nav_fragment_bpm);

        drawerFragment.setUp(R.id.nav_drawer_bpm, (DrawerLayout)findViewById(R.id.nav_drawer_bpm), toolbar);

        bpmPicker = (NumberPicker) findViewById(R.id.BpmPicker);
        bpmPicker.setMinValue(48);
        bpmPicker.setMaxValue(320);
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
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(this, "Settings not yet developed.", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.app_info) {
            Toast.makeText(this, "App Version : 1.0.3", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == android.R.id.home) {
            Intent upIntent = NavUtils.getParentActivityIntent(this);
            NavUtils.navigateUpTo(this, upIntent);

            return true;
        }
    return super.onOptionsItemSelected(item);
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

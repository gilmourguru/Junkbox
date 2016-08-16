package com.junkheadaictribute.tools.junkbox;

import android.app.Notification;
import android.app.NotificationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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
    private TextView tv1, tv2, tv3, tv4;
    private int min2sec = 60;
    private int milliSec = 1000;
    private int finalInterval = 500;
    private int bpm;
    private String firebaseBpmValue = null;
    private String firebaseBpmResult = null;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mBpmRef = mRootRef.child("BPM/curr_bpm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpm_led_tool);
        toolbar = (Toolbar) findViewById(R.id.app_bar_bpm);
        setSupportActionBar(toolbar);

        bpmPicker = (NumberPicker) findViewById(R.id.BpmPicker);
        bpmPicker.setMinValue(48);
        bpmPicker.setMaxValue(320);
        tv1 = (TextView) findViewById(R.id.textView2);
        tv2 = (TextView) findViewById(R.id.textView3);
        tv3 = (TextView) findViewById(R.id.textView4);
        tv4 = (TextView) findViewById(R.id.textView5);


        bpmPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // TODO Auto-generated method stub

                String Old = "Old BPM : ";
                String New = "New BPM : ";

                tv1.setText(Old.concat(String.valueOf(oldVal)));
                firebaseBpmValue = (String.valueOf(newVal));
                tv2.setText(firebaseBpmValue);
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
        finalInterval = (milliSec/(bpm/min2sec));
        Toast.makeText(this, "LED Interval : " + finalInterval, Toast.LENGTH_LONG).show();
        //Notification.Builder setLights (255, ((finalInterval%1)/2), ((finalInterval%1)/2));

    }

    private void testNotification() {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(Notification.PRIORITY_HIGH)
                .setOngoing(true);
        builder.setLights(0xff0000ff, (finalInterval/2), (finalInterval/2));
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }
}

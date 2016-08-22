package com.junkheadaictribute.tools.junkbox;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;




public class Home extends AppCompatActivity {

    private Toolbar toolbar;
    Button flacAppBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_appbar);

        toolbar = (Toolbar) findViewById(R.id.app_bar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        //flacAppBtn = (Button) findViewById(R.id.flac_app_btn);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.nav_fragment_home);

        drawerFragment.setUp(R.id.nav_fragment_home,(DrawerLayout)findViewById(R.id.nav_drawer_home), toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(this,"Sorry, but [" +item.getTitle() +"] isn't yet configured.",Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.app_info) {
            Toast.makeText(this,item.getTitle() + ": v 0.0.1",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    public void openFlacRecorderApp(View view) {
        Intent intent = new Intent(Home.this, FlacAudioRecorder.class);
        startActivity(intent);
        finish();
    }

    public void openAudioRecordTest(View view) {
        Intent intent = new Intent(Home.this, AudioRecordTest.class);
        startActivity(intent);
        finish();
    }

    public void openAudioTest3(View view) {
        Intent intent = new Intent(Home.this, AudioRecordTest3.class);
        startActivity(intent);
        finish();
    }

    public void openBpmTool(View view) {
        Intent intent = new Intent(Home.this, BpmLedTool.class);
        startActivity(intent);
        finish();
    }

}

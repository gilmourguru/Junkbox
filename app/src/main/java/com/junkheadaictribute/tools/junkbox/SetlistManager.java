package com.junkheadaictribute.tools.junkbox;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SetlistManager extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView mRecyclerView;
    private SetlistAdapter adapter;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mSongsRef = mRootRef.child("Songs");
    DatabaseReference song1 = mSongsRef.child("ThemBones");
    DatabaseReference song2 = mSongsRef.child("DamThatRiver");
    DatabaseReference song3 = mSongsRef.child("RainWhenIDie");
    DatabaseReference song4 = mSongsRef.child("AngryChair");
    DatabaseReference song5 = mSongsRef.child("ManInTheBox");
    DatabaseReference song6 = mSongsRef.child("ItAintLikeThat");
    DatabaseReference song7 = mSongsRef.child("HeavenBesideYou");
    DatabaseReference song8 = mSongsRef.child("NoExcuses");
    DatabaseReference song9 = mSongsRef.child("Rooster");
    DatabaseReference song10 = mSongsRef.child("RealThing");
    DatabaseReference song11 = mSongsRef.child("Would");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setlist_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_setlist);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "You hit info", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_setlist);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_setlist_manager);
        adapter = new SetlistAdapter(this, getData());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    public static List<SetlistSong> getData() {

        List<SetlistSong> setlistData = new ArrayList<>();

        int[] albumArt = {R.drawable.dirt_cover, R.drawable.dirt_cover, R.drawable.dirt_cover,
        R.drawable.dirt_cover, R.drawable.facelift_cover, R.drawable.facelift_cover,
                R.drawable.alice_in_chains_cover, R.drawable.jar_of_flies_cover, R.drawable.dirt_cover,
        R.drawable.facelift_cover, R.drawable.dirt_cover};

        String[] songTitles ={"Them Bones", "Dam That River", "Rain When I Die", "Angry Chair",
        "Man In The Box", "It Ain't Like That", "Heaven Beside You", "No Excuses", "Rooster",
         "Real Thing", "Would"};

        String[] songTimes = {"2:32", "3:09", "6:02", "4:48", "4:47", "4:38", "5:28", "4:16", "6:15",
        "4:03", "3:27"};

        String[] fromAlbums = {"Dirt", "Dirt", "Dirt", "Dirt", "Facelift", "Facelift",
                "Alice In Chains", "Jar Of Flies", "Dirt", "Facelift", "Dirt"};

        String[] songTempos = {"164 bpm", "132 bpm", "121 bpm", "107 bpm", "110 bpm", "103 bpm",
                "112 bpm", "117 bpm", "73 bpm", "124 bpm", "100 bpm"};

        for (int i = 0; i<songTitles.length && i<albumArt.length && i<songTimes.length
                && i<songTempos.length && i<fromAlbums.length; i++)

        {
            SetlistSong current = new SetlistSong();
            current.songTitle = songTitles[i];
            current.albumArt = albumArt[i];
            current.album = fromAlbums[i];
            current.time = songTimes[i];
            current.tempo = songTempos[i];
            setlistData.add(current);
        }

        return setlistData;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Snackbar.make(findViewById(R.id.snackbar_setlist), "Settings UI not yet configured :(", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
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
            drawer.closeDrawer(GravityCompat.START);
            Snackbar.make(findViewById(R.id.snackbar_setlist),"Already viewing Setlist Manager, Dumbass!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else if (id == R.id.nav_audioRecord3) {
            Intent mIntent = new Intent(this, AudioRecordTest3.class);
            drawer.closeDrawer(GravityCompat.START);
            startActivity(mIntent);
            return true;

        } else if (id == R.id.nav_bpmTool) {
            Intent mIntent = new Intent(this, BpmLedTool.class);
            drawer.closeDrawer(GravityCompat.START);
            startActivity(mIntent);
            return true;

        } else if (id == R.id.nav_lyrics) {
            drawer.closeDrawer(GravityCompat.START);
            Snackbar.make(findViewById(R.id.snackbar_setlist),"Lyrics Activity not yet developed!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        } else if (id == R.id.nav_share) {
            drawer.closeDrawer(GravityCompat.START);
            Snackbar.make(findViewById(R.id.snackbar_setlist),"Share what you doper?", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        } else if (id == R.id.nav_send) {
            drawer.closeDrawer(GravityCompat.START);
            Snackbar.make(findViewById(R.id.snackbar_setlist),"Nothing to send", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

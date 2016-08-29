package com.junkheadaictribute.tools.junkbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class NavHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Integer albumCovers[] = {R.drawable.alice_in_chains_cover, R.drawable.facelift_cover,
            R.drawable.dirt_cover, R.drawable.jar_of_flies_cover, R.drawable.mtv_unplugged_cover};
    private int currAlbumCover = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeImageSwitcher();
        setInitialAlbumCover();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.album_photo_changed, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                setAlbumRotateListener();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initializeImageSwitcher() {
        final ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(NavHome.this);
                return imageView;
            }
        });

        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
    }

    private void setAlbumRotateListener() {
        currAlbumCover++;
        if (currAlbumCover == 5) {
            currAlbumCover = 0;
        }
        setCurrAlbumCover();
    }



    private void setInitialAlbumCover() {
        setCurrAlbumCover();
    }

    private void setCurrAlbumCover() {
        final ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.setImageResource(albumCovers[currAlbumCover]);
        Animation albumPanAnimation = AnimationUtils.loadAnimation(this, R.anim.album_cover_pan);
        imageSwitcher.startAnimation(albumPanAnimation);
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
            Intent mIntent = new Intent(this, BpmLedTool.class);
            drawer.closeDrawer(GravityCompat.START);
            startActivity(mIntent);
            return true;

        } else if (id == R.id.nav_lyrics) {
            drawer.closeDrawer(GravityCompat.START);
            Snackbar.make(findViewById(R.id.snackbar_nav_home),"Lyrics Activity not yet developed!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        } else if (id == R.id.nav_share) {
            drawer.closeDrawer(GravityCompat.START);
            Snackbar.make(findViewById(R.id.snackbar_nav_home),"Share what you doper?", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        } else if (id == R.id.nav_send) {
            drawer.closeDrawer(GravityCompat.START);
            Snackbar.make(findViewById(R.id.snackbar_nav_home),"Nothing to send", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

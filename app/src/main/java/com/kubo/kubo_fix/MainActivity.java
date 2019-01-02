package com.kubo.kubo_fix;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.kubo.kubo_fix.Fragment.HomeFragment;
import com.kubo.kubo_fix.Fragment.OrderFragment;
import com.kubo.kubo_fix.Fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );


        drawer = findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        navigationView = findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );

        // kita set default nya HomeFragment Fragment
        loadFragment(new HomeFragment());
        // inisialisasi BottomNavigaionView
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        // beri listener pada saat item/menu bottomnavigation terpilih
        bottomNavigationView.setOnNavigationItemSelectedListener( MainActivity.this );


    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace( R.id.frame_container, fragment )
                    .commit();
            return true;
        }
        return false;
    }

        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.navigation_profile:
                    fragment = new ProfileFragment();
                    break;
//                case R.id.navigation_notifications:
//                    fragment = new fr_notification();
//                    break;
                case R.id.navigation_inprogress:
                    fragment = new OrderFragment();
                    break;
                case R.id.nav_help:
                    Toast.makeText( getApplicationContext(), "Action Help", Toast.LENGTH_SHORT ).show();
                    return true;
                case R.id.nav_setting:
                    Toast.makeText( getApplicationContext(), "Action Setting", Toast.LENGTH_SHORT ).show();
                    return true;
            }
            return loadFragment(fragment);
        }

    @Override
    public void onBackPressed() {
        drawer = findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
            finishAffinity();
        }
    }

    public void onConfigurationChanged (Configuration newConfig) {
        super.onConfigurationChanged( newConfig );
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}











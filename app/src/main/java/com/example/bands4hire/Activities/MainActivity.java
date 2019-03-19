package com.example.bands4hire.Activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.bands4hire.Fragments.About;
import com.example.bands4hire.Fragments.AddBand;
import com.example.bands4hire.Fragments.Main;
import com.example.bands4hire.Fragments.MyAdverts;
import com.example.bands4hire.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.bands4hire.Activities.Login.logout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Log.v("MainActivity", "inside onCreate");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        fragmentManager.beginTransaction().replace(R.id.fragmentHolder, new Main()).addToBackStack("MainFragment").commit();
        fragmentManager.executePendingTransactions();

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
        getMenuInflater().inflate(R.menu.main, menu);
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
            Intent loginActivity = new Intent(this, Login.class);
            startActivity(loginActivity);
            logout();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Log.v("MainActivity", "inside onNavItemSelected with item: "+id);

        if (id == R.id.nav_all_adverts) {
            fragmentManager.beginTransaction().replace(R.id.fragmentHolder, new Main()).addToBackStack("MainFragment").commit();
            fragmentManager.executePendingTransactions();
        } else if (id == R.id.nav_add_advert) {
            fragmentManager.beginTransaction().replace(R.id.fragmentHolder, new AddBand()).addToBackStack("AddAdvert").commit();
            fragmentManager.executePendingTransactions();
        } else if (id == R.id.nav_my_adverts) {
            fragmentManager.beginTransaction().replace(R.id.fragmentHolder, new MyAdverts()).addToBackStack("MyAdverts").commit();
            fragmentManager.executePendingTransactions();
        } else if (id == R.id.nav_about) {
            fragmentManager.beginTransaction().replace(R.id.fragmentHolder, new About()).addToBackStack("About").commit();
            fragmentManager.executePendingTransactions();
        } else if (id == R.id.nav_logout) {
            Intent loginActivity = new Intent(this, Login.class);
            startActivity(loginActivity);
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

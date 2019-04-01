package com.example.bands4hire.Activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.bands4hire.DataModels.BandAdvert;
import com.example.bands4hire.DrawerLocker.DrawerLocker;
import com.example.bands4hire.Fragments.About;
import com.example.bands4hire.Fragments.AddBand;
import com.example.bands4hire.Fragments.FirstRunWizard;
import com.example.bands4hire.Fragments.Main;
import com.example.bands4hire.Fragments.MyAdverts;
import com.example.bands4hire.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.bands4hire.Activities.Login.logout;
import static com.example.bands4hire.Seeds.AdvertSeeds.seedAdverts;
import static com.example.bands4hire.Seeds.UserSeeds.seedUsers;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DrawerLocker {

    DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    FragmentManager fragmentManager = getFragmentManager();
    String currentFragment = "";
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;


    //This object will be used to pass adverts between Fragments, eg when user
    //selects advert from 'All Adverts' to view, object will be stored here to be
    //used by 'SingleAdvert' fragment etc/
    public static BandAdvert advertTracker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //todo: ssed methods for testing and demonstration purposes only
        seedAdverts();
        seedUsers();

        Log.v("MainActivity", "inside onCreate");

        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        //Users collection is checked to see if 'userType' has been stored yet; if yes, the user has already selected
        //their user type, if not they will be shown a user selection fragment before opening up the application to them

        Query checkUserType = myDatabase.child("users").child(currentUser.getUid()).child("userType");
        checkUserType.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    fragmentManager.beginTransaction().replace(R.id.fragmentHolder, new Main()).addToBackStack("MainFragment").commit();
                    currentFragment = "MainFragment";
                    fragmentManager.executePendingTransactions();
                }else{
                    fragmentManager.beginTransaction().replace(R.id.fragmentHolder, new FirstRunWizard()).addToBackStack("FirstRunWizard").commit();
                    currentFragment = "FirstRunWizard";
                    fragmentManager.executePendingTransactions();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fragmentManager.beginTransaction().replace(R.id.fragmentHolder, new Main()).addToBackStack("MainFragment").commit();
        fragmentManager.executePendingTransactions();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(currentFragment.equals("FirstRunWizard")) {
            Toast.makeText(getApplicationContext(), "User type must be selected before exiting,", Toast.LENGTH_SHORT).show();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(OnQuerySearchView);
        //Search icon should only be available to user on All Adverts screen
        searchItem.setVisible(false);
        return true;
    }

    private SearchView.OnQueryTextListener OnQuerySearchView = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            Main.adapter.getFilter().filter(newText);
            return true;
        }
    };

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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //used if user is on first run with app, is brought to screen where they
    //select what type of user they are (band/booker), this disables nav drawer and icon
    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawer.setDrawerLockMode(lockMode);
        toggle.setDrawerIndicatorEnabled(enabled);
    }
}


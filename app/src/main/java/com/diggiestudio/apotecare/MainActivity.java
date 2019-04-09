package com.diggiestudio.apotecare;

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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.diggiestudio.apotecare.utilities.PrefManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private PrefManager manager = new PrefManager();

    LinearLayout menu1, menu2, menu3, menu4, menu5, menu6, menu7, menu8, menu9, menu10, menu11, menu12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        boolean login = manager.getBoolean(this, "isLoggedIn");
        if(!login){
            Login();
        }

        menu1 = findViewById(R.id.linearMenu1);
        menu2 = findViewById(R.id.linearMenu2);
        menu3 = findViewById(R.id.linearMenu3);
        menu4 = findViewById(R.id.linearMenu4);
        menu5 = findViewById(R.id.linearMenu5);
        menu6 = findViewById(R.id.linearMenu6);
        menu7 = findViewById(R.id.linearMenu7);
        menu8 = findViewById(R.id.linearMenu8);
        menu9 = findViewById(R.id.linearMenu9);
        menu10 = findViewById(R.id.linearMenu10);
        menu11 = findViewById(R.id.linearMenu11);
        menu12 = findViewById(R.id.linearMenu12);

        menu1.setClickable(true);
        menu2.setClickable(true);
        menu3.setClickable(true);
        menu4.setClickable(true);
        menu5.setClickable(true);
        menu6.setClickable(true);
        menu7.setClickable(true);
        menu8.setClickable(true);
        menu9.setClickable(true);
        menu10.setClickable(true);
        menu11.setClickable(true);
        menu12.setClickable(true);

        menu1.setOnClickListener(this);
        menu2.setOnClickListener(this);
        menu3.setOnClickListener(this);
        menu4.setOnClickListener(this);
        menu5.setOnClickListener(this);
        menu6.setOnClickListener(this);
        menu7.setOnClickListener(this);
        menu8.setOnClickListener(this);
        menu9.setOnClickListener(this);
        menu10.setOnClickListener(this);
        menu11.setOnClickListener(this);
        menu12.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tools) {
            manager.saveBoolean(getApplicationContext(), "isLoggedIn", false);
            startActivity(new Intent(this, LoginActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void Login(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.linearMenu1:
                startActivity(new Intent(this, CVDActivity.class));
                break;
            case R.id.linearMenu2:
                startActivity(new Intent(this, BMIActivity.class));
                break;
            case R.id.linearMenu3:
                Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.linearMenu4:
                Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.linearMenu5:
                Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.linearMenu6:
                Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.linearMenu7:
                Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.linearMenu8:
                Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.linearMenu9:
                Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.linearMenu10:
                Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.linearMenu11:
                Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.linearMenu12:
                Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}

package com.diggiestudio.apotecare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.diggiestudio.apotecare.utilities.PrefManager;

public class BMIOlahragaActivity extends AppCompatActivity {

    private PrefManager manager = new PrefManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiolahraga);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        boolean login = manager.getBoolean(this, "isLoggedIn");
        if(!login){
            Login();
        }
    }

    public void Login(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

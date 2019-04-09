package com.diggiestudio.apotecare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.diggiestudio.apotecare.utilities.PrefManager;

public class BMIAfterHasilActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnOlahraga, btnSuplemen;

    private PrefManager manager = new PrefManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiafter_hasil);

        btnOlahraga = findViewById(R.id.btnBMIOlahraga);
        btnSuplemen = findViewById(R.id.btnBMISuplemen);

        btnOlahraga.setOnClickListener(this);
        btnSuplemen.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        boolean login = manager.getBoolean(this, "isLoggedIn");
        if(!login){
            Login();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnBMIOlahraga:
                startActivity(new Intent(this, BMIOlahragaActivity.class));
                break;
            case R.id.btnBMISuplemen:
                Intent i = new Intent(this, ProdukActivity.class);
                i.putExtra("HasilProduk", "BMI_bbturun");
                startActivity(i);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void Login(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}

package com.diggiestudio.apotecare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.diggiestudio.apotecare.utilities.PrefManager;

public class CVDRekomendasiActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnTKD, btnGula, btnKolesterol, btnLain;
    private PrefManager manager = new PrefManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cvdrekomendasi);

        btnTKD = findViewById(R.id.btnCVDTKD);
        btnGula = findViewById(R.id.btnCVDGulaDarah);
        btnKolesterol = findViewById(R.id.btnCVDKolesterol);
        btnLain = findViewById(R.id.btnCVDLain);

        btnTKD.setOnClickListener(this);
        btnGula.setOnClickListener(this);
        btnKolesterol.setOnClickListener(this);
        btnLain.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        boolean login = manager.getBoolean(this, "isLoggedIn");
        if(!login){
            Login();
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

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, ProdukActivity.class);
        i.putExtra("HasilProduk", "CVD");

        switch (v.getId())
        {
            case R.id.btnCVDTKD:
                startActivity(i);
                break;
            case R.id.btnCVDGulaDarah:
                startActivity(i);
                break;
            case R.id.btnCVDKolesterol:
                startActivity(i);
                break;
            case R.id.btnCVDLain:
                startActivity(i);
                break;
        }
    }
}

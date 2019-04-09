package com.diggiestudio.apotecare;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.diggiestudio.apotecare.utilities.PrefManager;

public class BMIHasilActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout linearMessageBMI;
    TextView messageBMI;
    LinearLayout linear1, linear2, linear3, linear4, linear5, linear6;
    TextView tvSkorBMI, tvketerangan1, tvketerangan2, tvketerangan3, tvketerangan4, tvketerangan5, tvketerangan6;
    TextView tvketerangan_val1, tvketerangan_val2, tvketerangan_val3, tvketerangan_val4, tvketerangan_val5, tvketerangan_val6;
    Button btnMessage;

    String keteranganBMI = "";

    double hasilBMI;

    boolean isDiet = false;

    private PrefManager manager = new PrefManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmihasil);

        linearMessageBMI = findViewById(R.id.linearMessageBMI);
        messageBMI = findViewById(R.id.messageBMI);

        tvSkorBMI = findViewById(R.id.tvSkorBMI);

        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);
        linear3 = findViewById(R.id.linear3);
        linear4 = findViewById(R.id.linear4);
        linear5 = findViewById(R.id.linear5);
        linear6 = findViewById(R.id.linear6);

        tvketerangan1 = findViewById(R.id.tvKeterangan1);
        tvketerangan2 = findViewById(R.id.tvKeterangan2);
        tvketerangan3 = findViewById(R.id.tvKeterangan3);
        tvketerangan4 = findViewById(R.id.tvKeterangan4);
        tvketerangan5 = findViewById(R.id.tvKeterangan5);
        tvketerangan6 = findViewById(R.id.tvKeterangan6);

        tvketerangan_val1 = findViewById(R.id.keterangan1_val);
        tvketerangan_val2 = findViewById(R.id.keterangan1_va2);
        tvketerangan_val3 = findViewById(R.id.keterangan1_va3);
        tvketerangan_val4 = findViewById(R.id.keterangan1_va4);
        tvketerangan_val5 = findViewById(R.id.keterangan1_va5);
        tvketerangan_val6 = findViewById(R.id.keterangan1_va6);

        btnMessage = findViewById(R.id.btnBMIYes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnMessage.setOnClickListener(this);

        boolean login = manager.getBoolean(this, "isLoggedIn");
        if(!login){
            Login();
        }

        Bundle b = getIntent().getExtras();
        hasilBMI = b.getDouble("HasilBMI");

        if(hasilBMI != 0)
            calculateBMI();
        else
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

    public void Login(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void calculateBMI()
    {
        String messageBMI_BBNaik = "Apakah Anda ingin menaikkan berat badan?";
        String messageBMI_BBTurun = "Apakah Anda ingin menurunkan berat badan?";

        tvSkorBMI.setText("BMI ANDA : " + hasilBMI);

        if (hasilBMI < 16.00) {
            keteranganBMI = "SUVERY UNDERWEIGHT";
            linear1.setBackground(getResources().getDrawable(R.drawable.border_fill));
            tvketerangan1.setTextColor(Color.WHITE);
            tvketerangan_val1.setTextColor(Color.WHITE);
            linearMessageBMI.setVisibility(View.VISIBLE);
            messageBMI.setText(messageBMI_BBNaik);
            isDiet = false;
        } else if (hasilBMI >= 16.00 && hasilBMI <= 18.40) {
            keteranganBMI = "UNDERWEIGHT";
            linear2.setBackground(getResources().getDrawable(R.drawable.border_fill));
            tvketerangan2.setTextColor(Color.WHITE);
            tvketerangan_val2.setTextColor(Color.WHITE);
            linearMessageBMI.setVisibility(View.VISIBLE);
            messageBMI.setText(messageBMI_BBNaik);
            isDiet = false;
        } else if (hasilBMI >= 18.50 && hasilBMI <= 24.00) {
            keteranganBMI = "NORMAL";
            linear3.setBackground(getResources().getDrawable(R.drawable.border_fill));
            tvketerangan3.setTextColor(Color.WHITE);
            tvketerangan_val3.setTextColor(Color.WHITE);
            linearMessageBMI.setVisibility(View.GONE);
            btnMessage.setVisibility(View.GONE);
        } else if (hasilBMI >= 25.00 && hasilBMI <= 29.00) {
            keteranganBMI = "OVERWEIGHT";
            linear4.setBackground(getResources().getDrawable(R.drawable.border_fill));
            tvketerangan4.setTextColor(Color.WHITE);
            tvketerangan_val4.setTextColor(Color.WHITE);
            linearMessageBMI.setVisibility(View.VISIBLE);
            messageBMI.setText(messageBMI_BBTurun);
            isDiet = true;
        } else if (hasilBMI >= 30.00 && hasilBMI <= 34.00) {
            keteranganBMI = "OBES CLASS I";
            linear5.setBackground(getResources().getDrawable(R.drawable.border_fill));
            tvketerangan5.setTextColor(Color.WHITE);
            tvketerangan_val5.setTextColor(Color.WHITE);
            linearMessageBMI.setVisibility(View.VISIBLE);
            messageBMI.setText(messageBMI_BBTurun);
            isDiet = true;
        } else if (hasilBMI > 34.00 && hasilBMI <= 40.00) {
            keteranganBMI = "OBES CLASS II";
            linear6.setBackground(getResources().getDrawable(R.drawable.border_fill));
            tvketerangan6.setTextColor(Color.WHITE);
            tvketerangan_val6.setTextColor(Color.WHITE);
            linearMessageBMI.setVisibility(View.VISIBLE);
            messageBMI.setText(messageBMI_BBTurun);
            isDiet =  true;
        } else {
            keteranganBMI = "UNDEFINED";
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnBMIYes:
                if(isDiet)
                    startActivity(new Intent(this, BMIAfterHasilActivity.class));
                else {
                    Intent i = new Intent(this, ProdukActivity.class);
                    i.putExtra("HasilProduk", "BMI_bbnaik");
                    startActivity(i);
                }
                break;
        }
    }
}

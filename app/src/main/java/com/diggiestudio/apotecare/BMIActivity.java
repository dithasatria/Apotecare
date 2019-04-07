package com.diggiestudio.apotecare;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class BMIActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout linearFormBMI, linearHasilBMI;
    LinearLayout linear1, linear2, linear3, linear4, linear5, linear6;
    TextView tvSkorBMI, tvKeteranganSkorBMI;
    TextView tvketerangan1, tvketerangan2, tvketerangan3, tvketerangan4, tvketerangan5, tvketerangan6;
    TextView tvketerangan_val1, tvketerangan_val2, tvketerangan_val3, tvketerangan_val4, tvketerangan_val5, tvketerangan_val6;
    EditText etTinggiBadan, etBeratBadan;
    Button btnBMISubmit;

    String tinggi, berat;
    String keteranganBMI = "";

    int tinggiBadan = 0;
    int beratBadan = 0;

    double hasil = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        linearFormBMI = findViewById(R.id.linearFormBMI);
        linearHasilBMI = findViewById(R.id.linearHasilBMI);

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

        etTinggiBadan = findViewById(R.id.etTinggiBadan);
        etBeratBadan = findViewById(R.id.etBeratBadan);
        btnBMISubmit = findViewById(R.id.btnBMISubmit);
        tvSkorBMI = findViewById(R.id.tvSkorBMI);
        //tvKeteranganSkorBMI = findViewById(R.id.tvKeteranganSkorBMI);

        linearHasilBMI.setVisibility(View.GONE);
        //tinggiBadan = Integer.parseInt(tinggi);
        //beratBadan = Integer.parseInt(berat);

        btnBMISubmit.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etBeratBadan.setOnEditorActionListener(new EditText.OnEditorActionListener(){

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btnBMISubmit.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBMISubmit:
                hideKeybord();
                calculateBMI();
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

    private void calculateBMI(){
        tinggi = etTinggiBadan.getText().toString();
        berat = etBeratBadan.getText().toString();

        if(tinggi.equals("") || berat.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Isi semua form yang ada!", Toast.LENGTH_SHORT).show();
        }
        else {
            tinggiBadan = Integer.parseInt(tinggi);
            beratBadan = Integer.parseInt(berat);

            double tinggiCm = tinggiBadan * 0.01;
            double tinggiCal = tinggiCm * tinggiCm;

            hasil = beratBadan / tinggiCal;

            int angkaSignifikan = 2;
            double temp = Math.pow(10, angkaSignifikan);
            double hasil_akhir = (double) Math.round(hasil * temp) / temp;

            linearFormBMI.setVisibility(View.GONE);
            linearHasilBMI.setVisibility(View.VISIBLE);

            if (hasil_akhir < 16.00) {
                keteranganBMI = "SUVERY UNDERWEIGHT";
                linear1.setBackground(getResources().getDrawable(R.drawable.border_fill));
                tvketerangan1.setTextColor(Color.WHITE);
                tvketerangan_val1.setTextColor(Color.WHITE);
            } else if (hasil_akhir >= 16.00 && hasil_akhir <= 18.40) {
                keteranganBMI = "UNDERWEIGHT";
                linear2.setBackground(getResources().getDrawable(R.drawable.border_fill));
                tvketerangan2.setTextColor(Color.WHITE);
                tvketerangan_val2.setTextColor(Color.WHITE);
            } else if (hasil_akhir >= 18.50 && hasil_akhir <= 24.00) {
                keteranganBMI = "NORMAL";
                linear3.setBackground(getResources().getDrawable(R.drawable.border_fill));
                tvketerangan3.setTextColor(Color.WHITE);
                tvketerangan_val3.setTextColor(Color.WHITE);
            } else if (hasil_akhir >= 25.00 && hasil_akhir <= 29.00) {
                keteranganBMI = "OVERWEIGHT";
                linear4.setBackground(getResources().getDrawable(R.drawable.border_fill));
                tvketerangan4.setTextColor(Color.WHITE);
                tvketerangan_val4.setTextColor(Color.WHITE);
            } else if (hasil_akhir >= 30.00 && hasil_akhir <= 34.00) {
                keteranganBMI = "OBES CLASS I";
                linear5.setBackground(getResources().getDrawable(R.drawable.border_fill));
                tvketerangan5.setTextColor(Color.WHITE);
                tvketerangan_val5.setTextColor(Color.WHITE);
            } else if (hasil_akhir > 34.00 && hasil_akhir <= 40.00) {
                keteranganBMI = "OBES CLASS II";
                linear6.setBackground(getResources().getDrawable(R.drawable.border_fill));
                tvketerangan6.setTextColor(Color.WHITE);
                tvketerangan_val6.setTextColor(Color.WHITE);
            } else {
                keteranganBMI = "UNDEFINED";
            }

            tvSkorBMI.setText("BMI ANDA : " + hasil_akhir);
            //tvKeteranganSkorBMI.setText(keteranganBMI);

            Toast.makeText(getApplicationContext(),"hasil: " + hasil_akhir, Toast.LENGTH_LONG).show();
        }
    }

    public void hideKeybord() {
        try  {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {

        }
    }
}

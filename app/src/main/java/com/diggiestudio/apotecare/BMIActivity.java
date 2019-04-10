package com.diggiestudio.apotecare;

import android.content.Intent;
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

import com.diggiestudio.apotecare.utilities.PrefManager;

import org.w3c.dom.Text;

public class BMIActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout linearFormBMI, linearHasilBMI;
    TextView tvSkorBMI, tvKeteranganSkorBMI;

    EditText etTinggiBadan, etBeratBadan;
    Button btnBMISubmit;

    String tinggi, berat;

    int tinggiBadan = 0;
    int beratBadan = 0;

    double hasil = 0;

    private PrefManager manager = new PrefManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        linearFormBMI = findViewById(R.id.linearFormBMI);

        etTinggiBadan = findViewById(R.id.etTinggiBadan);
        etBeratBadan = findViewById(R.id.etBeratBadan);
        btnBMISubmit = findViewById(R.id.btnBMISubmit);
        tvSkorBMI = findViewById(R.id.tvSkorBMI);
        //tvKeteranganSkorBMI = findViewById(R.id.tvKeteranganSkorBMI);

        //tinggiBadan = Integer.parseInt(tinggi);
        //beratBadan = Integer.parseInt(berat);

        btnBMISubmit.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        boolean login = manager.getBoolean(this, "isLoggedIn");
        if(!login){
            Login();
        }

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

    public void Login(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
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

            //tvSkorBMI.setText("BMI ANDA : " + hasil_akhir);
            //tvKeteranganSkorBMI.setText(keteranganBMI);

            Intent i = new Intent(this, BMIHasilActivity.class);
            Bundle params = new Bundle();
            params.putDouble("HasilBMI", hasil_akhir);
            i.putExtras(params);
            startActivity(i);

            //Toast.makeText(getApplicationContext(),"hasil: " + hasil_akhir, Toast.LENGTH_LONG).show();
        }
    }

}

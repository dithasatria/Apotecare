package com.diggiestudio.apotecare;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.diggiestudio.apotecare.utilities.PrefManager;

public class CVDActivity extends AppCompatActivity implements View.OnClickListener {

    RadioGroup rgDiabetes, rgJK, rgPerokok;
    RadioButton rb_diabet_yes, rb_diabet_no, rb_pria, rb_wanita, rb_perokok_ya, rb_perokok_no;
    EditText etUsia, etTKD, etKolesterol;
    Button btnSubmit;

    String diabet = "";
    String jk = "";
    String perokok = "";

    static final  int hasil_merah_tua = 1;
    static final int hasil_merah = 2;
    static final int hasil_orange = 3;
    static final int hasil_kuning = 4;
    static final int hasil_hijau = 5;

    private PrefManager manager = new PrefManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cvd);

        boolean login = manager.getBoolean(this, "isLoggedIn");
        if(!login){
            Login();
        }

        rgDiabetes = findViewById(R.id.rgDiabet);
        rgJK = findViewById(R.id.rgjk);
        rgPerokok = findViewById(R.id.rgPerokok);

        rb_diabet_yes = findViewById(R.id.rb_diabet_yes);
        rb_diabet_no = findViewById(R.id.rb__diabet_no);
        rb_pria = findViewById(R.id.rb_pria);
        rb_wanita = findViewById(R.id.rb_wanita);
        rb_perokok_ya = findViewById(R.id.rb_perokok_yes);
        rb_perokok_no = findViewById(R.id.rb_perokok_no);

        etUsia = findViewById(R.id.etUsia);
        etTKD = findViewById(R.id.etTKDSistol);
        etKolesterol = findViewById(R.id.etKolesterol);

        btnSubmit = findViewById(R.id.btnSubmitCVD);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnSubmit.setOnClickListener(this);
    }

    public void Login(){
        startActivity(new Intent(CVDActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSubmitCVD:
                hideKeybord();
                if(rgDiabetes.getCheckedRadioButtonId() == -1 || rgJK.getCheckedRadioButtonId() == -1 || rgPerokok.getCheckedRadioButtonId() == -1 || etUsia.getText().toString().equals("") || etTKD.getText().toString().equals("") || etKolesterol.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Mohon isi semua form yang ada!", Toast.LENGTH_LONG).show();
                }
                else{
                    subMitdanHitungCVD();
                }
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

    private void calculateCVD()
    {
        rgDiabetes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbDiabet = (RadioButton) rgDiabetes.findViewById(checkedId);

                diabet = rbDiabet.getText().toString();
            }
        });

        rgJK.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbJK = (RadioButton) rgJK.findViewById(checkedId);

                jk = rbJK.getText().toString();
            }
        });

        rgPerokok.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbPerokok = (RadioButton) rgPerokok.findViewById(checkedId);

                perokok = rbPerokok.getText().toString();
            }
        });

        String usia = etUsia.getText().toString();
        String tkd = etTKD.getText().toString();
        String kolesterol = etKolesterol.getText().toString();

        Toast.makeText(getApplicationContext(), "diabet: " + diabet + " jk: " +jk+ " perokok: " +perokok + " usia: "+usia + " tkd: " +tkd+ " kolesterol: "+kolesterol, Toast.LENGTH_LONG).show();
    }

    public void hideKeybord() {
        try  {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {

        }
    }

    public void subMitdanHitungCVD()
    {
        int hasil_CVD = 0;

        RadioButton radioButtonDiabetes = (RadioButton) findViewById(rgDiabetes.getCheckedRadioButtonId());
        RadioButton radioButtonJK = (RadioButton) findViewById(rgJK.getCheckedRadioButtonId());
        RadioButton radioButtonMerokok = (RadioButton) findViewById(rgPerokok.getCheckedRadioButtonId());

        int usia, sistol, kolestrol, skalaKolestrol;

        usia = Integer.parseInt(etUsia.getText().toString()) ;
        sistol = Integer.parseInt(etTKD.getText().toString()) ;
        kolestrol = Integer.parseInt(etKolesterol.getText().toString()) ;
        skalaKolestrol = kolestrol / 38;


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        AlertDialog alertDialog = alertDialogBuilder.create();



        String msg = "";


        //jika diabet
        if(radioButtonDiabetes.getText().toString().equalsIgnoreCase("ya"))
        {
            msg += "DIABETES ";
            //jika cowok
            if(  radioButtonJK.getText().toString().equalsIgnoreCase("laki-laki") )
            {
                msg += "Cowok ";

                if( radioButtonMerokok.getText().toString().equalsIgnoreCase("ya") )
                {
                    msg += " Merokok";
                    if( usia >= 70 )
                    {
                        msg += " usia 70 ke atas";
                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_orange;
                            }
                        }
                    }else if( usia >= 60 )
                    {
                        msg += " usia 60 ke atas";
                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_orange;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }

                    }else if( usia >= 50 )
                    {
                        msg += " usia 50 ke atas";
                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_orange;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }
                    }else if( usia >= 40 )
                    {
                        msg += " usia 40 ke atas";

                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }

                    }


                }else
                {
                    msg += " tidak merokok" ;
                    if( usia >= 70 )
                    {
                        msg += " usia 70 ke atas";
                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_orange;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }


                    }else if( usia >= 60 )
                    {
                        msg += " usia 60 ke atas";
                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_orange;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }

                    }else if( usia >= 50 )
                    {
                        msg += " usia 50 ke atas";
                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }


                    }else if( usia >= 40 )
                    {
                        msg += " usia 40 ke atas";
                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_orange;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }

                    }

                }
            }
            //jika perempuan
            else if(  radioButtonJK.getText().toString().equalsIgnoreCase("perempuan") )
            {
                msg += "Perempuan";
                if( radioButtonMerokok.getText().toString().equalsIgnoreCase("ya") )
                {
                    msg += " Merokok" ;
                    if( usia >= 70 )
                    {
                        msg += " usia 70 ke atas";
                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_orange;
                            }
                        }


                    }else if( usia >= 60 )
                    {
                        msg += " usia 60 ke atas";
                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }

                    }else if( usia >= 50 )
                    {
                        msg += " usia 50 ke atas";
                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_orange;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }

                    }else if( usia >= 40 )
                    {
                        msg += " usia 40 ke atas";
                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_orange;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }

                    }


                }else
                {
                    msg += " tidak merokok" ;
                    if( usia >= 70 )
                    {
                        msg += " usia 70 ke atas";
                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_orange;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }


                    }else if( usia >= 60 )
                    {
                        msg += " usia 60 ke atas";
                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_orange;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }

                    }else if( usia >= 50 )
                    {
                        msg += " usia 50 ke atas";

                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }

                    }else if( usia >= 40 )
                    {
                        msg += " usia 40 ke atas";
                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }

                    }

                }
            }
        }else//jika idak diabet
        {
            msg += "TIDAK DIABETES ";
            //jika cowok
            if(  radioButtonJK.getText().toString().equalsIgnoreCase("laki-laki") )
            {
                msg += "Cowok ";

                if( radioButtonMerokok.getText().toString().equalsIgnoreCase("ya") )
                {
                    msg += " Merokok";
                    if( usia >= 70 )
                    {
                        msg += " usia 70 ke atas";

                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }


                    }else if( usia >= 60 )
                    {
                        msg += " usia 60 ke atas";

                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_orange;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }

                    }else if( usia >= 50 )
                    {
                        msg += " usia 50 ke atas";

                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }

                    }else if( usia >= 40 )
                    {
                        msg += " usia 40 ke atas";

                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_orange;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }

                    }


                }else
                {
                    msg += " tidak merokok" ;
                    if( usia >= 70 )
                    {
                        msg += " usia 70 ke atas";

                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }


                    }else if( usia >= 60 )
                    {
                        msg += " usia 60 ke atas";

                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }

                    }else if( usia >= 50 )
                    {
                        msg += " usia 50 ke atas";

                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_orange;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }

                    }else if( usia >= 40 )
                    {
                        msg += " usia 40 ke atas";

                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }

                    }

                }
            }
            //jika perempuan
            else if(  radioButtonJK.getText().toString().equalsIgnoreCase("perempuan") )
            {
                msg += "Perempuan";
                if( radioButtonMerokok.getText().toString().equalsIgnoreCase("ya") )
                {
                    msg += " Merokok" ;
                    if( usia >= 70 )
                    {
                        msg += " usia 70 ke atas";
                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }


                    }else if( usia >= 60 )
                    {
                        msg += " usia 60 ke atas";

                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_orange;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }




                    }else if( usia >= 50 )
                    {
                        msg += " usia 50 ke atas";


                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }

                    }else if( usia >= 40 )
                    {
                        msg += " usia 40 ke atas";

                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }

                    }


                }else
                {
                    msg += " tidak merokok" ;
                    if( usia >= 70 )
                    {
                        msg += " usia 70 ke atas";

                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }


                    }else if( usia >= 60 )
                    {
                        msg += " usia 60 ke atas";


                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_merah;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }

                    }else if( usia >= 50 )
                    {
                        msg += " usia 50 ke atas";

                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_orange;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }

                    }else if( usia >= 40 )
                    {
                        msg += " usia 40 ke atas";

                        if( sistol >= 180 )
                        {
                            msg += " sistol 180 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_merah_tua;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_kuning;
                            }
                        }else if( sistol >= 160 )
                        {
                            msg += " sistol 160 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_merah;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_kuning;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 140 )
                        {
                            msg += " sistol 140 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_orange;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }else if( sistol >= 120 )
                        {
                            msg += " sistol 120 ke atas";
                            if(skalaKolestrol >=8)
                            {
                                msg += " skalaKolestrol 8 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=7)
                            {
                                msg += " skalaKolestrol 7 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=6)
                            {
                                msg += " skalaKolestrol 6 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=5)
                            {
                                msg += " skalaKolestrol 5 ke atas";
                                hasil_CVD = hasil_hijau;
                            }else if(skalaKolestrol >=4)
                            {
                                msg += " skalaKolestrol 4 ke atas";
                                hasil_CVD = hasil_hijau;
                            }
                        }

                    }

                }
            }
        }

        msg += "Hasil CVD: ";

        switch (hasil_CVD )
        {
            case hasil_merah_tua:
                msg += "Merah Tua >40% ";
                break;
            case hasil_merah:
                msg += "Merah  30 - 39 % ";
                break;
            case hasil_orange:
                msg += "Orange  20 - 29 %";
                break;
            case hasil_kuning:
                msg += "Kuning  10 - 19 %";
                break;
            case hasil_hijau:
                msg += "Hijau  < 10%";
                break;

        }

        msg += "Usia " + usia
                + "Sistol " + sistol
                + "Kolestrol " + kolestrol
        ;

        //Toast.makeText(getApplicationContext(), "msg: "+msg +" hasil cvd: " + hasil_CVD, Toast.LENGTH_LONG).show();

        alertDialog.setMessage(msg);

        //alertDialog.show();

        Intent i = new Intent(this, CVDHasilActivity.class);
        i.putExtra("HasilCVD", hasil_CVD);
        startActivity(i);



    }
}

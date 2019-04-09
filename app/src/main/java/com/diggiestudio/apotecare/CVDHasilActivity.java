package com.diggiestudio.apotecare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CVDHasilActivity extends AppCompatActivity implements View.OnClickListener {

    int hasilCVD;

    TextView tvHasil, tvCVDDeskripsi;
    RelativeLayout linearHasilWarna;
    LinearLayout linearTurunkanResiko;
    Button btnNextCVD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cvdhasil);

        Intent i = getIntent();
        hasilCVD = i.getIntExtra("HasilCVD", 0);

        if(hasilCVD == 0)
            finish();

        tvHasil = findViewById(R.id.hasilCVD);
        tvCVDDeskripsi = findViewById(R.id.tvCVDDeskripsi);
        linearHasilWarna = findViewById(R.id.linearWarnaCVD);
        linearTurunkanResiko = findViewById(R.id.linearTurunkanResiko);
        btnNextCVD = findViewById(R.id.btnNextCVD);

        btnNextCVD.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        String hasilDtl = "Dalam rentang waktu 10 tahun ke depan dengan " +
                " kondisi seperti saat sekarang, " +
                "beresiko menderita penyakit kardiovaskuler " +
                "(stroke, serangan jantung) ";

        switch (hasilCVD)
        {
            case CVDActivity.hasil_merah_tua:
                tvHasil.setText(">= 40%");
                tvCVDDeskripsi.setText(hasilDtl + "lebih dari 40%");
                linearHasilWarna.setBackground(getResources().getDrawable(R.color.colorCVDDarkRed));
                break;

            case CVDActivity.hasil_merah:
                tvHasil.setText("30% to < 40%");
                tvCVDDeskripsi.setText(hasilDtl + "lebih dari 30% sampai kurang dari 40%");
                linearHasilWarna.setBackground(getResources().getDrawable(R.color.colorCVDRed));
                break;

            case CVDActivity.hasil_orange:
                tvHasil.setText("20% to < 30%");
                tvCVDDeskripsi.setText(hasilDtl + "lebih dari 20% sampai kurang dari 30%");
                linearHasilWarna.setBackground(getResources().getDrawable(R.color.colorCVDOrange));
                break;

            case CVDActivity.hasil_kuning:
                tvHasil.setText("10% to < 20%");
                tvCVDDeskripsi.setText(hasilDtl + "lebih dari 10% sampai kurang dari 20%");
                linearHasilWarna.setBackground(getResources().getDrawable(R.color.colorCVDYellow));
                break;

            case CVDActivity.hasil_hijau:
                tvHasil.setText("< 10%");
                linearTurunkanResiko.setVisibility(View.GONE);
                btnNextCVD.setVisibility(View.GONE);
                tvCVDDeskripsi.setText(hasilDtl + "kurang dari 10%");
                linearHasilWarna.setBackground(getResources().getDrawable(R.color.colorCVDGreen));
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

    public void actionTombolNext(View v)
    {
        //Intent i = new Intent(this, CVDHasilNextActivity.class);
        //i.putExtra("CVD", hasilCVD);
        //startActivity(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnNextCVD:
                startActivity(new Intent(this, CVDRekomendasiActivity.class));
                break;
        }
    }
}

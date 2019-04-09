package com.diggiestudio.apotecare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProdukDetailActivity extends AppCompatActivity {

    TextView tvProdukDetailName, tvProdukDetailPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_detail);

        tvProdukDetailName = findViewById(R.id.tvDetailProdukName);
        tvProdukDetailPrice = findViewById(R.id.tvProdukDetailPrice);

        Intent i = getIntent();
        String produk = i.getStringExtra("ProdukDetail");

        detailProduk(produk);
    }

    private void detailProduk(String produk)
    {
        switch (produk)
        {
            case "BMI_bbnaik":
                tvProdukDetailName.setText("SUSU PROTEIN \" + \"\\n\"+ \"UNIVERSAL GAINFAST 1000G");
                tvProdukDetailPrice.setText("Rp. 558.000");
                break;
            case "BMI_bbturun":
                tvProdukDetailName.setText("NUTRIMAX WAIST\" + \"\\n\"+ \"STRIMMER @30");
                tvProdukDetailPrice.setText("Rp. 295.000");
                break;
            case "CVD":
                tvProdukDetailName.setText("N.PLUS GARLIC @100");
                tvProdukDetailPrice.setText("Rp. 390.000");
                break;
        }
    }
}

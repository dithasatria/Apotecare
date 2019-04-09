package com.diggiestudio.apotecare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ProdukDetailActivity extends AppCompatActivity {

    TextView tvProdukDetailName, tvProdukDetailPrice, tvProdukDetailDescription;
    ImageView imgProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_detail);

        imgProduk = findViewById(R.id.imgDetailProduk);
        tvProdukDetailName = findViewById(R.id.tvDetailProdukName);
        tvProdukDetailPrice = findViewById(R.id.tvProdukDetailPrice);
        tvProdukDetailDescription = findViewById(R.id.produkDescription);

        Intent i = getIntent();
        String produk = i.getStringExtra("ProdukDetail");

        detailProduk(produk);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void detailProduk(String produk)
    {
        switch (produk)
        {
            case "BMI_bbnaik":
                tvProdukDetailName.setText("SUSU PROTEIN " + "\n"+ "UNIVERSAL GAINFAST 1000G");
                imgProduk.setImageResource(R.drawable.suplemen_gain_fast);
                tvProdukDetailPrice.setText("Rp. 558.000");
                tvProdukDetailDescription.setText("Manfaat :\n" +
                        "Membantu mengontrol berat badan dan membentuk tubuh yang ideal.\n" +
                        " \n" +
                        "Membantu memperkecil lingkar pinggang dan merampingkan perut.\n" +
                        "\n" +
                        " Membantu mencegah timbulnya penyakit degeneratif akibat kegemukan. \n" +
                        "\n" +
                        "Membantu meningkatkan vitalitas, produktifitas dan daya tahan tubuh.\n");
                break;
            case "BMI_bbturun":
                tvProdukDetailName.setText("NUTRIMAX WAIST" + "\n"+ "STRIMMER @30");
                tvProdukDetailPrice.setText("Rp. 295.000");
                imgProduk.setImageResource(R.drawable.bmi_bbturun);
                tvProdukDetailDescription.setText("Manfaat :\n" +
                        "Membantu mengontrol berat badan dan membentuk tubuh yang ideal.\n" +
                        " \n" +
                        "Membantu memperkecil lingkar pinggang dan merampingkan perut.\n" +
                        "\n" +
                        " Membantu mencegah timbulnya penyakit degeneratif akibat kegemukan. \n" +
                        "\n" +
                        "Membantu meningkatkan vitalitas, produktifitas dan daya tahan tubuh.\n");
                break;
            case "CVD":
                tvProdukDetailName.setText("N.PLUS GARLIC @100");
                tvProdukDetailPrice.setText("Rp. 390.000");
                imgProduk.setImageResource(R.drawable.cvd_produk);
                tvProdukDetailDescription.setText("Manfaat :\n" +
                        "Menurunkan hipertensi, hiperkolesterolemia, trigliserida, LDL dan meningkatkan HDL. \n" +
                        "\n" +
                        "Membantu menghambat penggumpalan darah abnormal.\n" +
                        "\n" +
                        "Menjaga kesehatan jantung & melancarkan sirkulasi darah keseluruh tubuh. \n");
                break;
        }
    }
}

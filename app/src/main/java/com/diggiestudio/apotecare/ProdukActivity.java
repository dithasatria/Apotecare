package com.diggiestudio.apotecare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.diggiestudio.apotecare.utilities.PrefManager;

public class ProdukActivity extends AppCompatActivity {

    private PrefManager manager = new PrefManager();

    TextView tvProdukName1, tvProdukName2, tvProdukName3, tvProdukName4;
    //TextView tvProdukPrice1, tvProdukPrice2, tvProdukPrice3, tvProdukPrice4;
    ImageView imgProduk1, imgProduk2, imgProduk3, imgProduk4;

    GridLayout mainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk);

        tvProdukName1 = findViewById(R.id.tvProdukName);
        tvProdukName2 = findViewById(R.id.tvProdukName2);
        tvProdukName3 = findViewById(R.id.tvProdukName3);
        tvProdukName4 = findViewById(R.id.tvProdukName4);

        /*
        tvProdukPrice1 = findViewById(R.id.tvProdukPrice);
        tvProdukPrice2 = findViewById(R.id.tvProdukPrice2);
        tvProdukPrice3 = findViewById(R.id.tvProdukPrice3);
        tvProdukPrice4 = findViewById(R.id.tvProdukPrice4);
        */

        imgProduk1 = findViewById(R.id.imgProduk);
        imgProduk2 = findViewById(R.id.imgProduk2);
        imgProduk3 = findViewById(R.id.imgProduk3);
        imgProduk4 = findViewById(R.id.imgProduk4);

        mainGrid = (GridLayout) findViewById(R.id.mainGrid);

        //Set Event

        boolean login = manager.getBoolean(this, "isLoggedIn");
        if(!login){
            Login();
        }

        Intent i = getIntent();
        String inProduk = i.getStringExtra("HasilProduk");

        //Toast.makeText(getApplicationContext(), "produk: "+inProduk, Toast.LENGTH_LONG).show();
        switch (inProduk)
        {
            case  "BMI_bbnaik":
                setProduk("SUSU PROTEIN " + "\n"+ "UNIVERSAL GAINFAST 1000G", "Rp. 558.000", "BMI_bbnaik");
                break;
            case "BMI_bbturun":
                setProduk("NUTRIMAX WAIST" + "\n"+ "STRIMMER @30", "Rp. 295.000", "BMI_bbturun");
                break;
            case "CVD":
                setProduk("N.PLUS GARLIC @100", "Rp. 390.000", "CVD");
                break;
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setProduk(String produkName, String produkPrice, String produkImg)
    {
        tvProdukName1.setText(produkName);
        tvProdukName2.setText(produkName);
        tvProdukName3.setText(produkName);
        tvProdukName4.setText(produkName);

        /*
        tvProdukPrice1.setText(produkPrice);
        tvProdukPrice2.setText(produkPrice);
        tvProdukPrice3.setText(produkPrice);
        tvProdukPrice4.setText(produkPrice);
        */

        if(produkImg.equals("BMI_bbnaik")) {
            imgProduk1.setImageResource(R.drawable.suplemen_gain_fast);
            imgProduk2.setImageResource(R.drawable.suplemen_gain_fast);
            imgProduk3.setImageResource(R.drawable.suplemen_gain_fast);
            imgProduk4.setImageResource(R.drawable.suplemen_gain_fast);
        }
        else if(produkImg.equals("BMI_bbturun")) {
            imgProduk1.setImageResource(R.drawable.bmi_bbturun);
            imgProduk2.setImageResource(R.drawable.bmi_bbturun);
            imgProduk3.setImageResource(R.drawable.bmi_bbturun);
            imgProduk4.setImageResource(R.drawable.bmi_bbturun);
        }
        else if(produkImg.equals("CVD")){
            imgProduk1.setImageResource(R.drawable.cvd_produk);
            imgProduk2.setImageResource(R.drawable.cvd_produk);
            imgProduk3.setImageResource(R.drawable.cvd_produk);
            imgProduk4.setImageResource(R.drawable.cvd_produk);
        }

        setSingleEvent(mainGrid, produkImg);
    }

    private void setSingleEvent(GridLayout mainGrid, final String produk) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), ProdukDetailActivity.class);
                    intent.putExtra("ProdukDetail", produk);
                    startActivity(intent);
                }
            });
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

package com.diggiestudio.apotecare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.diggiestudio.apotecare.utilities.PrefManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUserId, etPassword;
    Button btnLogin;

    private PrefManager manager = new PrefManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        boolean login = manager.getBoolean(this, "isLoggedIn");
        if(login){
            mainMenu();
        }

        etUserId = findViewById(R.id.etUserID);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        //getSupportActionBar().hide();

        btnLogin.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                doLogin();
                break;
        }
    }

    public void mainMenu(){
        startActivity(new Intent(this, CVDActivity.class));
        finish();
    }

    public void doLogin()
    {
        if(etPassword.getText().toString().equals("") || etUserId.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Harap isi semua form yang ada!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String userID = etUserId.getText().toString();
            String password = etPassword.getText().toString();

            if(userID.equals("userid") && password.equals("123456"))
            {
                Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_LONG).show();
                manager.saveBoolean(this, manager.KEY_IS_LOGIN, true);
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "User ID atau Password Salah!", Toast.LENGTH_LONG).show();
            }
        }
    }
}

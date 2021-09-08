package com.example.livereporterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT=1000;
    public static SharedPreferences shared;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String userid = "userKey";
    public static final String role = "roleKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String roleKey = (shared.getString(role, ""));
        String idKey = (shared.getString(userid, ""));

        final DBHelper db = new DBHelper(this);
        db.checkInit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, NewsHome.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);

    }
}
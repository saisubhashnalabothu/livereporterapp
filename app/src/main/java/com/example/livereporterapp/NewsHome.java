package com.example.livereporterapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class NewsHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_home);

        Button sportsBt = (Button) findViewById(R.id.sportsbt);
        Button polBt = (Button) findViewById(R.id.politicsbt);
        Button businessBt = (Button) findViewById(R.id.businessbt);
        Button genBt = (Button) findViewById(R.id.generalbt);

        sportsBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readNews("Sports");
            }
        });
        polBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readNews("Politics");
            }
        });
        businessBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readNews("Business");
            }
        });
        genBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readNews("General");
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.admin:
                Intent i = new Intent(NewsHome.this, AdminLogin.class);
                startActivity(i);
                finish();
                return true;
            case R.id.quit:
                this.finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void readNews(String cat){
        Intent i=new Intent(NewsHome.this,ReadNews.class);
        i.putExtra("category",cat);
        startActivity(i);
        finish();
    }
}
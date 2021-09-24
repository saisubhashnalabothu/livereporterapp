package com.example.livereporterapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;

public class ReadNews extends AppCompatActivity {

    private static final String TAG = "newsSearch";
    ArrayList<NewsItem> news;
    NewsAdapter nsa;
    final DBHelper db = new DBHelper(this);
    ListView spacesList;
    String cat;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_news);
        TextView rentBack = findViewById(R.id.renterSpacesBack);
        cat = getIntent().getStringExtra("category");

        spacesList = (ListView) findViewById(R.id.newslist);
        news = new ArrayList<>();
        TextView label = (TextView) findViewById(R.id.label);
        label.setText("Top "+cat+" News");

        try {
            loadListView(cat);
        } catch (ParseException e) {
            Intent i=new Intent(ReadNews.this,NewsHome.class);
            startActivity(i);
            finish();
            e.printStackTrace();
        }

        rentBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                if(!cat.equals("all"))
                    i = new Intent(ReadNews.this,NewsHome.class);
                else {
                    i = new Intent(ReadNews.this, AdminDashboard.class);
                    i.putExtra("user","admin");
                }

                startActivity(i);
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadListView( String cat) throws ParseException {
        news = db.getNews(cat);
        Log.d(TAG, "size: " + news.size());
        nsa = new NewsAdapter(this,news);
        spacesList.setAdapter(nsa);
        nsa.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        Intent i;
        if(!cat.equals("all"))
            i = new Intent(ReadNews.this,NewsHome.class);
        else {
            i = new Intent(ReadNews.this, AdminDashboard.class);
            i.putExtra("user","admin");
        }

        startActivity(i);
        finish();
    }
}
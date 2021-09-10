package com.example.livereporterapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter {

    Context context;
    ArrayList<NewsItem> news;

    public NewsAdapter(Context context, ArrayList<NewsItem> spaces) {
        this.context = context;
        this.news = spaces;
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int i) {
        return news.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.layout_news_item, null);

        TextView title = (TextView) view.findViewById(R.id.titletv);
        TextView loc = (TextView) view.findViewById(R.id.loctv);
        TextView newst = (TextView) view.findViewById(R.id.newstv);
        TextView datetv = (TextView) view.findViewById(R.id.datetv);
        ImageView img = (ImageView) view.findViewById(R.id.newsImg);

        NewsItem newsItem = news.get(i);


        title.setText(newsItem.title);
        loc.setText(newsItem.locality);
        newst.setText(newsItem.news);
        datetv.setText(newsItem.date);

        byte[] imgData = newsItem.image;
        Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
        img.setImageBitmap(bmp);

        return view;
    }
}


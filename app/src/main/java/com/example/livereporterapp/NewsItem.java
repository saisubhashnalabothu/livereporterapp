package com.example.livereporterapp;

public class NewsItem {
    String id, title, category, locality, news, date;
    byte[] image;

    public NewsItem(String id, String title,String category, String locality, String news, String date, byte[] image) {
        this.id = id;
        this.title = title;
        this.locality = locality;
        this.news = news;
        this.date = date;
        this.image = image;
    }

}

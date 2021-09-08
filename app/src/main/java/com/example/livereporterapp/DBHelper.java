package com.example.livereporterapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    public DBHelper(Context context){
        super(context,"parking_spaces.db",null,1);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE admin (id varchar(100) PRIMARY KEY,name varchar(100) NOT NULL,password varchar(100) NOT NULL,role varchar(8) NOT NULL DEFAULT 'admin')");
        db.execSQL("CREATE TABLE newsitem (id varchar(100) PRIMARY KEY,category varchar(30),title varchar(100) NOT NULL,img BLOB, locality varchar(100) NOT NULL,news varchar(1000) NOT NULL,date text)");

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists admin");
        db.execSQL("drop table if exists newsitem");
    }

    public Boolean insertAdmin(String id,String name,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("name",name);
        contentValues.put("password",password);
        contentValues.put("role","admin");

        long result = db.insert("admin",null,contentValues);

        Log.d(TAG, "admin inserted: " + id);

        if(result==-1)
            return false;
        return true;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Boolean insertNews(String title, String category, String locality, String news, byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String date = new SimpleDateFormat("dd/MM/yyyy - hh:mm", Locale.getDefault()).format(new Date());
        String id = new SimpleDateFormat("ddMMyyhhmmss", Locale.getDefault()).format(new Date());

        contentValues.put("id",id);
        contentValues.put("category",category);
        contentValues.put("title",title);
        contentValues.put("img",image);
        contentValues.put("locality",locality);
        contentValues.put("news",news);
        contentValues.put("date",date);

        long result = db.insert("newsitem",null,contentValues);


        if(result==-1)
            return false;
        return true;

    }

    public  Boolean checkInit(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from admin",null);
        if(cursor.getCount()>0) {
            cursor.close();
            return false;
        }
        cursor.close();

        insertAdmin("admin123","John Doe", "asdasd");
        return true;
    }

    public  Boolean checkLoginAdmin(String id,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from admin where id = ? and password = ?",new String[]{id,password});
        if(cursor.getCount()>0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }


    public ArrayList<NewsItem> getNews(String cat){

        ArrayList<NewsItem> newslist = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=null;

        if(!cat.equals("all"))
            cursor = db.rawQuery("select * from newsitem where category = ? ORDER BY id DESC",new String[]{cat});
        else
            cursor = db.rawQuery("select * from newsitem ORDER BY id DESC",null);

        while(cursor.moveToNext()){
            String id=cursor.getString(0);
            String category=cursor.getString(1);
            String title=cursor.getString(2);
            byte[] img=cursor.getBlob(3);
            String locality=cursor.getString(4);
            String news=cursor.getString(5);
            String date=cursor.getString(6);

            Log.d(TAG, "Fetching news id: " + id);
            newslist.add(new NewsItem(id,title,category,locality,news,date,img));
        }
        return newslist;

    }

}

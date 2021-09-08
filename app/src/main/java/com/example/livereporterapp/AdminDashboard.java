package com.example.livereporterapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class AdminDashboard extends AppCompatActivity {


    private static final int REQUEST_IMAGE_CAPTURE=101;
    static Bitmap imgBitmap;
    static boolean clicked=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        final DBHelper db = new DBHelper(this);
        final ImageView imageView = findViewById(R.id.regImg);
        final EditText namet = (EditText) findViewById(R.id.regTitle);
        final EditText loct = (EditText) findViewById(R.id.regLoc);
        final EditText newst = (EditText) findViewById(R.id.regNews);
        final RadioGroup rg = (RadioGroup) findViewById(R.id.catRad);

        final Button cambt = (Button) findViewById(R.id.camBt);
        final Button regNewsBt = (Button) findViewById(R.id.regBt);

        final String user = getIntent().getStringExtra("user");

        cambt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent imageIntent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
                startActivityForResult(imageIntent,REQUEST_IMAGE_CAPTURE);
            }
        });

        regNewsBt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String title = namet.getText().toString();
                String loc = loct.getText().toString();
                String news = newst.getText().toString();
                RadioButton rad = findViewById(rg.getCheckedRadioButtonId());
                String cat = rad.getText().toString();

                try {
                    if(title.equals("")||loc.equals("")||news.equals("")){
                        Toast.makeText(AdminDashboard.this,"Please fill all fields to continue", Toast.LENGTH_SHORT).show();
                    }
                    else if(!clicked) {
                        Toast.makeText(AdminDashboard.this, "Please click a live photo for the news", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                        byte[] img = bos.toByteArray();
                        Boolean insert = db.insertNews(title,cat,loc,news,img);
//                        Toast.makeText(AddSpace.this,insert, Toast.LENGTH_SHORT).show();
                        if(insert){
                            Toast.makeText(AdminDashboard.this,"News Added Successfully", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(AdminDashboard.this,AdminDashboard.class);
                            i.putExtra("user",user);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(AdminDashboard.this,"News could not be registered! Please try again", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                catch (Exception e){
                    Toast.makeText(AdminDashboard.this,"News could not be registered! Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            imgBitmap = (Bitmap) extras.get("data");
            final ImageView imageView = findViewById(R.id.regImg);
            imageView.setImageBitmap(imgBitmap);
            clicked=true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.adminmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Intent i = new Intent(AdminDashboard.this, NewsHome.class);
                startActivity(i);
                finish();
                return true;
            case R.id.quit:
                this.finishAffinity();
                return true;
            case R.id.list:
                i = new Intent(AdminDashboard.this, ReadNews.class);
                i.putExtra("category","all");
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
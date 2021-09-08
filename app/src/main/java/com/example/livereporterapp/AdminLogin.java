package com.example.livereporterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        final DBHelper db = new DBHelper(this);
        Button ownerLogBt = (Button) findViewById(R.id.ownerLoginBt);

        final EditText idt = (EditText) findViewById(R.id.ownerLoginIdt);
        final EditText passt = (EditText) findViewById(R.id.ownerPassIdt);

        ownerLogBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id="",pass="";
                try {
                    id = idt.getText().toString();
                    pass = passt.getText().toString();
                }
                catch (Exception e){
                    Log.e("ParkingSpacesApp", "exception", e);
                }

                if(id.equals("")||pass.equals("")){
                    Toast.makeText(AdminLogin.this,"Please fill all fields to continue", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean check = db.checkLoginAdmin(id,pass);
                    if(check){


                        SharedPreferences.Editor editor = MainActivity.shared.edit();

                        editor.putString(MainActivity.userid, id);
                        editor.putString(MainActivity.role, "admin");
                        editor.commit();

                        Intent i=new Intent(AdminLogin.this,AdminDashboard.class);
                        i.putExtra("user",id);
                        startActivity(i);
                        Toast.makeText(AdminLogin.this,"Admin Login Successful", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(AdminLogin.this,"Invalid User Credentials Entered", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
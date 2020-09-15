package com.example.delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import java.io.File;

public class rider_logout extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_logout);


        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/account/security2.txt");
        file.delete();


        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }
}
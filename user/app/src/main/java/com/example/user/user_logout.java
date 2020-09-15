package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import java.io.File;

public class user_logout extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logout);


        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/account/security.txt");
        file.delete();


        Intent intent = new Intent(this, user_login.class);
        startActivity(intent);
    }
}

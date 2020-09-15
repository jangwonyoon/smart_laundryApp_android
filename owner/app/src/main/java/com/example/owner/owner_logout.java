package com.example.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import static com.example.owner.owner_main.store_name1;

import java.io.File;

public class owner_logout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_logout);

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/account/security1.txt");
        file.delete();

        store_name1 = "";

        Intent intent = new Intent(this, owner_login.class);
        startActivity(intent);
    }
}

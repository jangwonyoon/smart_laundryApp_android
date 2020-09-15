package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class user_signup4 extends Activity {

    private Button go_to_main, go_to_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup4);

        go_to_main = (Button) findViewById(R.id.layout4_b2);

        go_to_main.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(user_signup4.this, MainActivity.class);
                startActivity(intent);
            }
        });

        go_to_login = (Button) findViewById(R.id.layout4_b1);

        go_to_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(user_signup4.this, user_login.class);
                startActivity(intent);
            }
        });
    }
}

package com.example.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.database.CursorJoiner;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;


public class owner_signup1 extends Activity {

    Button user_signup_back, user_signup_go;
    CheckBox user_signup_agree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_signup1);

        user_signup_back = (Button) findViewById(R.id.layout2_b1);

        user_signup_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(owner_signup1.this, MainActivity.class);
                startActivity(intent);
            }
        });

        user_signup_go = (Button) findViewById(R.id.layout2_b2);
        user_signup_agree = (CheckBox) findViewById(R.id.layouy2_c1);
        user_signup_go.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(owner_signup1.this, owner_signup2.class);
                if (user_signup_agree.isChecked())
                    startActivity(intent);
                else {
                    Toast.makeText(getApplicationContext(), "약관에 동의해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

package com.example.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.CursorJoiner;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;


public class forgot_id_pw extends Activity {

    Button signup_back, checkemail,b1,b2,b3,b4;
    EditText et1,et2,et3,et4;

    String user_email, snum;
    int num;
    String s_n;
    String user_email1,user_id1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_id_pw);

        b1 = (Button)findViewById(R.id.email);
        b2 = (Button) findViewById(R.id.layout2_b2);
        b3 = (Button) findViewById(R.id.layout2_b3);

        et1 = (EditText) findViewById(R.id.layout2_et1);
        et2 = (EditText)findViewById(R.id.layout2_et4);
        et3 = (EditText)findViewById(R.id.layout2_et2);
        et4 = (EditText) findViewById(R.id.layout2_et3);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_email = et1.getText().toString();
                user_email=user_email.replace(" ","");
                Random random = new Random();
                num = random.nextInt(999999);
                snum = String.valueOf(num);
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(getApplicationContext(),"이메일을 확인해주세요.",Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject = new JSONObject(response);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //서버로 Volley를 이용해서 요청을 함
                owner_signup2_email_db registerRequest = new owner_signup2_email_db(user_email,snum, responseListener);
                RequestQueue queue = Volley.newRequestQueue(forgot_id_pw.this);
                queue.add(registerRequest);
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getnum = et2.getText().toString();
                String realkey = String.valueOf(num);
                if (getnum.equals(realkey)){
                    et1.setEnabled(false);
                    s_n="yes";
                    AlertDialog.Builder builder = new AlertDialog.Builder(forgot_id_pw.this);
                    builder.setTitle("알림");
                    builder.setMessage("인증완료, 이메일을 확인하세요.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(forgot_id_pw.this, user_login.class);
                            startActivity(intent);
                        }
                    });
                    AlertDialog alert = builder.create();                                                       //빌더를 이용하여 AlertDialog객체를 생성합니다.
                    alert.show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(forgot_id_pw.this);
                    builder.setTitle("알림");
                    builder.setMessage("인증실패, 정확한 인증번호를 입력하세요.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alert = builder.create();                                                       //빌더를 이용하여 AlertDialog객체를 생성합니다.
                    alert.show();
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                };

                //서버로 Volley를 이용해서 요청을 함
                user_forgot_id_db registerRequest = new user_forgot_id_db(user_email,s_n, responseListener);
                RequestQueue queue = Volley.newRequestQueue(forgot_id_pw.this);
                queue.add(registerRequest);
            }
        });


        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    user_id1 = et3.getText().toString();
                    user_email1 = et4.getText().toString();
                    /*int cd = Integer.parseInt("0");*/

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "번호를 공백없이 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                if (user_id1.length() == 0 || user_email1.length() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(forgot_id_pw.this);
                    builder.setTitle("알림");
                    builder.setMessage("정보를 입력하세요.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alert = builder.create();                                                       //빌더를 이용하여 AlertDialog객체를 생성합니다.
                    alert.show();
                } else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                                if (success) {
                                    Intent intent = new Intent(forgot_id_pw.this, user_forgot_pw_change.class);
                                    intent.putExtra("user_email", user_email1);
                                    intent.putExtra("user_id",user_id1);

                                    startActivity(intent);
                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(forgot_id_pw.this);
                                    builder.setTitle("알림");
                                    builder.setMessage("ID와 E-mail을 다시 확인해주세요.");
                                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    AlertDialog alert = builder.create();                                                       //빌더를 이용하여 AlertDialog객체를 생성합니다.
                                    alert.show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };

                    //서버로 Volley를 이용해서 요청을 함
                    user_forgot_pw_db registerRequest = new user_forgot_pw_db(user_email1, user_id1, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(forgot_id_pw.this);
                    queue.add(registerRequest);
                }
            }
        });







        signup_back = (Button) findViewById(R.id.layout2_b1);
        signup_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(forgot_id_pw.this, MainActivity.class);
                startActivity(intent);
            }
        });

        /*checkemail = (Button) findViewById(R.id.layout2_b2);
        checkemail.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(forgot_id_pw.this);
                builder.setTitle("알림");
                builder.setMessage("이메일을 확인하세요.");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(forgot_id_pw.this, MainActivity.class);
                        Toast.makeText(getApplicationContext(), "메인으로 이동합니다.", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }
                });
                AlertDialog alert = builder.create();                                                       //빌더를 이용하여 AlertDialog객체를 생성합니다.
                alert.show();
            }
        });*/

        /*checkemail = (Button) findViewById(R.id.layout2_b3);
        checkemail.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(forgot_id_pw.this);
                builder.setTitle("알림");
                builder.setMessage("이메일을 확인하세요.");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(forgot_id_pw.this, MainActivity.class);
                        Toast.makeText(getApplicationContext(), "메인으로 이동합니다.", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }
                });
                AlertDialog alert = builder.create();                                                       //빌더를 이용하여 AlertDialog객체를 생성합니다.
                alert.show();

            }
        });*/

    }
}

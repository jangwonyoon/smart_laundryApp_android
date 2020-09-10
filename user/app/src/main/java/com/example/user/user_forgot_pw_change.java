package com.example.user;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class user_forgot_pw_change extends Activity {

    String user_email1,user_id1,u_pw;

    EditText et1;
    Button b1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_forgot_pw_change);

        Intent intent = getIntent();
        user_email1 = intent.getStringExtra("user_email");
        user_id1 = intent.getStringExtra("user_id");

        et1 = findViewById(R.id.et1);
        b1 = findViewById(R.id.b1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    u_pw = et1.getText().toString();



                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "번호를 공백없이 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                if (et1.getText().toString().length()<=7 || et1.getText().toString().length()>=17) {
                    Toast.makeText(getApplicationContext(), "비밀번호(8~16자)를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    u_pw = change_hash(u_pw);
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(user_forgot_pw_change.this);
                                    builder.setTitle("알림");
                                    builder.setMessage("비밀번호가 변경되었습니다.");
                                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(user_forgot_pw_change.this, user_login.class);

                                            startActivity(intent);
                                        }
                                    });
                                    AlertDialog alert = builder.create();                                                       //빌더를 이용하여 AlertDialog객체를 생성합니다.
                                    alert.show();
                                    return;
                                }
                                else {

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };

                    //서버로 Volley를 이용해서 요청을 함
                    user_forgot_pw_change_db registerRequest = new user_forgot_pw_change_db(user_email1, user_id1,u_pw, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(user_forgot_pw_change.this);
                    queue.add(registerRequest);
                }
            }
        });
    }

    public String change_hash(String text){
        // SHA-256 MessageDigest의 생성
        MessageDigest mdSHA256 = null;
        try {
            mdSHA256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // " Java 마스터! " 문자열 바이트로 메시지 다이제스트를 갱신
        try {
            mdSHA256.update(text.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 해시 계산 반환값은 바이트 배열
        byte[] sha256Hash = mdSHA256.digest();

        // 바이트배열을 16진수 문자열로 변환하여 표시
        StringBuilder hexSHA256hash = new StringBuilder();
        for(byte b : sha256Hash) {
            String hexString = String.format("%02x", b);
            hexSHA256hash.append(hexString);
        }

        return hexSHA256hash.toString();
    }
}

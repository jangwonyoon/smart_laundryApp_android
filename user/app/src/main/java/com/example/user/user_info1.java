package com.example.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class user_info1 extends Activity {

    String user_name1, user_address1, user_id1, user_email1, user_password1, user_address_detail1;
    int user_number1;
    Double user_lat1, user_long1;

    private TextView tv1,tv2;
    private Button b1,b2,b3,b4;
    private EditText et1,et2,et3,et4;

    private long backBtnTime = 0;

    public user_info1() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info1);

        Intent intent = getIntent();
        user_name1 = intent.getStringExtra("user_name");
        user_address1 = intent.getStringExtra("user_address");
        user_lat1 = intent.getDoubleExtra("user_lat",0.0);
        user_long1 = intent.getDoubleExtra("user_long",0.0);
        user_id1 = intent.getStringExtra("user_id");
        user_password1 = intent.getStringExtra("user_password");
        user_email1 = intent.getStringExtra("user_email");
        user_number1 = intent.getIntExtra("user_number",0);
        user_address_detail1 = intent.getStringExtra("user_address_detail");


        tv1 = findViewById(R.id.layout_tv1);
        tv2 = findViewById(R.id.layout_tv2);

        tv1.setText(user_name1);
        tv2.setText(user_id1);

        b1 = findViewById(R.id.layout_b1);
        b2 = findViewById(R.id.layout_b2);
        b3 = findViewById(R.id.layout_b3);
        b4 = findViewById(R.id.layout_b4);

        et2 = (EditText) findViewById(R.id.layout_et2);
        et3 = (EditText) findViewById(R.id.layout_et3);

        et2.setHint(String.valueOf(user_number1));
        et3.setHint(user_email1);




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et1 = (EditText) findViewById(R.id.layout_et1);
                String user_password = et1.getText().toString();
                int user_number = user_number1;
                String user_email = user_email1;
                String user_id = user_id1;

                user_password = change_hash(user_password);

                AlertDialog.Builder builder = new AlertDialog.Builder(user_info1.this);
                builder.setTitle("알림");
                builder.setMessage("비밀번호가 변경되었습니다.");
                AlertDialog alert = builder.create();                                                       //빌더를 이용하여 AlertDialog객체를 생성합니다.
                alert.show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                String user_name  = jsonObject.getString("user_name");
                                String user_address = jsonObject.getString("user_address");
                                Double user_lat = jsonObject.getDouble("user_lat");
                                Double user_long = jsonObject.getDouble("user_long");
                                String user_id = jsonObject.getString("user_id");
                                String user_password = jsonObject.getString("user_password");
                                int user_number = jsonObject.getInt("user_number");
                                String user_email = jsonObject.getString("user_email");

                                Intent intent = new Intent(user_info1.this, user_info1.class);

                                intent.putExtra("user_name",user_name);
                                intent.putExtra("user_address",user_address);
                                intent.putExtra("user_lat",user_lat);
                                intent.putExtra("user_long",user_long);
                                intent.putExtra("user_id",user_id);
                                intent.putExtra("user_password",user_password);
                                intent.putExtra("user_number",user_number);
                                intent.putExtra("user_email",user_email);
                                intent.putExtra("user_address_detail",user_address_detail1);
                                startActivity(intent);
                            }
                            //실패한 경우
                            else{
                                Toast.makeText(getApplicationContext(),"정확히 입력해주세요.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                user_info1_db loginRequest = new user_info1_db(user_password, user_number, user_email, user_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(user_info1.this);
                queue.add(loginRequest);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*et2 = (EditText) findViewById(R.id.layout_et2);*/
                String user_password = user_password1;
                int user_number = Integer.parseInt(et2.getText().toString());
                String user_email = user_email1;
                String user_id = user_id1;

                AlertDialog.Builder builder = new AlertDialog.Builder(user_info1.this);
                builder.setTitle("알림");
                builder.setMessage("번호가 변경되었습니다.");
                AlertDialog alert = builder.create();                                                       //빌더를 이용하여 AlertDialog객체를 생성합니다.
                alert.show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                String user_name  = jsonObject.getString("user_name");
                                String user_address = jsonObject.getString("user_address");
                                Double user_lat = jsonObject.getDouble("user_lat");
                                Double user_long = jsonObject.getDouble("user_long");
                                String user_id = jsonObject.getString("user_id");
                                String user_password = jsonObject.getString("user_password");
                                int user_number = jsonObject.getInt("user_number");
                                String user_email = jsonObject.getString("user_email");

                                Intent intent = new Intent(user_info1.this, user_info1.class);

                                intent.putExtra("user_name",user_name);
                                intent.putExtra("user_address",user_address);
                                intent.putExtra("user_lat",user_lat);
                                intent.putExtra("user_long",user_long);
                                intent.putExtra("user_id",user_id);
                                intent.putExtra("user_password",user_password);
                                intent.putExtra("user_number",user_number);
                                intent.putExtra("user_email",user_email);
                                intent.putExtra("user_address_detail",user_address_detail1);
                                startActivity(intent);
                            }
                            //실패한 경우
                            else{
                                Toast.makeText(getApplicationContext(),"정확히 입력해주세요.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                user_info1_db loginRequest = new user_info1_db(user_password, user_number, user_email, user_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(user_info1.this);
                queue.add(loginRequest);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*et3 = (EditText) findViewById(R.id.layout_et3);*/
                String user_password = user_password1;
                int user_number = user_number1;
                String user_email = et3.getText().toString();
                String user_id = user_id1;

                AlertDialog.Builder builder = new AlertDialog.Builder(user_info1.this);
                builder.setTitle("알림");
                builder.setMessage("이메일이 변경되었습니다.");
                AlertDialog alert = builder.create();                                                       //빌더를 이용하여 AlertDialog객체를 생성합니다.
                alert.show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                String user_name  = jsonObject.getString("user_name");
                                String user_address = jsonObject.getString("user_address");
                                Double user_lat = jsonObject.getDouble("user_lat");
                                Double user_long = jsonObject.getDouble("user_long");
                                String user_id = jsonObject.getString("user_id");
                                String user_password = jsonObject.getString("user_password");
                                int user_number = jsonObject.getInt("user_number");
                                String user_email = jsonObject.getString("user_email");

                                Intent intent = new Intent(user_info1.this, user_info1.class);

                                intent.putExtra("user_name",user_name);
                                intent.putExtra("user_address",user_address);
                                intent.putExtra("user_lat",user_lat);
                                intent.putExtra("user_long",user_long);
                                intent.putExtra("user_id",user_id);
                                intent.putExtra("user_password",user_password);
                                intent.putExtra("user_number",user_number);
                                intent.putExtra("user_email",user_email);
                                intent.putExtra("user_address_detail",user_address_detail1);
                                startActivity(intent);
                            }
                            //실패한 경우
                            else{
                                Toast.makeText(getApplicationContext(),"정확히 입력해주세요.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                user_info1_db loginRequest = new user_info1_db(user_password, user_number, user_email, user_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(user_info1.this);
                queue.add(loginRequest);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et4 = (EditText) findViewById(R.id.layout_et3);
                String user_password = user_password1;
                int user_number = user_number1;
                String user_email = user_email1;
                String user_id = user_id1;

                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/account/security.txt");
                file.delete();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                String user_name  = jsonObject.getString("user_name");
                                String user_address = jsonObject.getString("user_address");
                                Double user_lat = jsonObject.getDouble("user_lat");
                                Double user_long = jsonObject.getDouble("user_long");
                                String user_id = jsonObject.getString("user_id");
                                String user_password = jsonObject.getString("user_password");
                                int user_number = jsonObject.getInt("user_number");
                                String user_email = jsonObject.getString("user_email");

                                Intent intent = new Intent(user_info1.this, user_login.class);

                                intent.putExtra("user_name",user_name);
                                intent.putExtra("user_address",user_address);
                                intent.putExtra("user_lat",user_lat);
                                intent.putExtra("user_long",user_long);
                                intent.putExtra("user_id",user_id);
                                intent.putExtra("user_password",user_password);
                                intent.putExtra("user_number",user_number);
                                intent.putExtra("user_email",user_email);
                                intent.putExtra("user_address_detail",user_address_detail1);
                                startActivity(intent);
                            }
                            //실패한 경우
                            else{
                                Toast.makeText(getApplicationContext(),"정확히 입력해주세요.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                user_info1_db loginRequest = new user_info1_db(user_password, user_number, user_email, user_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(user_info1.this);
                queue.add(loginRequest);
            }
        });


    }
    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if(0 <= gapTime && 2000 >= gapTime) {
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        else {
            backBtnTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
        }
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

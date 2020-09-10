package com.example.delivery;

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

public class rider_info1 extends Activity {

    String rider_name1, rider_address1, rider_id1, rider_email1, rider_password1;
    String rider_number1;
    Double rider_lat1, rider_long1;

    private TextView tv1,tv2;
    private Button b1,b2,b3,b4;
    private EditText et1,et2,et3,et4;

    private long backBtnTime = 0;

    public rider_info1() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_info1);

        Intent intent = getIntent();
        rider_name1 = intent.getStringExtra("rider_name");
        rider_address1 = intent.getStringExtra("rider_address");
        rider_lat1 = intent.getDoubleExtra("rider_lat",0.0);
        rider_long1 = intent.getDoubleExtra("rider_long",0.0);
        rider_id1 = intent.getStringExtra("rider_id");
        rider_password1 = intent.getStringExtra("rider_password");
        rider_email1 = intent.getStringExtra("rider_email");
        rider_number1 = intent.getStringExtra("rider_number");


        tv1 = findViewById(R.id.layout_tv1);
        tv2 = findViewById(R.id.layout_tv2);

        tv1.setText(rider_name1);
        tv2.setText(rider_id1);

        b1 = findViewById(R.id.layout_b1);
        b2 = findViewById(R.id.layout_b2);
        b3 = findViewById(R.id.layout_b3);
        b4 = findViewById(R.id.layout_b4);

        et2 = (EditText) findViewById(R.id.layout_et2);
        et3 = (EditText) findViewById(R.id.layout_et3);

        et2.setHint(String.valueOf(rider_number1));
        et3.setHint(rider_email1);




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et1 = (EditText) findViewById(R.id.layout_et1);
                String rider_password = et1.getText().toString();
                String rider_number = rider_number1;
                String rider_email = rider_email1;
                String rider_id = rider_id1;

                rider_password = change_hash(rider_password);

                AlertDialog.Builder builder = new AlertDialog.Builder(rider_info1.this);
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
                                String rider_name  = jsonObject.getString("rider_name");
                                String rider_address = jsonObject.getString("rider_address");
                                Double rider_lat = jsonObject.getDouble("rider_lat");
                                Double rider_long = jsonObject.getDouble("rider_long");
                                String rider_id = jsonObject.getString("rider_id");
                                String rider_password = jsonObject.getString("rider_password");
                                String rider_number = jsonObject.getString("rider_number");
                                String rider_email = jsonObject.getString("rider_email");

                                Intent intent = new Intent(rider_info1.this, rider_info1.class);

                                intent.putExtra("rider_name",rider_name);
                                intent.putExtra("rider_address",rider_address);
                                intent.putExtra("rider_lat",rider_lat);
                                intent.putExtra("rider_long",rider_long);
                                intent.putExtra("rider_id",rider_id);
                                intent.putExtra("rider_password",rider_password);
                                intent.putExtra("rider_number",rider_number);
                                intent.putExtra("rider_email",rider_email);

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
                rider_info1_db loginRequest = new rider_info1_db(rider_password, rider_number, rider_email, rider_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(rider_info1.this);
                queue.add(loginRequest);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*et2 = (EditText) findViewById(R.id.layout_et2);*/
                String rider_password = rider_password1;
                String rider_number = et2.getText().toString();
                String rider_email = rider_email1;
                String rider_id = rider_id1;

                AlertDialog.Builder builder = new AlertDialog.Builder(rider_info1.this);
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
                                String rider_name  = jsonObject.getString("rider_name");
                                String rider_address = jsonObject.getString("rider_address");
                                Double rider_lat = jsonObject.getDouble("rider_lat");
                                Double rider_long = jsonObject.getDouble("rider_long");
                                String rider_id = jsonObject.getString("rider_id");
                                String rider_password = jsonObject.getString("rider_password");
                                String rider_number = jsonObject.getString("rider_number");
                                String rider_email = jsonObject.getString("rider_email");

                                Intent intent = new Intent(rider_info1.this, rider_info1.class);

                                intent.putExtra("rider_name",rider_name);
                                intent.putExtra("rider_address",rider_address);
                                intent.putExtra("rider_lat",rider_lat);
                                intent.putExtra("rider_long",rider_long);
                                intent.putExtra("rider_id",rider_id);
                                intent.putExtra("rider_password",rider_password);
                                intent.putExtra("rider_number",rider_number);
                                intent.putExtra("rider_email",rider_email);

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
                rider_info1_db loginRequest = new rider_info1_db(rider_password, rider_number, rider_email, rider_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(rider_info1.this);
                queue.add(loginRequest);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*et3 = (EditText) findViewById(R.id.layout_et3);*/
                String rider_password = rider_password1;
                String rider_number = rider_number1;
                String rider_email = et3.getText().toString();
                String rider_id = rider_id1;

                AlertDialog.Builder builder = new AlertDialog.Builder(rider_info1.this);
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
                                String rider_name  = jsonObject.getString("rider_name");
                                String rider_address = jsonObject.getString("rider_address");
                                Double rider_lat = jsonObject.getDouble("rider_lat");
                                Double rider_long = jsonObject.getDouble("rider_long");
                                String rider_id = jsonObject.getString("rider_id");
                                String rider_password = jsonObject.getString("rider_password");
                                String rider_number = jsonObject.getString("rider_number");
                                String rider_email = jsonObject.getString("rider_email");

                                Intent intent = new Intent(rider_info1.this, rider_info1.class);

                                intent.putExtra("rider_name",rider_name);
                                intent.putExtra("rider_address",rider_address);
                                intent.putExtra("rider_lat",rider_lat);
                                intent.putExtra("rider_long",rider_long);
                                intent.putExtra("rider_id",rider_id);
                                intent.putExtra("rider_password",rider_password);
                                intent.putExtra("rider_number",rider_number);
                                intent.putExtra("rider_email",rider_email);

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
                rider_info1_db loginRequest = new rider_info1_db(rider_password, rider_number, rider_email, rider_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(rider_info1.this);
                queue.add(loginRequest);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et4 = (EditText) findViewById(R.id.layout_et3);
                String rider_password = rider_password1;
                String rider_number = rider_number1;
                String rider_email = rider_email1;
                String rider_id = rider_id1;

                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/account/security2.txt");
                file.delete();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                String rider_name  = jsonObject.getString("rider_name");
                                String rider_address = jsonObject.getString("rider_address");
                                Double rider_lat = jsonObject.getDouble("rider_lat");
                                Double rider_long = jsonObject.getDouble("rider_long");
                                String rider_id = jsonObject.getString("rider_id");
                                String rider_password = jsonObject.getString("rider_password");
                                String rider_number = jsonObject.getString("rider_number");
                                String rider_email = jsonObject.getString("rider_email");

                                Intent intent = new Intent(rider_info1.this, login.class);

                                intent.putExtra("rider_name",rider_name);
                                intent.putExtra("rider_address",rider_address);
                                intent.putExtra("rider_lat",rider_lat);
                                intent.putExtra("rider_long",rider_long);
                                intent.putExtra("rider_id",rider_id);
                                intent.putExtra("rider_password",rider_password);
                                intent.putExtra("rider_number",rider_number);
                                intent.putExtra("rider_email",rider_email);

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
                rider_info1_db loginRequest = new rider_info1_db(rider_password, rider_number, rider_email, rider_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(rider_info1.this);
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
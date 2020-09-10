package com.example.delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class login extends Activity {

    String[] permission_list = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private Button findyourid, user_login, user_signup_button;
    private EditText user_id1, user_password1;

    private long backBtnTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkPermission();
        user_id1 = (EditText) findViewById(R.id.layout5_et1);
        user_password1 = (EditText) findViewById(R.id.layout5_et2);

        user_signup_button = (Button) findViewById(R.id.layout5_b3);
        user_signup_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(login.this, rider_signup1.class);
                startActivity(intent);
            }
        });

        /*findyourid = (Button) findViewById(R.id.layout5_b1);
        findyourid.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(user_login.this, forgot_id_pw.class);
                startActivity(intent);
            }
        });*/

        user_login = (Button) findViewById(R.id.layout5_b2);
        user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user_id = user_id1.getText().toString();
                final String user_password = user_password1.getText().toString();

                final String u_p = change_hash(user_password);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                /////////////////////// 파일 쓰기 ///////////////////////
                                // 파일 생성
                                File saveFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/account"); // 저장 경로
                                // 폴더 생성
                                if(!saveFile.exists()){ // 폴더 없을 경우
                                    saveFile.mkdir(); // 폴더 생성
                                }
                                try {
                                    long now = System.currentTimeMillis(); // 현재시간 받아오기
                                    Date date = new Date(now); // Date 객체 생성
                                    SimpleDateFormat sdf = new SimpleDateFormat();
                                    String nowTime = sdf.format(date);

                                    BufferedWriter buf = new BufferedWriter(new FileWriter(saveFile+"/security2.txt", true));
                                    /*buf.append(nowTime + " "); // 날짜 쓰기*/

                                    buf.append(user_id+" "+u_p); // 파일 쓰기
                                    buf.newLine(); // 개행
                                    buf.close();

                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                String rider_name  = jsonObject.getString("rider_name");
                                String rider_address = jsonObject.getString("rider_address");
                                Double rider_lat = jsonObject.getDouble("rider_lat");
                                Double rider_long = jsonObject.getDouble("rider_long");
                                String rider_id = jsonObject.getString("rider_id");


                                Toast.makeText(getApplicationContext(),"접속 성공",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(login.this, MainActivity.class);
                                intent.putExtra("rider_name",rider_name);
                                intent.putExtra("rider_address",rider_address);
                                intent.putExtra("rider_lat",rider_lat);
                                intent.putExtra("rider_long",rider_long);
                                intent.putExtra("rider_id",rider_id);

                                startActivity(intent);
                            }
                            //실패한 경우
                            else{
                                Toast.makeText(getApplicationContext(),"정확한 아이디/패스워드를 입력해주세요.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                rider_login_db loginRequest = new rider_login_db(user_id, u_p, responseListener);
                RequestQueue queue = Volley.newRequestQueue(login.this);
                queue.add(loginRequest);
            }
        });

        // 파일 생성
        String line = null; // 한줄씩 읽기
        File saveFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/account"); // 저장 경로
        // 폴더 생성
        if(!saveFile.exists()){ // 폴더 없을 경우
            saveFile.mkdir(); // 폴더 생성
        }
        try {
            BufferedReader buf = new BufferedReader(new FileReader(saveFile+"/security2.txt"));
            while((line=buf.readLine())!=null){
                StringBuilder tv = new StringBuilder();
                String info = tv.append(line).toString();
                /*tv.append("\n");*/
                String[] arr = info.split(" ");
                String id = arr[0];
                String pw = arr[1];
                /*Toast.makeText(getApplicationContext(),id+pw,Toast.LENGTH_SHORT).show();*/

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


                                Toast.makeText(getApplicationContext(),"접속 성공",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(login.this, MainActivity.class);
                                intent.putExtra("rider_name",rider_name);
                                intent.putExtra("rider_address",rider_address);
                                intent.putExtra("rider_lat",rider_lat);
                                intent.putExtra("rider_long",rider_long);
                                intent.putExtra("rider_id",rider_id);
                                startActivity(intent);
                            }
                            //실패한 경우
                            else{
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                rider_login_db registerRequest = new rider_login_db(id, pw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(this);
                queue.add(registerRequest);
            }
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void checkPermission(){
        //현재 안드로이드 버전이 6.0미만이면 메서드를 종료한다.
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;

        for(String permission : permission_list){
            //권한 허용 여부를 확인한다.
            int chk = checkCallingOrSelfPermission(permission);

            if(chk == PackageManager.PERMISSION_DENIED){
                //권한 허용을여부를 확인하는 창을 띄운다
                requestPermissions(permission_list,0);
            }
        }
    }
}
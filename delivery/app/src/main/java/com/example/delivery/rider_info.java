package com.example.delivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class rider_info extends AppCompatActivity{

    private Button user_ch_info;
    private EditText rider_password1;
    private long backBtnTime = 0;

    TextView get_text;

    String rider_name1, rider_address1, rider_id1;
    Double rider_lat1, rider_long1;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_info);

        rider_password1 = (EditText) findViewById(R.id.layout5_et2);

        Intent intent = getIntent();
        rider_name1 = intent.getStringExtra("rider_name");
        rider_address1 = intent.getStringExtra("rider_address");
        rider_lat1 = intent.getDoubleExtra("rider_lat",0.0);
        rider_long1 = intent.getDoubleExtra("rider_long",0.0);
        rider_id1 = intent.getStringExtra("rider_id");


        //액션바 설정하기//
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle("[개인정보]  "+rider_name1+"님 안녕하세요.");
        //액션바 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));

        /*menubar= (Button) findViewById(R.id.btn_open);
        menubar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(user_main1.this , menubar);



                MenuInflater inf = popup.getMenuInflater();
                inf.inflate(R.menu.menu1, popup.getMenu());
                popup.show();
            }
        });*/


        user_ch_info = (Button) findViewById(R.id.layout5_b1);
        user_ch_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rider_id = rider_id1;
                String rider_password = rider_password1.getText().toString();

                rider_password = change_hash(rider_password);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                String rider_name  = jsonObject.getString("rider_name");
                                String rider_id = jsonObject.getString("rider_id");
                                String rider_number = jsonObject.getString("rider_number");
                                String rider_password = jsonObject.getString("rider_password");
                                String rider_email = jsonObject.getString("rider_email");

                                Toast.makeText(getApplicationContext(),"접속 성공",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplication(), rider_info1.class);
                                intent.putExtra("rider_name",rider_name1);
                                intent.putExtra("rider_address",rider_address1);
                                intent.putExtra("rider_lat",rider_lat1);
                                intent.putExtra("rider_long",rider_long1);
                                intent.putExtra("rider_id",rider_id1);
                                intent.putExtra("rider_number",rider_number);
                                intent.putExtra("rider_password",rider_password);
                                intent.putExtra("rider_email",rider_email);
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
                rider_login_db loginRequest = new rider_login_db(rider_id, rider_password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(rider_info.this);
                queue.add(loginRequest);
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(item.getItemId()){
            case R.id.b1:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("rider_name",rider_name1);
                intent.putExtra("rider_address",rider_address1);
                intent.putExtra("rider_lat",rider_lat1);
                intent.putExtra("rider_long",rider_long1);
                intent.putExtra("rider_id",rider_id1);
                startActivity(intent);
                break;
            case R.id.b2:
                Intent intent1 = new Intent(this, rider_gongji.class);
                intent1.putExtra("rider_name",rider_name1);
                intent1.putExtra("rider_address",rider_address1);
                intent1.putExtra("rider_lat",rider_lat1);
                intent1.putExtra("rider_long",rider_long1);
                intent1.putExtra("rider_id",rider_id1);
                startActivity(intent1);
                break;
            case R.id.b3:
                Intent intent2 = new Intent(this, rider_changelocation.class);
                intent2.putExtra("rider_name",rider_name1);
                intent2.putExtra("rider_address",rider_address1);
                intent2.putExtra("rider_lat",rider_lat1);
                intent2.putExtra("rider_long",rider_long1);
                intent2.putExtra("rider_id",rider_id1);
                startActivity(intent2);
                break;
            case R.id.b4:
                Intent intent3 = new Intent(this, rider_info.class);
                intent3.putExtra("rider_name",rider_name1);
                intent3.putExtra("rider_address",rider_address1);
                intent3.putExtra("rider_lat",rider_lat1);
                intent3.putExtra("rider_long",rider_long1);
                intent3.putExtra("rider_id",rider_id1);
                startActivity(intent3);
                break;

            case R.id.b8:
                Intent intent7 = new Intent(this, rider_logout.class);
                startActivity(intent7);
                break;

        }

        return super.onOptionsItemSelected(item);
    }
    public void onClick(View v){

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
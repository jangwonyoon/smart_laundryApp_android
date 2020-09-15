package com.example.owner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

public class owner_info1 extends Activity {

    private long backBtnTime = 0;

    String owner_name1, owner_address1,store_name1,owner_id1, owner_password1, owner_email1,cd1, owner_nin1;
    Double owner_lat1, owner_long1;
    int owner_number1, owner_store_number1;
    String cd2;
    private TextView tv1,tv2,cd;
    private Button b1,b2,b3,b4,b5,b6,b7,b8,b9;
    private EditText et1,et2,et3,et4,et5,et6,et7;
    String a="1";
    String b="2";
    String c="3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_info1);

        Intent intent = getIntent();
        owner_name1 = intent.getStringExtra("owner_name");
        owner_address1 = intent.getStringExtra("owner_address");
        owner_lat1 = intent.getDoubleExtra("owner_lat",0.0);
        owner_long1 = intent.getDoubleExtra("owner_long",0.0);
        owner_id1 = intent.getStringExtra("owner_id");
        owner_number1 = intent.getIntExtra("owner_number",0);
        owner_password1 = intent.getStringExtra("owner_password");
        owner_email1 = intent.getStringExtra("owner_email");
        owner_store_number1 = intent.getIntExtra("owner_store_number",0);
        owner_nin1 = intent.getStringExtra("owner_nin");
        store_name1 = intent.getStringExtra("store_name");

        cd1 = intent.getStringExtra("cd");

        tv1 = findViewById(R.id.layout_tv1);
        tv2 = findViewById(R.id.layout_tv2);

        tv1.setText(owner_name1);
        tv2.setText(owner_id1);

        b1 = findViewById(R.id.layout_b1);
        b2 = findViewById(R.id.layout_b2);
        b3 = findViewById(R.id.layout_b3);
        b4 = findViewById(R.id.layout_b4);
        b5 = findViewById(R.id.layout_b5);
        b7 = findViewById(R.id.layout_b7);
        b8 = findViewById(R.id.layout_b8);
        b9 = findViewById(R.id.layout_b9);

        et2 = (EditText) findViewById(R.id.layout_et2);
        et3 = (EditText) findViewById(R.id.layout_et3);
        et4 = (EditText) findViewById(R.id.layout_et4);
        et5 = (EditText) findViewById(R.id.layout_et5);
        et6 = (EditText) findViewById(R.id.layout_et6);
        et7 = findViewById(R.id.layout_et7);

        et2.setHint(String.valueOf(owner_number1));
        et3.setHint(owner_email1);
        et4.setHint(String.valueOf(owner_store_number1));

        cd = (TextView) findViewById(R.id.layout_cd);
        b6 = (Button) findViewById(R.id.layout_b6);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                    if(success){ //회원등록에 성공한 경우
                        cd2 = jsonObject.getString("cd");
                        int b = jsonObject.getInt("b");
                        if (cd2.equals("0")){
                            cd.setText("개시");
                        }
                        else{
                            cd.setText("마감");
                        }

                        et7.setHint(b+"");

                    }
                    //실패한 경우
                    else{
                        Toast.makeText(getApplicationContext(),"중복된 아이디입니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        //서버로 Volley를 이용해서 요청을 함
        owner_info11_db registerRequest = new owner_info11_db(store_name1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(owner_info1.this);
        queue.add(registerRequest);

        b6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                cd2 = jsonObject.getString("cd");
                                if (cd2.equals("0")){
                                    cd.setText("개시");
                                }
                                else{
                                    cd.setText("마감");
                                }
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
                owner_info1_db_cd loginRequest = new owner_info1_db_cd(store_name1, responseListener);
                RequestQueue queue = Volley.newRequestQueue(owner_info1.this);
                queue.add(loginRequest);
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et1 = (EditText) findViewById(R.id.layout_et1);
                String owner_password = et1.getText().toString();
                int owner_number = owner_number1;
                String owner_email = owner_email1;
                int owner_store_number = owner_store_number1;
                String store_name = store_name1;

                owner_password = change_hash(owner_password);

                AlertDialog.Builder builder = new AlertDialog.Builder(owner_info1.this);
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
                                String owner_name  = jsonObject.getString("owner_name");
                                String owner_address = jsonObject.getString("owner_address");
                                Double owner_lat = jsonObject.getDouble("owner_lat");
                                Double owner_long = jsonObject.getDouble("owner_long");
                                String owner_id = jsonObject.getString("owner_id");
                                String owner_password = jsonObject.getString("owner_password");
                                int owner_number = jsonObject.getInt("owner_number");
                                String owner_email = jsonObject.getString("owner_email");
                                int owner_store_number = jsonObject.getInt("owner_store_number");
                                String store_name = jsonObject.getString("store_name");
                                String owner_nin = jsonObject.getString("owner_nin");
                                String cd = jsonObject.getString("cd");

                                Intent intent = new Intent(owner_info1.this, owner_info1.class);

                                intent.putExtra("owner_name",owner_name);
                                intent.putExtra("owner_address",owner_address);
                                intent.putExtra("owner_lat",owner_lat);
                                intent.putExtra("owner_long",owner_long);
                                intent.putExtra("owner_id",owner_id);
                                intent.putExtra("owner_password",owner_password);
                                intent.putExtra("owner_number",owner_number);
                                intent.putExtra("owner_email",owner_email);
                                intent.putExtra("owner_store_number",owner_store_number);
                                intent.putExtra("store_name",store_name);
                                intent.putExtra("owner_nin",owner_nin);
                                intent.putExtra("cd",cd);
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
                owner_info1_db loginRequest = new owner_info1_db(owner_password, owner_number, owner_email, owner_store_number, store_name, responseListener);
                RequestQueue queue = Volley.newRequestQueue(owner_info1.this);
                queue.add(loginRequest);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String owner_password = owner_password1;
                int owner_number = Integer.parseInt(et2.getText().toString());
                String owner_email = owner_email1;
                int owner_store_number = owner_store_number1;
                String store_name = store_name1;

                AlertDialog.Builder builder = new AlertDialog.Builder(owner_info1.this);
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
                                String owner_name  = jsonObject.getString("owner_name");
                                String owner_address = jsonObject.getString("owner_address");
                                Double owner_lat = jsonObject.getDouble("owner_lat");
                                Double owner_long = jsonObject.getDouble("owner_long");
                                String owner_id = jsonObject.getString("owner_id");
                                String owner_password = jsonObject.getString("owner_password");
                                int owner_number = jsonObject.getInt("owner_number");
                                String owner_email = jsonObject.getString("owner_email");
                                int owner_store_number = jsonObject.getInt("owner_store_number");
                                String store_name = jsonObject.getString("store_name");
                                String owner_nin = jsonObject.getString("owner_nin");
                                String cd = jsonObject.getString("cd");

                                Intent intent = new Intent(owner_info1.this, owner_info1.class);

                                intent.putExtra("owner_name",owner_name);
                                intent.putExtra("owner_address",owner_address);
                                intent.putExtra("owner_lat",owner_lat);
                                intent.putExtra("owner_long",owner_long);
                                intent.putExtra("owner_id",owner_id);
                                intent.putExtra("owner_password",owner_password);
                                intent.putExtra("owner_number",owner_number);
                                intent.putExtra("owner_email",owner_email);
                                intent.putExtra("owner_store_number",owner_store_number);
                                intent.putExtra("store_name",store_name);
                                intent.putExtra("owner_nin",owner_nin);
                                intent.putExtra("cd",cd);
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
                owner_info1_db loginRequest = new owner_info1_db(owner_password, owner_number, owner_email, owner_store_number, store_name, responseListener);
                RequestQueue queue = Volley.newRequestQueue(owner_info1.this);
                queue.add(loginRequest);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String owner_password = owner_password1;
                int owner_number = owner_number1;
                String owner_email = et3.getText().toString();
                int owner_store_number = owner_store_number1;
                String store_name = store_name1;

                AlertDialog.Builder builder = new AlertDialog.Builder(owner_info1.this);
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
                                String owner_name  = jsonObject.getString("owner_name");
                                String owner_address = jsonObject.getString("owner_address");
                                Double owner_lat = jsonObject.getDouble("owner_lat");
                                Double owner_long = jsonObject.getDouble("owner_long");
                                String owner_id = jsonObject.getString("owner_id");
                                String owner_password = jsonObject.getString("owner_password");
                                int owner_number = jsonObject.getInt("owner_number");
                                String owner_email = jsonObject.getString("owner_email");
                                int owner_store_number = jsonObject.getInt("owner_store_number");
                                String store_name = jsonObject.getString("store_name");
                                String owner_nin = jsonObject.getString("owner_nin");
                                String cd = jsonObject.getString("cd");

                                Intent intent = new Intent(owner_info1.this, owner_info1.class);

                                intent.putExtra("owner_name",owner_name);
                                intent.putExtra("owner_address",owner_address);
                                intent.putExtra("owner_lat",owner_lat);
                                intent.putExtra("owner_long",owner_long);
                                intent.putExtra("owner_id",owner_id);
                                intent.putExtra("owner_password",owner_password);
                                intent.putExtra("owner_number",owner_number);
                                intent.putExtra("owner_email",owner_email);
                                intent.putExtra("owner_store_number",owner_store_number);
                                intent.putExtra("store_name",store_name);
                                intent.putExtra("owner_nin",owner_nin);
                                intent.putExtra("cd",cd);
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
                owner_info1_db loginRequest = new owner_info1_db(owner_password, owner_number, owner_email, owner_store_number, store_name, responseListener);
                RequestQueue queue = Volley.newRequestQueue(owner_info1.this);
                queue.add(loginRequest);
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String owner_password = owner_password1;
                int owner_number = owner_number1;
                String owner_email = owner_email1;
                int owner_store_number = Integer.parseInt(et4.getText().toString());
                String store_name = store_name1;

                AlertDialog.Builder builder = new AlertDialog.Builder(owner_info1.this);
                builder.setTitle("알림");
                builder.setMessage("번호(가게)가 변경되었습니다.");
                AlertDialog alert = builder.create();                                                       //빌더를 이용하여 AlertDialog객체를 생성합니다.
                alert.show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                String owner_name  = jsonObject.getString("owner_name");
                                String owner_address = jsonObject.getString("owner_address");
                                Double owner_lat = jsonObject.getDouble("owner_lat");
                                Double owner_long = jsonObject.getDouble("owner_long");
                                String owner_id = jsonObject.getString("owner_id");
                                String owner_password = jsonObject.getString("owner_password");
                                int owner_number = jsonObject.getInt("owner_number");
                                String owner_email = jsonObject.getString("owner_email");
                                int owner_store_number = jsonObject.getInt("owner_store_number");
                                String store_name = jsonObject.getString("store_name");
                                String owner_nin = jsonObject.getString("owner_nin");
                                String cd = jsonObject.getString("cd");

                                Intent intent = new Intent(owner_info1.this, owner_info1.class);

                                intent.putExtra("owner_name",owner_name);
                                intent.putExtra("owner_address",owner_address);
                                intent.putExtra("owner_lat",owner_lat);
                                intent.putExtra("owner_long",owner_long);
                                intent.putExtra("owner_id",owner_id);
                                intent.putExtra("owner_password",owner_password);
                                intent.putExtra("owner_number",owner_number);
                                intent.putExtra("owner_email",owner_email);
                                intent.putExtra("owner_store_number",owner_store_number);
                                intent.putExtra("store_name",store_name);
                                intent.putExtra("owner_nin",owner_nin);
                                intent.putExtra("cd",cd);
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
                owner_info1_db loginRequest = new owner_info1_db(owner_password, owner_number, owner_email, owner_store_number, store_name, responseListener);
                RequestQueue queue = Volley.newRequestQueue(owner_info1.this);
                queue.add(loginRequest);
            }
        });



        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String o_c_time = et5.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(owner_info1.this);
                builder.setTitle("알림");
                builder.setMessage("영업시간이 변경되었습니다.");
                AlertDialog alert = builder.create();                                                       //빌더를 이용하여 AlertDialog객체를 생성합니다.
                alert.show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                Intent intent = new Intent(owner_info1.this, owner_info1.class);

                                intent.putExtra("owner_name",owner_name1);
                                intent.putExtra("owner_address",owner_address1);
                                intent.putExtra("owner_lat",owner_lat1);
                                intent.putExtra("owner_long",owner_long1);
                                intent.putExtra("owner_id",owner_id1);
                                intent.putExtra("owner_password",owner_password1);
                                intent.putExtra("owner_number",owner_number1);
                                intent.putExtra("owner_email",owner_email1);
                                intent.putExtra("owner_store_number",owner_store_number1);
                                intent.putExtra("store_name",store_name1);
                                intent.putExtra("owner_nin",owner_nin1);
                                intent.putExtra("cd",cd1);
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
                owner_info1_db_a loginRequest = new owner_info1_db_a(store_name1,o_c_time,a, responseListener);
                RequestQueue queue = Volley.newRequestQueue(owner_info1.this);
                queue.add(loginRequest);
            }
        });


        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String closed_day = et6.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(owner_info1.this);
                builder.setTitle("알림");
                builder.setMessage("휴무일이 변경되었습니다.");
                AlertDialog alert = builder.create();                                                       //빌더를 이용하여 AlertDialog객체를 생성합니다.
                alert.show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                Intent intent = new Intent(owner_info1.this, owner_info1.class);

                                intent.putExtra("owner_name",owner_name1);
                                intent.putExtra("owner_address",owner_address1);
                                intent.putExtra("owner_lat",owner_lat1);
                                intent.putExtra("owner_long",owner_long1);
                                intent.putExtra("owner_id",owner_id1);
                                intent.putExtra("owner_password",owner_password1);
                                intent.putExtra("owner_number",owner_number1);
                                intent.putExtra("owner_email",owner_email1);
                                intent.putExtra("owner_store_number",owner_store_number1);
                                intent.putExtra("store_name",store_name1);
                                intent.putExtra("owner_nin",owner_nin1);
                                intent.putExtra("cd",cd1);
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
                owner_info1_db_a loginRequest = new owner_info1_db_a(store_name1,closed_day,b, responseListener);
                RequestQueue queue = Volley.newRequestQueue(owner_info1.this);
                queue.add(loginRequest);
            }
        });

        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String closed_day = et7.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(owner_info1.this);
                builder.setTitle("알림");
                builder.setMessage("변경되었습니다.");
                AlertDialog alert = builder.create();                                                       //빌더를 이용하여 AlertDialog객체를 생성합니다.
                alert.show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                Intent intent = new Intent(owner_info1.this, owner_info1.class);

                                intent.putExtra("owner_name",owner_name1);
                                intent.putExtra("owner_address",owner_address1);
                                intent.putExtra("owner_lat",owner_lat1);
                                intent.putExtra("owner_long",owner_long1);
                                intent.putExtra("owner_id",owner_id1);
                                intent.putExtra("owner_password",owner_password1);
                                intent.putExtra("owner_number",owner_number1);
                                intent.putExtra("owner_email",owner_email1);
                                intent.putExtra("owner_store_number",owner_store_number1);
                                intent.putExtra("store_name",store_name1);
                                intent.putExtra("owner_nin",owner_nin1);
                                intent.putExtra("cd",cd1);
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
                owner_info1_db_a loginRequest = new owner_info1_db_a(store_name1,closed_day,c, responseListener);
                RequestQueue queue = Volley.newRequestQueue(owner_info1.this);
                queue.add(loginRequest);
            }
        });




        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et1 = (EditText) findViewById(R.id.layout_et1);
                String owner_password = owner_password1;
                int owner_number = owner_number1;
                String owner_email = owner_email1;
                int owner_store_number = owner_store_number1;
                String store_name = store_name1;

                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/account/security1.txt");
                file.delete();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                String owner_name  = jsonObject.getString("owner_name");
                                String owner_address = jsonObject.getString("owner_address");
                                Double owner_lat = jsonObject.getDouble("owner_lat");
                                Double owner_long = jsonObject.getDouble("owner_long");
                                String owner_id = jsonObject.getString("owner_id");
                                String owner_password = jsonObject.getString("owner_password");
                                int owner_number = jsonObject.getInt("owner_number");
                                String owner_email = jsonObject.getString("owner_email");
                                int owner_store_number = jsonObject.getInt("owner_store_number");
                                String store_name = jsonObject.getString("store_name");
                                String owner_nin = jsonObject.getString("owner_nin");
                                String cd = jsonObject.getString("cd");

                                Intent intent = new Intent(owner_info1.this, owner_login.class);

                                intent.putExtra("owner_name",owner_name);
                                intent.putExtra("owner_address",owner_address);
                                intent.putExtra("owner_lat",owner_lat);
                                intent.putExtra("owner_long",owner_long);
                                intent.putExtra("owner_id",owner_id);
                                intent.putExtra("owner_password",owner_password);
                                intent.putExtra("owner_number",owner_number);
                                intent.putExtra("owner_email",owner_email);
                                intent.putExtra("owner_store_number",owner_store_number);
                                intent.putExtra("store_name",store_name);
                                intent.putExtra("owner_nin",owner_nin);
                                intent.putExtra("cd",cd);
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
                owner_info1_db loginRequest = new owner_info1_db(owner_password, owner_number, owner_email, owner_store_number, store_name, responseListener);
                RequestQueue queue = Volley.newRequestQueue(owner_info1.this);
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

package com.example.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class owner_signup2 extends Activity {

    Button user_signup_back, user_signup_go,b1,b2,b3,b4;

    EditText et1,et2,et3,et4,et5,et6,et7,et8,et9;

    String snum, owner_name, owner_store_name, owner_id, owner_password, owner_email, owner_nin, owner_address, cd;
    int owner_number, owner_store_number;
    Double owner_lat, owner_long;


    int ch_id=0;
    int ch_em=0;
    int num;
    String ah_id="",ah_store="",a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_signup2);

        user_signup_back = (Button) findViewById(R.id.layout2_b1);
        user_signup_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(owner_signup2.this, owner_signup1.class);
                startActivity(intent);
            }
        });


        et1 = findViewById(R.id.layout2_et1);
        et2 = findViewById(R.id.layout2_et2);
        et3 = findViewById(R.id.layout2_et3);
        et4 = findViewById(R.id.layout2_et4);
        et5 = findViewById(R.id.layout2_et5);
        et6 = findViewById(R.id.layout2_et6);
        et7 = findViewById(R.id.layout2_et7);
        et8 = findViewById(R.id.layout2_et8);
        et9 = findViewById(R.id.layout2_et9);

        b1 = (Button) findViewById(R.id.redundency_id);
        b2 = (Button) findViewById(R.id.redundency_store);
        b3 = (Button) findViewById(R.id.redundency_email);
        b4 = (Button) findViewById(R.id.num_check);

        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(15);
        et3.setFilters(FilterArray);

        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    owner_store_name = et2.getText().toString();

                    /*user_email = et4.getText().toString();*/

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"입력칸(상호명)을 채워주세요.",Toast.LENGTH_SHORT).show();
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                Toast.makeText(getApplicationContext(),"사용 가능한 상호명입니다.",Toast.LENGTH_SHORT).show();
                                ah_store=owner_store_name;
                                et2.setText(ah_store);
                                et2.setEnabled(false);
                            }
                            //실패한 경우
                            else{
                                Toast.makeText(getApplicationContext(),"중복된 상호명 또는 특수문자를 사용하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 Volley를 이용해서 요청을 함
                owner_signup2_store_db registerRequest = new owner_signup2_store_db(owner_store_name, responseListener);
                RequestQueue queue = Volley.newRequestQueue(owner_signup2.this);
                queue.add(registerRequest);
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    owner_id = et3.getText().toString();
                    owner_id=owner_id.replace(" ","");

                    /*user_email = et4.getText().toString();*/

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"입력칸(ID)을 채워주세요.",Toast.LENGTH_SHORT).show();
                }
                if (et3.getText().toString().length() <= 7 || et3.getText().toString().length() >= 17) {
                    Toast.makeText(getApplicationContext(), "8~16자를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                                if (success) { //회원등록에 성공한 경우
                                    Toast.makeText(getApplicationContext(), "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                                    ah_id = owner_id;
                                    et3.setText(ah_id);
                                    et3.setEnabled(false);
                                }
                                //실패한 경우
                                else {
                                    Toast.makeText(getApplicationContext(), "중복된 아이디 또는 특수문자를 사용하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };

                    //서버로 Volley를 이용해서 요청을 함
                    owner_signup2_id_db registerRequest = new owner_signup2_id_db(owner_id, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(owner_signup2.this);
                    queue.add(registerRequest);
                }
            }
        });


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                owner_email = et6.getText().toString();
                owner_email=owner_email.replace(" ","");

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
                owner_signup2_email_db registerRequest = new owner_signup2_email_db(owner_email,snum, responseListener);
                RequestQueue queue = Volley.newRequestQueue(owner_signup2.this);
                queue.add(registerRequest);
            }

        });

        b4 = (Button) findViewById(R.id.num_check);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getnum = et9.getText().toString();
                String realkey = String.valueOf(num);
                if (getnum.equals(realkey)){
                    ch_em=1;
                    et6.setEnabled(false);
                    Toast.makeText(getApplicationContext(),"인증되었습니다.",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"인증실패",Toast.LENGTH_SHORT).show();
                }
            }
        });





        user_signup_go = (Button) findViewById(R.id.layout2_b2);
        user_signup_go.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    owner_name = et1.getText().toString();
                    owner_store_name = et2.getText().toString();
                    owner_id = et3.getText().toString();
                    owner_password = et4.getText().toString();
                    owner_number = Integer.parseInt(et5.getText().toString());
                    owner_email = et6.getText().toString();
                    owner_store_number = Integer.parseInt(et7.getText().toString());
                    owner_nin = et8.getText().toString();
                    owner_lat = Double.parseDouble("0.0");
                    owner_long = Double.parseDouble("0.0");
                    owner_address = "null";
                    /*int cd = Integer.parseInt("0");*/
                    cd = "0";

                    owner_name = owner_name.replace(" ", "");
                    owner_password = owner_password.replace(" ", "");
                    owner_email = owner_email.replace(" ", "");

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "번호를 공백없이 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

                //이름, 가게상호명, 아이디, 비밀번호, 연락처, 이메일, 가게연락처, 사업자등록번호

                if (et1.getText().toString().length()==0) {
                    Toast.makeText(getApplicationContext(), "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else if(et4.getText().toString().length()<=7 || et4.getText().toString().length()>=17){
                    Toast.makeText(getApplicationContext(), "비밀번호(8~16자)를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else if(String.valueOf(owner_nin).length()!=10){
                    Toast.makeText(getApplicationContext(), "사업자 등록번호를 정확히 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else if ((!ah_id.equals(owner_id)) || (ch_em == 0) || (!ah_store.equals(owner_store_name))) {
                    Toast.makeText(getApplicationContext(), "ID, 상호명 중복확인 및 이메일 인증을 해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if (String.valueOf(owner_number).length()!=10){
                    Toast.makeText(getApplicationContext(), "정확한 번호를 입력하였는지 확인해주세요.", Toast.LENGTH_SHORT).show();
                }else {
                    String hash_pass = change_hash(owner_password);
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                                if (success) { //회원등록에 성공한 경우
                                    String owner_nin = et8.getText().toString();
                                    String cd = "0";
                                    String owner_store_name = et2.getText().toString();
                                    String owner_name = et1.getText().toString();
                                    String owner_id = et3.getText().toString();
                                    String owner_number = et5.getText().toString();
                                    /*Toast.makeText(getApplicationContext(),"다음으로",Toast.LENGTH_SHORT).show();*/

                                    Intent intent = new Intent(owner_signup2.this, owner_signup3.class);
                                    intent.putExtra("owner_nin", owner_nin);
                                    intent.putExtra("cd", cd);
                                    intent.putExtra("owner_store_name", owner_store_name);
                                    intent.putExtra("owner_name", owner_name);
                                    intent.putExtra("owner_id", owner_id);
                                    intent.putExtra("owner_number", owner_number);

                                    startActivity(intent);
                                }
                                //실패한 경우
                                else {
                                    Toast.makeText(getApplicationContext(), "빈칸의 조건 및 중복확인, 인증을 해주세요.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };

                    owner_register1_db registerRequest = new owner_register1_db(owner_id, hash_pass, owner_name, owner_number,
                            owner_nin, owner_store_name, owner_address, owner_store_number, cd, owner_email, owner_lat, owner_long, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(owner_signup2.this);
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
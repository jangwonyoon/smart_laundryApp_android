package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import android.text.InputFilter;
import android.text.Spanned;
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

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.regex.Pattern;

public class user_signup2 extends Activity {

    Button user_signup_back, user_signup_go,b1,b2,b3;

    EditText et1,et2,et3,et4,et5,et6;

    String snum, user_name, user_id, user_password, user_email, user_address, user_address_detail;
    Double user_lat, user_long;
    int user_number;
    int ch_id=0;
    int ch_em=0;
    int num;
    String ah_id="",a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup2);

        user_signup_back = (Button) findViewById(R.id.layout2_b1);
        user_signup_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(user_signup2.this, user_signup1.class);
                startActivity(intent);
            }
        });


        et1 = findViewById(R.id.layout2_et1);
        et2 = findViewById(R.id.layout2_et2);
        et3 = findViewById(R.id.layout2_et3);
        et4 = findViewById(R.id.layout2_et4);
        et5 = findViewById(R.id.layout2_et5);
        et6 = findViewById(R.id.layout2_et6);

        b1 = (Button) findViewById(R.id.redundency_id);
        b2 = (Button) findViewById(R.id.redundency_email);

        et2.setFilters(new InputFilter[] {filter});
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(15);
        et2.setFilters(FilterArray);

        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    user_id = et2.getText().toString();
                    user_id = user_id.replace(" ", "");

                    /*user_email = et4.getText().toString();*/

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "입력칸(ID)을 채워주세요.", Toast.LENGTH_SHORT).show();
                }
                if (et2.getText().toString().length() <= 7 || et2.getText().toString().length() >= 17) {
                    Toast.makeText(getApplicationContext(), "8~16자를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                                if (success) { //회원등록에 성공한 경우
                                    Toast.makeText(getApplicationContext(), "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                                    ah_id = user_id;
                                    et2.setText(ah_id);
                                    et2.setEnabled(false);
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
                    user_signup2_id_db registerRequest = new user_signup2_id_db(user_id, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(user_signup2.this);
                    queue.add(registerRequest);
                }
            }
        });




        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_email = et4.getText().toString();
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
                user_signup2_email_db registerRequest = new user_signup2_email_db(user_email,snum, responseListener);
                RequestQueue queue = Volley.newRequestQueue(user_signup2.this);
                queue.add(registerRequest);
            }

        });


        b3 = (Button) findViewById(R.id.num_check);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getnum = et6.getText().toString();
                String realkey = String.valueOf(num);
                /*et6.setText(realkey);*/
                if (getnum.equals(realkey)){
                    ch_em=1;
                    et4.setEnabled(false);
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
                    user_name = et1.getText().toString();
                    /*user_id = et2.getText().toString();*/
                    user_password = et3.getText().toString();
                    user_email = et4.getText().toString();
                    user_number = Integer.parseInt(et5.getText().toString());
                    user_lat = Double.parseDouble("0.0");
                    user_long = Double.parseDouble("0.0");
                    user_address = "null";
                    user_address_detail = "null";

                    user_name=user_name.replace(" ","");
                    user_password=user_password.replace(" ","");
                    user_email=user_email.replace(" ","");

                    a = String.valueOf("0"+user_number);
                    /*Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();*/

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "번호를 공백없이 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                if (et1.getText().toString().length()==0) {
                    Toast.makeText(getApplicationContext(), "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else if(et3.getText().toString().length()<=7 || et3.getText().toString().length()>=17){
                    Toast.makeText(getApplicationContext(), "비밀번호(8~16자)를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else if ((!ah_id.equals(user_id)) || (ch_em==0)) {
                    Toast.makeText(getApplicationContext(), "ID중복확인 및 이메일 인증을 해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if (String.valueOf(user_number).length()!=10){
                    Toast.makeText(getApplicationContext(), "번호를 정확히 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    user_password = change_hash(user_password);
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                                if (success) { //회원등록에 성공한 경우
                                    String user_id = et2.getText().toString();
                                    /*Toast.makeText(getApplicationContext(),"다음으로",Toast.LENGTH_SHORT).show();*/

                                    Intent intent = new Intent(user_signup2.this, user_signup3.class);
                                    intent.putExtra("user_id", user_id);

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
                    user_register1_db registerRequest = new user_register1_db(user_name, user_id, user_password, user_email, user_address, a, user_lat, user_long, user_address_detail, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(user_signup2.this);
                    queue.add(registerRequest);
                }
            }
        });

    }
    protected InputFilter filter= new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");
            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }

    };

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
            mdSHA256.update(user_password.getBytes("UTF-8"));
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
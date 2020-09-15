package com.example.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class user_forpay extends Activity {

    String user_name1, user_address1, user_id1, user_address_detail1;
    Double user_lat1, user_long1;
    String title1;

    TextView tv1;
    EditText et1;
    Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_forpay);

        Intent intent = getIntent();
        user_name1 = intent.getStringExtra("user_name");
        user_address1 = intent.getStringExtra("user_address");
        user_lat1 = intent.getDoubleExtra("user_lat",0.0);
        user_long1 = intent.getDoubleExtra("user_long",0.0);
        title1 = intent.getStringExtra("title");
        user_id1 = intent.getStringExtra("user_id");
        user_address_detail1 = intent.getStringExtra("user_address_detail");

        tv1 = (TextView) findViewById(R.id.title1);
        tv1.setText(title1+" [결제하기]");

        b1 = (Button) findViewById(R.id.layout2_b1);
        b2 = (Button) findViewById(R.id.pay);

        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(user_forpay.this, user_itembasket.class);
                intent.putExtra("user_name",user_name1);
                intent.putExtra("user_address",user_address1);
                intent.putExtra("user_lat",user_lat1);
                intent.putExtra("user_long",user_long1);
                intent.putExtra("title",title1);
                intent.putExtra("user_id",user_id1);
                intent.putExtra("user_address_detail",user_address_detail1);
                startActivity(intent);
            }
        });

        Date currentTime = Calendar.getInstance().getTime();

        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH", Locale.getDefault());
        SimpleDateFormat minFormat = new SimpleDateFormat("mm", Locale.getDefault());
        SimpleDateFormat secFormat = new SimpleDateFormat("ss", Locale.getDefault());


        final String month1 = monthFormat.format(currentTime);
        final String day1 = dayFormat.format(currentTime);
        final String hour1 = hourFormat.format(currentTime);
        final String min1 = minFormat.format(currentTime);
        final String sec1 = secFormat.format(currentTime);

        et1 = (EditText) findViewById(R.id.et1);

        b2.setOnClickListener(new View.OnClickListener() {

            int n_o_count = 0;
            String u_address = user_address1;
            int date = Integer.parseInt(month1+day1+hour1+min1+sec1);
            int delivery_check = 0;
            String item_list_laundry_list_s_name = title1;
            String user_u_id = user_id1;
            int yes_no = 0;
            @Override
            public void onClick(View v) {
                String memo1 = et1.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                Toast.makeText(getApplicationContext(),"주문완료",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(user_forpay.this, ImageActivity.class);
                                intent.putExtra("user_name",user_name1);
                                intent.putExtra("user_address",user_address1);
                                intent.putExtra("user_lat",user_lat1);
                                intent.putExtra("user_long",user_long1);
                                intent.putExtra("title",title1);
                                intent.putExtra("user_id",user_id1);
                                intent.putExtra("user_address_detail",user_address_detail1);

                                startActivity(intent);
                            }
                            //실패한 경우
                            else{
                                Toast.makeText(getApplicationContext(),"이미 주문 중 입니다.:).",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 Volley를 이용해서 요청을 함
                user_forpay_db registerRequest = new user_forpay_db(n_o_count, u_address,date,memo1, delivery_check,
                        item_list_laundry_list_s_name, user_u_id, yes_no, responseListener);
                RequestQueue queue = Volley.newRequestQueue(user_forpay.this);
                queue.add(registerRequest);
            }
        });
    }
}

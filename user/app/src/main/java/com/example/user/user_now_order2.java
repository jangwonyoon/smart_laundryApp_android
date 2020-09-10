package com.example.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dinuscxj.progressbar.CircleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class user_now_order2 extends Activity {

    String user_name1, user_address1, user_id1, user_address_detail1,s_name1;
    Double user_lat1, user_long1;
    int date1;

    String user_address;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
    Button back,b1,b2;

    String owner_name1, owner_address1, store_name1;
    Double owner_lat1, owner_long1;

    String memo, items;
    int date,t_price,yes_no;

    private static String TAG = "phptest";


    private static final String DEFAULT_PATTERN = "%d%%";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_now_order2);

        Intent intent = getIntent();
        user_name1 = intent.getStringExtra("user_name");
        user_address1 = intent.getStringExtra("user_address");
        user_lat1 = intent.getDoubleExtra("user_lat",0.0);
        user_long1 = intent.getDoubleExtra("user_long",0.0);
        user_id1 = intent.getStringExtra("user_id");
        user_address_detail1 = intent.getStringExtra("user_address_detail");
        s_name1 = intent.getStringExtra("store_name");
        date1 = intent.getIntExtra("date",0);


        back = (Button) findViewById(R.id.layout2_b1);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(user_now_order2.this, user_now_order.class);
                intent.putExtra("user_name",user_name1);
                intent.putExtra("user_address",user_address1);
                intent.putExtra("user_lat",user_lat1);
                intent.putExtra("user_long",user_long1);
                intent.putExtra("user_id",user_id1);
                intent.putExtra("user_address_detail",user_address_detail1);

                startActivity(intent);
            }
        });

        tv1 = (TextView)findViewById(R.id.s_name);
        tv1.setText(s_name1);

        tv2 = (TextView)findViewById(R.id.date);
        tv2.setText("2020"+date1);


        tv3 = (TextView) findViewById(R.id.address);
        tv3.setText(user_address1);

        tv4 = (TextView)findViewById(R.id.memo);
        tv5 = (TextView) findViewById(R.id.t_price);

        tv7 = (TextView)findViewById(R.id.item_list);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                    if(success){ //회원등록에 성공한 경우
                        memo = jsonObject.getString("memo");
                        if (memo.length()==0){
                            memo = "공란";
                        }
                        t_price = jsonObject.getInt("t_price");
                        items = jsonObject.getString("items");
                        tv7.setText(items);
                        tv4.setText(memo);
                        tv5.setText(t_price+"원");


                        int yes_no = jsonObject.getInt("yes_no");
                        TextView tv10,tv11,tv12,tv13,tv14,tv15;
                        tv10 = findViewById(R.id.step1);
                        tv11 = findViewById(R.id.s_step1);
                        tv12 = findViewById(R.id.step2);
                        tv13 = findViewById(R.id.s_step2);
                        tv14 = findViewById(R.id.step3);
                        tv15 = findViewById(R.id.s_step3);


                        if(yes_no==0){
                            tv10.setTextColor(Color.parseColor("#111111"));
                            tv11.setTextColor(Color.parseColor("#111111"));
                        }
                        else if(yes_no==1){
                            tv12.setTextColor(Color.parseColor("#111111"));
                            tv13.setTextColor(Color.parseColor("#111111"));
                        }
                        else if(yes_no==2){
                            tv14.setTextColor(Color.parseColor("#111111"));
                            tv15.setTextColor(Color.parseColor("#111111"));
                        }

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
        user_now_order2_db registerRequest = new user_now_order2_db(s_name1,user_id1,date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(user_now_order2.this);
        queue.add(registerRequest);

    }

}

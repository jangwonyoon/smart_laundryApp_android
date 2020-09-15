package com.example.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class user_order_record2 extends Activity {

    Button back,b1,b2;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;

    String memo, items, u_address;

    String user_name1, user_address1, user_id1, user_address_detail1, store_name1;
    Double user_lat1, user_long1;

    int date1, t_price, yes_no, s_number, date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_record2);

        Intent intent = getIntent();
        user_name1 = intent.getStringExtra("user_name");
        user_address1 = intent.getStringExtra("user_address");
        user_lat1 = intent.getDoubleExtra("user_lat",0.0);
        user_long1 = intent.getDoubleExtra("user_long",0.0);
        user_id1 = intent.getStringExtra("user_id");
        user_address_detail1 = intent.getStringExtra("user_address_detail");
        store_name1 = intent.getStringExtra("store_name");
        date1 = intent.getIntExtra("date",0);

        back = (Button) findViewById(R.id.layout2_b1);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(user_order_record2.this, user_order_record.class);
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
        tv1.setText(store_name1);

        tv2 = (TextView)findViewById(R.id.date);


        tv3 = (TextView) findViewById(R.id.address);

        tv4 = (TextView)findViewById(R.id.memo);
        tv5 = (TextView) findViewById(R.id.t_price);

        tv6 = (TextView) findViewById(R.id.item_list);

        tv7 = (TextView)findViewById(R.id.yes_no);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                    if(success){ //회원등록에 성공한 경우
                        memo = jsonObject.getString("memo");
                        t_price = jsonObject.getInt("t_price");
                        items = jsonObject.getString("items");
                        yes_no = jsonObject.getInt("yes_no");
                        s_number = jsonObject.getInt("s_number");
                        date = jsonObject.getInt("date");
                        u_address = jsonObject.getString("u_address");
                        if (memo.length()==0){
                            memo = "공란";
                        }
                        tv2.setText("2020"+date);
                        tv3.setText(u_address);
                        tv4.setText(memo);
                        tv5.setText(t_price+"원");
                        tv6.setText(items);

                        if(yes_no==0){
                            tv7.setText("배달대행");
                        }
                        else{
                            tv7.setText("가게에서 직접배달");
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
        user_order_record2_db registerRequest = new user_order_record2_db(store_name1,user_id1, date1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(user_order_record2.this);
        queue.add(registerRequest);

        b1 = (Button) findViewById(R.id.call);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:"+"0"+s_number));
                startActivity(intent);
            }
        });

        b2 = (Button) findViewById(R.id.remove);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(user_order_record2.this);
                builder.setTitle("알림");
                builder.setMessage("이 주문기록을 삭제합니다.");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                                    if(success){ //회원등록에 성공한 경우
                                        Intent intent = new Intent(user_order_record2.this, user_order_record.class);
                                        intent.putExtra("user_name",user_name1);
                                        intent.putExtra("user_address",user_address1);
                                        intent.putExtra("user_lat",user_lat1);
                                        intent.putExtra("user_long",user_long1);
                                        intent.putExtra("user_id",user_id1);
                                        intent.putExtra("user_address_detail",user_address_detail1);


                                        startActivity(intent);
                                    }
                                    //실패한 경우
                                    else{
                                        Toast.makeText(getApplicationContext(),"주문기록 삭제 실패",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        };

                        //서버로 Volley를 이용해서 요청을 함
                        user_order_record2_db2 registerRequest = new user_order_record2_db2(store_name1, user_id1, t_price ,date, memo, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(user_order_record2.this);
                        queue.add(registerRequest);
                    }
                });

                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) { }
                });
                AlertDialog alert = builder.create();                                                       //빌더를 이용하여 AlertDialog객체를 생성합니다.
                alert.show();
            }
        });
    }
}

package com.example.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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

public class owner_order_record2 extends Activity {
    Button back;
    String owner_name1, owner_address1,store_name1, user_id1, date1;
    Double owner_lat1, owner_long1;
    String u_address, memo, items;
    int t_price, yes_no;

    TextView tv1,tv2,tv3,tv4,tv5, tv6,tv7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_order_record2);

        Intent intent = getIntent();
        owner_name1 = intent.getStringExtra("owner_name");
        owner_address1 = intent.getStringExtra("owner_address");
        owner_lat1 = intent.getDoubleExtra("owner_lat",0.0);
        owner_long1 = intent.getDoubleExtra("owner_long",0.0);
        store_name1 = intent.getStringExtra("store_name");
        date1 = intent.getStringExtra("date");
        user_id1 = intent.getStringExtra("user_id");

        int date = Integer.parseInt(date1);

        tv1 = (TextView) findViewById(R.id.u_id);
        tv1.setText(user_id1+"님의 주문기록");

        tv2 = (TextView) findViewById(R.id.date);
        tv2.setText("2020"+date1);

        tv3 = (TextView) findViewById(R.id.address);
        tv4 = (TextView) findViewById(R.id.item_list);
        tv5 = (TextView) findViewById(R.id.memo);
        tv6 = (TextView) findViewById(R.id.t_price);
        tv7 = (TextView) findViewById(R.id.yes_no);

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
                        u_address = jsonObject.getString("u_address");
                        if (memo.length()==0){
                            memo = "공란";
                        }
                        tv3.setText(u_address);
                        tv5.setText(memo);
                        tv6.setText(t_price+"원");
                        tv4.setText(items);

                        if(yes_no==0){
                            tv7.setText("배달대행");
                        }
                        else{
                            tv7.setText("가게에서 직접배달");
                        }

                    }
                    //실패한 경우
                    else{
                        Toast.makeText(getApplicationContext(),"불러오기 실패",Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        //서버로 Volley를 이용해서 요청을 함
        owner_order_record2_db registerRequest = new owner_order_record2_db(store_name1,user_id1, date, responseListener);
        RequestQueue queue = Volley.newRequestQueue(owner_order_record2.this);
        queue.add(registerRequest);

        back = (Button) findViewById(R.id.layout2_b1);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(owner_order_record2.this, owner_order_record.class);
                intent.putExtra("owner_name",owner_name1);
                intent.putExtra("owner_address",owner_address1);
                intent.putExtra("owner_lat",owner_lat1);
                intent.putExtra("owner_long",owner_long1);
                intent.putExtra("store_name",store_name1);

                startActivity(intent);
            }
        });
    }
}

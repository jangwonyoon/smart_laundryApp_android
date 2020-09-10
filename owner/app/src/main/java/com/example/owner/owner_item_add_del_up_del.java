package com.example.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class owner_item_add_del_up_del extends Activity {
    Button b1,b2;
    String owner_name1, owner_address1, store_name1, before_menu1;
    Double owner_lat1, owner_long1;
    EditText et1, et2;

    String item_name, laundry_list_s_name, before_menu;
    int item_price, before_price, before_price1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_item_add_del_up_del);

        Intent intent = getIntent();
        before_menu1 = intent.getStringExtra("menu");
        before_price1 = intent.getIntExtra("price",0);

        owner_name1 = intent.getStringExtra("owner_name");
        owner_address1 = intent.getStringExtra("owner_address");
        owner_lat1 = intent.getDoubleExtra("owner_lat",0.0);
        owner_long1 = intent.getDoubleExtra("owner_long",0.0);
        store_name1 = intent.getStringExtra("store_name");

        et1 = (EditText) findViewById(R.id.layout_et1);
        et2 = (EditText) findViewById(R.id.layout_et2);

        et1.setText(before_menu1);
        et2.setText(String.valueOf(before_price1));

        b1 = (Button) findViewById(R.id.layout_b4);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    item_name = et1.getText().toString();
                    item_price = Integer.parseInt(et2.getText().toString());
                    laundry_list_s_name = store_name1;
                    before_menu = before_menu1;
                    before_price = before_price1;

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"빈칸/정확한 값을 입력하세요.",Toast.LENGTH_SHORT).show();
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                /*String user_id = et2.getText().toString();*/
                                /*Toast.makeText(getApplicationContext(),"다음으로",Toast.LENGTH_SHORT).show();*/

                                Intent intent = new Intent(owner_item_add_del_up_del.this, owner_item_add_del.class);
                                intent.putExtra("owner_name",owner_name1);
                                intent.putExtra("owner_address",owner_address1);
                                intent.putExtra("owner_lat",owner_lat1);
                                intent.putExtra("owner_long",owner_long1);
                                intent.putExtra("store_name",store_name1);

                                startActivity(intent);
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
                owner_item_update_db registerRequest = new owner_item_update_db(item_name, item_price,laundry_list_s_name,before_menu, before_price, responseListener);
                RequestQueue queue = Volley.newRequestQueue(owner_item_add_del_up_del.this);
                queue.add(registerRequest);
            }
        });

        b2 = (Button) findViewById(R.id.layout_b3);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    item_name = et1.getText().toString();
                    item_price = Integer.parseInt(et2.getText().toString());
                    laundry_list_s_name = store_name1;

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"빈칸/정확한 값을 입력하세요.",Toast.LENGTH_SHORT).show();
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                /*String user_id = et2.getText().toString();*/
                                /*Toast.makeText(getApplicationContext(),"다음으로",Toast.LENGTH_SHORT).show();*/

                                Intent intent = new Intent(owner_item_add_del_up_del.this, owner_item_add_del.class);
                                intent.putExtra("owner_name",owner_name1);
                                intent.putExtra("owner_address",owner_address1);
                                intent.putExtra("owner_lat",owner_lat1);
                                intent.putExtra("owner_long",owner_long1);
                                intent.putExtra("store_name",store_name1);

                                startActivity(intent);
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
                owner_item_delete_db registerRequest = new owner_item_delete_db(item_name, item_price,laundry_list_s_name, responseListener);
                RequestQueue queue = Volley.newRequestQueue(owner_item_add_del_up_del.this);
                queue.add(registerRequest);
            }
        });
    }
}

package com.example.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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

public class owner_review_management_reply extends Activity {
    private Button b1,b2;
    private EditText et1;
    private TextView tv1;
    String date1;
    int date;

    String owner_name1, owner_address1,store_name1, now_user_id1;
    Double owner_lat1, owner_long1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_review_management_reply);

        Intent intent = getIntent();
        owner_name1 = intent.getStringExtra("owner_name");
        owner_address1 = intent.getStringExtra("owner_address");
        owner_lat1 = intent.getDoubleExtra("owner_lat",0.0);
        owner_long1 = intent.getDoubleExtra("owner_long",0.0);
        store_name1 = intent.getStringExtra("store_name");
        now_user_id1 = intent.getStringExtra("now_user_id");
        date1 = intent.getStringExtra("date");

        date = Integer.parseInt(date1);
        tv1 = (TextView) findViewById(R.id.now_user_id);
        tv1.setText(now_user_id1);

        et1 = (EditText) findViewById(R.id.reply);
        b1 = (Button) findViewById(R.id.savebutton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et1.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                Intent intent = new Intent(owner_review_management_reply.this, owner_review_management.class);
                                intent.putExtra("owner_name",owner_name1);
                                intent.putExtra("owner_address",owner_address1);
                                intent.putExtra("owner_lat",owner_lat1);
                                intent.putExtra("owner_long",owner_long1);
                                intent.putExtra("store_name",store_name1);

                                startActivity(intent);
                            }
                            //실패한 경우
                            else{
                                Toast.makeText(getApplicationContext(),"실행 ERROR",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 Volley를 이용해서 요청을 함
                owner_review_management_reply_db registerRequest = new owner_review_management_reply_db(store_name1,
                        now_user_id1, date, content, responseListener);
                RequestQueue queue = Volley.newRequestQueue(owner_review_management_reply.this);
                queue.add(registerRequest);
            }
        });

        b2 = (Button) findViewById(R.id.layout2_b1);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(owner_review_management_reply.this, owner_review_management.class);
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

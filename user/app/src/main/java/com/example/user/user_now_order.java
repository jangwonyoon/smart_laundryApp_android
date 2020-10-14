package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class user_now_order extends AppCompatActivity{
    private DrawerLayout mDrawerLayout;
    private ListView listView;
    ArrayList<user_now_order_list> user_now_order_list;
    user_now_order_Adpter myadapter;
    user_now_order_list list1,list2,list3;
    private long backBtnTime = 0;

    TextView get_text;

    Button b1,b2,b3,b4,b5,b6,b7,b8, menubar;

    String user_name1, user_address1, user_id1, user_address_detail1;
    Double user_lat1, user_long1;

    private static String TAG = "phptest";

    private EditText mEditTextName;
    private EditText mEditTextCountry;
    private TextView mTextViewResult;
    private ArrayList<user_now_order_list> mArrayList;
    private user_now_order_Adpter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText mEditTextSearchKeyword;
    private String mJsonString;

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
        setContentView(R.layout.activity_user_now_order);


        Intent intent = getIntent();
        user_name1 = intent.getStringExtra("user_name");
        user_address1 = intent.getStringExtra("user_address");
        user_lat1 = intent.getDoubleExtra("user_lat",0.0);
        user_long1 = intent.getDoubleExtra("user_long",0.0);
        user_id1 = intent.getStringExtra("user_id");
        user_address_detail1 = intent.getStringExtra("user_address_detail");

        //액션바 설정하기//
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle("[주문현황]  "+user_name1+"님 안녕하세요.");
        //액션바 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        //=======================================리스트시작===========================================

        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        mArrayList = new ArrayList<>();

        mAdapter = new user_now_order_Adpter(this, mArrayList, user_name1,user_address1,user_lat1,user_long1,user_id1,user_address_detail1);
        mRecyclerView.setAdapter(mAdapter);

        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        /*mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));*/
        RecyclerDecoration spaceDecoration = new RecyclerDecoration(20);
        mRecyclerView.addItemDecoration(spaceDecoration);

        user_now_order.GetData task = new user_now_order.GetData();
        task.execute("http://edit0.dothome.co.kr/user_now_order_db.php",user_id1);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                //mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                if(id == R.id.b1){
                    Intent intent = new Intent(getApplicationContext(), user_main1.class);
                    intent.putExtra("user_name",user_name1);
                    intent.putExtra("user_address",user_address1);
                    intent.putExtra("user_lat",user_lat1);
                    intent.putExtra("user_long",user_long1);
                    intent.putExtra("user_id",user_id1);
                    intent.putExtra("user_address_detail",user_address_detail1);
                    startActivity(intent);
                }
                else if(id == R.id.b2){
                    Intent intent1 = new Intent(getApplicationContext(), user_gongji.class);
                    intent1.putExtra("user_name",user_name1);
                    intent1.putExtra("user_address",user_address1);
                    intent1.putExtra("user_lat",user_lat1);
                    intent1.putExtra("user_long",user_long1);
                    intent1.putExtra("user_id",user_id1);
                    intent1.putExtra("user_address_detail",user_address_detail1);
                    startActivity(intent1);
                }
                else if(id == R.id.b3){
                    Intent intent2 = new Intent(getApplicationContext(), user_changelocation.class);
                    intent2.putExtra("user_name",user_name1);
                    intent2.putExtra("user_address",user_address1);
                    intent2.putExtra("user_lat",user_lat1);
                    intent2.putExtra("user_long",user_long1);
                    intent2.putExtra("user_id",user_id1);
                    intent2.putExtra("user_address_detail",user_address_detail1);
                    startActivity(intent2);
                }
                else if(id == R.id.b4){
                    Intent intent3 = new Intent(getApplicationContext(), user_info.class);
                    intent3.putExtra("user_name",user_name1);
                    intent3.putExtra("user_address",user_address1);
                    intent3.putExtra("user_lat",user_lat1);
                    intent3.putExtra("user_long",user_long1);
                    intent3.putExtra("user_id",user_id1);
                    intent3.putExtra("user_address_detail",user_address_detail1);
                    startActivity(intent3);
                }
                else if(id == R.id.b5){
                    Intent intent4 = new Intent(getApplicationContext(), user_review.class);
                    intent4.putExtra("user_name",user_name1);
                    intent4.putExtra("user_address",user_address1);
                    intent4.putExtra("user_lat",user_lat1);
                    intent4.putExtra("user_long",user_long1);
                    intent4.putExtra("user_id",user_id1);
                    intent4.putExtra("user_address_detail",user_address_detail1);
                    startActivity(intent4);
                }
                else if(id == R.id.b6){
                    Intent intent5 = new Intent(getApplicationContext(), user_now_order.class);
                    intent5.putExtra("user_name",user_name1);
                    intent5.putExtra("user_address",user_address1);
                    intent5.putExtra("user_lat",user_lat1);
                    intent5.putExtra("user_long",user_long1);
                    intent5.putExtra("user_id",user_id1);
                    intent5.putExtra("user_address_detail",user_address_detail1);
                    startActivity(intent5);
                }
                else if(id == R.id.b7){
                    Intent intent6 = new Intent(getApplicationContext(), user_order_record.class);
                    intent6.putExtra("user_name",user_name1);
                    intent6.putExtra("user_address",user_address1);
                    intent6.putExtra("user_lat",user_lat1);
                    intent6.putExtra("user_long",user_long1);
                    intent6.putExtra("user_id",user_id1);
                    intent6.putExtra("user_address_detail",user_address_detail1);
                    startActivity(intent6);
                }
                else if(id == R.id.b8){
                    Intent intent7 = new Intent(getApplicationContext(), user_logout.class);
                    startActivity(intent7);
                }

                return true;
            }
        });
    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(user_now_order.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            /*mTextViewResult.setText(result);*/
            Log.d(TAG, "response - " + result);

            if (result == null){

                mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            /*String serverURL = params[0];
            String postParameters = params[1];*/
            /*String user_lat = (String)params[1];
            String user_long = (String)params[2];*/

            String serverURL = params[0];
            String postParameters = "user_id=" + params[1];

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){

        String TAG_JSON="result";
        String TAG_s_name = "s_name";
        String TAG_date = "date";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String s_name1 = item.getString(TAG_s_name);
                String date1 = item.getString(TAG_date);

                user_now_order_list personalData = new user_now_order_list();

                personalData.setMember_s_name(s_name1);
                personalData.setMember_date(date1);

                mArrayList.add(personalData);
                mAdapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }




    /*@Override
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
                Intent intent = new Intent(this, user_main1.class);
                intent.putExtra("user_name",user_name1);
                intent.putExtra("user_address",user_address1);
                intent.putExtra("user_lat",user_lat1);
                intent.putExtra("user_long",user_long1);
                intent.putExtra("user_id",user_id1);
                intent.putExtra("user_address_detail",user_address_detail1);
                startActivity(intent);
                break;
            case R.id.b2:
                Intent intent1 = new Intent(this, user_gongji.class);
                intent1.putExtra("user_name",user_name1);
                intent1.putExtra("user_address",user_address1);
                intent1.putExtra("user_lat",user_lat1);
                intent1.putExtra("user_long",user_long1);
                intent1.putExtra("user_id",user_id1);
                intent1.putExtra("user_address_detail",user_address_detail1);
                startActivity(intent1);
                break;
            case R.id.b3:
                Intent intent2 = new Intent(this, user_changelocation.class);
                intent2.putExtra("user_name",user_name1);
                intent2.putExtra("user_address",user_address1);
                intent2.putExtra("user_lat",user_lat1);
                intent2.putExtra("user_long",user_long1);
                intent2.putExtra("user_id",user_id1);
                intent2.putExtra("user_address_detail",user_address_detail1);
                startActivity(intent2);
                break;
            case R.id.b4:
                Intent intent3 = new Intent(this, user_info.class);
                intent3.putExtra("user_name",user_name1);
                intent3.putExtra("user_address",user_address1);
                intent3.putExtra("user_lat",user_lat1);
                intent3.putExtra("user_long",user_long1);
                intent3.putExtra("user_id",user_id1);
                intent3.putExtra("user_address_detail",user_address_detail1);
                startActivity(intent3);
                break;
            case R.id.b5:
                Intent intent4 = new Intent(this, user_review.class);
                intent4.putExtra("user_name",user_name1);
                intent4.putExtra("user_address",user_address1);
                intent4.putExtra("user_lat",user_lat1);
                intent4.putExtra("user_long",user_long1);
                intent4.putExtra("user_id",user_id1);
                intent4.putExtra("user_address_detail",user_address_detail1);
                startActivity(intent4);
                break;
            case R.id.b6:
                Intent intent5 = new Intent(this, user_now_order.class);
                intent5.putExtra("user_name",user_name1);
                intent5.putExtra("user_address",user_address1);
                intent5.putExtra("user_lat",user_lat1);
                intent5.putExtra("user_long",user_long1);
                intent5.putExtra("user_id",user_id1);
                intent5.putExtra("user_address_detail",user_address_detail1);
                startActivity(intent5);
                break;
            case R.id.b7:
                Intent intent6 = new Intent(this, user_order_record.class);
                intent6.putExtra("user_name",user_name1);
                intent6.putExtra("user_address",user_address1);
                intent6.putExtra("user_lat",user_lat1);
                intent6.putExtra("user_long",user_long1);
                intent6.putExtra("user_id",user_id1);
                intent6.putExtra("user_address_detail",user_address_detail1);
                startActivity(intent6);
                break;
            case R.id.b8:
                Intent intent7 = new Intent(this, user_logout.class);
                startActivity(intent7);
                break;

        }

        return super.onOptionsItemSelected(item);
    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}

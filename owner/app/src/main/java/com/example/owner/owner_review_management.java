package com.example.owner;

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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class owner_review_management extends AppCompatActivity{
    private DrawerLayout mDrawerLayout;
    String owner_name1, owner_address1,store_name1, total_num;
    Double owner_lat1, owner_long1;
    double avg;

    private long backBtnTime = 0;
    Button b1,b2,b3,b4,b5,b6,b7,b8;
    TextView tv1,tv2,tv3,tv4,tv5;
    RatingBar rb1;

    private static String TAG = "phptest";

    private EditText mEditTextName;
    private EditText mEditTextCountry;
    private TextView mTextViewResult;
    private ArrayList<owner_review_management_list> mArrayList;
    private owner_review_management_Adpter mAdapter;
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
        setContentView(R.layout.activity_owner_review_management);

        Intent intent = getIntent();
        owner_name1 = intent.getStringExtra("owner_name");
        owner_address1 = intent.getStringExtra("owner_address");
        owner_lat1 = intent.getDoubleExtra("owner_lat",0.0);
        owner_long1 = intent.getDoubleExtra("owner_long",0.0);
        store_name1 = intent.getStringExtra("store_name");

        tv1 = (TextView) findViewById(R.id.avgrating);
        tv2 = (TextView) findViewById(R.id.total_num);
        rb1 = (RatingBar) findViewById(R.id.rating);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                    if(success){ //회원등록에 성공한 경우
                        total_num = jsonObject.getString("total_num");
                        avg = jsonObject.getDouble("avg");
                        tv2.setText(total_num);
                        tv1.setText(String.format("%.1f", avg));
                        rb1.setRating((int)avg);
                    }
                    //실패한 경우
                    else{
                        Toast.makeText(getApplicationContext(),"SUM ERROR",Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        //서버로 Volley를 이용해서 요청을 함
        owner_review_management_db_sum registerRequest = new owner_review_management_db_sum(store_name1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(owner_review_management.this);
        queue.add(registerRequest);


        //액션바 설정하기//
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle("[리뷰관리] "+owner_name1+"사장님");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF4472C4));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu3);

        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        mArrayList = new ArrayList<>();

        mAdapter = new owner_review_management_Adpter(this, mArrayList, store_name1, owner_name1,owner_address1,owner_lat1,owner_long1);
        mRecyclerView.setAdapter(mAdapter);

        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        /*mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));*/
        RecyclerDecoration spaceDecoration = new RecyclerDecoration(10);
        mRecyclerView.addItemDecoration(spaceDecoration);


        owner_review_management.GetData task = new owner_review_management.GetData();
        task.execute("http://edit0.dothome.co.kr/owner_review_management_db.php",store_name1);



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
                    Intent intent = new Intent(getApplicationContext(), owner_main.class);
                    intent.putExtra("owner_name",owner_name1);
                    intent.putExtra("owner_address",owner_address1);
                    intent.putExtra("owner_lat",owner_lat1);
                    intent.putExtra("owner_long",owner_long1);
                    intent.putExtra("store_name",store_name1);
                    startActivity(intent);
                }
                else if(id == R.id.b2){
                    Intent intent1 = new Intent(getApplicationContext(), owner_order_y_n.class);
                    intent1.putExtra("owner_name",owner_name1);
                    intent1.putExtra("owner_address",owner_address1);
                    intent1.putExtra("owner_lat",owner_lat1);
                    intent1.putExtra("owner_long",owner_long1);
                    intent1.putExtra("store_name",store_name1);
                    startActivity(intent1);
                    startActivity(intent1);
                }
                else if(id == R.id.b3){
                    Intent intent2 = new Intent(getApplicationContext(), owner_item_add_del.class);
                    intent2.putExtra("owner_name",owner_name1);
                    intent2.putExtra("owner_address",owner_address1);
                    intent2.putExtra("owner_lat",owner_lat1);
                    intent2.putExtra("owner_long",owner_long1);
                    intent2.putExtra("store_name",store_name1);
                    startActivity(intent2);
                }
                else if(id == R.id.b4){
                    Intent intent3 = new Intent(getApplicationContext(), owner_gongji_management.class);
                    intent3.putExtra("owner_name",owner_name1);
                    intent3.putExtra("owner_address",owner_address1);
                    intent3.putExtra("owner_lat",owner_lat1);
                    intent3.putExtra("owner_long",owner_long1);
                    intent3.putExtra("store_name",store_name1);
                    startActivity(intent3);
                }
                else if(id == R.id.b5){
                    Intent intent4 = new Intent(getApplicationContext(), owner_info.class);
                    intent4.putExtra("owner_name",owner_name1);
                    intent4.putExtra("owner_address",owner_address1);
                    intent4.putExtra("owner_lat",owner_lat1);
                    intent4.putExtra("owner_long",owner_long1);
                    intent4.putExtra("store_name",store_name1);
                    startActivity(intent4);
                }
                else if(id == R.id.b6){
                    Intent intent5 = new Intent(getApplicationContext(), owner_review_management.class);
                    intent5.putExtra("owner_name",owner_name1);
                    intent5.putExtra("owner_address",owner_address1);
                    intent5.putExtra("owner_lat",owner_lat1);
                    intent5.putExtra("owner_long",owner_long1);
                    intent5.putExtra("store_name",store_name1);
                    startActivity(intent5);
                }
                else if(id == R.id.b7){
                    Intent intent6 = new Intent(getApplicationContext(), owner_logout.class);
                    startActivity(intent6);
                }
                else if(id == R.id.b8){
                    Intent intent7 = new Intent(getApplicationContext(), owner_order_record.class);
                    intent7.putExtra("owner_name",owner_name1);
                    intent7.putExtra("owner_address",owner_address1);
                    intent7.putExtra("owner_lat",owner_lat1);
                    intent7.putExtra("owner_long",owner_long1);
                    intent7.putExtra("store_name",store_name1);
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

            progressDialog = ProgressDialog.show(owner_review_management.this,
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
            String postParameters = "store_name=" + params[1];

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
        String TAG_u_id = "u_id";
        String TAG_rating = "rating";
        String TAG_date = "date";
        String TAG_content = "content";
        String TAG_items = "items";
        String TAG_o_comment = "o_comment";
        String TAG_image_one = "image_one";
        /*String TAG_image_two = "image_two";
        String TAG_image_three = "image_three";*/


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String u_id1 = item.getString(TAG_u_id);
                String rating1 = item.getString(TAG_rating);
                String date1 = item.getString(TAG_date);
                String content1 = item.getString(TAG_content);
                String items1 = item.getString(TAG_items);
                String o_comment1 = item.getString(TAG_o_comment);
                String image_one1 = item.getString(TAG_image_one);
                /*String image_two1 = item.getString(TAG_image_two);
                String image_three1 = item.getString(TAG_image_three);*/

                owner_review_management_list personalData = new owner_review_management_list();

                personalData.setMember_u_id(u_id1);
                personalData.setMember_rating(rating1);
                personalData.setMember_date(date1);
                personalData.setMember_content(content1);
                personalData.setMember_items(items1);
                personalData.setMember_o_comment(o_comment1);
                personalData.setMember_image1(image_one1);
                /*personalData.setMember_image2(image_two1);
                personalData.setMember_image3(image_three1);*/


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
        getMenuInflater().inflate(R.menu.menu2, menu);
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
                Intent intent = new Intent(this, owner_main.class);
                intent.putExtra("owner_name",owner_name1);
                intent.putExtra("owner_address",owner_address1);
                intent.putExtra("owner_lat",owner_lat1);
                intent.putExtra("owner_long",owner_long1);
                intent.putExtra("store_name",store_name1);
                startActivity(intent);
                break;
            case R.id.b2:
                Intent intent1 = new Intent(this, owner_order_y_n.class);
                intent1.putExtra("owner_name",owner_name1);
                intent1.putExtra("owner_address",owner_address1);
                intent1.putExtra("owner_lat",owner_lat1);
                intent1.putExtra("owner_long",owner_long1);
                intent1.putExtra("store_name",store_name1);
                startActivity(intent1);
                break;
            case R.id.b3:
                Intent intent2 = new Intent(this, owner_item_add_del.class);
                intent2.putExtra("owner_name",owner_name1);
                intent2.putExtra("owner_address",owner_address1);
                intent2.putExtra("owner_lat",owner_lat1);
                intent2.putExtra("owner_long",owner_long1);
                intent2.putExtra("store_name",store_name1);
                startActivity(intent2);
                break;
            case R.id.b4:
                Intent intent3 = new Intent(this, owner_gongji_management.class);
                intent3.putExtra("owner_name",owner_name1);
                intent3.putExtra("owner_address",owner_address1);
                intent3.putExtra("owner_lat",owner_lat1);
                intent3.putExtra("owner_long",owner_long1);
                intent3.putExtra("store_name",store_name1);
                startActivity(intent3);
                break;
            case R.id.b5:
                Intent intent4 = new Intent(this, owner_info.class);
                intent4.putExtra("owner_name",owner_name1);
                intent4.putExtra("owner_address",owner_address1);
                intent4.putExtra("owner_lat",owner_lat1);
                intent4.putExtra("owner_long",owner_long1);
                intent4.putExtra("store_name",store_name1);
                startActivity(intent4);
                break;
            case R.id.b6:
                Intent intent5 = new Intent(this, owner_review_management.class);
                intent5.putExtra("owner_name",owner_name1);
                intent5.putExtra("owner_address",owner_address1);
                intent5.putExtra("owner_lat",owner_lat1);
                intent5.putExtra("owner_long",owner_long1);
                intent5.putExtra("store_name",store_name1);
                startActivity(intent5);
                break;
            case R.id.b7:
                Intent intent6 = new Intent(this, owner_logout.class);
                startActivity(intent6);
                break;
            case R.id.b8:
                Intent intent7 = new Intent(this, owner_order_record.class);
                intent7.putExtra("owner_name",owner_name1);
                intent7.putExtra("owner_address",owner_address1);
                intent7.putExtra("owner_lat",owner_lat1);
                intent7.putExtra("owner_long",owner_long1);
                intent7.putExtra("store_name",store_name1);
                startActivity(intent7);
                break;


        }

        return super.onOptionsItemSelected(item);
    }*/
    public void onClick(View v){

    }

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
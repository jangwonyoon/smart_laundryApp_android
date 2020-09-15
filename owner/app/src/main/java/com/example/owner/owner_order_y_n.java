package com.example.owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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

public class owner_order_y_n extends AppCompatActivity{

    TextView tv1;
    ImageView iv1;

    int count=0;

    public String var_name, var_address, var_store_name;
    public Double var_lat, var_long;
    private long backBtnTime = 0;
    Button b1,b2,b3,b4,b5,b6,b7,b8;
    TextView get_text;

    private ListView listView;

    private static String TAG = "phptest";

    private EditText mEditTextName;
    private EditText mEditTextCountry;
    private TextView mTextViewResult;
    private ArrayList<owner_order_y_n_list> mArrayList;
    private owner_order_y_n_Adpter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText mEditTextSearchKeyword;
    private String mJsonString;


    String owner_name1, owner_address1,store_name1;
    Double owner_lat1, owner_long1;

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
        setContentView(R.layout.activity_owner_order_y_n);

        Intent intent = getIntent();
        owner_name1 = intent.getStringExtra("owner_name");
        owner_address1 = intent.getStringExtra("owner_address");
        owner_lat1 = intent.getDoubleExtra("owner_lat",0.0);
        owner_long1 = intent.getDoubleExtra("owner_long",0.0);
        store_name1 = intent.getStringExtra("store_name");

        var_name = owner_name1;
        var_address = owner_address1;
        var_lat = owner_lat1;
        var_long = owner_long1;
        var_store_name = store_name1;

        mTextViewResult = (TextView) findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        mArrayList = new ArrayList<>();

        mAdapter = new owner_order_y_n_Adpter(this, mArrayList, var_name, var_address, var_lat, var_long, var_store_name);
        mRecyclerView.setAdapter(mAdapter);

        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        /*mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));*/
        RecyclerDecoration spaceDecoration = new RecyclerDecoration(20);
        mRecyclerView.addItemDecoration(spaceDecoration);

        owner_order_y_n.GetData task = new owner_order_y_n.GetData();
        task.execute("http://edit0.dothome.co.kr/owner_order_y_n_db.php", var_store_name,"");

        final SwipeRefreshLayout srfl = findViewById(R.id.swipe_refresh_layout);

        srfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.setAdapter(mAdapter);
                mArrayList.clear();
                mAdapter.notifyDataSetChanged();

                owner_order_y_n.GetData task1 = new owner_order_y_n.GetData();
                task1.execute("http://edit0.dothome.co.kr/owner_order_y_n_db.php", var_store_name,"");
                srfl.setRefreshing(false);
            }
        });

        tv1 = findViewById(R.id.backtext);
        iv1 = findViewById(R.id.backimage);
        if(count==0) {
            iv1.setImageResource(R.drawable.reject);
            iv1.setVisibility(View.VISIBLE);
            tv1.setVisibility(View.VISIBLE);
        }


        //액션바 설정하기//
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle("[수락/거절] "+owner_name1+"사장님 안녕하세요.");
        //액션바 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));



    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(owner_order_y_n.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
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
        String TAG_u_address = "u_address";
        String TAG_items = "items";
        String TAG_u_number ="u_number";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String u_address1 = item.getString(TAG_u_address);
                String items1 = item.getString(TAG_items);
                String u_number = item.getString(TAG_u_number);

                owner_order_y_n_list personalData = new owner_order_y_n_list();

                personalData.setMember_u_address(u_address1);
                personalData.setMember_u_id(items1);
                personalData.setMember_u_pw(u_number);

                count++;

                if(count>0){
                    iv1.setVisibility(View.INVISIBLE);
                    tv1.setVisibility(View.INVISIBLE);
                }

                mArrayList.add(personalData);
                mAdapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

    @Override
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
    }
    public void onClick(View v){

    }
}
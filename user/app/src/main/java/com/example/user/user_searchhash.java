package com.example.user;

import androidx.appcompat.app.AppCompatActivity;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class user_searchhash extends Activity {
    Button b1,b2,b3;
    TextView tv11;
    ImageView iv11;
    Button searchbutton;
    EditText et1;
    String getsearch;
    LinearLayout searchbuttonlayout;
    int count=0;

    public String var_name, var_address,  var_id;
    public Double var_lat, var_long;

    String user_name1, user_address1,user_id1, user_address_detail1, data1, data2, data3;
    Double user_lat1, user_long1;

    private static String TAG = "phptest";

    private EditText mEditTextName;
    private EditText mEditTextCountry;
    private TextView mTextViewResult;
    private ArrayList<user_searchhash_list> mArrayList;
    private user_searchhash_Adpter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText mEditTextSearchKeyword;
    private String mJsonString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_searchhash);


        Intent intent = getIntent();
        user_name1 = intent.getStringExtra("user_name");
        user_address1 = intent.getStringExtra("user_address");
        user_lat1 = intent.getDoubleExtra("user_lat",0.0);
        user_long1 = intent.getDoubleExtra("user_long",0.0);
        user_id1 = intent.getStringExtra("user_id");
        user_address_detail1 = intent.getStringExtra("user_address_detail");
        data1 = intent.getStringExtra("data1");

        var_name = user_name1;
        var_address = user_address1;
        var_lat = user_lat1;
        var_long = user_long1;
        var_id = user_id1;
        String var_address_detail = user_address_detail1;


        b1 = findViewById(R.id.showdata1);
        b2 = findViewById(R.id.showdata2);
        b3 = findViewById(R.id.showdata3);

        b1.setText(data1);
        b2.setText(data2);
        b3.setText(data3);

        et1 = findViewById(R.id.layout2_et1);
        searchbuttonlayout = findViewById(R.id.searchbuttonlayout);

        searchbutton = findViewById(R.id.searchbutton);
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getsearch = et1.getText().toString();

                if(b1.getText().toString().equals("") && b3.getText().toString().equals("") && b3.getText().toString().equals("")){
                    searchbuttonlayout.setVisibility(View.VISIBLE);
                }

                if(b1.getText().toString().equals("")){
                    b1.setVisibility(View.VISIBLE);
                    b1.setText(getsearch);
                    data1 = getsearch;
                }
                else if(b2.getText().toString().equals("")){
                    b2.setVisibility(View.VISIBLE);
                    b2.setText(getsearch);
                    data1 = b1.getText().toString();
                    data2 = getsearch;
                }
                else if(b3.getText().toString().equals("")){
                    b3.setVisibility(View.VISIBLE);
                    b3.setText(getsearch);
                    data1 = b1.getText().toString();
                    data2 = b2.getText().toString();
                    data3 = getsearch;
                }
                et1.setText("");

                mRecyclerView.setAdapter(mAdapter);
                mArrayList.clear();
                mAdapter.notifyDataSetChanged();

                user_searchhash.GetData task2 = new user_searchhash.GetData();
                task2.execute("http://edit0.dothome.co.kr/user_searchhash_db.php",String.valueOf(user_lat1),String.valueOf(user_long1),String.valueOf(data1),
                        String.valueOf(data2),String.valueOf(data3));



            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setText("");
                if(!b2.getText().toString().equals("")){
                    b1.setText(b2.getText().toString());
                    b2.setText(b3.getText().toString());
                    b3.setText("");
                }
                else if((!b3.getText().toString().equals(""))){
                    b1.setText(b3.getText().toString());
                    b3.setText("");
                }
                if(b1.getText().toString().equals("") && b3.getText().toString().equals("") && b3.getText().toString().equals("")){
                    searchbuttonlayout.setVisibility(View.GONE);
                }

                data1 = b1.getText().toString();
                data2 = b2.getText().toString();
                data3 = b3.getText().toString();

                mRecyclerView.setAdapter(mAdapter);
                mArrayList.clear();
                mAdapter.notifyDataSetChanged();

                user_searchhash.GetData task2 = new user_searchhash.GetData();
                task2.execute("http://edit0.dothome.co.kr/user_searchhash_db.php",String.valueOf(user_lat1),String.valueOf(user_long1),String.valueOf(data1),
                        String.valueOf(data2),String.valueOf(data3));
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b2.setText("");
                if(!b3.getText().toString().equals("")){
                    b2.setText(b3.getText().toString());
                    b3.setText("");
                }
                data1 = b1.getText().toString();
                data2 = b2.getText().toString();
                data3 = b3.getText().toString();

                mRecyclerView.setAdapter(mAdapter);
                mArrayList.clear();
                mAdapter.notifyDataSetChanged();

                user_searchhash.GetData task2 = new user_searchhash.GetData();
                task2.execute("http://edit0.dothome.co.kr/user_searchhash_db.php",String.valueOf(user_lat1),String.valueOf(user_long1),String.valueOf(data1),
                        String.valueOf(data2),String.valueOf(data3));
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b3.setText("");

                data1 = b1.getText().toString();
                data2 = b2.getText().toString();
                data3 = b3.getText().toString();

                mRecyclerView.setAdapter(mAdapter);
                mArrayList.clear();
                mAdapter.notifyDataSetChanged();

                user_searchhash.GetData task2 = new user_searchhash.GetData();
                task2.execute("http://edit0.dothome.co.kr/user_searchhash_db.php",String.valueOf(user_lat1),String.valueOf(user_long1),String.valueOf(data1),
                        String.valueOf(data2),String.valueOf(data3));
            }
        });



        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        mArrayList = new ArrayList<>();

        mAdapter = new user_searchhash_Adpter(this, mArrayList, var_name,var_address,var_lat,var_long,var_id, var_address_detail);
        mRecyclerView.setAdapter(mAdapter);

        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        /*mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));*/

        RecyclerDecoration spaceDecoration = new RecyclerDecoration(10);
        mRecyclerView.addItemDecoration(spaceDecoration);
        Toast.makeText(this, data1+data2+data3,Toast.LENGTH_SHORT).show();

        user_searchhash.GetData task = new user_searchhash.GetData();
        task.execute("http://edit0.dothome.co.kr/user_searchhash_db.php",String.valueOf(user_lat1),String.valueOf(user_long1),String.valueOf(data1),
                String.valueOf(data2),String.valueOf(data3));


        tv11 = findViewById(R.id.backtext);
        iv11 = findViewById(R.id.backimage);
        if(count==0) {
            iv11.setImageResource(R.drawable.reject);
            iv11.setVisibility(View.VISIBLE);
            tv11.setVisibility(View.VISIBLE);
        }



    }


    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(user_searchhash.this,
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
            String postParameters = "user_lat=" + params[1] + "&user_long=" + params[2] + "&data1=" + params[3] +
                    "&data2=" + params[4] + "&data3=" + params[5];

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
        String TAG_o_id = "o_id";
        String TAG_o_pw ="o_pw";
        String TAG_cd = "cd";
        String TAG_data2 = "data2";
        String TAG_data3 = "data3";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String s_name1 = item.getString(TAG_s_name);
                String o_id1 = item.getString(TAG_o_id);
                String o_pw1 = item.getString(TAG_o_pw);
                String cd1 = item.getString(TAG_cd);
                String data21 = item.getString(TAG_data2);
                String data31 = item.getString(TAG_data3);

                user_searchhash_list personalData = new user_searchhash_list();

                personalData.setMember_s_name(s_name1);
                personalData.setMember_o_id(o_id1);
                personalData.setMember_o_pw(o_pw1);
                personalData.setMember_cd(cd1);
                personalData.setMember_data2(data21);
                personalData.setMember_data3(data31);

                count++;

                if(count>0){
                    iv11.setVisibility(View.INVISIBLE);
                    tv11.setVisibility(View.INVISIBLE);
                }

                mArrayList.add(personalData);
                mAdapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
}
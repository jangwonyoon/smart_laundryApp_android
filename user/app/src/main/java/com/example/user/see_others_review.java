package com.example.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

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

public class see_others_review extends Activity {

    String user_name1, user_address1;
    Double user_lat1, user_long1;
    String store_name1, user_id1, user_address_detail1, now_user_id1;
    String total_num;

    TextView tv1,tv2,tv3,tv4,tv5;
    Button b1;

    private static String TAG = "phptest";

    private EditText mEditTextName;
    private EditText mEditTextCountry;
    private TextView mTextViewResult;
    private ArrayList<see_others_review_list> mArrayList;
    private see_others_review_Adpter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText mEditTextSearchKeyword;
    private String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_others_review);

        Intent intent = getIntent();
        user_name1 = intent.getStringExtra("user_name");
        user_address1 = intent.getStringExtra("user_address");
        user_lat1 = intent.getDoubleExtra("user_lat",0.0);
        user_long1 = intent.getDoubleExtra("user_long",0.0);
        store_name1 = intent.getStringExtra("title");
        now_user_id1 = intent.getStringExtra("now_user_id");
        user_id1 = intent.getStringExtra("user_id");
        user_address_detail1 = intent.getStringExtra("user_address_detail");

//        tv1 = findViewById(R.id.u_id);
//        tv1.setText(now_user_id1);


        tv2 = findViewById(R.id.num);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                    if(success){ //회원등록에 성공한 경우
                        total_num = jsonObject.getString("total_num");
                        /*avg = jsonObject.getDouble("avg");*/
                        tv2.setText(total_num);
                        /*tv2.setText(String.format("%.1f", avg));
                        rb1.setRating((int)avg);*/
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
        see_others_review_db_sum registerRequest = new see_others_review_db_sum(now_user_id1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(see_others_review.this);
        queue.add(registerRequest);

        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        mArrayList = new ArrayList<>();

        mAdapter = new see_others_review_Adpter(this, mArrayList, store_name1, user_name1,user_address1,user_lat1,user_long1,user_id1,user_address_detail1);
        mRecyclerView.setAdapter(mAdapter);

        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));


        see_others_review.GetData task = new see_others_review.GetData();
        task.execute("http://edit0.dothome.co.kr/see_others_review_db.php",now_user_id1);
    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(see_others_review.this,
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
            String postParameters = "now_u_id=" + params[1];

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

                String s_name1 = item.getString(TAG_s_name);
                String rating1 = item.getString(TAG_rating);
                String date1 = item.getString(TAG_date);
                String content1 = item.getString(TAG_content);
                String items1 = item.getString(TAG_items);
                String o_comment1 = item.getString(TAG_o_comment);
                String image_one1 = item.getString(TAG_image_one);
                /*String image_two1 = item.getString(TAG_image_two);
                String image_three1 = item.getString(TAG_image_three);*/

                see_others_review_list personalData = new see_others_review_list();

                personalData.setMember_s_name(s_name1);
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
}

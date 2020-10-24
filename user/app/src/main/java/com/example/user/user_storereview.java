package com.example.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
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

public class user_storereview extends Fragment {

    TextView tv11;
    ImageView iv11;

    int count=0;

    public user_storereview() {
    }

    String TAG = "phptest";

    EditText mEditTextName;
    EditText mEditTextCountry;
    TextView mTextViewResult;
    ArrayList<user_storereview_list> mArrayList;
    user_storereview_Adpter mAdapter;
    RecyclerView mRecyclerView;
    EditText mEditTextSearchKeyword;
    String mJsonString;

    Button b1;



    String user_name1, user_address1;
    Double user_lat1, user_long1;
    String store_name1, user_id1, user_address_detail1;

    TextView tv1,tv2,tv3;
    RatingBar rb1;

    String total_num;
    double avg;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_user_storereview, container, false);



        //user_id 받아야 할 것
        Intent intent = getActivity().getIntent();
        user_name1 = intent.getStringExtra("user_name");
        user_address1 = intent.getStringExtra("user_address");
        user_lat1 = intent.getDoubleExtra("user_lat",0.0);
        user_long1 = intent.getDoubleExtra("user_long",0.0);
        store_name1 = intent.getStringExtra("title");
        user_id1 = intent.getStringExtra("user_id");
        user_address_detail1 = intent.getStringExtra("user_address_detail");

        tv1 = rootView.findViewById(R.id.title1);
        tv1.setText(store_name1 + " [리뷰]");

        tv2 = (TextView) rootView.findViewById(R.id.avgrating);
        tv3 = (TextView) rootView.findViewById(R.id.total_review_num);
        rb1 = (RatingBar) rootView.findViewById(R.id.rating);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                    if(success){ //회원등록에 성공한 경우
                        total_num = jsonObject.getString("total_num");
                        avg = jsonObject.getDouble("avg");
                        tv3.setText(total_num);
                        tv2.setText(String.format("%.1f", avg));
                        rb1.setRating((int) avg);
                    }
                    //실패한 경우
                    else{
                        Toast.makeText(getActivity(),"SUM ERROR",Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        user_storereview_db_sum registerRequest = new user_storereview_db_sum(store_name1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(registerRequest);


        //=======================================리스트시작===========================================

        mTextViewResult = (TextView)rootView.findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        mArrayList = new ArrayList<>();

        mAdapter = new user_storereview_Adpter(getActivity(), mArrayList, store_name1, user_name1,user_address1,user_lat1,user_long1,user_id1,user_address_detail1);
        mRecyclerView.setAdapter(mAdapter);

        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));


        user_storereview.GetData task = new user_storereview.GetData();
        task.execute("http://edit0.dothome.co.kr/user_storereview_db.php",store_name1);

        tv11 = rootView.findViewById(R.id.backtext);
        iv11 = rootView.findViewById(R.id.backimage);
        if(count==0) {
            iv11.setImageResource(R.drawable.reject);
            iv11.setVisibility(View.VISIBLE);
            tv11.setVisibility(View.VISIBLE);
        }


//        b1 = (Button) rootView.findViewById(R.id.layout2_b1);
//        b1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), user_main1.class);
//                intent.putExtra("user_name",user_name1);
//                intent.putExtra("user_address",user_address1);
//                intent.putExtra("user_lat",user_lat1);
//                intent.putExtra("user_long",user_long1);
//                intent.putExtra("title",store_name1);
//                intent.putExtra("user_id",user_id1);
//                intent.putExtra("user_address_detail",user_address_detail1);
//                startActivity(intent);
//            }
//        });


        return rootView;

        //xml 레이아웃이 인플레이트 되고 자바소스 코드와 연결이된다.
    }





    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(),
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

                user_storereview_list personalData = new user_storereview_list();

                personalData.setMember_u_id(u_id1);
                personalData.setMember_rating(rating1);
                personalData.setMember_date(date1);
                personalData.setMember_content(content1);
                personalData.setMember_items(items1);
                personalData.setMember_o_comment(o_comment1);
                personalData.setMember_image1(image_one1);
                /*personalData.setMember_image2(image_two1);
                personalData.setMember_image3(image_three1);*/


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

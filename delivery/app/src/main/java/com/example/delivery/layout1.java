package com.example.delivery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.delivery.MainActivity.rider_id1;
import static com.example.delivery.MainActivity.rider_name1;
import static com.example.delivery.MainActivity.rider_address1;
import static com.example.delivery.MainActivity.rider_long1;
import static com.example.delivery.MainActivity.rider_lat1;

public class layout1 extends Fragment {

    ViewGroup viewGroup;

    TextView tv1;
    ImageView iv1;

    int count=0;
    String TAG = "phptest";

    EditText mEditTextName;
    EditText mEditTextCountry;
    TextView mTextViewResult;
    ArrayList<layout1_list> mArrayList;
    layout1_Adpter mAdapter;
    RecyclerView mRecyclerView;
    EditText mEditTextSearchKeyword;
    String mJsonString;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_layout1,container,false);

        mTextViewResult = (TextView)viewGroup.findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) viewGroup.findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        mArrayList = new ArrayList<>();

        mAdapter = new layout1_Adpter(getActivity(), rider_id1, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));


        layout1.GetData task = new layout1.GetData();
        task.execute("http://edit0.dothome.co.kr/layout1_db.php",rider_id1,rider_name1,rider_address1,String.valueOf(rider_lat1),String.valueOf(rider_long1));


        final SwipeRefreshLayout srfl = viewGroup.findViewById(R.id.swipe_refresh_layout);

        srfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.setAdapter(mAdapter);
                mArrayList.clear();
                mAdapter.notifyDataSetChanged();

                layout1.GetData task1 = new layout1.GetData();
                task1.execute("http://edit0.dothome.co.kr/layout1_db.php",rider_id1,rider_name1,rider_address1,String.valueOf(rider_lat1),String.valueOf(rider_long1));
                srfl.setRefreshing(false);
            }
        });


        tv1 = viewGroup.findViewById(R.id.backtext);
        iv1 = viewGroup.findViewById(R.id.backimage);
        if(count==0) {
            iv1.setImageResource(R.drawable.reject);
            iv1.setVisibility(View.VISIBLE);
            tv1.setVisibility(View.VISIBLE);
        }



        return viewGroup;
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
            String postParameters = "rider_id=" + params[1] + "&rider_name=" + params[2] + "&rider_address=" + params[3] +
                    "&rider_lat=" + params[4] + "&rider_long=" + params[5];
            /*"user_lat=" + params[1] + "&user_long=" + params[2];*/

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
        String TAG_s_address = "o_address";
        String TAG_price = "d_price";
        String TAG_items = "item";
        String TAG_u_number = "u_number";
        String TAG_s_number = "o_number";
        String TAG_d_type = "d_type";



        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String u_address1 = item.getString(TAG_u_address);
                String s_address1 = item.getString(TAG_s_address);
                String price1 = item.getString(TAG_price);
                String items1 = item.getString(TAG_items);
                String u_number1 = item.getString(TAG_u_number);
                String s_number1 = item.getString(TAG_s_number);
                String d_type1 = item.getString(TAG_d_type);

                layout1_list personalData = new layout1_list();

                personalData.setMember_u_address(u_address1);
                personalData.setMember_s_address(s_address1);
                personalData.setMember_price(price1);
                personalData.setMember_items(items1);
                personalData.setMember_u_number(u_number1);
                personalData.setMember_s_number(s_number1);
                personalData.setMember_d_type(d_type1);


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

}
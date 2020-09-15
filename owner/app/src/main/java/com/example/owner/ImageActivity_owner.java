package com.example.owner;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ImageActivity_owner extends Activity {
    String user_id1, user_address;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
    Button back,b1,b2;

    String owner_name1, owner_address1, store_name1;
    Double owner_lat1, owner_long1;

    String memo, user_number;
    int date,t_price,yes_no;

    private static String TAG = "phptest";

    private EditText mEditTextName;
    private EditText mEditTextCountry;
    private TextView mTextViewResult;
    private ArrayList<ImageActivity_owner_list> mArrayList;
    private ImageActivity_owner_Adpter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText mEditTextSearchKeyword;
    private String mJsonString;

    String[] permission_list = {
            Manifest.permission.SEND_SMS
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_owner);
        checkPermission();



        back = (Button) findViewById(R.id.layout2_b1);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ImageActivity_owner.this, owner_main.class);
                intent.putExtra("owner_name",owner_name1);
                intent.putExtra("owner_address",owner_address1);
                intent.putExtra("owner_lat",owner_lat1);
                intent.putExtra("owner_long",owner_long1);
                intent.putExtra("store_name",store_name1);

                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        owner_name1 = intent.getStringExtra("owner_name");
        owner_address1 = intent.getStringExtra("owner_address");
        owner_lat1 = intent.getDoubleExtra("owner_lat", 0.0);
        owner_long1 = intent.getDoubleExtra("owner_long", 0.0);
        user_id1 = intent.getStringExtra("u_id");
        user_address = intent.getStringExtra("u_address");
        store_name1 = intent.getStringExtra("store_name");

        tv1 = (TextView)findViewById(R.id.u_id);
        tv1.setText(user_id1+"님의 주문표");

        tv2 = (TextView)findViewById(R.id.date);


        tv3 = (TextView) findViewById(R.id.address);
        tv3.setText(user_address);

        tv4 = (TextView)findViewById(R.id.memo);
        tv5 = (TextView) findViewById(R.id.t_price);
        /*tv6 = (TextView) findViewById(R.id.yes_no);*/

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                    if(success){ //회원등록에 성공한 경우
                        memo = jsonObject.getString("memo");
                        if (memo.length()==0){
                            memo = "공란";
                        }
                        date = jsonObject.getInt("date");
                        t_price = jsonObject.getInt("t_price");
                        /*yes_no = jsonObject.getInt("yes_no");*/
                        tv2.setText("2020"+date);
                        tv4.setText(memo);
                        tv5.setText(t_price+"원");
                        /*if (yes_no==0){
                            tv6.setText("X");
                        }
                        else{
                            tv6.setText("O");
                        }*/
                        user_number = jsonObject.getString("u_number");
                        String r_number = jsonObject.getString("r_number");

                        TextView tv_n1 = (TextView) findViewById(R.id.u_number);
                        TextView tv_n2 = (TextView) findViewById(R.id.r_number);

                        tv_n1.setText("고객: 0"+user_number);
                        tv_n2.setText("라이더: "+r_number);
                        if(r_number.equals("null")){
                            tv_n2.setText("라이더: "+"라이더를 불러주세요.");
                        }

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
        ImageActivity_owner_db registerRequest = new ImageActivity_owner_db(store_name1,user_id1, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ImageActivity_owner.this);
        queue.add(registerRequest);


        /*b1 = (Button)findViewById(R.id.change);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                yes_no = jsonObject.getInt("yes_no");
                                if (yes_no==0){
                                    tv6.setText("X");
                                }
                                else{
                                    tv6.setText("O");
                                }
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
                ImageActivity_owner1_db registerRequest = new ImageActivity_owner1_db(store_name1,user_id1, yes_no, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ImageActivity_owner.this);
                queue.add(registerRequest);
            }
        });*/

        b2 = (Button)findViewById(R.id.finish);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ImageActivity_owner.this);
                builder.setTitle("알림");
                builder.setMessage("현재주문을 완료하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String phoneNo = "0"+user_number;
                        String sms = "고객님의 세탁이 완료되었습니다.\n-세탁의민족-";
                        try {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                            Toast.makeText(getApplicationContext(), "문자전송 완료", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "전송 오류", Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();//오류 원인이 찍힌다.
                            e.printStackTrace();
                        }
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                                    if(success){ //회원등록에 성공한 경우
                                        Intent intent = new Intent(ImageActivity_owner.this, owner_main.class);
                                        intent.putExtra("owner_name",owner_name1);
                                        intent.putExtra("owner_address",owner_address1);
                                        intent.putExtra("owner_lat",owner_lat1);
                                        intent.putExtra("owner_long",owner_long1);
                                        intent.putExtra("u_id",user_id1);
                                        intent.putExtra("u_address",user_address);
                                        intent.putExtra("store_name",store_name1);

                                        startActivity(intent);
                                    }
                                    //실패한 경우
                                    else{
                                        Toast.makeText(getApplicationContext(),"주문완료를 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        };

                        //서버로 Volley를 이용해서 요청을 함
                        ImageActivity_owner2_db registerRequest = new ImageActivity_owner2_db(store_name1, user_id1, t_price ,date, memo, yes_no,user_address, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(ImageActivity_owner.this);
                        queue.add(registerRequest);
                    }
                });

                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) { }
                });
                AlertDialog alert = builder.create();                                                       //빌더를 이용하여 AlertDialog객체를 생성합니다.
                alert.show();
            }
        });


        mTextViewResult = (TextView) findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        mArrayList = new ArrayList<>();

        mAdapter = new ImageActivity_owner_Adpter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        ImageActivity_owner.GetData task = new ImageActivity_owner.GetData();
        task.execute("http://edit0.dothome.co.kr/ImageActivity_owner_itemlist_db.php", store_name1,user_id1);




    }

    public void sendSmsIntent(String number){
        try{
            Uri smsUri = Uri.parse("sms:"+number);
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, smsUri);
            sendIntent.putExtra("sms_body", "test");
            startActivity(sendIntent);

//        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//        sendIntent.putExtra("address", number);
//        sendIntent.putExtra("sms_body", editBody.getText().toString());
//        sendIntent.setType("vnd.android-dir/mms-sms");
//        startActivity(sendIntent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ImageActivity_owner.this,
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

            String serverURL = params[0];
            String postParameters = "store_name=" + params[1] + "&user_id=" + params[2];


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
        String TAG_item = "item_list_menu";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String item1 = item.getString(TAG_item);


                ImageActivity_owner_list personalData = new ImageActivity_owner_list();

                personalData.setMember_item(item1);

                mArrayList.add(personalData);
                mAdapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

    /*private void SendSMS(String phonenumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        String sendTo = phonenumber;
        String myMessage = message;
        smsManager.sendTextMessage(sendTo, null, myMessage, null, null);
        Toast.makeText(getApplicationContext(), "전송되었습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }




    private void sendSMS(String phoneNumber, String message) {
        String SENT = "SMS_SENT_ACTION";
        String DELIVERED = "SMS_DELIVERED_ACTION";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "알림 문자 메시지가 전송되었습니다.", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        }, new IntentFilter(SENT));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }*/

    public void checkPermission(){
        //현재 안드로이드 버전이 6.0미만이면 메서드를 종료한다.
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;

        for(String permission : permission_list){
            //권한 허용 여부를 확인한다.
            int chk = checkCallingOrSelfPermission(permission);

            if(chk == PackageManager.PERMISSION_DENIED){
                //권한 허용을여부를 확인하는 창을 띄운다
                requestPermissions(permission_list,0);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0)
        {
            for(int i=0; i<grantResults.length; i++)
            {
                //허용됬다면
                if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                }
                else {
                    Toast.makeText(getApplicationContext(),"앱권한을 설정하세요.",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }

}

package com.example.delivery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.delivery.MainActivity.rider_id1;
import static com.example.delivery.MainActivity.rider_name1;
import static com.example.delivery.MainActivity.rider_address1;
import static com.example.delivery.MainActivity.rider_long1;
import static com.example.delivery.MainActivity.rider_lat1;

public class layout1 extends Fragment {

    ViewGroup viewGroup;

    TextView tv1;
    ImageView iv1;

    int count = 0;
    String TAG = "phptest";

    TextView mTextViewResult;
    ArrayList<layout1_list> mArrayList;
    layout1_Adpter mAdapter;
    RecyclerView mRecyclerView;
    String mJsonString;


    int status = 0;

    private LocationManager locationManager;
    private static final int REQUEST_CODE_LOCATION = 2;
    double latitude;
    double longitude;
    TextView logView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_layout1, container, false);

        /*latlong = viewGroup.findViewById(R.id.latlong);*/
        Log.d("Main", "onCreate");

        /*logView = (TextView) viewGroup.findViewById(R.id.latlong);*/
        /*logView.setText("GPS 불러오는 중.....");*/
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        // GPS 프로바이더 사용가능여부
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 네트워크 프로바이더 사용가능여부
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Log.d("Main", "isGPSEnabled=" + isGPSEnabled);
        Log.d("Main", "isNetworkEnabled=" + isNetworkEnabled);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();

                /*logView.setText("latitude: " + lat + ", longitude: " + lng);*/

                latitude = lat;
                longitude = lng;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                logView.setText("onStatusChanged");
            }

            public void onProviderEnabled(String provider) {
                logView.setText("onProviderEnabled");
            }

            public void onProviderDisabled(String provider) {
                logView.setText("onProviderDisabled");
            }
        };

        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        // 수동으로 위치 구하기
        String locationProvider = LocationManager.GPS_PROVIDER;
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        if (lastKnownLocation != null) {
            double lng = lastKnownLocation.getLatitude();
            double lat = lastKnownLocation.getLatitude();
            Log.d("Main", "longtitude=" + lng + ", latitude=" + lat);
        }


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

        if(status==0) {
            layout1.GetData task = new layout1.GetData();
            task.execute("http://edit0.dothome.co.kr/layout1_db.php", rider_id1, rider_name1, rider_address1, String.valueOf(rider_lat1), String.valueOf(rider_long1));
        }
        else{}
        Switch s = viewGroup.findViewById(R.id.smartswitch);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mRecyclerView.setAdapter(mAdapter);
                    mArrayList.clear();
                    mAdapter.notifyDataSetChanged();
                    status=1;



                    layout1.GetData task = new layout1.GetData();
                    task.execute("http://edit0.dothome.co.kr/layout1_db_for_smart.php",rider_id1,rider_name1,rider_address1,String.valueOf(latitude),String.valueOf(longitude));

                }else{
                    mRecyclerView.setAdapter(mAdapter);
                    mArrayList.clear();
                    mAdapter.notifyDataSetChanged();
                    status=0;



                    layout1.GetData task = new layout1.GetData();
                    task.execute("http://edit0.dothome.co.kr/layout1_db.php",rider_id1,rider_name1,rider_address1,String.valueOf(rider_lat1),String.valueOf(rider_long1));
                }
            }
        });


        final SwipeRefreshLayout srfl = viewGroup.findViewById(R.id.swipe_refresh_layout);
        srfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(status==1){
                    mRecyclerView.setAdapter(mAdapter);
                    mArrayList.clear();
                    mAdapter.notifyDataSetChanged();

                    layout1.GetData task1 = new layout1.GetData();
                    task1.execute("http://edit0.dothome.co.kr/layout1_db_for_smart.php",rider_id1,rider_name1,rider_address1,String.valueOf(latitude),String.valueOf(longitude));
                    srfl.setRefreshing(false);
                }
                else{
                    mRecyclerView.setAdapter(mAdapter);
                    mArrayList.clear();
                    mAdapter.notifyDataSetChanged();

                    layout1.GetData task1 = new layout1.GetData();
                    task1.execute("http://edit0.dothome.co.kr/layout1_db.php",rider_id1,rider_name1,rider_address1,String.valueOf(rider_lat1),String.valueOf(rider_long1));
                    srfl.setRefreshing(false);
                }
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

        String TAG_a = "a";



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

                String a1 = item.getString(TAG_a);

                layout1_list personalData = new layout1_list();

                personalData.setMember_u_address(u_address1);
                personalData.setMember_s_address(s_address1);
                personalData.setMember_price(price1);
                personalData.setMember_items(items1);
                personalData.setMember_u_number(u_number1);
                personalData.setMember_s_number(s_number1);
                personalData.setMember_d_type(d_type1);

                personalData.setMember_a(a1);


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

    /**
     * 사용자의 위치를 수신
     */
    private Location getMyLocation() {
        Location currentLocation = null;
        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("////////////사용자에게 권한을 요청해야함");
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, this.REQUEST_CODE_LOCATION);
            getMyLocation(); //이건 써도되고 안써도 되지만, 전 권한 승인하면 즉시 위치값 받아오려고 썼습니다!
        }
        else {
            System.out.println("////////////권한요청 안해도됨");

            // 수동으로 위치 구하기
            String locationProvider = LocationManager.GPS_PROVIDER;
            currentLocation = locationManager.getLastKnownLocation(locationProvider);
            if (currentLocation != null) {
                double lng = currentLocation.getLongitude();
                double lat = currentLocation.getLatitude();
            }
        }
        return currentLocation;
    }

}
package com.example.delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class layout2_first extends Activity implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {

    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    private Button user_signup_back, user_signup_go;
    private EditText user_address_detail1;
    private GoogleMap mMap;

    String set_address;
    Double latitude, longitude;
    List<Address> a;

    Double o_lat,o_long,u_lat,u_long;

    String rider_name1, rider_address1,rider_id1,u_address,s_address;
    Double rider_lat1, rider_long1;
    String u_number,s_number,d_type;
    TextView tv1,tv2;
    Button b1,b2,b3;


    String temp_date;

    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout2_first);

        Intent intent = getIntent();
        rider_name1 = intent.getStringExtra("rider_name");
        rider_address1 = intent.getStringExtra("rider_address");
        rider_lat1 = intent.getDoubleExtra("rider_lat",0.0);
        rider_long1 = intent.getDoubleExtra("rider_long",0.0);
        rider_id1 = intent.getStringExtra("rider_id");
        u_address = intent.getStringExtra("u_address");
        s_address = intent.getStringExtra("s_address");
        u_number = intent.getStringExtra("u_number");
        s_number = intent.getStringExtra("s_number");
        d_type = intent.getStringExtra("d_type");

        tv1 = findViewById(R.id.u_number);
        tv2 = findViewById(R.id.s_number);

        tv1.setText("고객: 0"+u_number);
        tv2.setText("가게: 0"+s_number);

        b1 = (Button) findViewById(R.id.u_call);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:"+"0"+u_number));
                startActivity(intent);
            }
        });

        b2 = (Button) findViewById(R.id.s_call);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:"+"0"+s_number));
                startActivity(intent);
            }
        });

        b3 = (Button)findViewById(R.id.layout3_b1);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(layout2_first.this, MainActivity.class);
                intent.putExtra("rider_id",rider_id1);
                intent.putExtra("rider_name",rider_name1);
                intent.putExtra("rider_address",rider_address1);
                intent.putExtra("rider_lat",rider_lat1);
                intent.putExtra("rider_long",rider_long1);

                startActivity(intent);
            }
        });












        done = (Button) findViewById(R.id.done);

        if(d_type.equals("0")){
            done.setEnabled(false);
        }
        else{
            done.setEnabled(true);
        }

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();

                SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
                SimpleDateFormat hourFormat = new SimpleDateFormat("HH", Locale.getDefault());
                SimpleDateFormat minFormat = new SimpleDateFormat("mm", Locale.getDefault());
                SimpleDateFormat secFormat = new SimpleDateFormat("ss", Locale.getDefault());


                final String month1 = monthFormat.format(currentTime);
                final String day1 = dayFormat.format(currentTime);
                final String hour1 = hourFormat.format(currentTime);
                final String min1 = minFormat.format(currentTime);
                final String sec1 = secFormat.format(currentTime);

                temp_date = month1+day1+hour1+min1+sec1;
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                Toast.makeText(getApplicationContext(),"배달완료",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(layout2_first.this, MainActivity.class);
                                intent.putExtra("rider_id",rider_id1);
                                intent.putExtra("rider_name",rider_name1);
                                intent.putExtra("rider_address",rider_address1);
                                intent.putExtra("rider_lat",rider_lat1);
                                intent.putExtra("rider_long",rider_long1);

                                startActivity(intent);
                            }
                            //실패한 경우
                            else{
                                Toast.makeText(getApplicationContext(),"오류",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 Volley를 이용해서 요청을 함
                layout2_first_done_db registerRequest = new layout2_first_done_db(rider_id1,u_address, s_address,temp_date, responseListener);
                RequestQueue queue = Volley.newRequestQueue(layout2_first.this);
                queue.add(registerRequest);
            }
        });


        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.googlemap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                    if(success){ //회원등록에 성공한 경우
                        Double num1= 0.0;
                        Double num2 = 0.0;
                        Double num3 = 0.0;
                        Double num4 = 0.0;

                        if(d_type.equals("0")){
                            o_lat = jsonObject.getDouble("o_lat");
                            o_long = jsonObject.getDouble("o_long");
                            num1=o_lat;
                            num2=o_long;
                            u_lat = jsonObject.getDouble("u_lat");
                            u_long = jsonObject.getDouble("u_long");
                            num3=u_lat;
                            num4 = u_long;
                        }
                        else{
                            o_lat = jsonObject.getDouble("o_lat");
                            o_long = jsonObject.getDouble("o_long");
                            num3=o_lat;
                            num4=o_long;
                            u_lat = jsonObject.getDouble("u_lat");
                            u_long = jsonObject.getDouble("u_long");
                            num1=u_lat;
                            num2 = u_long;
                        }

                        LatLng location1 = new LatLng(num1, num2);
                        MarkerOptions markerOptions1 = new MarkerOptions();
                        markerOptions1.title("목적지");
                        /*markerOptions.snippet("");*/
                        markerOptions1.position(location1);
                        googleMap.addMarker(markerOptions1);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location1,16));

                        LatLng location = new LatLng(num3, num4);
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.title("출발지");
                        /*markerOptions.snippet("");*/
                        markerOptions.position(location);
                        googleMap.addMarker(markerOptions);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,16));
                    }
                    //실패한 경우
                    else{
                        Toast.makeText(getApplicationContext(),"오류",Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        //서버로 Volley를 이용해서 요청을 함
        layout2_first_map_db registerRequest = new layout2_first_map_db(d_type,u_number,u_address,s_number,s_address, responseListener);
        RequestQueue queue = Volley.newRequestQueue(layout2_first.this);
        queue.add(registerRequest);
        Toast.makeText(getApplicationContext(),""+o_lat+o_long+"",Toast.LENGTH_SHORT).show();


        /*googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,16));*/


        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationButtonClickListener(this);
        googleMap.setOnMyLocationButtonClickListener(this);



        /*final Geocoder g = new Geocoder(this);
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point) {
                MarkerOptions mOptions = new MarkerOptions();
                // 마커 타이틀
                mOptions.title("이곳으로 설정하기!");
                latitude = point.latitude; // 위도
                longitude = point.longitude; // 경도
                // 마커의 스니펫(간단한 텍스트) 설정
                *//*mOptions.snippet(latitude.toString() + ", " + longitude.toString());*//*
                // LatLng: 위도 경도 쌍을 나타냄
                mOptions.position(new LatLng(latitude, longitude));

                googleMap.clear();

                // 마커(핀) 추가
                googleMap.addMarker(mOptions);

                try {
                    a = g.getFromLocation(latitude,longitude,1);
                    Toast.makeText(getApplicationContext(), ""+a.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
                    set_address = a.get(0).getAddressLine(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                *//*Double temp1 = latitude;
                Double temp2 = longitude;
                if (latitude == temp1 || longitude == temp2){
                    googleMap.remove
                }*//*
            }
        });*/
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }
}
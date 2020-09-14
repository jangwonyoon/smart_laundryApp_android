package com.example.delivery;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class layout2_first extends FragmentActivity
        implements OnMapReadyCallback {

    Double latitude,longitude;
    List<Address> a=null;

    private FusedLocationSource locationSource;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private NaverMap naverMap;

    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    private Button user_signup_back, user_signup_go;
    private EditText user_address_detail1;
    private GoogleMap mMap;

    String set_address;

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


        /*fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.googlemap);
        mapFragment.getMapAsync(this);*/

        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
        androidx.fragment.app.FragmentManager fm = getSupportFragmentManager();
        com.naver.maps.map.MapFragment mapFragment = (com.naver.maps.map.MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = com.naver.maps.map.MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);
    }

    /*@Override
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
                        *//*markerOptions.snippet("");*//*
                        markerOptions1.position(location1);
                        googleMap.addMarker(markerOptions1);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location1,16));

                        LatLng location = new LatLng(num3, num4);
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.title("출발지");
                        *//*markerOptions.snippet("");*//*
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


        *//*googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,16));*//*


        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationButtonClickListener(this);
        googleMap.setOnMyLocationButtonClickListener(this);



        *//*final Geocoder g = new Geocoder(this);
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
                *//**//*mOptions.snippet(latitude.toString() + ", " + longitude.toString());*//**//*
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


                *//**//*Double temp1 = latitude;
                Double temp2 = longitude;
                if (latitude == temp1 || longitude == temp2){
                    googleMap.remove
                }*//**//*
            }
        });*//*
    }*/

    @UiThread
    @Override
    public void onMapReady(@NonNull final NaverMap naverMap) {
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                final double lat = location.getLatitude();
                final double lng = location.getLongitude();

                final Marker marker = new Marker();
                final Marker marker1 = new Marker();

                final InfoWindow infoWindow = new InfoWindow();
                infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(getApplication()) {
                    @NonNull
                    @Override
                    public CharSequence getText(@NonNull InfoWindow infoWindow) {
                        return "목적지";
                    }
                });
                final InfoWindow infoWindow1 = new InfoWindow();
                infoWindow1.setAdapter(new InfoWindow.DefaultTextAdapter(getApplication()) {
                    @NonNull
                    @Override
                    public CharSequence getText(@NonNull InfoWindow infoWindow) {
                        return "출발지";
                    }
                });


                final Geocoder g = new Geocoder(getApplication());

                UiSettings uiSettings = naverMap.getUiSettings();
                uiSettings.setCompassEnabled(true);
                uiSettings.setLocationButtonEnabled(true);
                naverMap.setLocationSource(locationSource);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우

                                List<Address> a = null;


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


                                marker.setPosition(new com.naver.maps.geometry.LatLng(num1,num2));
                                marker.setMap(naverMap);

                                marker1.setPosition(new com.naver.maps.geometry.LatLng(num3,num4));
                                marker1.setMap(naverMap);

                                infoWindow.open(marker);
                                infoWindow1.open(marker1);

                                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new com.naver.maps.geometry.LatLng(num3,num4))
                                        .animate(CameraAnimation.Fly, 3000);
                                naverMap.moveCamera(cameraUpdate);

                                final Double f_num1 = num1;
                                final Double f_num2 = num2;
                                final Double f_num3 = num3;
                                final Double f_num4 = num4;

                                a = g.getFromLocation(lat,lng,1);
                                final String b = a.get(0).getAddressLine(0);
                                marker.setOnClickListener(new Overlay.OnClickListener() {//목적지
                                    @Override
                                    public boolean onClick(@NonNull Overlay overlay) {
                                        startActivity(new Intent(Intent.ACTION_VIEW,
                                                Uri.parse("nmap://route/car?slat="+lat+"&slng="+lng+"&sname="+b+"&dlat="+f_num1+"&dlng="+f_num2+"&dname="+u_address+"&appname=com.example.myapp")));
                                        return false;
                                    }
                                });

                                marker1.setOnClickListener(new Overlay.OnClickListener() {//출발지
                                    @Override
                                    public boolean onClick(@NonNull Overlay overlay) {
                                        startActivity(new Intent(Intent.ACTION_VIEW,
                                                Uri.parse("nmap://route/car?slat="+lat+"&slng="+lng+"&sname="+b+"&dlat="+f_num3+"&dlng="+f_num4+"&dname="+s_address+"&appname=com.example.myapp")));
                                        return false;
                                    }
                                });




                        /*LatLng location1 = new LatLng(num1, num2);
                        MarkerOptions markerOptions1 = new MarkerOptions();
                        markerOptions1.title("목적지");
                        *//*markerOptions.snippet("");*//*
                        markerOptions1.position(location1);
                        googleMap.addMarker(markerOptions1);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location1,16));*/

                        /*LatLng location = new LatLng(num3, num4);
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.title("출발지");
                        *//*markerOptions.snippet("");*//*
                        markerOptions.position(location);
                        googleMap.addMarker(markerOptions);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,16));*/
                            }
                            //실패한 경우
                            else{
                                Toast.makeText(getApplicationContext(),"오류",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 Volley를 이용해서 요청을 함
                layout2_first_map_db registerRequest = new layout2_first_map_db(d_type,u_number,u_address,s_number,s_address, responseListener);
                RequestQueue queue = Volley.newRequestQueue(layout2_first.this);
                queue.add(registerRequest);

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            public void onProviderEnabled(String provider) {

            }

            public void onProviderDisabled(String provider) {

            }
        };

        // Register the listener with the Location Manager to receive location updates
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
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);








        /*Toast.makeText(getApplicationContext(),""+o_lat+o_long+"",Toast.LENGTH_SHORT).show();*/


        /*LatLng coord = new LatLng(37.5670135, 126.9783740);

        Toast.makeText(getApplication(),
                "위도: " + coord.latitude + ", 경도: " + coord.longitude,
                Toast.LENGTH_SHORT).show();*/
    }

    /*@Override
    public boolean onClick(@NonNull Overlay overlay) {
        if(overlay instanceof Marker){
            Intent Sharing_intent = new Intent(Intent.ACTION_SEND);
            Sharing_intent.setType("text/plain");




            *//*String a = "https://map.naver.com/v5/search/"+re_set_title+"?c=14104623.8586647,4511836.5253034,15,0,0,0,dh";*//*
            String a = "https://map.naver.com/v5/search/";
            Sharing_intent.putExtra(Intent.EXTRA_TEXT, "nmap://search/bus?query=222&appname=com.example.myapp");

            startActivity(Sharing_intent);

            return true;
        }
        return false;
    }*/

    /*@Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }*/
}
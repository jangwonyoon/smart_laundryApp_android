package com.example.delivery;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
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
import com.naver.maps.map.util.FusedLocationSource;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class rider_changelocation extends AppCompatActivity
        implements OnMapReadyCallback {

    public static String rider_name1, rider_address1,rider_id1;
    public static Double rider_lat1, rider_long1;

    Double latitude,longitude;
    List<Address> a=null;

    private FusedLocationSource locationSource;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private NaverMap naverMap;



    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    private GoogleMap mMap;
    private long backBtnTime = 0;

    TextView get_text;
    EditText et1;

    Button b1, b2, b3, b4, b5, b6, b7, b8, menubar;

    String user_name1, user_address1, user_address_detail1;
    Double user_lat1, user_long1;
    private Button user_ch_lo;
    String set_address, user_id1;
    ;

    Double rider_lat, rider_long;
    String user_address, user_id, user_address_detail;

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if (0 <= gapTime && 2000 >= gapTime) {
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            backBtnTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_changelocation);


        Intent intent = getIntent();
        rider_name1 = intent.getStringExtra("rider_name");
        rider_address1 = intent.getStringExtra("rider_address");
        rider_lat1 = intent.getDoubleExtra("rider_lat",0.0);
        rider_long1 = intent.getDoubleExtra("rider_long",0.0);
        rider_id1 = intent.getStringExtra("rider_id");


        //액션바 설정하기//
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle("[위치변경]  " + rider_name1 + "님 안녕하세요.");
        //액션바 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));


        et1 = (EditText) findViewById(R.id.layout3_et1);

        user_ch_lo = (Button) findViewById(R.id.layout3_b2);
        user_ch_lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //현재 입력되어 있는 값을 가져온다(get)
                try {
                    rider_lat = latitude;
                    rider_long = longitude;
                    String m = a.get(0).getAddressLine(0).replaceAll("대한민국","");
                    user_address = m;
                    user_id = user_id1;
                    user_address_detail = et1.getText().toString();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "지도에 위치를 지정하세요.", Toast.LENGTH_SHORT).show();
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if (success) { //회원등록에 성공한 경우
                                Toast.makeText(getApplicationContext(), "주소가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(rider_changelocation.this, rider_changelocation.class);
                                intent.putExtra("rider_name",rider_name1);
                                intent.putExtra("rider_address",user_address);
                                intent.putExtra("rider_lat",rider_lat);
                                intent.putExtra("rider_long",rider_long);
                                intent.putExtra("rider_id",rider_id1);
                                startActivity(intent);
                            }
                            //실패한 경우
                            else {
                                Toast.makeText(getApplicationContext(), "주소 변경 실패\n상품을 주문해놓은 상태라면, 모든 주문을 마친 후 변경할 수 있습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                rider_register2_db registerRequest = new rider_register2_db(rider_lat, rider_long, user_address, rider_id1, user_address_detail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(rider_changelocation.this);
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


        *//*마커*//*
        LatLng location = new LatLng(rider_lat1, rider_long1);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("현재 배달 위치");
        markerOptions.snippet(rider_address1);
        markerOptions.position(location);
        googleMap.addMarker(markerOptions);

        *//*googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,16));*//*
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 16));

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
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationButtonClickListener(this);
        googleMap.setOnMyLocationButtonClickListener(this);


        final Geocoder g = new Geocoder(this);
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
                mOptions.snippet(latitude.toString() + ", " + longitude.toString());
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
        });
    }*/

    @UiThread
    @Override
    public void onMapReady(@NonNull final NaverMap naverMap) {
        final InfoWindow infoWindow = new InfoWindow();
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(getApplication()) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return "주소 지정";
            }
        });


        final Marker marker = new Marker();
        marker.setPosition(new com.naver.maps.geometry.LatLng( rider_lat1,rider_long1));
        marker.setMap(naverMap);
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new com.naver.maps.geometry.LatLng(rider_lat1,rider_long1))
                .animate(CameraAnimation.Fly, 3000);
        naverMap.moveCamera(cameraUpdate);

        final Geocoder g = new Geocoder(this);

        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
        uiSettings.setLocationButtonEnabled(true);
        naverMap.setLocationSource(locationSource);

        infoWindow.open(marker);

        naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener(){
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull com.naver.maps.geometry.LatLng latLng) {
                /*Toast.makeText(getApplicationContext(), latLng.latitude + ", " + latLng.longitude+"", Toast.LENGTH_SHORT).show();*/
                marker.setPosition(new LatLng( latLng.latitude,latLng.longitude));
                marker.setMap(naverMap);
                latitude=latLng.latitude;
                longitude=latLng.longitude;

                /*naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);*/

                try {
                    a = g.getFromLocation(latitude,longitude,1);
                    String m = a.get(0).getAddressLine(0).replaceAll("대한민국","");
                    Toast.makeText(getApplicationContext(), ""+m, Toast.LENGTH_SHORT).show();
                    set_address = a.get(0).getAddressLine(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        /*LatLng coord = new LatLng(37.5670135, 126.9783740);

        Toast.makeText(getApplication(),
                "위도: " + coord.latitude + ", 경도: " + coord.longitude,
                Toast.LENGTH_SHORT).show();*/
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu1, menu);
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
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("rider_name",rider_name1);
                intent.putExtra("rider_address",rider_address1);
                intent.putExtra("rider_lat",rider_lat1);
                intent.putExtra("rider_long",rider_long1);
                intent.putExtra("rider_id",rider_id1);
                startActivity(intent);
                break;
            case R.id.b2:
                Intent intent1 = new Intent(this, rider_gongji.class);
                intent1.putExtra("rider_name",rider_name1);
                intent1.putExtra("rider_address",rider_address1);
                intent1.putExtra("rider_lat",rider_lat1);
                intent1.putExtra("rider_long",rider_long1);
                intent1.putExtra("rider_id",rider_id1);
                startActivity(intent1);
                break;
            case R.id.b3:
                Intent intent2 = new Intent(this, rider_changelocation.class);
                intent2.putExtra("rider_name",rider_name1);
                intent2.putExtra("rider_address",rider_address1);
                intent2.putExtra("rider_lat",rider_lat1);
                intent2.putExtra("rider_long",rider_long1);
                intent2.putExtra("rider_id",rider_id1);
                startActivity(intent2);
                break;
            case R.id.b4:
                Intent intent3 = new Intent(this, rider_info.class);
                intent3.putExtra("rider_name",rider_name1);
                intent3.putExtra("rider_address",rider_address1);
                intent3.putExtra("rider_lat",rider_lat1);
                intent3.putExtra("rider_long",rider_long1);
                intent3.putExtra("rider_id",rider_id1);
                startActivity(intent3);
                break;

            case R.id.b8:
                Intent intent7 = new Intent(this, rider_logout.class);
                startActivity(intent7);
                break;

        }

        return super.onOptionsItemSelected(item);
    }
    public void onClick(View v){

    }

    /*@Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }*/
}
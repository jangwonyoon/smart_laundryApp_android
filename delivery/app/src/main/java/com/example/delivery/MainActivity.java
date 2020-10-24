package com.example.delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    layout1 fragment1;
    layout2 fragment2;
    layout3 fragment3;

    public static String rider_name1, rider_address1,rider_id1;
    public static Double rider_lat1, rider_long1;


    private long backBtnTime = 0;
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
        setContentView(R.layout.activity_main);



        Intent intent = getIntent();
        rider_name1 = intent.getStringExtra("rider_name");
        rider_address1 = intent.getStringExtra("rider_address");
        rider_lat1 = intent.getDoubleExtra("rider_lat",0.0);
        rider_long1 = intent.getDoubleExtra("rider_long",0.0);
        rider_id1 = intent.getStringExtra("rider_id");

        bottomNavigationView = findViewById(R.id.bottomNavigationView); //프래그먼트 생성
        fragment1 = new layout1();
        fragment2 = new layout2();
        fragment3 = new layout3();

        getSupportActionBar().setTitle(rider_name1+"님");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF4472C4));


        //제일 처음 띄워줄 뷰를 세팅해줍니다. commit();까지 해줘야 합니다.
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,fragment1).commitAllowingStateLoss();


        //bottomnavigationview의 아이콘을 선택 했을때 원하는 프래그먼트가 띄워질 수 있도록 리스너를 추가합니다.
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.tab1:{
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment1).commitAllowingStateLoss();

                        return true;
                    }
                    case R.id.tab2:{
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment2).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.tab3:{
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment3).commitAllowingStateLoss();
                        return true;
                    }
                    default:
                        return false;
                }
            }
        });

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
}
package com.example.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;

public class showimage extends Activity {

    String user_name1, user_address1;
    Double user_lat1, user_long1;
    String store_name1, user_id1, user_address_detail1;

    byte[] arr;

    Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showimage);

        Intent intent = getIntent();
        user_name1 = intent.getStringExtra("user_name");
        user_address1 = intent.getStringExtra("user_address");
        user_lat1 = intent.getDoubleExtra("user_lat",0.0);
        user_long1 = intent.getDoubleExtra("user_long",0.0);
        store_name1 = intent.getStringExtra("title");
        user_id1 = intent.getStringExtra("user_id");
        user_address_detail1 = intent.getStringExtra("user_address_detail");
        arr = getIntent().getByteArrayExtra("image");


        try {
            Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
            PhotoView Bigimage = findViewById(R.id.Bigimage);
            Bigimage.setImageBitmap(image);
        }catch (Exception e){
            e.printStackTrace();
        }

        /*back = findViewById(R.id.button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), user_storereview.class);
                intent.putExtra("user_name",user_name1);
                intent.putExtra("user_address",user_address1);
                intent.putExtra("user_lat",user_lat1);
                intent.putExtra("user_long",user_long1);
                intent.putExtra("user_id",user_id1);
                intent.putExtra("user_address_detail",user_address_detail1);
                intent.putExtra("title",store_name1);
                startActivity(intent);
            }
        });*/

    }
}
package com.example.delivery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static com.example.delivery.MainActivity.rider_address1;
import static com.example.delivery.MainActivity.rider_id1;
import static com.example.delivery.MainActivity.rider_lat1;
import static com.example.delivery.MainActivity.rider_long1;
import static com.example.delivery.MainActivity.rider_name1;


public class layout1_Adpter extends RecyclerView.Adapter<layout1_Adpter.CustomViewHolder> {
    final String TAG = "Connect";

    layout1_Adpter ConnectFTP;

    String result="";
    String newFilePath="";

    private ArrayList<layout1_list> mList = null;
    private Activity context = null;
    private String rider_id;
    /*String var_name = ((user_main1)user_main1.context).var_name;*/





    public layout1_Adpter(Activity context, String rider_id, ArrayList<layout1_list> list) {
        this.context = context;
        this.mList = list;
        this.rider_id = rider_id;

    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView u_address,s_address,items;
        protected TextView price,d_type_tv1;
        protected LinearLayout privatereview;
        protected String u_number,s_number,d_type;
        protected Button getorder;





        public CustomViewHolder(View view) {
            super(view);
            this.u_address = (TextView) view.findViewById(R.id.u_address);
            this.s_address = (TextView) view.findViewById(R.id.s_address);
            this.items = (TextView) view.findViewById(R.id.items);
            this.price = (TextView) view.findViewById(R.id.price);
            this.privatereview = (LinearLayout) view.findViewById(R.id.privatereview);
            this.d_type_tv1 = (TextView) view.findViewById(R.id.d_type);
            this.getorder = (Button) view.findViewById(R.id.getorder);


        }


    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout1_info, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder viewholder, final int position) {
        viewholder.d_type = mList.get(position).getMember_d_type();
        if(viewholder.d_type.equals("0")){
            viewholder.d_type_tv1.setText("픽업");
            viewholder.u_address.setText("고객: "+mList.get(position).getMember_u_address());
            viewholder.s_address.setText("가게: "+mList.get(position).getMember_s_address());
        }
        else{
            viewholder.d_type_tv1.setText("배달");
            viewholder.u_address.setText("가게: "+mList.get(position).getMember_s_address());
            viewholder.s_address.setText("고객: "+mList.get(position).getMember_u_address());
        }
        viewholder.price.setText("비용: "+mList.get(position).getMember_price()+"원");
        viewholder.items.setText("상품: "+mList.get(position).getMember_items());
        viewholder.u_number = mList.get(position).getMember_u_number();
        viewholder.s_number = mList.get(position).getMember_s_number();

        viewholder.getorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                Intent intent = new Intent(context.getApplicationContext(),MainActivity.class);
                                intent.putExtra("rider_name",rider_name1);
                                intent.putExtra("rider_address",rider_address1);
                                intent.putExtra("rider_lat",rider_lat1);
                                intent.putExtra("rider_long",rider_long1);
                                intent.putExtra("rider_id",rider_id1);
                                context.startActivity(intent);
                            }
                            //실패한 경우
                            else{
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 Volley를 이용해서 요청을 함
                layout1_Adpter_db registerRequest = new layout1_Adpter_db(rider_id,mList.get(position).getMember_u_address(),
                        mList.get(position).getMember_s_address(),mList.get(position).getMember_price(),mList.get(position).getMember_items(),
                        mList.get(position).getMember_d_type(),responseListener);
                RequestQueue queue = Volley.newRequestQueue(context.getApplication());
                queue.add(registerRequest);
            }
        });




        /*viewholder.b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), user_order_record2.class);
                intent.putExtra("user_name",user_name);
                intent.putExtra("user_address",user_address);
                intent.putExtra("user_lat",user_lat);
                intent.putExtra("user_long",user_long);
                intent.putExtra("user_id",user_id);
                intent.putExtra("user_address_detail",user_address_detail);
                intent.putExtra("store_name",s_name);
                intent.putExtra("date",date);

                context.startActivity(intent);
            }
        });*/


        //누르면 다음 화면으로
        /*viewholder.privatereview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), see_others_review.class);
                intent.putExtra("user_name",user_name);
                intent.putExtra("user_address",user_address);
                intent.putExtra("user_lat",user_lat);
                intent.putExtra("user_long",user_long);
                intent.putExtra("now_user_id",mList.get(position).getMember_u_id());
                intent.putExtra("user_id",user_id);
                intent.putExtra("user_address_detail",user_address_detail);

                intent.putExtra("title",store_name);

                context.startActivity(intent);
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}


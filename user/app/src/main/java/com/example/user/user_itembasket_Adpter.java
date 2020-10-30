package com.example.user;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class user_itembasket_Adpter extends RecyclerView.Adapter<user_itembasket_Adpter.CustomViewHolder> {

    private ArrayList<user_itembasket_list> mList = null;
    private Activity context = null;
    /*String var_name = ((user_main1)user_main1.context).var_name;*/
    public user_main1 um1 = new user_main1();

    String user_name;
    String user_address;
    Double user_lat;
    Double user_long;
    String user_id;
    String title;
    String user_address_detail;



    public user_itembasket_Adpter(Activity context, ArrayList<user_itembasket_list> list, String user_name, String user_address,
                                Double user_lat, Double user_long, String title, String user_id, String user_address_detail) {
        this.context = context;
        this.mList = list;
        this.user_name = user_name;
        this.user_address = user_address;
        this.user_lat = user_lat;
        this.user_long = user_long;
        this.title = title;
        this.user_id = user_id;
        this.user_address_detail = user_address_detail;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView menu;
        protected TextView price;
        protected LinearLayout layout_get_item;

        protected Button b1,b2;
        protected TextView tv1;


        public CustomViewHolder(View view) {
            super(view);
            this.menu = (TextView) view.findViewById(R.id.item_name);
            this.price = (TextView) view.findViewById(R.id.price);
            this.layout_get_item = (LinearLayout) view.findViewById(R.id.layout_get_item);
            this.b1 = (Button) view.findViewById(R.id.deleteitem);
            this.b2 = (Button) view.findViewById(R.id.putitem);
            this.tv1 = view.findViewById(R.id.count);
        }


    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_itembasket_info, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder viewholder, final int position) {
        final String a,b;
        viewholder.menu.setText(mList.get(position).getMember_menu());
        viewholder.price.setText(mList.get(position).getMember_price()+"원");
        a = mList.get(position).getMember_menu();
        b = mList.get(position).getMember_price();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                    if(success){ //회원등록에 성공한 경우
                        /*Toast.makeText(context.getApplicationContext(),"담기성공",Toast.LENGTH_SHORT).show();*/
                        int count = jsonObject.getInt("count");
                        viewholder.tv1.setText(String.valueOf(count));
                                /*Intent intent = new Intent(user_signup2.this, user_signup3.class);
                                intent.putExtra("user_id",user_id);

                                startActivity(intent);*/
                    }
                    //실패한 경우
                    else{
                        Toast.makeText(context.getApplicationContext(),"중복된 아이디입니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        //서버로 Volley를 이용해서 요청을 함
        user_itempage_temp1_count_db registerRequest = new user_itempage_temp1_count_db(a, Integer.parseInt(b), user_id, title,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
        queue.add(registerRequest);



        /*viewholder.b1.setOnClickListener(new View.OnClickListener() {
            String menu = a;
            int price = Integer.parseInt(b);

            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                *//*Toast.makeText(context.getApplicationContext(),"담기성공",Toast.LENGTH_SHORT).show();*//*
                                int count = jsonObject.getInt("count");
                                viewholder.tv1.setText(String.valueOf(count));
                                *//*Intent intent = new Intent(user_signup2.this, user_signup3.class);
                                intent.putExtra("user_id",user_id);

                                startActivity(intent);*//*
                            }
                            //실패한 경우
                            else{
                                Toast.makeText(context.getApplicationContext(),"중복된 아이디입니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 Volley를 이용해서 요청을 함
                user_itempage_temp1_db registerRequest = new user_itempage_temp1_db(menu, price, user_id, title,responseListener);
                RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
                queue.add(registerRequest);
            }
        });*/

        viewholder.b2.setOnClickListener(new View.OnClickListener() {
            String menu = a;
            int price = Integer.parseInt(b);

            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                /*Toast.makeText(context.getApplicationContext(),"담기성공",Toast.LENGTH_SHORT).show();*/
                                int count = jsonObject.getInt("count");
                                viewholder.tv1.setText(String.valueOf(count));
                                /*Intent intent = new Intent(user_signup2.this, user_signup3.class);
                                intent.putExtra("user_id",user_id);

                                startActivity(intent);*/

                                Intent intent = new Intent(context.getApplicationContext(),user_itembasket.class);
                                intent.putExtra("user_name",user_name);
                                intent.putExtra("user_address",user_address);
                                intent.putExtra("user_lat",user_lat);
                                intent.putExtra("user_long",user_long);
                                intent.putExtra("title",title);
                                intent.putExtra("user_id",user_id);
                                intent.putExtra("user_address_detail",user_address_detail);

                                context.startActivity(intent);
                            }
                            //실패한 경우
                            else{
                                Toast.makeText(context.getApplicationContext(),"중복된 아이디입니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 Volley를 이용해서 요청을 함
                user_itempage_temp1_db registerRequest = new user_itempage_temp1_db(menu, price, user_id, title,responseListener);
                RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
                queue.add(registerRequest);
            }
        });




        viewholder.b1.setOnClickListener(new View.OnClickListener() {
            String menu = a;
            int price = Integer.parseInt(b);

            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                /*Toast.makeText(context.getApplicationContext(),"취소완료",Toast.LENGTH_SHORT).show();*/

                                Intent intent = new Intent(context.getApplicationContext(),user_itembasket.class);
                                intent.putExtra("user_name",user_name);
                                intent.putExtra("user_address",user_address);
                                intent.putExtra("user_lat",user_lat);
                                intent.putExtra("user_long",user_long);
                                intent.putExtra("title",title);
                                intent.putExtra("user_id",user_id);
                                intent.putExtra("user_address_detail",user_address_detail);

                                context.startActivity(intent);
                            }
                            //실패한 경우
                            else{
                                Toast.makeText(context.getApplicationContext(),"중복된 아이디입니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 Volley를 이용해서 요청을 함
                user_itempage_temp2_db registerRequest = new user_itempage_temp2_db(menu, price, user_id, title,responseListener);
                RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
                queue.add(registerRequest);
            }
        });
        /*viewholder.layout_get_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(),ImageActivity.class);

                *//*intent.putExtra("image",Integer.toString(mList.get(position).getImage()));*//*
                intent.putExtra("name",mList.get(position).getMember_menu());
                intent.putExtra("gender",mList.get(position).getMember_price());

                intent.putExtra("user_name",user_name);
                intent.putExtra("user_address",user_address);
                intent.putExtra("user_lat",user_lat);
                intent.putExtra("user_long",user_long);
                intent.putExtra("user_id",user_id);
                intent.putExtra("title",title);
                context.startActivity(intent);
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }





}


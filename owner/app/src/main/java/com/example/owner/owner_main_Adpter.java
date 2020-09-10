package com.example.owner;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class owner_main_Adpter extends RecyclerView.Adapter<owner_main_Adpter.CustomViewHolder> {

    private ArrayList<owner_main1_list> mList = null;
    private Activity context = null;
    /*String var_name = ((user_main1)user_main1.context).var_name;*/


    String owner_name;
    String owner_address;
    Double owner_lat;
    Double owner_long;
    String store_name;
    int yes_no=0;
    String temp_date;
    String state = "0";



    public owner_main_Adpter(Activity context, ArrayList<owner_main1_list> list, String owner_name, String owner_address,
                             Double owner_lat, Double owner_long, String store_name) {
        this.context = context;
        this.mList = list;
        this.owner_name = owner_name;
        this.owner_address = owner_address;
        this.owner_lat = owner_lat;
        this.owner_long = owner_long;
        this.store_name = store_name;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView u_id;
        protected TextView u_pw;
        protected TextView u_address;
        protected LinearLayout intothestore;
        protected Button b1,b2;
        protected View v1,v2;

        public CustomViewHolder(View view) {
            super(view);
            this.u_id = (TextView) view.findViewById(R.id.u_id);
            /*this.u_pw = (TextView) view.findViewById(R.id.u_pw);*/
            this.u_address = (TextView) view.findViewById(R.id.u_address);
            this.intothestore = (LinearLayout) view.findViewById(R.id.intothestore);
            this.b1 = (Button) view.findViewById(R.id.b1);
            this.b2 = (Button) view.findViewById(R.id.b2);
            this.v1 = (View) view.findViewById(R.id.b1);
            this.v2 = (View) view.findViewById(R.id.b2);
        }


    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.human_info_owner, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder viewholder, final int position) {

        viewholder.u_address.setText(mList.get(position).getMember_u_address());
        viewholder.u_id.setText(mList.get(position).getMember_u_id());
        /*viewholder.u_pw.setText(mList.get(position).getMember_u_pw());*/
        viewholder.intothestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(),ImageActivity_owner.class);

                /*intent.putExtra("image",Integer.toString(mList.get(position).getImage()));*/
                intent.putExtra("u_address",mList.get(position).getMember_u_address());
                intent.putExtra("u_id",mList.get(position).getMember_u_id());
                intent.putExtra("u_pw",mList.get(position).getMember_u_pw());

                intent.putExtra("owner_name",owner_name);
                intent.putExtra("owner_address",owner_address);
                intent.putExtra("owner_lat",owner_lat);
                intent.putExtra("owner_long",owner_long);
                intent.putExtra("store_name",store_name);
                context.startActivity(intent);
            }
        });

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                    if(success){ //회원등록에 성공한 경우
                        yes_no = jsonObject.getInt("yes_no");
                        if (yes_no==0){
                            viewholder.b1.setEnabled(true);
                            viewholder.b2.setEnabled(false);
                        }
                        else{
                            viewholder.b1.setEnabled(false);
                        }
                        if(yes_no==2){
                            viewholder.b2.setEnabled(false);
                        }
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
        ImageActivity_owner1_db registerRequest = new ImageActivity_owner1_db(store_name,mList.get(position).getMember_u_id(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
        queue.add(registerRequest);

        viewholder.b1.setOnClickListener(new View.OnClickListener() {
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
                                viewholder.b1.setEnabled(false);
                                viewholder.b2.setEnabled(true);


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
                ImageActivity_owner3_db registerRequest = new ImageActivity_owner3_db(store_name,mList.get(position).getMember_u_id(),
                        mList.get(position).getMember_u_address(), owner_address,temp_date, responseListener);
                RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
                queue.add(registerRequest);
            }
        });

        viewholder.b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                viewholder.b2.setEnabled(false);

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
                //n_o_count, u_address,date,memo1, delivery_check,
                //                        item_list_laundry_list_s_name, user_u_id, yes_no,
                ImageActivity_owner4_db registerRequest = new ImageActivity_owner4_db(store_name,mList.get(position).getMember_u_id(),
                        mList.get(position).getMember_u_address(), owner_address, responseListener);
                RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
                queue.add(registerRequest);
            }
        });


    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }





}

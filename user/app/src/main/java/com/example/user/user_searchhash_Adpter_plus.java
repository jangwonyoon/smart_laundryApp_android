package com.example.user;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class user_searchhash_Adpter_plus extends RecyclerView.Adapter<user_searchhash_Adpter_plus.CustomViewHolder> {

    private ArrayList<user_searchhash_list_plus> mList = null;
    private Activity context = null;
    /*String var_name = ((user_main1)user_main1.context).var_name;*/
    public user_main1 um1 = new user_main1();

    String user_name;
    String user_address;
    Double user_lat;
    Double user_long;
    String user_id;
    String user_address_detail;



    public user_searchhash_Adpter_plus(Activity context, ArrayList<user_searchhash_list_plus> list, String user_name, String user_address,
                                       Double user_lat, Double user_long, String user_id, String user_address_detail) {
        this.context = context;
        this.mList = list;
        this.user_name = user_name;
        this.user_address = user_address;
        this.user_lat = user_lat;
        this.user_long = user_long;
        this.user_id = user_id;
        this.user_address_detail = user_address_detail;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView o_id;
        protected TextView s_name;
        protected TextView o_pw,data2,data3;
        protected LinearLayout intothestore;


        public CustomViewHolder(View view) {
            super(view);
            this.s_name = (TextView) view.findViewById(R.id.s_name);
            /*this.o_id = (TextView) view.findViewById(R.id.o_id);
            this.o_pw = (TextView) view.findViewById(R.id.o_pw);
            this.intothestore = (LinearLayout) view.findViewById(R.id.intothestore);
            this.data2 = view.findViewById(R.id.data2);
            this.data3 = view.findViewById(R.id.data3);*/
        }


    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_searchhash_plus_info, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, final int position) {

        viewholder.s_name.setText(mList.get(position).getMember_s_name());
        /*if(mList.get(position).getMember_o_id().equals("null")){
            viewholder.o_id.setText("★0.0");
        }
        else {
            viewholder.o_id.setText("★" + mList.get(position).getMember_o_id().substring(0, 3));
            *//*viewholder.o_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context.getApplication(), "하하하",Toast.LENGTH_SHORT).show();
                }
            });*//*
        }

        if(mList.get(position).getMember_o_pw().equals("tag1") && mList.get(position).getMember_data2().equals("tag2") &&
                mList.get(position).getMember_data3().equals("tag3")){
                    viewholder.o_pw.setText("");
                    viewholder.data2.setText("");
                    viewholder.data3.setText("");
        }

        viewholder.o_pw.setText(mList.get(position).getMember_o_pw());
        viewholder.data2.setText(mList.get(position).getMember_data2());
        viewholder.data3.setText(mList.get(position).getMember_data3());

        if(mList.get(position).getMember_cd().equals("1")){
            *//*viewholder.intothestore.setBackgroundResource();*//*
            viewholder.s_name.setTextColor(Color.parseColor("#BEBEBE"));
            viewholder.o_id.setTextColor(Color.parseColor("#BEBEBE"));
            viewholder.o_pw.setTextColor(Color.parseColor("#BEBEBE"));
            viewholder.data2.setTextColor(Color.parseColor("#BEBEBE"));
            viewholder.data3.setTextColor(Color.parseColor("#BEBEBE"));
            viewholder.intothestore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context.getApplicationContext(), ImageActivity.class);

                    *//*intent.putExtra("image",Integer.toString(mList.get(position).getImage()));*//*
                    intent.putExtra("title", mList.get(position).getMember_s_name());
                    intent.putExtra("gender", mList.get(position).getMember_o_id());
                    intent.putExtra("age", mList.get(position).getMember_o_pw());

                    intent.putExtra("user_name", user_name);
                    intent.putExtra("user_address", user_address);
                    intent.putExtra("user_lat", user_lat);
                    intent.putExtra("user_long", user_long);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("user_address_detail", user_address_detail);
                    context.startActivity(intent);
                }
            });
        }else {
            viewholder.intothestore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context.getApplicationContext(), ImageActivity.class);

                    *//*intent.putExtra("image",Integer.toString(mList.get(position).getImage()));*//*
                    intent.putExtra("title", mList.get(position).getMember_s_name());
                    intent.putExtra("gender", mList.get(position).getMember_o_id());
                    intent.putExtra("age", mList.get(position).getMember_o_pw());

                    intent.putExtra("user_name", user_name);
                    intent.putExtra("user_address", user_address);
                    intent.putExtra("user_lat", user_lat);
                    intent.putExtra("user_long", user_long);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("user_address_detail", user_address_detail);
                    context.startActivity(intent);
                }
            });
        }*/


    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }





}

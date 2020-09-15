package com.example.owner;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class owner_order_record_Adpter extends RecyclerView.Adapter<owner_order_record_Adpter.CustomViewHolder> {

    private ArrayList<owner_order_record_list> mList = null;
    private Activity context = null;
    /*String var_name = ((user_main1)user_main1.context).var_name;*/

    String owner_name;
    String owner_address;
    Double owner_lat;
    Double owner_long;
    String store_name;



    public owner_order_record_Adpter(Activity context, ArrayList<owner_order_record_list> list, String owner_name, String owner_address,
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
        protected TextView u_address;
        protected TextView date;

        protected Button b1;


        public CustomViewHolder(View view) {
            super(view);
            this.u_id = (TextView) view.findViewById(R.id.u_id);
            this.u_address = (TextView) view.findViewById(R.id.u_address);
            this.date = (TextView) view.findViewById(R.id.date);
            this.b1 = (Button) view.findViewById(R.id.detail);

        }


    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.owner_order_record_info, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, final int position) {
        viewholder.u_id.setText(mList.get(position).getMember_u_id());
        viewholder.u_address.setText(mList.get(position).getMember_u_address());
        viewholder.date.setText("2020"+mList.get(position).getMember_date());

        viewholder.b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), owner_order_record2.class);
                intent.putExtra("owner_name",owner_name);
                intent.putExtra("owner_address",owner_address);
                intent.putExtra("owner_lat",owner_lat);
                intent.putExtra("owner_long",owner_long);
                intent.putExtra("user_id",String.valueOf(mList.get(position).getMember_u_id()));
                intent.putExtra("store_name",store_name);
                intent.putExtra("date",String.valueOf(mList.get(position).getMember_date()));

                context.startActivity(intent);
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


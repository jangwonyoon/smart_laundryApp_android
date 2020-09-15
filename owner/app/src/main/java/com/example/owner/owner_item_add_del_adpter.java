package com.example.owner;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class owner_item_add_del_adpter extends RecyclerView.Adapter<owner_item_add_del_adpter.CustomViewHolder> {

    private ArrayList<owner_item_add_del_list> mList = null;
    private Activity context = null;
    /*String var_name = ((user_main1)user_main1.context).var_name;*/


    String owner_name;
    String owner_address;
    Double owner_lat;
    Double owner_long;
    String store_name;



    public owner_item_add_del_adpter(Activity context, ArrayList<owner_item_add_del_list> list, String owner_name, String owner_address,
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
        protected TextView menu;
        protected TextView price;
        protected LinearLayout layout_get_item;


        public CustomViewHolder(View view) {
            super(view);
            this.menu = (TextView) view.findViewById(R.id.item_name);
            this.price = (TextView) view.findViewById(R.id.price);
            this.layout_get_item = (LinearLayout) view.findViewById(R.id.layout_get_item);
        }


    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_owner_item_add_del_info, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, final int position) {

        viewholder.menu.setText(mList.get(position).getMember_menu());
        viewholder.price.setText(mList.get(position).getMember_price()+"원");
        viewholder.layout_get_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(),owner_item_add_del_up_del.class);

                /*intent.putExtra("image",Integer.toString(mList.get(position).getImage()));*/
                intent.putExtra("menu",mList.get(position).getMember_menu());
                intent.putExtra("price",Integer.parseInt(mList.get(position).getMember_price()));

                intent.putExtra("owner_name",owner_name);
                intent.putExtra("owner_address",owner_address);
                intent.putExtra("owner_lat",owner_lat);
                intent.putExtra("owner_long",owner_long);
                intent.putExtra("store_name",store_name);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }





}

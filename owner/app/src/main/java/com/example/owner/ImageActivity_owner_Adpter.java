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


public class ImageActivity_owner_Adpter extends RecyclerView.Adapter<ImageActivity_owner_Adpter.CustomViewHolder> {

    private ArrayList<ImageActivity_owner_list> mList = null;
    private Activity context = null;
    /*String var_name = ((user_main1)user_main1.context).var_name;*/





    public ImageActivity_owner_Adpter(Activity context, ArrayList<ImageActivity_owner_list> list) {
        this.context = context;
        this.mList = list;

    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView item;

        public CustomViewHolder(View view) {
            super(view);
            this.item = (TextView) view.findViewById(R.id.item);
        }


    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_image_owner_info, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, final int position) {

        viewholder.item.setText(mList.get(position).getMember_item());


    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }





}

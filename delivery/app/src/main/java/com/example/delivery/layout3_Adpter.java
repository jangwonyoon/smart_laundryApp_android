package com.example.delivery;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class layout3_Adpter extends RecyclerView.Adapter<layout3_Adpter.CustomViewHolder> {
    final String TAG = "Connect";

    layout3_Adpter ConnectFTP;

    String result="";
    String newFilePath="";

    private ArrayList<layout3_list> mList = null;
    private Activity context = null;
    private String rider_id;
    /*String var_name = ((user_main1)user_main1.context).var_name;*/





    public layout3_Adpter(Activity context, String rider_id, ArrayList<layout3_list> list) {
        this.context = context;
        this.mList = list;
        this.rider_id = rider_id;

    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView date,s_address,items;
        protected TextView price,d_type_tv1;
        protected LinearLayout privatereview;
        protected String u_number,s_number,d_type;
        protected Button getorder;





        public CustomViewHolder(View view) {
            super(view);
            this.date = (TextView) view.findViewById(R.id.date);
            this.price = (TextView) view.findViewById(R.id.price);
            this.privatereview = (LinearLayout) view.findViewById(R.id.privatereview);
            this.d_type_tv1 = (TextView) view.findViewById(R.id.d_type);


        }


    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout3_info, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder viewholder, final int position) {
        viewholder.d_type = mList.get(position).getMember_d_type();
        if(viewholder.d_type.equals("0")){
            viewholder.d_type_tv1.setText("픽업");
            viewholder.date.setText("날짜: 2020"+mList.get(position).getMember_temp_date());
            viewholder.price.setText("비용: "+mList.get(position).getMember_r_price());
        }
        else{
            viewholder.d_type_tv1.setText("배달");
            viewholder.date.setText("날짜: 2020"+mList.get(position).getMember_temp_date());
            viewholder.price.setText("비용: "+mList.get(position).getMember_r_price());
        }






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


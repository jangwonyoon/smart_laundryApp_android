package com.example.delivery;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.delivery.MainActivity.rider_address1;
import static com.example.delivery.MainActivity.rider_id1;
import static com.example.delivery.MainActivity.rider_lat1;
import static com.example.delivery.MainActivity.rider_long1;
import static com.example.delivery.MainActivity.rider_name1;


public class layout2_Adpter extends RecyclerView.Adapter<layout2_Adpter.CustomViewHolder> {
    final String TAG = "Connect";

    layout2_Adpter ConnectFTP;

    String result="";
    String newFilePath="";

    private ArrayList<layout2_list> mList = null;
    private Activity context = null;
    private String rider_id, rider_name,rider_address;
    private Double rider_lat,rider_long;
    /*String var_name = ((user_main1)user_main1.context).var_name;*/





    public layout2_Adpter(Activity context, String rider_id,String rider_name,String rider_address,Double rider_lat,Double rider_long, ArrayList<layout2_list> list) {
        this.context = context;
        this.mList = list;
        this.rider_id = rider_id;
        this.rider_name = rider_name;
        this.rider_address = rider_address;
        this.rider_lat = rider_lat;
        this.rider_long = rider_long;



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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout2_info, null);
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
        viewholder.privatereview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), layout2_first.class);
                intent.putExtra("rider_id",rider_id);
                intent.putExtra("rider_name",rider_name);
                intent.putExtra("rider_address",rider_address);
                intent.putExtra("rider_lat",rider_lat);
                intent.putExtra("rider_long",rider_long);
                intent.putExtra("s_address",mList.get(position).getMember_s_address());
                intent.putExtra("u_address",mList.get(position).getMember_u_address());
                intent.putExtra("u_number",mList.get(position).getMember_u_number());
                intent.putExtra("s_number",mList.get(position).getMember_s_number());
                intent.putExtra("d_type",mList.get(position).getMember_d_type());

                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}


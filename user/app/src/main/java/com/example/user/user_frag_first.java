package com.example.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class user_frag_first extends Fragment {

    Bitmap image;
    ImageView BigImage;
    TextView main_title;

    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;

    Button user_main1_back, intoitemlist, btn1, intoreview;

    String o_c_time;
    String closed_day;
    String o_name;
    String o_nin;
    String s_address;
    int t_order;
    TextView gongji;

    int img;

    String user_name1, user_address1, user_id1, user_address_detail1;
    Double user_lat1, user_long1;
    String a;

    public user_frag_first() {
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_user_frag_first, container, false);

            Intent intent = getActivity().getIntent();
            user_name1 = intent.getStringExtra("user_name");
            user_address1 = intent.getStringExtra("user_address");
            user_lat1 = intent.getDoubleExtra("user_lat",0.0);
            user_long1 = intent.getDoubleExtra("user_long",0.0);
            user_id1 = intent.getStringExtra("user_id");
            user_address_detail1 = intent.getStringExtra("user_address_detail");


            final String re_set_title = intent.getStringExtra("title");




            tv1 = rootView.findViewById(R.id.o_c_time);
            tv2 = rootView.findViewById(R.id.closed_day);
            tv3 = rootView.findViewById(R.id.t_order);
            tv4 = rootView.findViewById(R.id.o_name);
            tv5 = rootView.findViewById(R.id.s_name);
            tv5.setText(re_set_title);
            tv6 = rootView.findViewById(R.id.s_address);
            tv7 = rootView.findViewById(R.id.o_nin);

            Response.Listener<String> responseListener1 = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                        if(success){ //회원등록에 성공한 경우
                            o_c_time = jsonObject.getString("o_c_time");
                            closed_day = jsonObject.getString("closed_day");
                            t_order = jsonObject.getInt("t_order");
                            o_name = jsonObject.getString("o_name");
                            s_address = jsonObject.getString("s_address");
                            o_nin = jsonObject.getString("o_nin");

                            tv1.setText(o_c_time);
                            tv2.setText(closed_day);
                            tv3.setText(String.valueOf(t_order));
                            tv4.setText(o_name);
                            tv6.setText(s_address);
                            tv7.setText(o_nin);

                        }
                        //실패한 경우
                        else{
                            Toast.makeText(getActivity(),"불러오기 실패",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };
            user_image2_db registerRequest1 = new user_image2_db(re_set_title, responseListener1);
            RequestQueue queue1 = Volley.newRequestQueue(getActivity());
            queue1.add(registerRequest1);

        btn1 = rootView.findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Sharing_intent = new Intent(Intent.ACTION_SEND);
                Sharing_intent.setType("text/plain");

                String Test_Message = re_set_title+" 에 대한 정보보기!";



                /*String a = "https://map.naver.com/v5/search/"+re_set_title+"?c=14104623.8586647,4511836.5253034,15,0,0,0,dh";*/
                String a = "https://map.naver.com/v5/search/"+re_set_title;
                Sharing_intent.putExtra(Intent.EXTRA_TEXT, Test_Message+"\n"+a);

                Intent Sharing = Intent.createChooser(Sharing_intent, "공유하기");
                startActivity(Sharing);
            }
        });




            main_title = rootView.findViewById(R.id.title1);
            main_title.setText(re_set_title);
            /*title = main_title.getText().toString();*/

            user_main1_back = (Button) rootView.findViewById(R.id.layout2_b1);
            user_main1_back.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), user_main1.class);
                    intent.putExtra("user_name",user_name1);
                    intent.putExtra("user_address",user_address1);
                    intent.putExtra("user_lat",user_lat1);
                    intent.putExtra("user_long",user_long1);
                    intent.putExtra("user_id",user_id1);
                    intent.putExtra("user_address_detail",user_address_detail1);
                    startActivity(intent);
                }
            });

            intoitemlist = (Button)rootView.findViewById(R.id.intoitemlist);
            intoitemlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), user_itempage.class);
                    intent.putExtra("user_name",user_name1);
                    intent.putExtra("user_address",user_address1);
                    intent.putExtra("user_lat",user_lat1);
                    intent.putExtra("user_long",user_long1);
                    intent.putExtra("title",main_title.getText().toString());
                    intent.putExtra("user_id",user_id1);
                    intent.putExtra("user_address_detail",user_address_detail1);
                    startActivity(intent);
                }
            });

            /*intoreview = (Button)rootView.findViewById(R.id.intoreview);
            intoreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), user_storereview.class);
                    intent.putExtra("user_name",user_name1);
                    intent.putExtra("user_address",user_address1);
                    intent.putExtra("user_lat",user_lat1);
                    intent.putExtra("user_long",user_long1);
                    intent.putExtra("title",main_title.getText().toString());
                    intent.putExtra("user_id",user_id1);
                    intent.putExtra("user_address_detail",user_address_detail1);
                    startActivity(intent);
                }
            });*/

            gongji = (TextView) rootView.findViewById(R.id.gongjiforuser);
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                        if(success){ //회원등록에 성공한 경우
                            String gongji1 = jsonObject.getString("content");
                            a = gongji1;
                            gongji.setText(a);
                        }
                        //실패한 경우
                        else{
                            Toast.makeText(getActivity(),"사장님공지가 아직 없습니다!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };

            //서버로 Volley를 이용해서 요청을 함
            user_image_db registerRequest = new user_image_db(re_set_title, responseListener);
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(registerRequest);


            /*ImageView store_pic = (ImageView)findViewById(R.id.image1);*/
            /*TextView store_name = (TextView)findViewById(R.id.title1);*/
        /*TextView store_age = (TextView)findViewById(R.id.age1);
        TextView store_gender = (TextView)findViewById(R.id.gender1);*/

            /*store_name.setText(intent.getStringExtra("name"));*/
        /*img=Integer.parseInt(intent.getStringExtra("image"));
        store_pic.setImageResource(img);
        store_age.setText(intent.getStringExtra("age"));
        store_gender.setText(intent.getStringExtra("gender"));*/


        /*BigImage = (ImageView)findViewById(R.id.BigImage);

        Intent intent = getIntent();
        byte[] arr = getIntent().getByteArrayExtra("image");
        image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        BigImage.setImageBitmap(image);*/





        return rootView;
        //xml 레이아웃이 인플레이트 되고 자바소스 코드와 연결이된다.
    }

}
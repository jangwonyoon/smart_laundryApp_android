package com.example.user;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class user_forpay_db extends StringRequest {
    //서버 URL 설정
    final static private String URL = "http://edit0.dothome.co.kr/user_forpay_db.php";
    private Map<String, String> map;

    public user_forpay_db(int n_o_count, String u_address, int date, String memo, int delivery_check,
                          String item_list_laundry_list_s_name, String user_u_id, int yes_no, Double user_lat, Double user_long, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();

        map.put("n_o_count",n_o_count+"");
        map.put("u_address",u_address);
        map.put("date",date+"");
        map.put("memo",memo);
        map.put("delivery_check",delivery_check+"");
        map.put("item_list_laundry_list_s_name",item_list_laundry_list_s_name);
        map.put("user_u_id",user_u_id);
        map.put("yes_no",yes_no+"");
        map.put("user_lat",user_lat+"");
        map.put("user_long",user_long+"");


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

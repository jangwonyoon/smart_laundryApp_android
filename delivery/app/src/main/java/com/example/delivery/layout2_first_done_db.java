package com.example.delivery;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class layout2_first_done_db extends StringRequest {
    //서버 URL 설정
    final static private String URL = "http://edit0.dothome.co.kr/layout2_first_done_db.php";
    private Map<String, String> map;

    public layout2_first_done_db(String rider_id, String u_address, String s_address,String temp_date, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();

        map.put("u_address",u_address);
        map.put("rider_id",rider_id);
        map.put("s_address",s_address);
        map.put("temp_date",temp_date);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

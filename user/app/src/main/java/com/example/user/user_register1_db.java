package com.example.user;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class user_register1_db extends StringRequest {
    //서버 URL 설정
    final static private String URL = "http://edit0.dothome.co.kr/user_register.php";
    private Map<String, String> map;

    public user_register1_db(String user_name, String user_id, String user_password, String user_email, String user_address,
                             String user_number, Double user_lat, Double user_long, String user_address_detail, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();

        map.put("user_name",user_name);
        map.put("user_id",user_id);
        map.put("user_password",user_password);
        map.put("user_email",user_email);
        map.put("user_address",user_address);
        map.put("user_number",user_number);
        map.put("user_lat",user_lat+"");
        map.put("user_long",user_long+"");
        map.put("user_address_detail",user_address_detail);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

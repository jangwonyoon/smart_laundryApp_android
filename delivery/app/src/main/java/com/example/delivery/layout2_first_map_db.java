package com.example.delivery;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class layout2_first_map_db extends StringRequest {
    //서버 URL 설정
    final static private String URL = "http://edit0.dothome.co.kr/layout2_first_map_db.php";
    private Map<String, String> map;

    public layout2_first_map_db(String d_type, String u_number, String u_address, String s_number, String s_address, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();

        map.put("d_type",d_type);
        map.put("u_address",u_address);
        map.put("u_number",u_number);
        map.put("s_address",s_address);
        map.put("s_number",s_number);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

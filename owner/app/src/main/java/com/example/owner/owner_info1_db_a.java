package com.example.owner;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class owner_info1_db_a extends StringRequest {
    //서버 URL 설정
    final static private String URL = "http://edit0.dothome.co.kr/owner_info1_db_a.php";
    private Map<String, String> map;

    public owner_info1_db_a(String store_name,String o_c_time,String a, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();

        map.put("o_c_time",o_c_time);
        map.put("a",a);
        map.put("store_name",store_name);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

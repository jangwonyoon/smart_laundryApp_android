package com.example.owner;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class owner_register2_db extends StringRequest {
    //서버 URL 설정
    final static private String URL = "http://edit0.dothome.co.kr/owner_register_location.php";
    private Map<String, String> map;

    public owner_register2_db(Double owner_lat, Double owner_long, String owner_address, String owner_nin, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();

        map.put("owner_lat",owner_lat+"");
        map.put("owner_long",owner_long+"");
        map.put("owner_address",owner_address);
        map.put("owner_nin",owner_nin);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

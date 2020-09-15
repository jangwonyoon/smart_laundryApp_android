package com.example.owner;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class owner_register3_db extends StringRequest {
    //서버 URL 설정
    final static private String URL = "http://edit0.dothome.co.kr/owner_register_add.php";
    private Map<String, String> map;

    public owner_register3_db(String owner_store_name, String owner_name, String owner_id, int owner_number, Double owner_lat, Double owner_long,
                              String owner_address, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();

        map.put("owner_id",owner_id);
        map.put("owner_name",owner_name);
        map.put("owner_number",owner_number+"");
        map.put("owner_store_name",owner_store_name);
        map.put("owner_address",owner_address);
        map.put("owner_lat",owner_lat+"");
        map.put("owner_long",owner_long+"");


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
